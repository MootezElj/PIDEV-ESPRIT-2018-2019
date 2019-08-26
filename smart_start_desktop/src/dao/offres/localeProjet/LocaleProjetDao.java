package dao.offres.localeProjet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import dao.offres.projet.ProjetDao;
import entities.offres.LocaleProjet;
import utils.Connexion;

public class LocaleProjetDao {
	Connection con=Connexion.getInstance().getCon();
	public void add(LocaleProjet localeProjet)  {
		try {
			String sql="INSERT INTO `locale_projet`(`idProjet`,`positionX`,`positionY`,`ID`) VALUES(?,?,?,DEFAULT)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,localeProjet.getProjet().getId());
			pstmt.setDouble(2,localeProjet.getPositionX());
			pstmt.setDouble(3,localeProjet.getPositionY());
			//Initializing Date Format for futur use
			String pattern = "yyyy/MM/dd";
			DateFormat df = new SimpleDateFormat(pattern);
			pstmt.executeUpdate();
			System.out.println("locale_projet Ajouté avec success");
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<LocaleProjet> selectBy(String attribute,String value) {
		try {
			String sql="SELECT * FROM `locale_projet` WHERE "+attribute+"=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1,value);
			ResultSet rs = pstmt.executeQuery();
			ProjetDao projetDao = new ProjetDao();
			List<LocaleProjet> localeProjets = new ArrayList<>();
			while(rs.next()){
				LocaleProjet localeProjet = new LocaleProjet();
				localeProjet.setId(rs.getInt("ID"));
				localeProjet.setProjet(projetDao.selectBy("id", rs.getString("idProjet")).get(0));
				localeProjet.setPositionX(rs.getDouble("positionX"));
				localeProjet.setPositionY(rs.getDouble("positionY"));
				localeProjets.add(localeProjet);
			}
			return  localeProjets;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void delete(LocaleProjet localeProjet) {
		try {
			String sql="DELETE FROM `locale_projet` WHERE id=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,localeProjet.getId());
			pstmt.executeUpdate();
			System.out.println("locale Projet supprime avec success");
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
