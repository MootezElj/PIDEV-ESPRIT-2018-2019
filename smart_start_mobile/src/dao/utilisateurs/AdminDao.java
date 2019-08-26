/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.utilisateurs;

import entities.utilisateurs.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.ConnectionUtil;

/**
 *
 * @author Bacem Aloui
 */
public class AdminDao {
    
    
    Connection con = null;

    public AdminDao() {
        try {
            con = ConnectionUtil.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    
    
     
     
    public void update(User user) throws SQLException {
        String sql = "UPDATE `fos_user` SET `username`=?,`username_canonical`=?,`email`=?,`email_canonical`=?,`enabled`=?,`salt`=?,`password`=?,`last_login`=?,`confirmation_token`=?,`password_requested_at`=?,`roleJv`=?,`first_name`=?,`last_name`=?,`dateNaissance`=? WHERE `ID`=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getUsername());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getEmail());
            pstmt.setBoolean(5, user.isEnabled());
            pstmt.setString(6, user.getSalt());        
            pstmt.setString(7, user.getPassword());
            pstmt.setString(8, new java.sql.Date(user.getLastLogin().getTime()).toString());
            pstmt.setString(9, user.getConfirmationToken());
           
            pstmt.setString(10, new java.sql.Date(user.getPasswordRequestedAt().getTime()).toString());
            pstmt.setString(11, user.getRoleJv());
            pstmt.setString(12, user.getFirstName());
            pstmt.setFloat(13, user.getSolde());
            pstmt.setString(14, user.getLastName());
            pstmt.setString(15, new java.sql.Date(user.getDateNaissance().getTime()).toString());
        pstmt.executeUpdate();
    }
    
      
    public List<User> selectAll() throws SQLException {
        String sql = "SELECT * FROM `fos_user`";
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        //User user = new User();
        List<User> userList = new ArrayList<>();
        while (rs.next()) {
            User user = new User();
            user.setId(rs.getInt(1));
            user.setUsername(rs.getString(2));           
            user.setUsernameCanonical(rs.getString(3));
            
            user.setEmail(rs.getString(4));            
            user.setEmailCanonical(rs.getString(5));
            user.setEnabled(rs.getBoolean(6));
            user.setSalt(rs.getString(7));
            user.setPassword(rs.getString(8));
            user.setLastLogin(rs.getDate(9));
           //confirmation token 
            
            user.setPasswordRequestedAt(rs.getDate(11));
            user.setRoleJv(rs.getString(12));
            user.setFirstName(rs.getString(13));
            
            user.setSolde(rs.getFloat(14));
            user.setLastName(rs.getString(15));
             user.setDateNaissance(rs.getDate(16));
           
           
            
           
            System.out.println(user);
            userList.add(user);
        }
        return userList;
    }

    public void delete (int id) throws SQLException {
        String sql="DELETE FROM `user` WHERE `ID`=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
    }
    
    
    
}
