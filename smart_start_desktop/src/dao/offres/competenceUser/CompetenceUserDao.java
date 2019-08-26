package dao.offres.competenceUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.offres.user.UserDao;
import entities.offres.CompetenceUser;
import entities.offres.Technologie;
import entities.offres.User;
import utils.Connexion;

public class CompetenceUserDao {

 	Connection con=Connexion.getInstance().getCon();

    public List<CompetenceUser> selectAll() throws SQLException {
        String sql="SELECT * FROM `CompetenceUser`";
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        List<CompetenceUser> CompetenceUserList=new ArrayList<>();
        while(rs.next()){

            CompetenceUser CompetenceUser = new CompetenceUser();
            CompetenceUser.setId(rs.getInt(1));
            CompetenceUser.setLibelle(rs.getString(2));
            CompetenceUserList.add(CompetenceUser);
        }
        return CompetenceUserList;
    }

    public List<CompetenceUser> selectByUserID(int userId) throws SQLException {
        String sql="SELECT c.idCompetenceUser, c.libelle FROM CompetenceUser_user cu INNER JOIN CompetenceUser c ON cu.idCompetenceUser=c.IDCompetenceUser WHERE cu.idUser=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1,userId);
        ResultSet rs = pstmt.executeQuery();
        List<CompetenceUser> CompetenceUserList=new ArrayList<>();
        while(rs.next()){
            CompetenceUser CompetenceUser = new CompetenceUser();
            CompetenceUser.setId(rs.getInt(1));
            CompetenceUser.setLibelle(rs.getString(2));
            CompetenceUserList.add(CompetenceUser);
        }
        return CompetenceUserList;
    }

    public List<CompetenceUser> selectByEmploisID(int empId) throws SQLException {
        String sql="SELECT c.idCompetenceUser, c.libelle FROM CompetenceUser_emplois ce INNER JOIN CompetenceUser c ON ce.idCompetenceUser=c.IDCompetenceUser WHERE ce.idannemp=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1,empId);
        ResultSet rs = pstmt.executeQuery();
        CompetenceUser CompetenceUser = new CompetenceUser();
        List<CompetenceUser> CompetenceUserList=new ArrayList<>();
        while(rs.next()){
            CompetenceUser.setId(rs.getInt(1));
            CompetenceUser.setLibelle(rs.getString(2));
        }
        return CompetenceUserList;
    }

    public CompetenceUser findOne(String lib) throws SQLException {
        String sql="SELECT * FROM CompetenceUser WHERE libelle=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1,lib);
        ResultSet rs = pstmt.executeQuery();
        CompetenceUser CompetenceUser=new CompetenceUser();
        while (rs.next()){
            CompetenceUser.setId(rs.getInt(1));
            CompetenceUser.setLibelle(rs.getString(2));
        }
        return  CompetenceUser;
    }

    public void addToUser(CompetenceUser CompetenceUser, User user) throws SQLException {
        String sql = "INSERT INTO `CompetenceUser_user`(`idUser`, `idCompetenceUser`) VALUES (?,?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1,user.getId());
        pstmt.setInt(2,CompetenceUser.getId());
        pstmt.executeUpdate();
    }
    public void deleteFromUser(CompetenceUser CompetenceUser, User user) throws SQLException {
        String sql = "DELETE FROM `CompetenceUser_user` WHERE iduser=? AND idCompetenceUser=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1,user.getId());
        pstmt.setInt(2,CompetenceUser.getId());
        pstmt.executeUpdate();
    }
    
    
    //By Mootez, permet de retouner une liste de users qui ont une technologie t
    public List<User> getUsersHavingTech(Technologie t){
    	UserDao userDao = new UserDao();
    	try {
			String sql="SELECT * FROM `competence_user` WHERE idCompetence=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1,Integer.toString(t.getId()));
			ResultSet rs = pstmt.executeQuery();
			List<User> userList = new ArrayList<>();
			while(rs.next()){
				userList.add(userDao.selectBy("id", rs.getString("idUser")).get(0)); 
			}
			return  userList;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

    }
    
    
}
