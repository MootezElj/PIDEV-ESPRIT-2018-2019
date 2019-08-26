package entities.planings;



import javafx.beans.property.SimpleStringProperty;

public class Calendar {
    private User id_user;
        private final SimpleStringProperty name;
        private final SimpleStringProperty startYear;
        private final SimpleStringProperty endYear;

        private final SimpleStringProperty startDate;
         
        public Calendar(String name, String startYear, String endYear, String startingDate) {
            this.name = new SimpleStringProperty(name);
            this.startYear = new SimpleStringProperty(startYear);
            this.endYear = new SimpleStringProperty(endYear);
            this.startDate = new SimpleStringProperty(startingDate);

        }

    public void setId_user(User id_user) {
        this.id_user = id_user;
    }

    public User getId_user() {
        return id_user;
    }
         
        public String getName() {
            return name.get();
        }

        public String getStartYear() {
            return startYear.get();
        }

        public String getEndYear() {
            return endYear.get();
        }
        
        public String getStartDate() {
            return startDate.get();
        }

    }
