package dao.utilisateurs;

import entities.utilisateurs.User;
import utils.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class UserDao {

    Connection con;

    public UserDao() {
        try {
            con = ConnectionUtil.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(User user) throws SQLException {
        String sql = "INSERT INTO `fos_user`(`username`, `username_canonical`, `email`, `email_canonical`, `enabled`, `salt`, `password`, `last_login`, `confirmation_token`, `password_requested_at`, `roleJv`, `first_name`, `solde`,`last_name`, `dateNaissance`,`roles`,`ID`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,DEFAULT )";
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
        
            
            pstmt.setString(16,(user.getRoleJv()=="Client") ? "a:1:{i:0;s:11:\"ROLE_CLIENT\";}":"a:1:{i:0;s:15:\"ROLE_FREELANCER\";}"  );
   
        pstmt.executeUpdate();
        //System.out.println( pstmt.executeUpdate());
    }

    public void update(User user) throws SQLException {
        String sql = "UPDATE `fos_user` SET `username`=?,`username_canonical`=?,`email`=?,`email_canonical`=?,`enabled`=?,`salt`=?,`password`=?,`last_login`=?,`confirmation_token`=?,`password_requested_at`=?,`roleJv`=?,`first_name`=?,`last_name`=?,`dateNaissance`=?,`roles`=? WHERE `ID`=?";
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
            
            //pstmt.setString(16, "a:1:{i:0;s:15:\"ROLE_FREELANCER\";}");
        pstmt.executeUpdate();
    }

    
    
    public List<User> selectAll() throws SQLException {
        String sql = "SELECT * FROM `fos_user` where RoleJv like 'Freelancer' or RoleJv like 'Client'";
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
            user.setRoleJv(rs.getString("RoleJv"));
            user.setFirstName(rs.getString(13));
            
            user.setSolde(rs.getFloat(14));
            user.setLastName(rs.getString(15));
             user.setDateNaissance(rs.getDate("dateNaissance"));
           
           
            
           
            System.out.println(user);
            userList.add(user);
        }
        return userList;
    }

    public List<User> selectRole(String Role) throws SQLException {
        String sql = "SELECT * FROM `fos_user` where `roleJv` =?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, Role);
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
            user.setRoleJv(rs.getString("roleJv"));
            user.setFirstName(rs.getString(13));
            
            user.setSolde(rs.getFloat(14));
            user.setLastName(rs.getString(15));
             user.setDateNaissance(rs.getDate("dateNaissance"));
           
             System.out.println(user);
            userList.add(user);
        }
        return userList;
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM `fos_user` WHERE `ID`=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
    }

    public User select(String nom) throws SQLException {
        String sql = "SELECT * FROM `fos_user` where `username` =?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, nom);
        ResultSet rs = pstmt.executeQuery();
        User user = new User();

        while (rs.next()) { 
            
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
            user.setRoleJv(rs.getString("roleJv"));
            user.setFirstName(rs.getString(13));
            
            user.setSolde(rs.getFloat(14));
            user.setLastName(rs.getString(15));
             user.setDateNaissance(rs.getDate(16));
           
            System.out.println(user);

        }
        return user;
    }

    public void bannir(int id, boolean a) throws SQLException {
        String sql;
        if (a) {
            sql = "UPDATE `fos_user` SET `ENABLED`=0 WHERE `ID`=?";
        } else {
            sql = "UPDATE `fos_user` SET `ENABLED`=1 WHERE `ID`=?";
        }
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, id);
        pstmt.executeUpdate();

    }

    @FXML
    public void updated(User user) throws SQLException {
        String sql = "UPDATE `fos_user` SET `first_name`=?,`salt`=?,`roleJv`=?,`dateNaissance`=?,`username_canonical`=?,`last_name`=?,`email`=?,`username`=? WHERE `ID`=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, user.getFirstName());
        pstmt.setString(2, user.getSalt());
        pstmt.setString(3, user.getRoleJv());
        
        pstmt.setString(4, new java.sql.Date(user.getDateNaissance().getTime()).toString());
        pstmt.setString(5, user.getUsernameCanonical());
        pstmt.setString(6, user.getLastName());
        pstmt.setString(7, user.getEmail());
        pstmt.setString(8, user.getUsername());
        pstmt.setInt(9, user.getId());

        pstmt.executeUpdate();

    }
    
    public List<User> selectBy(String attribute,String value) throws SQLException {
        String sql="SELECT * FROM `fos_user` where "+attribute+"= ?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1,value);
        ResultSet rs = pstmt.executeQuery();
        List<User> userList=new ArrayList<>();
        while(rs.next()){
            User user=new User();
             
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));           
            user.setUsernameCanonical(rs.getString("username_canonical"));
            
            user.setEmail(rs.getString("email"));            
            user.setEmailCanonical(rs.getString("email_canonical"));
            user.setEnabled(rs.getBoolean("enabled"));
            user.setSalt(rs.getString("salt"));
            user.setPassword(rs.getString("password"));
            user.setLastLogin(rs.getDate("last_login"));
           //confirmation token 
            
            user.setPasswordRequestedAt(rs.getDate("password_requested_at"));
            user.setRoleJv(rs.getString("roleJv"));
               user.setConfirmationToken(rs.getString("confirmation_token"));
            user.setFirstName(rs.getString("first_name"));
            
            user.setSolde(rs.getFloat("solde"));
            user.setLastName(rs.getString("last_name"));
             user.setDateNaissance(rs.getDate("dateNaissance"));
             
            userList.add(user);
        }
        return  userList;
    }
    
    public void updatePassword( User user) throws SQLException {
        System.out.println(user.getPassword());
        String sql="UPDATE `fos_user` SET `PASSWORD`=? WHERE `ID`=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1,user.getPassword());
        pstmt.setInt(2,user.getId());
        pstmt.executeUpdate();
        System.out.println(user.getPassword());
    }

}
