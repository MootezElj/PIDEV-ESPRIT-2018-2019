package services.offres.projet;

import java.util.ArrayList;
import java.util.List;

import dao.offres.projet.ProjetTechDao;
import entities.offres.Projet;
import entities.offres.Technologie;
import services.offres.competenceUser.CompetenceUserService;
import services.offres.user.UserService;

public class ServiceProjetTech {
	ProjetTechDao projetTechDao = new ProjetTechDao();
	
	public void ajouterTech(Projet projet, List<Technologie> techs) {
		System.out.println("techs "+techs);
		techs.forEach(tech->{
			System.out.println("forEach tech");
			projetTechDao.add(projet,tech);
			if (projet.isPublie()) {
				notifierFreelancers(projet, techs);
			}
		});		
	
	}
	
	//supprime tous les technologie d'un projet
	public void removeAllTech(Projet projet) {
		projetTechDao.delete(projet);
	}
	
	public List<Technologie> getTechsByProjet(Projet p){
		return projetTechDao.getTechsByProjet(p);
		
	}
	
	
	//retourne une liste de type Technologie tiree par une liste de chaine de caractere
	public List<Technologie> getTechsByNameList(List<String> noms){
		ServiceTechnologie serviceTechnologie = new ServiceTechnologie();
		List<Technologie> techs=new ArrayList<Technologie>();
		noms.forEach(nom->{
			techs.add(serviceTechnologie.getTechParNom(nom));
		});
		return techs;
	}
	
	
	public void notifierFreelancers(Projet p,List<Technologie> techs) {
		UserService userService = new UserService();
    	CompetenceUserService competenceUserService = new CompetenceUserService();
		techs.forEach(tech->{
			competenceUserService.notifierUsersHaving(p,tech);
		});
	}
	
	
	
}
