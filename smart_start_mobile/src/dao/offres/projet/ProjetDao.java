package dao.offres.projet;

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

import dao.offres.user.UserDao;
import entities.offres.Projet;
import entities.offres.User;
import utils.Connexion;

public class ProjetDao {
	UserDao userDao= new UserDao();
	Connection con=Connexion.getInstance().getCon();

	public void add(Projet projet)  {
		try {
			String sql="INSERT INTO `projet`(`id`, `client_id`,`nomProjet`,`TitreProjet`,`DateDebut`,`dateFin`,`dateRealisation`,"
					+ "`dateCreation`,`datePublication`,`description`,`publie`,`valide`,`signale`,`cout`,`archive`) VALUES( DEFAULT,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1,Integer.toString(projet.getClient().getId()));
			pstmt.setString(2,projet.getNomProjet());
			pstmt.setString(3,projet.getTitreProjet());
			
			//Initializing Date Format for futur use
			String pattern = "yyyy/MM/dd";
			DateFormat df = new SimpleDateFormat(pattern);
			
			//date Debut
			String dateDebutSql= df.format(projet.getDateDebut());
			//date Fin
			String dateFinSql= df.format(projet.getDateFin());
			
			//association de la date d'aujourdhui tt que date de creation
			Date today = Calendar.getInstance().getTime();
			String todayAsString = df.format(today);
		
			pstmt.setString(4,dateDebutSql);//datedebut
			pstmt.setString(5,dateFinSql);//fin 
			pstmt.setString(6,null);
			pstmt.setString(7,todayAsString);//dateCreationSql
			pstmt.setString(8,null);
			pstmt.setString(9,projet.getDescription());
			pstmt.setString(10,(projet.isPublie() ? "1": "0"));
			pstmt.setString(11,"1");
			pstmt.setString(12,"0");
			pstmt.setString(13,Double.toString(projet.getCout()));
			pstmt.setString(14,(projet.isArchive() ? "1": "0"));
			pstmt.executeUpdate();
			System.out.println("Produit Ajoute avec success");
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void update(Projet projet) {
		try {
				
			String sql="UPDATE `projet` SET `nomProjet`=?, `TitreProjet`=?, `DateDebut`=?,"
					+ "`dateFin`=?, `dateRealisation`=?, `datePublication`=?,"
					+ "`description`=?, `publie`=?, `valide`=?, `signale`=?, `cout`=?, `archive`=? WHERE `id`=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			//Initializing Date Format for futur use
			String pattern = "yyyy/MM/dd";
			DateFormat df = new SimpleDateFormat(pattern);
			//date Debut
			String dateDebutSql= df.format(projet.getDateDebut());
			//date Fin
			String dateFinSql= df.format(projet.getDateFin());
			//date creation
			String datePublicationsql = (projet.getDatePublication()==null)? null : df.format(projet.getDatePublication());
			//date realistation
			String dateRealistationsql = (projet.getDateCreation()==null)? null : df.format(projet.getDateCreation());
			
			
			//
			pstmt.setString(1,projet.getNomProjet());
			pstmt.setString(2,projet.getTitreProjet());
			pstmt.setString(3,dateDebutSql);//datedebut
			pstmt.setString(4,dateFinSql);//fin 
			pstmt.setString(5,dateRealistationsql);//date realisation
			pstmt.setString(6,datePublicationsql);//datePublication
			pstmt.setString(7,projet.getDescription());
			pstmt.setString(8,(projet.isPublie() ? "1": "0"));
			pstmt.setString(9,(projet.isValide() ? "1": "0"));
			pstmt.setString(10,(projet.isSignale() ? "1": "0"));
			pstmt.setString(11,Double.toString(projet.getCout()));
			pstmt.setString(12,(projet.isArchive() ? "1": "0"));
			pstmt.setString(13,Integer.toString(projet.getId()));
			//
			pstmt.executeUpdate();
			System.out.println("Produit modifie avec success");
			System.out.println(pstmt.toString());
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Projet> selectBy(String attribute,String value) {
		try {
			
			User client = new User(2);
			String sql="SELECT * FROM `projet` WHERE "+attribute+"=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1,value);
			ResultSet rs = pstmt.executeQuery();
			List<Projet> projetList = new ArrayList<>();
			while(rs.next()){
				Projet projet = new Projet();
	            projet.setId(rs.getInt(1));
	            projet.setClient(userDao.selectBy("id", rs.getString("client_id")).get(0)); 
	            projet.setNomProjet(rs.getString("nomProjet"));
	            projet.setTitreProjet(rs.getString("TitreProjet"));
	            projet.setDateDebut(rs.getDate("DateDebut"));
	            projet.setDateFin(rs.getDate("DateFin"));
	            projet.setDateRealisation(rs.getDate("dateRealisation"));
	            projet.setDateCreation(rs.getDate("dateCreation"));
	            projet.setDatePublication(rs.getDate("datePublication"));
	            projet.setDescription(rs.getString("description"));
	            projet.setPublie(rs.getBoolean("publie"));
	            projet.setValide(rs.getBoolean("valide"));
	            projet.setArchive(rs.getBoolean("archive"));
	            projet.setSignale(rs.getBoolean("signale"));
	            projet.setCout(rs.getDouble("cout"));	 
	            projetList.add(projet);
			}
			return  projetList;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	public void delete(Projet projet) {
		try {
			String sql="DELETE FROM `projet` WHERE id=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,projet.getId());
			pstmt.executeUpdate();
			System.out.println("Produit supprime avec success");
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Il faut changer Client client = new client(2) par fetching de la base de donne
	public List<Projet> selectAll() {
		try {
		User client = new User(2);
		  String sql="SELECT * FROM `projet`";
	        PreparedStatement pstmt = con.prepareStatement(sql);
	        ResultSet rs = pstmt.executeQuery();
	        List<Projet> projetList=new ArrayList<>();
	        while(rs.next()){
	            Projet projet = new Projet();
	            projet.setId(rs.getInt(1));
	            projet.setClient(client); // !!!! a changer
	            projet.setNomProjet(rs.getString("nomProjet"));
	            projet.setTitreProjet(rs.getString("TitreProjet"));
	            projet.setDateDebut(rs.getDate("DateDebut"));
	            projet.setDateFin(rs.getDate("DateFin"));
	            projet.setDateRealisation(rs.getDate("dateRealisation"));
	            projet.setDateCreation(rs.getDate("dateCreation"));
	            projet.setDatePublication(rs.getDate("datePublication"));
	            projet.setDescription(rs.getString("description"));
	            projet.setPublie(rs.getBoolean("publie"));
	            projet.setValide(rs.getBoolean("valide"));
	            projet.setArchive(rs.getBoolean("archive"));
	            projet.setSignale(rs.getBoolean("signale"));
	            projet.setCout(rs.getDouble("cout"));	 
	            projetList.add(projet);
	        }
	        return projetList;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	    }
	

	
	public Projet selectLast() {
		try {
		  String sql="SELECT * FROM `projet` WHERE id=max(id)";
	        PreparedStatement pstmt = con.prepareStatement(sql);
	        ResultSet rs = pstmt.executeQuery();
	        List<Projet> projetList=new ArrayList<>();
	        while(rs.next()){
	            Projet projet = new Projet();
	            projet.setId(rs.getInt("id"));
	            System.out.println(projet.getId());
	            projet.setClient(userDao.selectBy("client_id", rs.getString("client_id")).get(0));
	            projet.setNomProjet(rs.getString("nomProjet"));
	            projet.setTitreProjet(rs.getString("TitreProjet"));
	            projet.setDateDebut(rs.getDate("DateDebut"));
	            projet.setDateFin(rs.getDate("DateFin"));
	            projet.setDateRealisation(rs.getDate("dateRealisation"));
	            projet.setDateCreation(rs.getDate("dateCreation"));
	            projet.setDatePublication(rs.getDate("datePublication"));
	            projet.setDescription(rs.getString("description"));
	            projet.setPublie(rs.getBoolean("publie"));
	            projet.setValide(rs.getBoolean("valide"));
	            projet.setArchive(rs.getBoolean("archive"));
	            projet.setSignale(rs.getBoolean("signale"));
	            projet.setCout(rs.getDouble("cout"));	 
	            projetList.add(projet);
	        }
	        return projetList.get(0);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	    }
	
	
	      
}


	