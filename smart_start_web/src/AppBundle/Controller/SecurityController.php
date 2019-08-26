<?php

namespace AppBundle\Controller;

use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;

class SecurityController extends Controller
{

    /**
     * @Route("/",name="homeCheck")
     */
    public function RedirectAction(){

        $authchecker=$this->container->get('security.authorization_checker');
        $user=$this->getUser();
        if ($authchecker->isGranted('ROLE_ADMIN') ){
            return $this->render('@Admin/base_admin.html.twig',array(
            'admin'=>$user
            ));}
        else if ($authchecker->isGranted('ROLE_FREELANCER') ){

            return $this->render('@Freelancer/home/home_freelancer.html.twig',array(
                'freelancer'=>$user
            ));
        }
        else if ($authchecker->isGranted('ROLE_CLIENT') ){
            return $this->render('@Client/home/home_client.html.twig',array(
                "client"=>$user
            ));
        }
        else
            return $this->render('@Visiteur/home/home_visiteur.html.twig');
    }
}
