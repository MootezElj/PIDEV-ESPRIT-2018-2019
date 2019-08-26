package dao.offres.negociationDemande;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.offres.demande.DemandeDao;
import dao.offres.projet.ProjetDao;
import entities.offres.NegociationDemande;
import utils.Connexion;

public class NegoticationDemandeDao {
	
	DemandeDao demandeDao = new DemandeDao();
	ProjetDao projetDao = new ProjetDao();
	Connection con=Connexion.getInstance().getCon();
	public void add(NegociationDemande negociationDemande)  {
		try {
			String sql="INSERT INTO `client_negocie_demande`(`idDemande`,`idProjet`,`coutNego`,`id`) VALUES(?,?,?,DEFAULT)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,negociationDemande.getDemandeNegociation().getId());
			pstmt.setInt(2,negociationDemande.getProjetNegociation().getId());
			pstmt.setDouble(3,negociationDemande.getCoutNego());
			pstmt.executeUpdate();
			System.out.println("negociation Ajouté avec success");
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<NegociationDemande> selectBy(String attribute,String value) {
		try {
			String sql="SELECT * FROM `client_negocie_demande` WHERE "+attribute+"=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1,value);
			ResultSet rs = pstmt.executeQuery();
			ProjetDao projetDao = new ProjetDao();
			List<NegociationDemande> negociationDemandes = new ArrayList<>();
			while(rs.next()){
				NegociationDemande negociationDemande = new NegociationDemande();
				negociationDemande.setId(rs.getInt("id"));
				negociationDemande.
				setDemandeNegociation(
						demandeDao.selectBy("id",rs.getString("idDemande")).get(0));
				negociationDemande.setProjetNegociation(
						projetDao.selectBy("id",rs.getString("idProjet")).get(0));
				negociationDemande.setCoutNego(rs.getDouble("coutNego"));
				negociationDemandes.add(negociationDemande);
			}
			return  negociationDemandes;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

//	public void delete(LocaleProjet localeProjet) {
//		try {
//			String sql="DELETE FROM `locale_projet` WHERE id=?";
//			PreparedStatement pstmt = con.prepareStatement(sql);
//			pstmt.setInt(1,localeProjet.getId());
//			pstmt.executeUpdate();
//			System.out.println("locale Projet supprime avec success");
//		}catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//	}
	
	
}
