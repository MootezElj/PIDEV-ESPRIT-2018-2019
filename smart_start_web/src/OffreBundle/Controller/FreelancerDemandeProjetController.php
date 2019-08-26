<?php

namespace OffreBundle\Controller;

use OffreBundle\Entity\FreelancerDemandeProjet;
use OffreBundle\Entity\Projet;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;

/**
 * Freelancerdemandeprojet controller.
 *
 * @Route("offres")
 */
class FreelancerDemandeProjetController extends Controller
{
    /**
     * Lists all freelancerDemandeProjet entities.
     *
     * @Route("/", name="postedProjectList" ,methods={"GET"})
     */
    public function allPostedProjectAction(Request $request)
    {
        $authchecker = $this->container->get('security.authorization_checker');
        if ($authchecker->isGranted('ROLE_FREELANCER')) {
            $freelancer = $this->getUser();
            $role = "freelancer";
            $em = $this->getDoctrine()->getManager();
            $projets = $em->getRepository(Projet::class)->findBy(array('publie' => true));
            return $this->render('@Offre/freelancerdemandeprojet/listProjetPublieFreelancer.html.twig', array(
                'projets' => $projets,
                'role' => $role,
                'freelancer' => $freelancer
            ));


        } else if ($authchecker->isGranted('ROLE_CLIENT')) {
            $role = "client";
            $client = $this->getUser();
            $em = $this->getDoctrine()->getManager();
            $projets = $em->getRepository(Projet::class)->findBy(array('publie' => true));
            return $this->render('@Offre/freelancerdemandeprojet/listProjetPublieClient.html.twig', array(
                'projets' => $projets,
                'role' => $role,
                'client' => $client
            ));
        } else {
            $em = $this->getDoctrine()->getManager();
            $projets = $em->getRepository(Projet::class)->findBy(array('publie' => true));
            return $this->render('@Offre/freelancerdemandeprojet/listProjetPublieVisiteur.html.twig', array(
                'projets' => $projets,
            ));

        }

    }


    /**
     * Creates a new freelancerDemandeProjet entity.
     *
     * @Route("/new/{id}", name="freelancerdemandeprojet_new", methods={"GET", "POST"})
     */
    public function newAction(Request $request)
    {
        $authchecker = $this->container->get('security.authorization_checker');
        if ($authchecker->isGranted('ROLE_FREELANCER')) {
            $freelancer = $this->getUser();
            $em = $this->getDoctrine()->getManager();
            $idProjet = $request->attributes->get('id');
            $projet = $em->getRepository(Projet::class)->find($idProjet);
            $freelancerDemandeProjet = new Freelancerdemandeprojet();
            $form = $this->createForm('OffreBundle\Form\FreelancerDemandeProjetType', $freelancerDemandeProjet);
            $form->handleRequest($request);
            if ($form->isSubmitted() && $form->isValid()) {
                $freelancerDemandeProjet->setProjet($projet);
                $freelancerDemandeProjet->setValide(true);
                $freelancerDemandeProjet->setSignale(false);
                $freelancerDemandeProjet->setFreelancer($freelancer);
                $freelancerDemandeProjet->setDateDemande(new \DateTime());
                $em->persist($freelancerDemandeProjet);
                $em->flush();
                $this->addFlash('success', 'Votre demande a été ajouté');
                return $this->redirectToRoute('freelancerdemandeprojet_show', array(
                    'id' => $freelancerDemandeProjet->getId(),
                ));
            }
            if ($form->isSubmitted())
                $this->addFlash('error', 'Champ invalide');
            return $this->render('@Offre/freelancerdemandeprojet/new.html.twig', array(
                'freelancerDemandeProjet' => $freelancerDemandeProjet,
                'form' => $form->createView(),
                'projet' => $projet,
                'freelancer' => $freelancer
            ));
        }
        else{
            return $this->redirectToRoute('fos_user_security_login');
        }
    }

