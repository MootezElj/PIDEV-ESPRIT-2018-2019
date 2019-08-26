<?php

namespace OffreBundle\Controller;

use OffreBundle\Entity\CategorieTechnologie;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;use Symfony\Component\HttpFoundation\Request;

/**
 * Categorietechnologie controller.
 *
 * @Route("categorietechnologie")
 */
class CategorieTechnologieController extends Controller
{
    /**
     * Lists all categorieTechnologie entities.
     *
     * @Route("/", name="categorietechnologie_index")
     * @Method("GET")
     */
    public function indexAction()
    {
        $em = $this->getDoctrine()->getManager();

        $categorieTechnologies = $em->getRepository('OffreBundle:CategorieTechnologie')->findAll();

        return $this->render('categorietechnologie/index.html.twig', array(
            'categorieTechnologies' => $categorieTechnologies,
        ));
    }

    /**
     * Creates a new categorieTechnologie entity.
     *
     * @Route("/new", name="categorietechnologie_new")
     * @Method({"GET", "POST"})
     */
    public function newAction(Request $request)
    {
        $categorieTechnologie = new Categorietechnologie();
        $form = $this->createForm('OffreBundle\Form\CategorieTechnologieType', $categorieTechnologie);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($categorieTechnologie);
            $em->flush();

            return $this->redirectToRoute('categorietechnologie_show', array('id' => $categorieTechnologie->getId()));
        }

        return $this->render('categorietechnologie/new.html.twig', array(
            'categorieTechnologie' => $categorieTechnologie,
            'form' => $form->createView(),
        ));
    }

    /**
     * Finds and displays a categorieTechnologie entity.
     *
     * @Route("/{id}", name="categorietechnologie_show")
     * @Method("GET")
     */
    public function showAction(CategorieTechnologie $categorieTechnologie)
    {
        $deleteForm = $this->createDeleteForm($categorieTechnologie);

        return $this->render('categorietechnologie/show.html.twig', array(
            'categorieTechnologie' => $categorieTechnologie,
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Displays a form to edit an existing categorieTechnologie entity.
     *
     * @Route("/{id}/edit", name="categorietechnologie_edit")
     * @Method({"GET", "POST"})
     */
    public function editAction(Request $request, CategorieTechnologie $categorieTechnologie)
    {
        $deleteForm = $this->createDeleteForm($categorieTechnologie);
        $editForm = $this->createForm('OffreBundle\Form\CategorieTechnologieType', $categorieTechnologie);
        $editForm->handleRequest($request);

        if ($editForm->isSubmitted() && $editForm->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('categorietechnologie_edit', array('id' => $categorieTechnologie->getId()));
        }

        return $this->render('categorietechnologie/edit.html.twig', array(
            'categorieTechnologie' => $categorieTechnologie,
            'edit_form' => $editForm->createView(),
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Deletes a categorieTechnologie entity.
     *
     * @Route("/{id}", name="categorietechnologie_delete")
     * @Method("DELETE")
     */
    public function deleteAction(Request $request, CategorieTechnologie $categorieTechnologie)
    {
        $form = $this->createDeleteForm($categorieTechnologie);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->remove($categorieTechnologie);
            $em->flush();
        }

        return $this->redirectToRoute('categorietechnologie_index');
    }

    /**
     * Creates a form to delete a categorieTechnologie entity.
     *
     * @param CategorieTechnologie $categorieTechnologie The categorieTechnologie entity
     *
     * @return \Symfony\Component\Form\Form The form
     */
    private function createDeleteForm(CategorieTechnologie $categorieTechnologie)
    {
        return $this->createFormBuilder()
            ->setAction($this->generateUrl('categorietechnologie_delete', array('id' => $categorieTechnologie->getId())))
            ->setMethod('DELETE')
            ->getForm()
        ;
    }
}
