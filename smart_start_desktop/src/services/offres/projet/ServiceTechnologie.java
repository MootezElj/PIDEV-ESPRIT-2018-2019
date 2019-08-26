package services.offres.projet;

import java.util.List;

import dao.offres.projet.TechnologieDao;
import entities.offres.Projet;
import entities.offres.Technologie;

public class ServiceTechnologie {

	ServiceProjetTech serviceProjetTech = new ServiceProjetTech();
	
	private TechnologieDao technologieDao = new TechnologieDao();


	public List<Technologie> getAllTechs(){
		return technologieDao.selectAll();
	}
	
	public void ajouterTechsToProjet(List<Technologie> techs, Projet projet) {
		serviceProjetTech.ajouterTech(projet,techs);
	}
	
	//retourne une technologie par son nom
	public Technologie getTechParNom(String nomCat) {
		return technologieDao.selectBy("nomTechnologie", nomCat).get(0);
	}

}
