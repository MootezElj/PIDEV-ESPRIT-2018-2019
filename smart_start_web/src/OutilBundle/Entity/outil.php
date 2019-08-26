<?php

namespace OutilBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * outil
 *
 * @ORM\Table(name="outil")
 * @ORM\Entity(repositoryClass="OutilBundle\Repository\outilRepository")
 */
class outil
{
    /**
     * @var int
     *
     * @ORM\Column(name="id_outil", type="integer")
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="AUTO")
     */
    private $idOutil;

    /**
     * @var string
     *
     * @ORM\Column(name="nom_outil", type="string", length=255)
     */
    private $nomOutil;

    /**
     * @var string
     *
     * @ORM\Column(name="caracteristiques", type="string", length=255)
     */
    private $caracteristiques;

    /**
     * @var bool
     *
     * @ORM\Column(name="location", type="boolean")
     */
    private $location;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="date_location", type="date")
     */
    private $dateLocation;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="date_retour_location", type="date")
     */
    private $dateRetourLocation;

    /**
     * @var bool
     *
     * @ORM\Column(name="achat", type="boolean")
     */
    private $achat;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="date_achat", type="date")
     */
    private $dateAchat;

    /**
     * @var float
     *
     * @ORM\Column(name="prix_location", type="float")
     */
    private $prixLocation;

    /**
     * @var float
     *
     * @ORM\Column(name="prix_achat", type="float")
     */
    private $prixAchat;
    /**
     * @ORM\ManyToOne(targetEntity="categorie_outil")
     * @ORM\JoinColumn(name="categorie",referencedColumnName="idCategorie")
     */
    private $categorie;

    /**
     * Get id
     *
     * @return int
     */
    public function getIdOutil()
    {
        return $this->idOutil;
    }

    /**
     * Set nomOutil
     *
     * @param string $nomOutil
     *
     * @return outil
     */
    public function setNomOutil($nomOutil)
    {
        $this->nomOutil = $nomOutil;

        return $this;
    }

    /**
     * Get nomOutil
     *
     * @return string
     */
    public function getNomOutil()
    {
        return $this->nomOutil;
    }

    /**
     * Set caracteristiques
     *
     * @param string $caracteristiques
     *
     * @return outil
     */
    public function setCaracteristiques($caracteristiques)
    {
        $this->caracteristiques = $caracteristiques;

        return $this;
    }

    /**
     * Get caracteristiques
     *
     * @return string
     */
    public function getCaracteristiques()
    {
        return $this->caracteristiques;
    }

    /**
     * Set location
     *
     * @param boolean $location
     *
     * @return outil
     */
    public function setLocation($location)
    {
        $this->location = $location;

        return $this;
    }

    /**
     * Get location
     *
     * @return bool
     */
    public function getLocation()
    {
        return $this->location;
    }

    /**
     * Set dateLocation
     *
     * @param \DateTime $dateLocation
     *
     * @return outil
     */
    public function setDateLocation($dateLocation)
    {
        $this->dateLocation = $dateLocation;

        return $this;
    }

    /**
     * Get dateLocation
     *
     * @return \DateTime
     */
    public function getDateLocation()
    {
        return $this->dateLocation;
    }

    /**
     * Set dateRetourLocation
     *
     * @param \DateTime $dateRetourLocation
     *
     * @return outil
     */
    public function setDateRetourLocation($dateRetourLocation)
    {
        $this->dateRetourLocation = $dateRetourLocation;

        return $this;
    }

    /**
     * Get dateRetourLocation
     *
     * @return \DateTime
     */
    public function getDateRetourLocation()
    {
        return $this->dateRetourLocation;
    }

    /**
     * Set achat
     *
     * @param boolean $achat
     *
     * @return outil
     */
    public function setAchat($achat)
    {
        $this->achat = $achat;

        return $this;
    }

    /**
     * Get achat
     *
     * @return bool
     */
    public function getAchat()
    {
        return $this->achat;
    }

    /**
     * Set dateAchat
     *
     * @param \DateTime $dateAchat
     *
     * @return outil
     */
    public function setDateAchat($dateAchat)
    {
        $this->dateAchat = $dateAchat;

        return $this;
    }

    /**
     * Get dateAchat
     *
     * @return \DateTime
     */
    public function getDateAchat()
    {
        return $this->dateAchat;
    }

    /**
     * Set prixLocation
     *
     * @param float $prixLocation
     *
     * @return outil
     */
    public function setPrixLocation($prixLocation)
    {
        $this->prixLocation = $prixLocation;

        return $this;
    }

    /**
     * Get prixLocation
     *
     * @return float
     */
    public function getPrixLocation()
    {
        return $this->prixLocation;
    }

    /**
     * Set prixAchat
     *
     * @param float $prixAchat
     *
     * @return outil
     */
    public function setPrixAchat($prixAchat)
    {
        $this->prixAchat = $prixAchat;

        return $this;
    }

    /**
     * Get prixAchat
     *
     * @return float
     */
    public function getPrixAchat()
    {
        return $this->prixAchat;
    }

    /**
     * @return mixed
     */
    public function getCategorie()
    {
        return $this->categorie;
    }

    /**
     * @param mixed $categorie
     */
    public function setCategorie($categorie)
    {
        $this->categorie = $categorie;
    }


}

