<?php

namespace OffreBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * freelancerParticipeProjet
 *
 * @ORM\Table(name="freelancer_participe_projet")
 * @ORM\Entity(repositoryClass="OffreBundle\Repository\freelancerParticipeProjetRepository")
 */
class freelancerParticipeProjet
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
     * @var float
     *
     * @ORM\Column(name="duree", type="float")
     */
    private $duree;

    /**
     * @var float
     *
     * @ORM\Column(name="cout", type="float")
     */
    private $cout;



    /**
     * @var \DateTime
     *
     * @ORM\Column(name="dateParticipation", type="date")
     */
    private $dateParticipation;

    /**
     * @var string
     *
     * @ORM\Column(name="role", type="string", length=255, nullable=true)
     */
    private $role;


    /**
     * @ORM\ManyToOne(targetEntity="AppBundle\Entity\User",inversedBy="participationsFreelancer")
     * @ORM\JoinColumn(name="Freelancer_id", referencedColumnName="id")
     */
    private $freelancer;

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
     * @ORM\ManyToOne(targetEntity="Projet", inversedBy="participations")
     * @ORM\JoinColumn(name="projet_id", referencedColumnName="id")
     */
    private $projet;

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
     * Set duree
     *
     * @param float $duree
     *
     * @return freelancerParticipeProjet
     */
    public function setDuree($duree)
    {
        $this->duree = $duree;

        return $this;
    }

    /**
     * Get duree
     *
     * @return float
     */
    public function getDuree()
    {
        return $this->duree;
    }

    /**
     * Set cout
     *
     * @param float $cout
     *
     * @return freelancerParticipeProjet
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
     * Set dateParticipation
     *
     * @param \DateTime $dateParticipation
     *
     * @return freelancerParticipeProjet
     */
    public function setDateParticipation($dateParticipation)
    {
        $this->dateParticipation = $dateParticipation;

        return $this;
    }

    /**
     * Get dateParticipation
     *
     * @return \DateTime
     */
    public function getDateParticipation()
    {
        return $this->dateParticipation;
    }

    /**
     * Set role
     *
     * @param string $role
     *
     * @return freelancerParticipeProjet
     */
    public function setRole($role)
    {
        $this->role = $role;

        return $this;
    }

    /**
     * Get role
     *
     * @return string
     */
    public function getRole()
    {
        return $this->role;
    }
}

