package dao.offres.projet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.offres.Projet;
import entities.offres.Technologie;
import entities.offres.User;
import services.offres.projet.ServiceProjet;
import utils.Connexion;

public class ProjetTechDao {
	Connection con=Connexion.getInstance().getCon();
	
	public void add(Projet projet, Technologie tech) {
		try {
			
			String sql="INSERT INTO `projet_technologie`(`projet_id`, `technologie_id`) VALUES(?,?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1,Integer.toString(projet.getId()));
			pstmt.setString(2,Integer.toString(tech.getId()));
			pstmt.executeUpdate();
			System.out.println("Technologie applique au projet"+projet.getNomProjet()+" avec success");
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(Projet projet) {
		try {
			String sql="DELETE FROM `projet_technologie` WHERE projet_id=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,projet.getId());
			pstmt.executeUpdate();
			System.out.println("Tech supprime avec success");
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Projet> getProjetByTech(Technologie t) {
		try {
			ProjetDao projetDao = new ProjetDao();
			String sql="SELECT * FROM `projet_technologie` WHERE technologie_id=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1,Integer.toString(t.getId()));
			ResultSet rs = pstmt.executeQuery();
			List<Projet> projetList = new ArrayList<>();
			while(rs.next()){
				projetList.add(projetDao.selectBy("id", rs.getString("projet_id")).get(0));
			}
			return  projetList;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Technologie> getTechsByProjet(Projet p) {
		try {
			ServiceProjet serviceProjet = new ServiceProjet();
			TechnologieDao technologieDao = new TechnologieDao();
			ProjetDao projetDao = new ProjetDao();
			User client = new User(2);
			String sql="SELECT * FROM `projet_technologie` WHERE projet_id=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1,Integer.toString(p.getId()));
			ResultSet rs = pstmt.executeQuery();
			List<Technologie> techList = new ArrayList<>();
			while(rs.next()){
				techList.add(technologieDao.selectBy("id", rs.getString("technologie_id")).get(0));
			}
			return  techList;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
