<?php

namespace VisiteurBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;

class VisiteurController extends Controller
{
    public function indexAction(){
        return $this->render('@Visiteur/home/home_visiteur.html.twig');
    }
}
