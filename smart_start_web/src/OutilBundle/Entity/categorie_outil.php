<?php

namespace OutilBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * categorie_outil
 *
 * @ORM\Table(name="categorie_outil")
 * @ORM\Entity(repositoryClass="OutilBundle\Repository\categorie_outilRepository")
 */
class categorie_outil
{
    /**
     * @var int
     *
     * @ORM\Column(name="id_categorie_outil", type="integer")
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="AUTO")
     */
    private $idCategorie;

    /**
     * @var string
     *
     * @ORM\Column(name="nom_categorie_outil", type="string", length=255)
     */
    private $nomCategorieOutil;


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
     * Set nomCategorieOutil
     *
     * @param string $nomCategorieOutil
     *
     * @return categorie_outil
     */
    public function setNomCategorieOutil($nomCategorieOutil)
    {
        $this->nomCategorieOutil = $nomCategorieOutil;

        return $this;
    }

    /**
     * Get nomCategorieOutil
     *
     * @return string
     */
    public function getNomCategorieOutil()
    {
        return $this->nomCategorieOutil;
    }
}

