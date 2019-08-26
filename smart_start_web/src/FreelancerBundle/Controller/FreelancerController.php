<?php

namespace FreelancerBundle\Controller;

use FreelancerBundle\Entity\Freelancer;
use FreelancerBundle\Entity\MesProjets;
use FreelancerBundle\Form\MesProjetsType;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;

class FreelancerController extends Controller
{
    public function indexAction(){
        $freelancer = $this->getUser();
        return $this->render('@Freelancer/home/home_freelancer.html.twig');
    }
    public function ajoutProjetAction(){

              return $this->render('@Freelancer/gestion_projet/ajoutProjet_freelancer.html.twig' );
    }

    public function onAjouteAction(Request $request){
        $em=$this->getDoctrine()->getManager();
        $freelancer=$em->getRepository(Freelancer::class)->find(1);
        $mesProjets = new MesProjets();
        $mesProjets->setNomProjet($request->request->get('_Nom_Projet'));
        $mesProjets->setTechnologie($request->request->get('_Technologie'));
        $mesProjets->setLiengit($request->request->get('_Lien_Git'));
        $mesProjets->setDescription($request->request->get('_Description'));
        $mesProjets->setFreelancer($freelancer);
        $em->persist($mesProjets);
        $em->flush();
        return $this->redirectToRoute('freelancer_homepage');
    }
    public function consulterProjetAction(){

        return $this->render('@Freelancer/gestion_projet/consulterProjet_freelancer.html.twig' );
    }
    public function consulterOffreAction(){

        return $this->render('@Freelancer/gestion_offre/consulterOffre_freelancer.html.twig' );
    }
    public function consulterDemandeAction(){

        return $this->render('@Freelancer/gestion_demande/consulterDemande_freelancer.html.twig' );
    }
    public function profilAction(){

        return $this->render('@Freelancer/profil/profil_freelancer.html.twig' );
    }
}
