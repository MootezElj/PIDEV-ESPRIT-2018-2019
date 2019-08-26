<?php

namespace OffreBundle\Entity;

use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Context\ExecutionContextInterface;
use Symfony\Component\Validator\Constraints as Assert;

/**
 * Projet
 *
 * @ORM\Table(name="projet")
 * @ORM\Entity(repositoryClass="OffreBundle\Repository\ProjetRepository")
 */
class Projet
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
     *
     * @var string
     *
     * @ORM\Column(name="nomProjet", type="string", length=255)
     */
    private $nomProjet;

    /**
     * @var string
     *
     * @ORM\Column(name="TitreProjet", type="string", length=255)
     */
    private $titreProjet;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="DateDebut", type="date", nullable=true)
     */
    private $dateDebut;

    /**
     * @var \DateTime
     * @Assert\GreaterThan(propertyPath="dateDebut")
     * @ORM\Column(name="dateFin", type="date", length=255, nullable=true)
     */
    private $dateFin;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="dateRealisation", type="date", nullable=true)
     */
    private $dateRealisation;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="dateCreation", type="date", nullable=true)
     */
    private $dateCreation;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="datePublication", type="date", nullable=true)
     */
    private $datePublication;

    /**
     * @var string
     *
     * @ORM\Column(name="description", type="text")
     */
    private $description;

    /**
     * @var bool
     *
     * @ORM\Column(name="publie", type="boolean", nullable=true)
     */
    private $publie;

    /**
     * @var bool
     *
     * @ORM\Column(name="valide", type="boolean")
     */
    private $valide;

    /**
     * @var bool
     *
     * @ORM\Column(name="signale", type="boolean", nullable=true)
     */
    private $signale;

    /**
     *
     * @ORM\OneToMany(targetEntity="freelancerParticipeProjet", mappedBy="projet")
     */
    private $participations;

    /**
     * Many features have one product. This is the owning side.
     * @ORM\ManyToOne(targetEntity="AppBundle\Entity\User", inversedBy="$projetsClient"))
     * @ORM\JoinColumn(name="client_id", referencedColumnName="id")
     */
    private $client;

    /**
     * @var float
     *
     * @ORM\Column(name="cout", type="float")
     */
    private $cout;

    /**
     * @return float
     */
    public function getCout()
    {
        return $this->cout;
    }

    /**
     * @param float $cout
     */
    public function setCout($cout)
    {
        $this->cout = $cout;
    }


    /**
     * @return mixed
     */
    public function getClient()
    {
        return $this->client;
    }

    /**
     * @param mixed $client
     */
    public function setClient($client)
    {
        $this->client = $client;
    }


    /**
     * @return mixed
     */
    public function getDemandes()
    {
        return $this->demandes;
    }

    /**
     * @param mixed $demandes
     */
    public function setDemandes($demandes)
    {
        $this->demandes = $demandes;
    }


    /**
     *
     * @ORM\OneToMany(targetEntity="freelancerDemandeProjet", mappedBy="projet")
     */
    private $demandes;

    /**
     * @return mixed
     */
    public function getParticipations()
    {
        return $this->participations;
    }

    /**
     * @param mixed $participations
     */
    public function setParticipations($participations)
    {
        $this->participations = $participations;
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


    /**
     * @ORM\ManyToMany(targetEntity="Technologie", inversedBy="projets")
     * @ORM\JoinTable(name="projet_technologie")
     */
    private $technologies;

    public function __construct() {
        $this->technologies = new \Doctrine\Common\Collections\ArrayCollection();
        $this->participations = new ArrayCollection();
    }

    public function addTechnologie(Technologie $technologie)
    {
        $technologie->addProjet($this); // synchronously updating inverse side
        $this->technologies[] = $technologie;
    }


    public function removeTechnologie($technologie)
    {
        return $this->technologies->removeElement($technologie);
    }

    public function removeDemande($demande)
    {
        return $this->demandes->removeElement($demande);
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
     * Set nomProjet
     *
     * @param string $nomProjet
     *
     * @return Projet
     */
    public function setNomProjet($nomProjet)
    {
        $this->nomProjet = $nomProjet;
        return $this;
    }
    /**
     * Get nomProjet
     *
     * @return string
     */
    public function getNomProjet()
    {
        return $this->nomProjet;
    }

    /**
     * Set titreProjet
     *
     * @param string $titreProjet
     *
     * @return Projet
     */
    public function setTitreProjet($titreProjet)
    {
        $this->titreProjet = $titreProjet;

        return $this;
    }

    /**
     * Get titreProjet
     *
     * @return string
     */
    public function getTitreProjet()
    {
        return $this->titreProjet;
    }

    /**
     * Set dateDebut
     *
     * @param \DateTime $dateDebut
     *
     * @return Projet
     */
    public function setDateDebut($dateDebut)
    {
        $this->dateDebut = $dateDebut;

        return $this;
    }

    /**
     * Get dateDebut
     *
     * @return \DateTime
     */
    public function getDateDebut()
    {
        return $this->dateDebut;
    }

    /**
     * Set dateFin
     *
     * @param string $dateFin
     *
     * @return Projet
     */
    public function setDateFin($dateFin)
    {
        $this->dateFin = $dateFin;

        return $this;
    }

    /**
     * Get dateFin
     *
     * @return string
     */
    public function getDateFin()
    {
        return $this->dateFin;
    }

    /**
     * Set dateRealisation
     *
     * @param \DateTime $dateRealisation
     *
     * @return Projet
     */
    public function setDateRealisation($dateRealisation)
    {
        $this->dateRealisation = $dateRealisation;

        return $this;
    }

    /**
     * Get dateRealisation
     *
     * @return \DateTime
     */
    public function getDateRealisation()
    {
        return $this->dateRealisation;
    }

    /**
     * Set dateCreation
     *
     * @param \DateTime $dateCreation
     *
     * @return Projet
     */
    public function setDateCreation($dateCreation)
    {
        $this->dateCreation = $dateCreation;

        return $this;
    }

    /**
     * Get dateCreation
     *
     * @return \DateTime
     */
    public function getDateCreation()
    {
        return $this->dateCreation;
    }

    /**
     * Set datePublication
     *
     * @param \DateTime $datePublication
     *
     * @return Projet
     */
    public function setDatePublication($datePublication)
    {
        $this->datePublication = $datePublication;

        return $this;
    }

    /**
     * Get datePublication
     *
     * @return \DateTime
     */
    public function getDatePublication()
    {
        return $this->datePublication;
    }

    /**
     * Set description
     *
     * @param string $description
     *
     * @return Projet
     */
    public function setDescription($description)
    {
        $this->description = $description;

        return $this;
    }

    /**
     * Get description
     *
     * @return string
     */
    public function getDescription()
    {
        return $this->description;
    }

    /**
     * Set publie
     *
     * @param boolean $publie
     *
     * @return Projet
     */
    public function setPublie($publie)
    {
        $this->publie = $publie;

        return $this;
    }

    /**
     * Get publie
     *
     * @return bool
     */
    public function getPublie()
    {
        return $this->publie;
    }

    /**
     * Set valide
     *
     * @param boolean $valide
     *
     * @return Projet
     */
    public function setValide($valide)
    {
        $this->valide = $valide;

        return $this;
    }

    /**
     * Get valide
     *
     * @return bool
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
     * @return Projet
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

    /**
     * @Assert\Callback
     */
    public function validate(ExecutionContextInterface $context, $payload)
    {
        if(strlen($this->nomProjet)<2){
            $context->buildViolation('Nom projet trop court')
                ->atPath('nomProjet')
                ->addViolation();
        }
        if(strlen($this->titreProjet)<2){
            $context->buildViolation('Titre du projet trop court')
                ->atPath('titreProjet')
                ->addViolation();
        }
        $jour_auj = date('j');
        $mois_auj = date('m');
        $annee_auj = date('y');
        if($this->dateDebut->format('y')-$annee_auj>=4 ){
            $context->buildViolation('Date de debut sup ou egale a 4')
                ->atPath('dateDebut')
                ->addViolation();
        }

        if(date_diff($this->dateFin,$this->dateDebut)->format('%R%a days') > 0 ){
            $context->buildViolation('La date de fin doit etre apres la date de debut')
                ->atPath('dateFin')
                ->addViolation();
        }
    }

}

