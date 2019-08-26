<?php

namespace OffreBundle\Controller;

use OffreBundle\Entity\Technologie;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;use Symfony\Component\HttpFoundation\Request;

/**
 * Technologie controller.
 *
 * @Route("technologie")
 */
class TechnologieController extends Controller
{
    /**
     * Lists all technologie entities.
     *
     * @Route("/", name="technologie_index")
     * @Method("GET")
     */
    public function indexAction()
    {
        $em = $this->getDoctrine()->getManager();

        $technologies = $em->getRepository('OffreBundle:Technologie')->findAll();

        return $this->render('technologie/index.html.twig', array(
            'technologies' => $technologies,
        ));
    }

    /**
     * Creates a new technologie entity.
     *
     * @Route("/new", name="technologie_new")
     * @Method({"GET", "POST"})
     */
    public function newAction(Request $request)
    {
        $technologie = new Technologie();
        $form = $this->createForm('OffreBundle\Form\TechnologieType', $technologie);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($technologie);
            $em->flush();

            return $this->redirectToRoute('technologie_show', array('id' => $technologie->getId()));
        }

        return $this->render('technologie/new.html.twig', array(
            'technologie' => $technologie,
            'form' => $form->createView(),
        ));
    }

    /**
     * Finds and displays a technologie entity.
     *
     * @Route("/{id}", name="technologie_show")
     * @Method("GET")
     */
    public function showAction(Technologie $technologie)
    {
        $deleteForm = $this->createDeleteForm($technologie);

        return $this->render('technologie/show.html.twig', array(
            'technologie' => $technologie,
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Displays a form to edit an existing technologie entity.
     *
     * @Route("/{id}/edit", name="technologie_edit")
     * @Method({"GET", "POST"})
     */
    public function editAction(Request $request, Technologie $technologie)
    {
        $deleteForm = $this->createDeleteForm($technologie);
        $editForm = $this->createForm('OffreBundle\Form\TechnologieType', $technologie);
        $editForm->handleRequest($request);

        if ($editForm->isSubmitted() && $editForm->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('technologie_edit', array('id' => $technologie->getId()));
        }

        return $this->render('technologie/edit.html.twig', array(
            'technologie' => $technologie,
            'edit_form' => $editForm->createView(),
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Deletes a technologie entity.
     *
     * @Route("/{id}", name="technologie_delete")
     * @Method("DELETE")
     */
    public function deleteAction(Request $request, Technologie $technologie)
    {
        $form = $this->createDeleteForm($technologie);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->remove($technologie);
            $em->flush();
        }

        return $this->redirectToRoute('technologie_index');
    }

    /**
     * Creates a form to delete a technologie entity.
     *
     * @param Technologie $technologie The technologie entity
     *
     * @return \Symfony\Component\Form\Form The form
     */
    private function createDeleteForm(Technologie $technologie)
    {
        return $this->createFormBuilder()
            ->setAction($this->generateUrl('technologie_delete', array('id' => $technologie->getId())))
            ->setMethod('DELETE')
            ->getForm()
        ;
    }
}
