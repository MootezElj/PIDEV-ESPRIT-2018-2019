<?php

namespace OffreBundle\Controller;

use AppBundle\Entity\User;
use OffreBundle\Entity\Projet;
use OffreBundle\Entity\Technologie;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Component\HttpFoundation\Request;
use OffreBundle\Entity\freelancerDemandeProjet;

/**
 * Projet controller.
 *
 * @Route("projet")
 */
class ProjetController extends Controller
{
    /**
     * Lists all projet entities.
     *
     * @Route("/liste", name="projet_index")
     */
    public function indexAction()
    {
        $authchecker = $this->container->get('security.authorization_checker');
        if ($authchecker->isGranted('ROLE_CLIENT')) {
            $client = $this->getUser();
            $projets = $client->getprojetsClient();
            return $this->render('@Offre/projet/index.html.twig', array(
                'projets' => $projets,
                'client' => $client,

            ));
        }
        else{
            return $this->redirectToRoute('fos_user_security_login');
        }
    }

    /**
     * Creates a new projet entity.
     *
     * @Route("/new", name="projet_new")
     * @Method({"POST", "POST"})
     */
    public function newAction(Request $request)
    {
        $authchecker = $this->container->get('security.authorization_checker');
        if ($authchecker->isGranted('ROLE_CLIENT')) {
            //Reception de lutilisateur
            $client = $this->getUser();
            $em = $this->getDoctrine()->getManager();
            //reception de tous les techs de la base
            $technologies = $em->getRepository(Technologie::class)->findAll();
            $projet = new Projet();
            //affectation du projet au client connecté
            $projet->setClient($client);
            $form = $this->createForm('OffreBundle\Form\ProjetType', $projet);
            $form->handleRequest($request);
            if ($form->isSubmitted() && $form->isValid()) {
                $techsId = $request->get("checkedTechs");
                if ($techsId)
                    foreach ($techsId as $tech) {
                        $technologie = $em->getRepository(Technologie::class)->find($tech);
                        $projet->addTechnologie($technologie);
                    }
                $time = new \DateTime();
                $projet->setDateCreation($time);
                $projet->setSignale(false);
                $projet->setPublie(false);
                $em = $this->getDoctrine()->getManager();
                $em->persist($projet);
                $em->flush();
                return $this->redirectToRoute('projet_show', array(
                    'id' => $projet->getId(),
                    'client' => $client
                ));
            }
            return $this->render('@Offre/projet/new.html.twig', array(
                'projet' => $projet,
                'client' => $client,
                'form' => $form->createView(),
                'technologies' => $technologies,
            ));
        }
        else{
            return $this->redirectToRoute('fos_user_security_login');
        }

    }


    /**
     * Creates a new projet entity.
     *
     * @Route("/{id}/edit", name="projet_edit")
     * @Method({"GET", "POST"})
     */
    public function EditAction(Request $request)
    {
        $authchecker = $this->container->get('security.authorization_checker');
        if ($authchecker->isGranted('ROLE_CLIENT')) {
            $client = $this->getUser();
            $em = $this->getDoctrine()->getManager();
            $idProjet = $request->attributes->get('id');
            $projet = $em->getRepository(Projet::class)->find($idProjet);
            //reception de tous les techs de la base
            $technologies = $em->getRepository(Technologie::class)->findAll();
            $form = $this->createForm('OffreBundle\Form\ProjetType', $projet);
            $form->handleRequest($request);
            if ($form->isSubmitted() && $form->isValid()) {
                $techsId = $request->get("checkedTechs");
                $allTech = $projet->getTechnologies();
                foreach ($allTech as $tech) {
                    $projet->removeTechnologie($tech);
                }

                if ($techsId) {
                    foreach ($techsId as $tech) {
                        $technologie = $em->getRepository(Technologie::class)->find($tech);
                        $projet->addTechnologie($technologie);
                    }
                }
                $em->persist($projet);
                $em->flush();
                return $this->redirectToRoute('projet_show', array('id' => $projet->getId(),
                    'client' => $client
                ));
            }
            return $this->render('@Offre/projet/new.html.twig', array(
                'projet' => $projet,
                'client' => $client,
                'form' => $form->createView(),
                'technologies' => $technologies,
            ));
        }
        else{
            return $this->redirectToRoute('fos_user_security_login');
        }
    }

    /**
     * Finds and displays a projet entity.
     *
     * @Route("/{id}", name="projet_show")
     * @Method("GET")
     */
    public function showAction(Projet $projet)
    {
        $authchecker = $this->container->get('security.authorization_checker');
        if ($authchecker->isGranted('ROLE_CLIENT')) {
            $client = $this->getUser();
            return $this->render('@Offre/projet/show.html.twig', array(
                'projet' => $projet,
                'client' => $client
            ));
        }
        else{
            return $this->redirectToRoute('fos_user_security_login');
        }
    }

    /**
     * Creates a new projet entity.
     *
     * @Route("/{id}/delete", name="projet_deletee")
     * @Method({"GET", "POST"})
     */
    public function deleteAction(Request $request)
    {
        $authchecker = $this->container->get('security.authorization_checker');
        if ($authchecker->isGranted('ROLE_CLIENT')) {
            $client = $this->getUser();

            $em = $this->getDoctrine()->getManager();
            $idProjet = $request->attributes->get('id');
            $projet = $em->getRepository(Projet::class)->find($idProjet);
            $demandes=$em->getRepository(FreelancerDemandeProjet::class)
                ->findBy(array('projet'=>$idProjet));
            foreach ($demandes as $demande){
                $em->remove($demande);
            }
            $em->flush();
            foreach ($projet->getDemandes() as $demande){
                $projet->removeDemande($demande);
            }
            $em->remove($projet);
            $em->flush();
            return $this->redirectToRoute('projet_index', array(
                'client' => $client
            ));
        }
        else{
            return $this->redirectToRoute('fos_user_security_login');
        }
    }


    /**
     * publie un projet.
     *
     * @Route("/{id}/post", name="projet_post")
     * @Method({"GET", "POST"})
     */
    public function postAction(Request $request)
    {


        $authchecker = $this->container->get('security.authorization_checker');
        if ($authchecker->isGranted('ROLE_CLIENT')) {
            $client = $this->getUser();
            $em = $this->getDoctrine()->getManager();
            $idProjet = $request->attributes->get('id');
            $projet = $em->getRepository(Projet::class)->find($idProjet);
            $projet->setpublie(true);
            $projet->setdatePublication(new \DateTime());
            $em->persist($projet);
            $em->flush();
            return $this->redirectToRoute('projet_index', array(
                'client' => $client));

        }
        else{
            return $this->redirectToRoute('fos_user_security_login');
        }
    }


    //La partie du freelancer


}
