package services.offres.competenceUser;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.offres.competenceUser.CompetenceUserDao;
import entities.offres.CompetenceUser;
import entities.offres.Projet;
import entities.offres.Technologie;
import entities.offres.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.SendMail;
import utils.TableViewFormat;

public class CompetenceUserService {

    CompetenceUserDao dao=new CompetenceUserDao();
    CompetenceUserDao competenceUserDao=new CompetenceUserDao();
    
    public ObservableList<TableViewFormat> getCompetenceUsersByUser(User user) throws SQLException {
        List<CompetenceUser> CompetenceUserList= dao.selectByUserID(user.getId());
        ObservableList<TableViewFormat> data= FXCollections.observableArrayList();
        for (CompetenceUser CompetenceUser : CompetenceUserList) {
            TableViewFormat tvf=new TableViewFormat( Integer.toString(CompetenceUser.getId()),CompetenceUser.getLibelle());
            data.add(tvf);
        }
        return data;
    }

    public List<String> getAllCompetenceUsers() throws SQLException {
        List<CompetenceUser> list=dao.selectAll();
        List<String> data = new ArrayList<>();
        for (CompetenceUser element: list) {
            data.add(element.getLibelle());
        }
        return data;
    }

    public TableViewFormat updateUserCompetenceUser(String lib,User user) throws SQLException {
        CompetenceUser CompetenceUser=null;
        TableViewFormat element=new TableViewFormat();
        CompetenceUser=dao.findOne(lib);
        if (CompetenceUser != null){
            dao.addToUser(CompetenceUser,user);
            element.setId(Integer.toString(CompetenceUser.getId()));
            element.setLib(CompetenceUser.getLibelle());
        }
        return element;
    }

    public TableViewFormat deleteUserCompetenceUser(String lib,User user) throws SQLException {
        CompetenceUser CompetenceUser=null;
        TableViewFormat element=new TableViewFormat();
        CompetenceUser=dao.findOne(lib);
        if (CompetenceUser != null){
            dao.deleteFromUser(CompetenceUser,user);
            element.setId(Integer.toString(CompetenceUser.getId()));
            element.setLib(CompetenceUser.getLibelle());
        }
        return element;
    }

	public void notifierUsersHaving(Projet p,Technologie tech) {
		competenceUserDao.getUsersHavingTech(tech).forEach(user->{
			System.out.println("HQTCNQU");
			SendMail.sendMail(user.getEmail(), "Projet avec votre competance '"+tech.getNomTechnologie()+"' publier", "Nous somme heureux de vous informer qu'un projet sous le nom de "+p.getNomProjet()
					+ " a ete publier maintenant verifier la liste des offres");
		});;
	}
	
	
	
}
