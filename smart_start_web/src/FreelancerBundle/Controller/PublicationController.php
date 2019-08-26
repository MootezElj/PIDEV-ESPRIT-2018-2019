<?php

namespace FreelancerBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;

class PublicationController extends Controller
{
    public function showPublicationAction(){
        return $this->render('@Freelancer/gestion_Publication/Show.html.twig');
    }

    public function  addPublicationAction(){
        return $this->render('@Freelancer/gestion_Publication/add_publication.html.twig');
    }
}
