package services.offres.demande;

import java.sql.SQLException;
import java.util.List;

import dao.offres.demande.DemandeDao;
import entities.offres.Demande;
import entities.offres.Projet;
import entities.offres.User;

public class ServiceDemande {
	DemandeDao demandeDao = new DemandeDao();
	
	public void ajouterDemande(Demande demande) {
		//TODO change the freelancer with the current connected user 
		User freelancer = new User(19);// a changer
		demandeDao.add(demande);
	}
	
	
	//retourne la liste des demandes modifiables d'un client
	public List<Demande> getDemandeFreelancer(User freelancer) throws SQLException {
        List<Demande> list=demandeDao.selectBy("Freelancer_id",Integer.toString(freelancer.getId()));
        return list;
    }
	
	public void supprimerDemande(Demande d) {
		demandeDao.delete(d);
	}
 
	public void MAJDemande(Demande d) {
		demandeDao.update(d);
	}
	
	public List<Demande> getListeDemandeOfProjet(Projet p){
		List<Demande> list=demandeDao.selectBy("projet_id",Integer.toString(p.getId()));
		list.forEach(d->{
			d.setIdFreelancer(d.getFreelancer().getId());
		});
        return list;
	}

}
