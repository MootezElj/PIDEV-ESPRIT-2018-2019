package services.utilisateurs;

import dao.utilisateurs.CompetenceUserDao;
import entities.utilisateurs.CompetenceUser;
import entities.utilisateurs.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.TableViewFormat;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompetenceUserService {

    CompetenceUserDao dao=null;

    public CompetenceUserService() {
        dao=new CompetenceUserDao();
    }

    public ObservableList<TableViewFormat> getCompetencesByUser(User user) throws SQLException {
        List<CompetenceUser> competenceList= dao.selectByUserID(user.getId());
        ObservableList<TableViewFormat> data= FXCollections.observableArrayList();
        for (CompetenceUser competence : competenceList) {
            TableViewFormat tvf=new TableViewFormat( Integer.toString(competence.getId()),competence.getLibelle());
            data.add(tvf);
        }
        return data;
    }

    public List<String> getAllCompetences() throws SQLException {
        List<CompetenceUser> list=dao.selectAll();
        List<String> data = new ArrayList<>();
        for (CompetenceUser element: list) {
            data.add(element.getLibelle());
        }
        return data;
    }

    public TableViewFormat updateUserCompetence(String lib,User user) throws SQLException {
        CompetenceUser competence=null;
        TableViewFormat element=new TableViewFormat();
        competence=dao.findOne(lib);
        if (competence != null){
            dao.addToUser(competence,user);
            element.setId(Integer.toString(competence.getId()));
            element.setLib(competence.getLibelle());
        }
        return element;
    }

    public TableViewFormat deleteUserCompetence(String lib,User user) throws SQLException {
        CompetenceUser competence=null;
        TableViewFormat element=new TableViewFormat();
        competence=dao.findOne(lib);
        if (competence != null){
            dao.deleteFromUser(competence,user);
            element.setId(Integer.toString(competence.getId()));
            element.setLib(competence.getLibelle());
        }
        return element;
    }
}
