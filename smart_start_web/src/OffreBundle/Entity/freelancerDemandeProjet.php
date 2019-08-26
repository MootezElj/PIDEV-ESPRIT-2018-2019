<?php

namespace OffreBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * freelancerDemandeProjet
 *
 * @ORM\Table(name="freelancer_demande_projet")
 * @ORM\Entity(repositoryClass="OffreBundle\Repository\freelancerDemandeProjetRepository")
 */
class freelancerDemandeProjet
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
     * @ORM\Column(name="titreDemande", type="string", length=255)
     */
    private $titreDemande;

    /**
     * @var string
     *
     * @ORM\Column(name="descriptionDemande", type="text")
     */
    private $descriptionDemande;

    /**
     * @var float
     *
     * @ORM\Column(name="periodeRealisationMin", type="float")
     */
    private $periodeRealisationMin;

    /**
     * @var float
     *
     * @ORM\Column(name="periodeRealisationMax", type="float")
     */
    private $periodeRealisationMax;

    /**
     * @var float
     *
     * @ORM\Column(name="cout", type="float")
     */
    private $cout;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="dateDemande", type="date")
     */
    private $dateDemande;

    /**
     * @var string
     *
     * @ORM\Column(name="valide", type="string", length=255)
     */
    private $valide;

    /**
     * @var bool
     *
     * @ORM\Column(name="signale", type="boolean", nullable=true)
     */
    private $signale;

    /**
     * @ORM\ManyToOne(targetEntity="Projet", inversedBy="demandes")
     * @ORM\JoinColumn(name="projet_id", referencedColumnName="id")
     */
    private $projet;

    /**
     * @return mixed
     */
    public function getProjet()
    {
        return $this->projet;
    }

    /**
     * @param mixed $projet
     */
    public function setProjet($projet)
    {
        $this->projet = $projet;
    }

    /**
     * @return mixed
     */
    public function getFreelancer()
    {
        return $this->freelancer;
    }

    /**
     * @param mixed $freelancer
     */
    public function setFreelancer($freelancer)
    {
        $this->freelancer = $freelancer;
    }

    /**
     * @ORM\ManyToOne(targetEntity="AppBundle\Entity\User" , inversedBy="demandes" )
     * @ORM\JoinColumn(name="Freelancer_id", referencedColumnName="id")
     */
    private $freelancer;

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
     * Set titreDemande
     *
     * @param string $titreDemande
     *
     * @return freelancerDemandeProjet
     */
    public function setTitreDemande($titreDemande)
    {
        $this->titreDemande = $titreDemande;

        return $this;
    }

    /**
     * Get titreDemande
     *
     * @return string
     */
    public function getTitreDemande()
    {
        return $this->titreDemande;
    }

    /**
     * Set descriptionDemande
     *
     * @param string $descriptionDemande
     *
     * @return freelancerDemandeProjet
     */
    public function setDescriptionDemande($descriptionDemande)
    {
        $this->descriptionDemande = $descriptionDemande;

        return $this;
    }

    /**
     * Get descriptionDemande
     *
     * @return string
     */
    public function getDescriptionDemande()
    {
        return $this->descriptionDemande;
    }

    /**
     * Set periodeRealisationMin
     *
     * @param float $periodeRealisationMin
     *
     * @return freelancerDemandeProjet
     */
    public function setPeriodeRealisationMin($periodeRealisationMin)
    {
        $this->periodeRealisationMin = $periodeRealisationMin;

        return $this;
    }

    /**
     * Get periodeRealisationMin
     *
     * @return float
     */
    public function getPeriodeRealisationMin()
    {
        return $this->periodeRealisationMin;
    }

    /**
     * Set periodeRealisationMax
     *
     * @param float $periodeRealisationMax
     *
     * @return freelancerDemandeProjet
     */
    public function setPeriodeRealisationMax($periodeRealisationMax)
    {
        $this->periodeRealisationMax = $periodeRealisationMax;

        return $this;
    }

    /**
     * Get periodeRealisationMax
     *
     * @return float
     */
    public function getPeriodeRealisationMax()
    {
        return $this->periodeRealisationMax;
    }

    /**
     * Set cout
     *
     * @param float $cout
     *
     * @return freelancerDemandeProjet
     */
    public function setCout($cout)
    {
        $this->cout = $cout;

        return $this;
    }

    /**
     * Get cout
     *
     * @return float
     */
    public function getCout()
    {
        return $this->cout;
    }

    /**
     * Set dateDemande
     *
     * @param \DateTime $dateDemande
     *
     * @return freelancerDemandeProjet
     */
    public function setDateDemande($dateDemande)
    {
        $this->dateDemande = $dateDemande;

        return $this;
    }

    /**
     * Get dateDemande
     *
     * @return \DateTime
     */
    public function getDateDemande()
    {
        return $this->dateDemande;
    }

    /**
     * Set valide
     *
     * @param string $valide
     *
     * @return freelancerDemandeProjet
     */
    public function setValide($valide)
    {
        $this->valide = $valide;

        return $this;
    }

    /**
     * Get valide
     *
     * @return string
     */
    public function getValide()
    {
        return $this->valide;
    }

    /**
     * Set signale
     *
     * @param boolean $signale
     *
     * @return freelancerDemandeProjet
     */
    public function setSignale($signale)
    {
        $this->signale = $signale;

        return $this;
    }

    /**
     * Get signale
     *
     * @return bool
     */
    public function getSignale()
    {
        return $this->signale;
    }
}

