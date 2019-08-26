package dao.utilisateurs;

import entities.utilisateurs.CompetenceUser;
import entities.utilisateurs.User;
import utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompetenceUserDao {

    Connection con = null;

    public CompetenceUserDao() {
        try {
            con = ConnectionUtil.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<CompetenceUser> selectAll() throws SQLException {
        String sql="SELECT * FROM `competence_user`";
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        List<CompetenceUser> competenceList=new ArrayList<>();
        while(rs.next()){

            CompetenceUser competence = new CompetenceUser();
            competence.setId(rs.getInt(1));
            competence.setLibelle(rs.getString(2));
            competenceList.add(competence);
        }
        return competenceList;
    }

    public List<CompetenceUser> selectByUserID(int userId) throws SQLException {
        String sql="SELECT c.competence_userId, c.libelle FROM competence_user cu INNER JOIN competence_user c ON cu.competence_userId=c.competence_userId WHERE cu.idUser=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1,userId);
        ResultSet rs = pstmt.executeQuery();
        List<CompetenceUser> competenceList=new ArrayList<>();
        while(rs.next()){
            CompetenceUser competence = new CompetenceUser();
            competence.setId(rs.getInt(1));
            competence.setLibelle(rs.getString(2));
            competenceList.add(competence);
        }
        return competenceList;
    }
//
//    public List<CompetenceUser> selectByEmploisID(int empId) throws SQLException {
//        String sql="SELECT c.idcompetence, c.libelle FROM competence_emplois ce INNER JOIN competence_user c ON ce.idcompetence=c.IDCOMPETENCE WHERE ce.idannemp=?";
//        PreparedStatement pstmt = con.prepareStatement(sql);
//        pstmt.setInt(1,empId);
//        ResultSet rs = pstmt.executeQuery();
//        CompetenceUser competence = new CompetenceUser();
//        List<CompetenceUser> competenceList=new ArrayList<>();
//        while(rs.next()){
//            competence.setId(rs.getInt(1));
//            competence.setLibelle(rs.getString(2));
//        }
//        return competenceList;
//    }

    public CompetenceUser findOne(String lib) throws SQLException {
        String sql="SELECT * FROM competence_user WHERE libelle=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1,lib);
        ResultSet rs = pstmt.executeQuery();
        CompetenceUser competence=new CompetenceUser();
        while (rs.next()){
            competence.setId(rs.getInt(1));
            competence.setLibelle(rs.getString(2));
        }
        return  competence;
    }

    public void addToUser(CompetenceUser competence, User user) throws SQLException {
        String sql = "INSERT INTO `competence_user`(`competence_userId`,`idUser`, `libelle`) VALUES (DEFAULT,?,?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1,user.getId());
        pstmt.setString(2,competence.getLibelle());
        pstmt.executeUpdate();
    }
    public void deleteFromUser(CompetenceUser competence, User user) throws SQLException {
        String sql = "DELETE FROM `competence_user` WHERE iduser=? AND competence_userId=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1,user.getId());
        pstmt.setInt(2,competence.getId());
        pstmt.executeUpdate();
    }
}
