package entities.offres;

public class LocaleProjet {
	private int id;
	private Projet projet;
	private Double positionX;
	private Double positionY;
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
	public Double getPositionX() {
		return positionX;
	}
	public void setPositionX(Double positionX) {
		this.positionX = positionX;
	}
	public Double getPositionY() {
		return positionY;
	}
	public void setPositionY(Double positionY) {
		this.positionY = positionY;
	}
	public LocaleProjet() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LocaleProjet(Projet projet, Double positionX, Double positionY) {
		super();
		this.projet = projet;
		this.positionX = positionX;
		this.positionY = positionY;
	}
	public LocaleProjet(int id, Projet projet, Double positionX, Double positionY) {
		super();
		this.id = id;
		this.projet = projet;
		this.positionX = positionX;
		this.positionY = positionY;
	}
	
	
	
}
