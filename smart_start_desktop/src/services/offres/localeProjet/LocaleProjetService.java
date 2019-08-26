package services.offres.localeProjet;

import dao.offres.localeProjet.LocaleProjetDao;
import entities.offres.LocaleProjet;
import entities.offres.Projet;

public class LocaleProjetService {

	LocaleProjetDao localeProjetDao = new LocaleProjetDao();




	public Double getPositionX(Projet projet) {
		if (localeProjetDao.selectBy("idProjet",Integer.toString(projet.getId())).size()!=0)
		return localeProjetDao.selectBy("idProjet",Integer.toString(projet.getId())).get(0).getPositionX();	
		else return 0.0;
	}

	public Double getPositionY(Projet projet) {
		return localeProjetDao.selectBy("idProjet",Integer.toString(projet.getId())).get(0).getPositionY();	
	}


	public void supprimerLocaleDeProjet(Projet projet) {
		if (localeProjetDao.
				selectBy("idProjet", Integer.toString(projet.getId())).size()!=0)
			localeProjetDao.delete(localeProjetDao.
					selectBy("idProjet", Integer.toString(projet.getId())).get(0));
		System.out.println("locale Supprimer");
	}



	public void ajouterLocaleProjet(LocaleProjet localeProjet) {
		supprimerLocaleDeProjet(localeProjet.getProjet());
		localeProjetDao.add(localeProjet);
	}


	public void supprimerLocaleProjet(LocaleProjet localeProjet) {
		localeProjetDao.delete(localeProjet);
	}
	
	

}
