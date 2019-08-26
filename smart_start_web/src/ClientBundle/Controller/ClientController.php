<?php

namespace ClientBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\File\File;
use Symfony\Component\HttpFoundation\Request;

class ClientController extends Controller
{
    public function indexAction(){
        $client=$this->getUser();
        return $this->render('@Client/home/home_client.html.twig',
            array(
                'client'=>$client
            ));
    }

    public function profilAction(){
        return $this->render('@Client/profil/profil_client.html.twig');
    }


    //*****PROJET
    //Ajoute un projet a un client
    public function ajoutProjetAction(){

        return $this->render('@Client/gestion_projet/ajoutProjet_client.html.twig' );


    }

    //La liste de projet d'un client
    public function listeProjetAction(){
        return $this->render('@Client/gestion_projet/listeProjet_client.html.twig');
    }

    //La liste de projet avec la possibiliré de modification
    public function modifierProjetAction(){
        return $this->render('@Client/gestion_projet/modifierProjet_client.html.twig');
    }
    //FIN ***** PROJET

    //*****OFFRE
    //Ajoute un offre a un client
    public function ajoutOffreAction(){
        return $this->render('@Client/gestion_offre/ajoutOffre_client.html.twig');
    }

    //La liste de offre d'un client
    public function listeOffreAction(){
        return $this->render('@Client/gestion_offre/listeOffre_client.html.twig');
    }

    //La liste de offre avec la possibiliré de modification
    public function modifierOffreAction(){
        return $this->render('@Client/gestion_offre/modifierOffre_client.html.twig');
    }
    //FIN ***** OFFRE

    //*****CALENDRIER
    //Ajoute un offre a un client
    public function calendrierAction(){
        return $this->render('@Client/gestion_calendrier/calendrier.html.twig');
    }
    //FIN ***** CALENDRIER

    //*****NOTIFICATION
    //Ajoute un offre a un client
    public function notificationAction(){
        return $this->render('@Client/notification/notification_client.html.twig');
    }
    //FIN ***** NOTIFICATION


    //*****FEEDBACK
    //Ajoute un feedback a un client
    public function ajoutFeedbackFormAction(Request $request){


        return $this->render('@Client/gestion_feedback/ajoutFeedback.html.twig',array(

            'isNew'=>true
        ));
    }

    //Action de click du submit du formulaire
    public function ajoutFeedbackValidationAction(Request $request){

        return $this->redirectToRoute('client_homepage');
    }

    //Liste de toute les feedback
    public function listeToutFeedbackAction()
    {   //Fetching Objects (modeles) from the Database
        $feedbacks=$this->getDoctrine()->getRepository(Feedback::class)->findAll();
        //add the list of modeles to the render function as input to be sent to the view
        return $this->render('@Client/gestion_feedback/listeFeedback.html.twig', array(
            'feedbacks'=>$feedbacks
        ));
    }

    //Cette methode retourne la liste de feedback d'un client ou freelancer
    public function listeFeedbackClientAction(Request $request)
    {
        $em=$this->getDoctrine()->getManager();
        $client = $em->getRepository(Client::class)->find(1);
        $feedbacks = $client->getFeedbacks();
        //add the list of modeles to the render function as input to be sent to the view
        return $this->render('@Client/gestion_feedback/listeFeedback.html.twig', array(
            'feedbacks'=>$feedbacks,
            'client'=>$client
        ));
    }

    //Cette action permet de lister tout les feedback d'un user et donne la possibilité de modification
    public function listeFeedbackModiferAction(Request $request)
    {
        $em=$this->getDoctrine()->getManager();
        $client = $em->getRepository(Client::class)->find(1);
        $feedbacks = $client->getFeedbacks();
        //add the list of modeles to the render function as input to be sent to the view
        return $this->render('@Client/gestion_feedback/listeFeedbackUpdate.html.twig', array(
            'feedbacks'=>$feedbacks,
            'client'=>$client
        ));
    }

    //Cette action permet de modifier un feedback
    public function OnModifierFeedbackAction(Request $request, $id)
    {
        //get the feedback with the id
        $em=$this->getDoctrine()->getManager();
        $feedback = $em->getRepository(Feedback::class)->find($id);
        if ($request->isMethod('POST')) {
            //update our object given the sent data in the request
            $feedback->setNote($request->get('_note'));
            $feedback->setTitre($request->get('_titre'));
            $feedback->setDescription($request->get('_description'));
            $feedback->setDate(new \DateTime());
            $em->flush();
            return $this->redirectToRoute('client_listeFeedbackClientSpecefique_client');
        }
        return $this->render('@Client/gestion_feedback/ajoutFeedback.html.twig', array(
            'feedback' => $feedback,
            'isNew' => false,
        ));
    }

    //Cette action permet de supprimer un feedback
    public function OnsupprimerFeedbackAction(Request $request, $id)
    {
        //get the object to be removed given the submitted id
        $em = $this->getDoctrine()->getManager();
        $feedback= $em->getRepository(Feedback::class)->find($id);
        //remove from the ORM
        $em->remove($feedback);
        //update the data base
        $em->flush();
        return $this->redirectToRoute("client_listeFeedbackClientSpecefique");
    }
    //FIN ***** FEEDBACK







}
