<?php

namespace OutilBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;

class DefaultController extends Controller
{
    public function indexAction()
    {
        return $this->render('OutilBundle:Default:index.html.twig');
    }
}
