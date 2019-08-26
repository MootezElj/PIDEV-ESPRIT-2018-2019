package entities.offres;

import java.util.Date;

public class Demande {
	private int id;
	private Projet projet;
	private User freelancer;
	private String titreDemande;
	private String descriptionDemande;
	private Double periodeRealisationMin;
	private Double periodeRealisationMax;
	private Double coutDemande;
	private Date dateDemande;
	private boolean valide;
	private boolean signale;
	private String nomProjet;
	private int idFreelancer;
	
	public Demande( Projet projet, User freelancer, String titreDemande, String descriptionDemande,
			Double periodeRealisationMin, Double periodeRealisationMax, Double coutDemande) {
		super();
		this.projet = projet;
		this.freelancer = freelancer;
		this.titreDemande = titreDemande;
		this.descriptionDemande = descriptionDemande;
		this.periodeRealisationMin = periodeRealisationMin;
		this.periodeRealisationMax = periodeRealisationMax;
		this.coutDemande = coutDemande;
		if (projet!=null)
		nomProjet = projet.getNomProjet();
		if (freelancer!=null)
			idFreelancer=freelancer.getId();
	}
	
	
	
	public int getIdFreelancer() {
		return idFreelancer;
	}



	public void setIdFreelancer(int idFreelancer) {
		this.idFreelancer = idFreelancer;
	}



	public void setNomProjet(String nomProjet) {
		this.nomProjet = nomProjet;
	}



	public Demande() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Projet getProjet() {
		return projet;
	}
	public void setProjet(Projet projet) {
		this.projet = projet;
	}
	public User getFreelancer() {
		return freelancer;
	}
	public void setFreelancer(User freelancer) {
		this.freelancer = freelancer;
	}
	public String getTitreDemande() {
		return titreDemande;
	}
	public void setTitreDemande(String titreDemande) {
		this.titreDemande = titreDemande;
	}
	public String getDescriptionDemande() {
		return descriptionDemande;
	}
	public void setDescriptionDemande(String descriptionDemande) {
		this.descriptionDemande = descriptionDemande;
	}
	public Double getPeriodeRealisationMin() {
		return periodeRealisationMin;
	}
	public void setPeriodeRealisationMin(Double periodeRealisationMin) {
		this.periodeRealisationMin = periodeRealisationMin;
	}
	public Double getPeriodeRealisationMax() {
		return periodeRealisationMax;
	}
	public void setPeriodeRealisationMax(Double periodeRealisationMax) {
		this.periodeRealisationMax = periodeRealisationMax;
	}
	public Double getCoutDemande() {
		return coutDemande;
	}
	public void setCoutDemande(Double coutDemande) {
		this.coutDemande = coutDemande;
	}
	public Date getDateDemande() {
		return dateDemande;
	}
	public void setDateDemande(Date dateDemande) {
		this.dateDemande = dateDemande;
	}
	public boolean isValide() {
		return valide;
	}
	public void setValide(boolean valide) {
		this.valide = valide;
	}
	public boolean isSignale() {
		return signale;
	}
	public void setSignale(boolean signale) {
		this.signale = signale;
	}
	
	public String getNomProjet() {
		return projet.getNomProjet();
	}
	
	
}
