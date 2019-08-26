package entities.offres;

public class NegociationDemande {

	private int id;
	private Projet projetNegociation;
	private Demande demandeNegociation;
	private double coutNego;
	public NegociationDemande() {
		super();
		// TODO Auto-generated constructor stub
	}
	public NegociationDemande(int id, Projet projetNegociation, Demande demandeNegociation, double coutNego) {
		super();
		this.id = id;
		this.projetNegociation = projetNegociation;
		this.demandeNegociation = demandeNegociation;
		this.coutNego = coutNego;
	}
	public NegociationDemande(Projet projetNegociation, Demande demandeNegociation, double coutNego) {
		super();
		this.projetNegociation = projetNegociation;
		this.demandeNegociation = demandeNegociation;
		this.coutNego = coutNego;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Projet getProjetNegociation() {
		return projetNegociation;
	}
	public void setProjetNegociation(Projet projetNegociation) {
		this.projetNegociation = projetNegociation;
	}
	public Demande getDemandeNegociation() {
		return demandeNegociation;
	}
	public void setDemandeNegociation(Demande demandeNegociation) {
		this.demandeNegociation = demandeNegociation;
	}
	public double getCoutNego() {
		return coutNego;
	}
	public void setCoutNego(double coutNego) {
		this.coutNego = coutNego;
	}
	
	
	
	
}
