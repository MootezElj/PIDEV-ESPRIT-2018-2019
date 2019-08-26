package services.offres.negociationDemande;

import dao.offres.negociationDemande.NegoticationDemandeDao;
import entities.offres.NegociationDemande;

public class NegociationDemandeService {

	NegoticationDemandeDao negoticationDemandeDao = new NegoticationDemandeDao();
	
	public void ajouterNegociation(NegociationDemande negociation) {
		negoticationDemandeDao.add(negociation);
	}
	
	
	
	
	
	
}
