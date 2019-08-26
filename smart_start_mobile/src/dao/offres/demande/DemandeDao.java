package dao.offres.demande;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dao.offres.projet.ProjetDao;
import dao.offres.user.UserDao;
import entities.offres.Demande;
import entities.offres.User;
import utils.Connexion;

public class DemandeDao {
	private ProjetDao projetDao = new ProjetDao();
	private UserDao userDao = new UserDao();
	Connection con=Connexion.getInstance().getCon();
	public void add(Demande demande)  {
		try {
			String sql="INSERT INTO `freelancer_demande_projet`( `projet_id`,`titreDemande`,`descriptionDemande`,`periodeRealisationMin`,`periodeRealisationMax`,`cout`,"
					+ "`dateDemande`,`valide`,`signale`,`Freelancer_id`,`id`) VALUES( ?,?,?,?,?,?,?,?,?,?,DEFAULT)";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1,Integer.toString(demande.getProjet().getId()));
			pstmt.setString(2,demande.getTitreDemande());
			pstmt.setString(3,demande.getDescriptionDemande());
			pstmt.setString(4,Double.toString(demande.getPeriodeRealisationMin()));
			pstmt.setString(5,Double.toString(demande.getPeriodeRealisationMax()));
			pstmt.setString(6,Double.toString(demande.getCoutDemande()));
			//Initializing Date Format for futur use
			String pattern = "yyyy/MM/dd";
			DateFormat df = new SimpleDateFormat(pattern);
			//association de la date d'aujourdhui tt que date de creation
			Date today = Calendar.getInstance().getTime();
			String todayAsString = df.format(today);
		
			pstmt.setString(7,todayAsString);//datedebut
			pstmt.setString(8,"1");
			pstmt.setString(9,"0");
			pstmt.setString(10,Integer.toString(demande.getFreelancer().getId()));
			pstmt.executeUpdate();
			System.out.println("Demande Ajoute avec success");
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update(Demande demande) {
		try {
				
			String sql="UPDATE `freelancer_demande_projet` SET `titreDemande`=?, `descriptionDemande`=?, `periodeRealisationMin`=?,"
					+ "`periodeRealisationMax`=?, `cout`=? WHERE `id`=?";
			PreparedStatement pstmt = con.prepareStatement(sql);	
			//
			pstmt.setString(1,demande.getTitreDemande());
			pstmt.setString(2,demande.getDescriptionDemande());
			pstmt.setString(3,Double.toString(demande.getPeriodeRealisationMin()));
			pstmt.setString(4,Double.toString(demande.getPeriodeRealisationMax()));
			pstmt.setString(5,Double.toString(demande.getCoutDemande()));//fin 
			pstmt.setString(6,Integer.toString(demande.getId()));//date realisation
			pstmt.executeUpdate();
			System.out.println("Demande modifie avec success");
			System.out.println(pstmt.toString());
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Demande> selectBy(String attribute,String value) {
		try {
			ProjetDao projetDao = new ProjetDao();
			String sql="SELECT * FROM `freelancer_demande_projet` WHERE "+attribute+"=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1,value);
			ResultSet rs = pstmt.executeQuery();
			
			List<Demande> demandeList = new ArrayList<>();
			while(rs.next()){
				Demande demande = new Demande();
				demande.setProjet(projetDao.selectBy("id", rs.getString("projet_id")).get(0));
				demande.setId(rs.getInt(1));
				demande.setFreelancer(userDao.selectBy("id", Integer.toString(rs.getInt("Freelancer_id"))).get(0));
				demande.setTitreDemande(rs.getString("titreDemande"));
				demande.setDescriptionDemande(rs.getString("descriptionDemande"));
				demande.setPeriodeRealisationMin(rs.getDouble("periodeRealisationMin"));
				demande.setPeriodeRealisationMax(rs.getDouble("periodeRealisationMax"));
				demande.setCoutDemande(rs.getDouble("cout"));
				demande.setDateDemande(rs.getDate("dateDemande"));
				demande.setValide(rs.getBoolean("valide"));
				demande.setSignale(rs.getBoolean("signale"));
	            demandeList.add(demande);
			}
			return  demandeList;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void delete(Demande demande) {
		try {
			String sql="DELETE FROM `freelancer_demande_projet` WHERE id=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,demande.getId());
			pstmt.executeUpdate();
			System.out.println("Demande supprime avec success");
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Il faut changer Client client = new client(2) par fetching de la base de donne
	public List<Demande> selectAll() {
		try {
		User client = new User();
		  String sql="SELECT * FROM `freelancer_demande_projet`";
	        PreparedStatement pstmt = con.prepareStatement(sql);
	        ResultSet rs = pstmt.executeQuery();
	        List<Demande> demandeList=new ArrayList<>();
	        while(rs.next()){
	            Demande demande = new Demande();
				demande.setProjet(projetDao.selectBy("id", rs.getString("projet_id")).get(0));
				demande.setId(rs.getInt(1));
				demande.setFreelancer(userDao.selectBy("id", Integer.toString(rs.getInt("Freelancer_id"))).get(0));
				demande.setTitreDemande(rs.getString("titreDemande"));
				demande.setDescriptionDemande(rs.getString("descriptionDemande"));
				demande.setPeriodeRealisationMin(rs.getDouble("periodeRealisationMin"));
				demande.setPeriodeRealisationMax(rs.getDouble("periodeRealisationMax"));
				demande.setCoutDemande(rs.getDouble("cout"));
				demande.setDateDemande(rs.getDate("dateDemande"));
				demande.setValide(rs.getBoolean("valide"));
				demande.setSignale(rs.getBoolean("signale"));
	            demandeList.add(demande);
	        }
	        return demandeList;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	    }
	

	
	   
}
