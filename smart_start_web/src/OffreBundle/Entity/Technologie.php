<?php

namespace OffreBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Technologie
 *
 * @ORM\Table(name="technologie")
 * @ORM\Entity(repositoryClass="OffreBundle\Repository\TechnologieRepository")
 */
class Technologie
{
    /**
     * @var int
     *
     * @ORM\Column(name="id", type="integer")
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="AUTO")
     */
    private $id;

    /**
     * @var string
     *
     * @ORM\Column(name="nomTechnologie", type="string", length=255)
     */
    private $nomTechnologie;

    /**
     * @ORM\ManyToMany(targetEntity="Projet", mappedBy="projets")
     */
    private $projets;

    /**
     * @return mixed
     */
    public function getCategorieTechnologie()
    {
        return $this->categorieTechnologie;
    }

    /**
     * @param mixed $categorieTechnologie
     */
    public function setCategorieTechnologie($categorieTechnologie)
    {
        $this->categorieTechnologie = $categorieTechnologie;
    }


    /**
     * @ORM\ManyToOne(targetEntity="categorieTechnologie", inversedBy="technologies")
     * @ORM\JoinColumn(name="Categorie_tech_id", referencedColumnName="id")
     */
    private $categorieTechnologie;


    /**
     * @return mixed
     */
    public function getProjets()
    {
        return $this->projets;
    }


    public function addProjet(Projet $projet)
    {
        $this->projets[] = $projet;
    }

    /**
     * @param mixed $projets
     */
    public function setProjets($projets)
    {
        $this->projets = $projets;
    }

    public function __construct() {
        $this->projets = new \Doctrine\Common\Collections\ArrayCollection();
    }

    /**
     * Get id
     *
     * @return int
     */
    public function getId()
    {
        return $this->id;
    }

    /**
     * Set nomTechnologie
     *
     * @param string $nomTechnologie
     *
     * @return Technologie
     */
    public function setNomTechnologie($nomTechnologie)
    {
        $this->nomTechnologie = $nomTechnologie;

        return $this;
    }

    /**
     * Get nomTechnologie
     *
     * @return string
     */
    public function getNomTechnologie()
    {
        return $this->nomTechnologie;
    }
}

