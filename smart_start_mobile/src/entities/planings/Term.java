package entities.planings;



import javafx.beans.property.SimpleStringProperty;

public class Term {
    private User id_user;
    private final SimpleStringProperty termName;
    private final SimpleStringProperty termDate;

    public void setId_user(User id_user) {
        this.id_user = id_user;
    }

    public User getId_user() {
        return id_user;
    }

    public Term(String termName, String termDate) {
        this.termName = new SimpleStringProperty(termName);
        this.termDate = new SimpleStringProperty(termDate);
    }

    public String getTermName() {
        return termName.get();
    }

    public String getTermDate() {
        return termDate.get();
    }
}
