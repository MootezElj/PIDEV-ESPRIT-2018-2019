package services.offres.projet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dao.offres.projet.ProjetDao;
import entities.offres.Projet;
import entities.offres.User;

public class ServiceProjet {
	
	private ProjetDao projetDao;
	
	public ServiceProjet() {
		projetDao =  new ProjetDao();
	}
	
	//retourne la liste de tous les projets dans la base
	 public List<String> getAllProjets() throws SQLException {
	        List<Projet> list=projetDao.selectAll();
	        List<String> data = new ArrayList<>();
	        for (Projet element: list) {
	            data.add(element.getNomProjet());
	        }
	        return data;
	    }
	 
		//retourne la liste des projets modifiables d'un client
	 public List<Projet> getClientProjets(User client) throws SQLException {
	        List<Projet> list=projetDao.selectBy("client_id",Integer.toString(client.getId()));
	        return list;
	    }
	 
	//retourne la liste des projets modifiables d'un client
	 public List<Projet> getPublishedProjets() throws SQLException {
	        List<Projet> list=projetDao.selectBy("publie", "1");
	        return list;
	    }

	 
	public void publierProjet(Projet projet) {
		projet.setPublie(true);
		Date today = Calendar.getInstance().getTime();
		projet.setDatePublication(today);
		projetDao.update(projet);
	}	
	
	public ArrayList<Projet> getProjetsOfUser(User user){
		return (ArrayList<Projet>) projetDao.selectBy("client_id", Integer.toString(user.getId()));
	}
	 

}
