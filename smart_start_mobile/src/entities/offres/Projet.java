package entities.offres;

import java.util.Date;

public class Projet {
	
	
	private int id;
	private User client;
	private String nomProjet;
	private String TitreProjet;
	private Date DateDebut;
	private Date dateFin;
	private Date dateRealisation;
	private Date dateCreation;
	private Date datePublication;
	private String description;
	private boolean publie;
	private boolean valide;
	private boolean signale;
	private double cout;
	private int idClient;

	@Override
	public String toString() {
		return "Projet [id=" + id + ", client=" + client.getId() + ", nomProjet=" + nomProjet + ", TitreProjet=" + TitreProjet
				+ "]";
	}
	private boolean archive;
	
	public User getClient() {
		return client;
	}

	public void setClient(User client) {
		this.client = client;
	}
	
	public Projet(User client, String nomProjet, String titreProjet, Date dateDebut, Date dateFin,
			String description, boolean publie, boolean valide, boolean signale, double cout, boolean archive) {
		super();
		this.client = client;
		this.nomProjet = nomProjet;
		TitreProjet = titreProjet;
		DateDebut = dateDebut;
		this.dateFin = dateFin;
		this.description = description;
		this.publie = publie;
		this.valide = valide;
		this.signale = signale;
		this.cout = cout;
		this.archive = archive;
		this.idClient = client.getId();
	}

	public String getTitreProjet() {
		return TitreProjet;
	}


	public void setTitreProjet(String titreProjet) {
		TitreProjet = titreProjet;
	}



	public Date getDateDebut() {
		return DateDebut;
	}



	public void setDateDebut(Date dateDebut) {
		DateDebut = dateDebut;
	}



	public Date getDateFin() {
		return dateFin;
	}



	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}



	public Date getDateRealisation() {
		return dateRealisation;
	}



	public void setDateRealisation(Date dateRealisation) {
		this.dateRealisation = dateRealisation;
	}



	public Date getDateCreation() {
		return dateCreation;
	}



	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}



	public Date getDatePublication() {
		return datePublication;
	}



	public void setDatePublication(Date datePublication) {
		this.datePublication = datePublication;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public boolean isPublie() {
		return publie;
	}



	public void setPublie(boolean publie) {
		this.publie = publie;
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



	public double getCout() {
		return cout;
	}



	public void setCout(double cout) {
		this.cout = cout;
	}



	public boolean isArchive() {
		return archive;
	}



	public void setArchive(boolean archive) {
		this.archive = archive;
	}

	
	
	public Projet( String nomProjet) {
		this.nomProjet = nomProjet;
	}



	public Projet() {
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNomProjet() {
		return nomProjet;
	}
	public void setNomProjet(String nomProjet) {
		this.nomProjet = nomProjet;
	}





}
