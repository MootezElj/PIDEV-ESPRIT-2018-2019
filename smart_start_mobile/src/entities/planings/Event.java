package entities.planings;



import javafx.beans.property.SimpleStringProperty;

public class Event {
    private User id_user;
    private final SimpleStringProperty term;
    private final SimpleStringProperty subject;
    private final SimpleStringProperty date;
    private final SimpleStringProperty dateFin;
    
    public Event(String term, String subject, String date ,String dateFin) {
        this.term = new SimpleStringProperty(term);
        this.subject = new SimpleStringProperty(subject);
        this.date = new SimpleStringProperty(date);
        this.dateFin = new SimpleStringProperty(dateFin);
    }

    public void setId_user(User id_user) {
        this.id_user = id_user;
    }

    public User getId_user() {
        return id_user;
    }

  
    
    // Note: We need these accessors.
    public String getTerm() {
        return term.get();
    }

    public String getSubject() {
        return subject.get();
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty getDateFin() {
        return dateFin;
    }

 

   
    
    
}
