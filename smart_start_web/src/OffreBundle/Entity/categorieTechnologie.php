<?php

namespace OffreBundle\Entity;

use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\ORM\Mapping as ORM;

/**
 * categorieTechnologie
 *
 * @ORM\Table(name="categorie_technologie")
 * @ORM\Entity(repositoryClass="OffreBundle\Repository\categorieTechnologieRepository")
 */
class categorieTechnologie
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
     * @ORM\Column(name="nomCategorie", type="string", length=255)
     */
    private $nomCategorie;


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
     * Set nomCategorie
     *
     * @param string $nomCategorie
     *
     * @return categorieTechnologie
     */
    public function setNomCategorie($nomCategorie)
    {
        $this->nomCategorie = $nomCategorie;

        return $this;
    }

    /**
     * Get nomCategorie
     *
     * @return string
     */
    public function getNomCategorie()
    {
        return $this->nomCategorie;
    }

    /**
     *
     *@ORM\OneToMany(targetEntity="Technologie", mappedBy="categorieTechnologie")
     */
    private $technologies;

    public function __construct() {
        $this->technologies = new ArrayCollection();
    }

    /**
     * @return mixed
     */
    public function getTechnologies()
    {
        return $this->technologies;
    }
    /**
     * @param mixed $technologies
     */
    public function setTechnologies($technologies)
    {
        $this->technologies = $technologies;
    }

}