    /**
     * Finds and displays a freelancerDemandeProjet entity.
     *
     * @Route("/{id}", name="freelancerdemandeprojet_show", methods={"GET"})
     */
    public function showAction(FreelancerDemandeProjet $freelancerDemandeProjet)
    {
        $deleteForm = $this->createDeleteForm($freelancerDemandeProjet);
        $freelancer = $this->getUser();
        return $this->render('@Offre/freelancerdemandeprojet/show.html.twig', array(
            'freelancerDemandeProjet' => $freelancerDemandeProjet,
            'freelancer' => $freelancer,
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Displays a form to edit an existing freelancerDemandeProjet entity.
     *
     * @Route("/{id}/edit", name="freelancerdemandeprojet_edit",methods={"GET", "POST"})
     */
    public function editAction(Request $request)
    {
        $authchecker = $this->container->get('security.authorization_checker');
        if ($authchecker->isGranted('ROLE_FREELANCER')) {
        $em = $this->getDoctrine()->getManager();
        $freelancer = $this->getUser();
        $idDemande = $request->attributes->get('id');
        $freelancerDemandeProjet = $em->getRepository(FreelancerDemandeProjet::class)->find($idDemande);
        $projet = $freelancerDemandeProjet->getProjet();
        $editForm = $this->createForm('OffreBundle\Form\FreelancerDemandeProjetType', $freelancerDemandeProjet);
        $editForm->handleRequest($request);

        if ($editForm->isSubmitted() && $editForm->isValid()) {
            $em->flush();
            $this->addFlash('success', 'Votre demande a été ajouté');
            return $this->redirectToRoute('freelancerdemandeprojet_show', array('id' => $freelancerDemandeProjet->getId()));
        }

        if ($editForm->isSubmitted())
            $this->addFlash('error', 'Champ invalide');
        return $this->render('@Offre/freelancerdemandeprojet/new.html.twig', array(
            'freelancerDemandeProjet' => $freelancerDemandeProjet,
            'form' => $editForm->createView(),
            'projet' => $projet,
            'freelancer' => $freelancer
        ));
    }
        else{
            return $this->redirectToRoute('fos_user_security_login');
        }
    }


    public function listeDemandeAction($id)
    {
        $em = $this->getDoctrine()->getManager();
        $projet = $em->getRepository(Projet::class)->find($id);
        $demandes = $projet->getDemandes();
        $authchecker = $this->container->get('security.authorization_checker');
        if ($authchecker->isGranted('ROLE_FREELANCER')) {
            $freelancer = $this->getUser();
            return $this->render('@Offre/freelancerdemandeprojet/listeDemandesFreelancer.html.twig', array(
                'freelancer' => $freelancer,
                'projet' => $projet,
                'demandes' => $demandes
            ));
        } else if ($authchecker->isGranted('ROLE_CLIENT')) {
            $client = $this->getUser();
            return $this->render('@Offre/freelancerdemandeprojet/listeDemandesClient.html.twig', array(
                'client' => $client,
                'projet' => $projet,
                'demandes' => $demandes
            ));
        } else {
            return $this->render('@Offre/freelancerdemandeprojet/listeDemandesVisiteur.html.twig', array(
                'projet' => $projet,
                'demandes' => $demandes
            ));
        }
    }


    //affiche la liste de demandes d'un freelancer avec la possibilité de suppression et modification
    public function mesDemandesAction(Request $request)
    {
        $authchecker = $this->container->get('security.authorization_checker');
        if ($authchecker->isGranted('ROLE_FREELANCER')) {
            $freelancer = $this->getUser();
            $demandes = $freelancer->getdemandes();
            return $this->render('@Offre/freelancerdemandeprojet/listeDemandesModifiable.html.twig', array(
                'demandes' => $demandes,
                'freelancer' => $freelancer
            ));
        }
        else{
           return $this->redirectToRoute('fos_user_security_login');
        }
    }


    /**
     * Deletes a freelancerDemandeProjet entity.
     *
     * @Route("/{id}", name="freelancerdemandeprojet_delete", methods={"DELETE"})
     */
    public function deleteAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        $idDemande = $request->attributes->get('id');
        $demande=$em->getRepository(FreelancerDemandeProjet::class)->find($idDemande);
        $em->remove($demande);
        $em->flush();
        return $this->redirectToRoute('liste_mesDemandes');
    }

    /**
     * Creates a form to delete a freelancerDemandeProjet entity.
     *
     * @param FreelancerDemandeProjet $freelancerDemandeProjet The freelancerDemandeProjet entity
     *
     * @return \Symfony\Component\Form\Form The form
     */
    private function createDeleteForm(FreelancerDemandeProjet $freelancerDemandeProjet)
    {
        return $this->createFormBuilder()
            ->setAction($this->generateUrl('freelancerdemandeprojet_delete', array('id' => $freelancerDemandeProjet->getId())))
            ->setMethod('DELETE')
            ->getForm();
    }


}
