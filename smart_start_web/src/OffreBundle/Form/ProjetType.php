<?php

namespace OffreBundle\Form;


use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\HiddenType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\DateType;

class ProjetType extends AbstractType
{
    /**
     * {@inheritdoc}
     */
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder->add('nomProjet',TextType::class,
            ['attr'  =>[
                'class'=>''
    ]])
            ->add('titreProjet')
            ->add('dateDebut', DateType::class, [
        'widget' => 'single_text'])
            ->add('dateFin', DateType::class, [
                'widget' => 'single_text'])
            ->add('cout')
            ->add('valide',HiddenType::class, [
                'data' => 'true',
            ])
            ->add('description');
//            ->add('dateRealisation')
//            ->add('dateCreation')
//            ->add('datePublication')
//            ->add('publie')
//            ->add('signale')
//            ->add('technologies');
    }/**
     * {@inheritdoc}
     */
    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults(array(
            'data_class' => 'OffreBundle\Entity\Projet'
        ));
    }

    /**
     * {@inheritdoc}
     */
    public function getBlockPrefix()
    {
        return 'offrebundle_projet';
    }


}
