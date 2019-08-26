<?php

namespace AdminBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;

class AdminController extends Controller
{
    public function indexAction(){
        return $this->render('@Admin/dashboard/dashboard_admin.html.twig');
    }
}
