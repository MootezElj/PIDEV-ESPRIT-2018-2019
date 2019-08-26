package entities.offres;

import javafx.beans.property.BooleanProperty;

public class Technologie {
	private int id;
	private String nomTechnologie;
	private int Categorie_tech_id;


	public Technologie() {
		super();

	}

	public Technologie(int id, String nomTechnologie, int categorie_tech_id, BooleanProperty checked) {
		super();
		this.id = id;
		this.nomTechnologie = nomTechnologie;
		Categorie_tech_id = categorie_tech_id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}


	public Technologie(int id, String nom) {
		super();
		this.id = id;
		this.nomTechnologie = nom;
	}

	public String getNomTechnologie() {
		return nomTechnologie;
	}
	public void setNomTechnologie(String nomTechnologie) {
		this.nomTechnologie = nomTechnologie;
	}
	public int getCategorie_tech_id() {
		return Categorie_tech_id;
	}
	public void setCategorie_tech_id(int categorie_tech_id) {
		Categorie_tech_id = categorie_tech_id;
	}



}
