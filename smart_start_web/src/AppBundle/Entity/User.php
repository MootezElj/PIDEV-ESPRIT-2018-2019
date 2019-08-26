<?php

namespace AppBundle\Entity;

use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\ORM\Mapping as ORM;

use Vich\UploaderBundle\Mapping\Annotation as Vich;
use Symfony\Component\Validator\Constraints as Assert;
use FOS\MessageBundle\Model\ParticipantInterface;
use FOS\UserBundle\Model\User as BaseUser;
use Symfony\Component\HttpFoundation\File\File;

/**
 * User
 *
 * @ORM\Table(name="fos_user")
 * @ORM\Entity(repositoryClass="AppBundle\Repository\UserRepository")
 * @Vich\Uploadable
 */
class User extends BaseUser implements ParticipantInterface
{
    /**
     * @var int
     *
     * @ORM\Column(name="id", type="integer")
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="AUTO")
     */
    protected $id;

    /**
     * @ORM\Column(name="first_name", type="string")
     */
    protected $FirstName;
    /**
     * @ORM\Column(name="last_name", type="string")
     */
    protected $LastName;

    /**
     *
     * @Vich\UploadableField(mapping="profile_image", fileNameProperty="imageName", nullable=true)
     *
     * @var File
     */
    protected $imageFile;

    /**
     * @ORM\Column(type="string", length=255, nullable=true)
     */
    protected $imageName;

    /**
     * @Assert\DateTime
     * @var string A "Y-m-d H:i:s" formatted value
     */
    protected $updatedAt;

    /**
     * @var string
     *
     * @ORM\Column(name="competance", type="string", length=255, nullable=true)
     */
    protected $competance;

    /**
     * @var bool
     *
     * @ORM\Column(name="valide", type="boolean", nullable=true)
     */
    protected $valide;

    /**
     * @var bool
     *
     * @ORM\Column(name="signale", type="boolean", nullable=true)
     */
    protected $signale;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="dateNaissance", type="date", nullable=true)
     */
    private $dateNaissance;


    /**
     *
     * @ORM\OneToMany(targetEntity="OffreBundle\Entity\freelancerParticipeProjet", mappedBy="freelancer")
     */
    private $participationsFreelancer;

    /**
     *
     * @ORM\OneToMany(targetEntity="OffreBundle\Entity\freelancerDemandeProjet", mappedBy="freelancer")
     */
    private $demandes;

    /**
     * One Client has many projets created. This is the inverse side.
     * @ORM\OneToMany(targetEntity="OffreBundle\Entity\Projet", mappedBy="client")
     */
    private $projetsClient;

    /**
     * @return mixed
     */
    public function getProjetsClient()
    {
        return $this->projetsClient;
    }

    /**
     * @param mixed $projetsClient
     */
    public function setProjetsClient($projetsClient)
    {
        $this->projetsClient = $projetsClient;
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
     * @return mixed
     */
    public function getParticipationsFreelancer()
    {
        return $this->participationsFreelancer;
    }

    /**
     * @param mixed $participationsFreelancer
     */
    public function setParticipationsFreelancer($participationsFreelancer)
    {
        $this->participationsFreelancer = $participationsFreelancer;
    }


    /**
     * @return \DateTime
     */
    public function getDateNaissance()
    {
        return $this->dateNaissance;
    }

    /**
     * @param \DateTime $dateNaissance
     */
    public function setDateNaissance($dateNaissance)
    {
        $this->dateNaissance = $dateNaissance;
    }


    /**
     * @return string
     */
    public function getCompetance()
    {
        return $this->competance;
    }

    /**
     * @param string $competance
     */
    public function setCompetance($competance)
    {
        $this->competance = $competance;
    }

    /**
     * @return bool
     */
    public function isValide()
    {
        return $this->valide;
    }

    /**
     * @param bool $valide
     */
    public function setValide($valide)
    {
        $this->valide = $valide;
    }

    /**
     * @return bool
     */
    public function isSignale()
    {
        return $this->signale;
    }

    /**
     * @param bool $signale
     */
    public function setSignale($signale)
    {
        $this->signale = $signale;
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

    public function __construct()
    {
        parent::__construct();
        $this->participationsFreelancer = new ArrayCollection();
        $this->projetsClient = new ArrayCollection();
    }

    /**
     * @return mixed
     */
    public function getLastName()
    {
        return $this->LastName;
    }

    /**
     * @param mixed $LastName
     */
    public function setLastName($LastName)
    {
        $this->LastName = $LastName;
    }

    /**
     * @return mixed
     */
    public function getFirstName()
    {
        return $this->FirstName;
    }

    /**
     * @param mixed $FirstName
     */
    public function setFirstName($FirstName)
    {
        $this->FirstName = $FirstName;
    }

    /**
     * @return File
     */
    public function getImageFile()
    {
        return $this->imageFile;
    }

    /**
     * @param File $imageFile
     */
    public function setImageFile($imageFile)
    {

        $this->imageFile = $imageFile;

        if (null !== $imageFile) {
            // It is required that at least one field changes if you are using doctrine
            // otherwise the event listeners won't be called and the file is lost
            $this->updatedAt = new \DateTime;
        }
    }

    /**
     * @return mixed
     */
    public function getImageName()
    {
        return $this->imageName;
    }

    /**
     * @param mixed $imageName
     */
    public function setImageName($imageName)
    {
        $this->imageName = $imageName;
    }

    /**
     * @return mixed
     */
    public function getUpdatedAt()
    {
        return $this->updatedAt;
    }

    /**
     * @param mixed $updatedAt
     */
    public function setUpdatedAt($updatedAt)
    {
        $this->updatedAt = $updatedAt;
    }


}

