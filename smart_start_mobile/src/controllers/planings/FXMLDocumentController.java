
package controllers.planings;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.effects.JFXDepthManager;

import entities.planings.Event;
import entities.planings.Model;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.print.PrinterJob;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.ConnexionUtil;


public class FXMLDocumentController implements Initializable {
    
    // Calendar fields
    @FXML
    private Label monthLabel;
    @FXML
    private HBox weekdayHeader;
    @FXML
    private GridPane calendarGrid;
    @FXML
    private ScrollPane scrollPane;
    
    // Selections boxes
    @FXML
    private JFXComboBox<String> selectedYear;
    @FXML
    private JFXListView<String> monthSelect;   
    
    //--------- Database Handler -----------------------------------------
    ConnexionUtil databaseHandler;
    //--------------------------------------------------------------------
    
    // Color pickers
    @FXML
    private JFXColorPicker vaccance;
    @FXML
    private JFXColorPicker travail;
    @FXML
    private JFXColorPicker anniverssaire;
    @FXML
    private JFXColorPicker formation;
    @FXML
    private JFXColorPicker workshop;
    @FXML
    private JFXColorPicker certif;
    @FXML
    private JFXColorPicker important;
    @FXML
    private JFXColorPicker urgent;

    
    // Check Boxes for filtering
    @FXML
    private JFXCheckBox fallSemCheckBox;
    @FXML
    private JFXCheckBox springSemCheckBox;
    @FXML
    private JFXCheckBox summerSemCheckBox;
    @FXML
    private JFXCheckBox allQtrCheckBox;
    @FXML
    private JFXCheckBox allMbaCheckBox;
    @FXML
    private JFXCheckBox allHalfCheckBox;
    @FXML
    private JFXCheckBox allCampusCheckBox;
    @FXML
    private JFXCheckBox allHolidayCheckBox;
    @FXML
    private JFXCheckBox allTraTrbCheckBox;  
    @FXML
    private JFXCheckBox selectAllCheckBox; 
    
    // Other global variables for the class
    public static boolean workingOnCalendar = false;
    public static boolean checkBoxesHaveBeenClicked = false;
    
    @FXML
    private AnchorPane rootPane;
    @FXML
    private VBox colorRootPane;
    @FXML
    private VBox toolsRootPane;
    @FXML
    private VBox centerArea;
    @FXML
    private Label calendarNameLbl;
    
    //**************************************************************************
    //**************************************************************************
    //**************************************************************************
    
    // Events
    private void addEvent(VBox day) {
        
        // Purpose - Add event to a day
        
        // Do not add events to days without labels
        if(!day.getChildren().isEmpty()) {
            
            // Get the day number
            Label lbl = (Label) day.getChildren().get(0);
            System.out.println(lbl.getText());
            
            // Store event day and month in data singleton
            Model.getInstance().event_day = Integer.parseInt(lbl.getText());
            Model.getInstance().event_month = Model.getInstance().getMonthIndex(monthSelect.getSelectionModel().getSelectedItem());      
            Model.getInstance().event_year = Integer.parseInt(selectedYear.getValue());
              Model.getInstance().eventEnd_day = Integer.parseInt(lbl.getText());
            Model.getInstance().eventEnd_month = Model.getInstance().getMonthIndex(monthSelect.getSelectionModel().getSelectedItem());      
            Model.getInstance().eventEnd_year = Integer.parseInt(selectedYear.getValue());
            System.out.println(monthSelect);
            // Open add event view
            try {
               // Load root layout from fxml file.
               FXMLLoader loader = new FXMLLoader();
               loader.setLocation(getClass().getResource("/views/planings/add_event.fxml"));
               AnchorPane rootLayout = (AnchorPane) loader.load();
               Stage stage = new Stage(StageStyle.UNDECORATED);
               stage.initModality(Modality.APPLICATION_MODAL); 

               // Pass main controller reference to view
               AddEventController eventController = loader.getController();
               eventController.setMainController(this);
               
               // Show the scene containing the root layout.
               Scene scene = new Scene(rootLayout);
               stage.setScene(scene);
               stage.show();
                
           } catch (IOException ex) {
               Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
           }
        }
    }    
    
    private void editEvent(VBox day, String descript, String termID) {
        
        // Store event fields in data singleton
        Label dayLbl = (Label)day.getChildren().get(0);
        Model.getInstance().event_day = Integer.parseInt(dayLbl.getText());
        Model.getInstance().eventEnd_day = Integer.parseInt(dayLbl.getText());
        Model.getInstance().event_month = Model.getInstance().getMonthIndex(monthSelect.getSelectionModel().getSelectedItem());      
        Model.getInstance().event_year = Integer.parseInt(selectedYear.getValue());
        Model.getInstance().eventEnd_month = Model.getInstance().getMonthIndex(monthSelect.getSelectionModel().getSelectedItem());      
        Model.getInstance().eventEnd_year = Integer.parseInt(selectedYear.getValue());
        Model.getInstance().event_subject = descript;
        Model.getInstance().event_term_id = Integer.parseInt(termID);

        // When user clicks on any date in the calendar, event editor window opens
        try {
           // Load root layout from fxml file.
           FXMLLoader loader = new FXMLLoader();
           loader.setLocation(getClass().getResource("/views/planings/edit_event.fxml"));
            
           AnchorPane rootLayout = (AnchorPane) loader.load();
           Stage stage = new Stage(StageStyle.UNDECORATED);
           stage.initModality(Modality.APPLICATION_MODAL); 

           // Pass main controller reference to view
           EditEventController eventController = loader.getController();
           eventController.setMainController(this);
           
           // Show the scene containing the root layout.
           Scene scene = new Scene(rootLayout);
           stage.setScene(scene);
           stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }   
    
  
    public void newCalendarEvent() {
        // New Calendar view
         try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/planings/add_calendar.fxml"));
            AnchorPane rootLayout = (AnchorPane) loader.load();
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL); 

            // Pass main controller reference to view
            AddCalendarController calendarController = loader.getController();
            calendarController.setMainController(this);
            
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);
            stage.show();
            
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void listCalendarsEvent() {
        // List Calendar view
         try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/planings/list_calendars.fxml"));
            AnchorPane rootLayout = (AnchorPane) loader.load();
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL); 

            // Pass main controller reference to view
            ListCalendarsController listController = loader.getController();
            listController.setMainController(this);
            
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);
            stage.show();
            
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void manageTermsEvent() {
        // Manage Terms view
         try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/planings/list_terms.fxml"));
            AnchorPane rootLayout = (AnchorPane) loader.load();
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL); 

            // Pass main controller reference to view
            ListTermsController listController = loader.getController();
            listController.setMainController(this);
            
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);
            stage.show();
            
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   
    
   
    
    
    private void initializeMonthSelector(){
        
        // Add event listener to each month list item, allowing user to change months
        monthSelect.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            
           
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//                 System.out.println(monthSelect.getSelectionModel().getSelectedItem());
                    // Necessary to check for null because change listener will
                    // also detect clear() calls
                    if (newValue != null) {
                        
                        // Show selected/current month above calendar
                        monthLabel.setText(newValue);
                        System.out.println("mounth new "+newValue);
                        // Update the VIEWING MONTH;
                       Model.getInstance().viewing_month = Model.getInstance().getMonthIndex(newValue);
                   
                       
                        //Model.getInstance().viewing_month = monthSelect.getSelectionModel().getSelectedIndex();
                        
                        
                       // System.out.println("mounth"+Model.getInstance().viewing_month);

                        // Update view
                        repaintView();
                    }
                    
                }
            });
        
        // Add event listener to each year item, allowing user to change years
        selectedYear.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    
                    if (newValue != null){
                        
                        // Update the VIEWING YEAR
                        Model.getInstance().viewing_year = Integer.parseInt(newValue);
                        
                        // Update view
                        repaintView();
                    }
                }
            });
    }
    
    private void loadCalendarLabels(){
        
        // Get the current VIEW
        int year = Model.getInstance().viewing_year;
        int month = Model.getInstance().viewing_month;
        
        // Note: Java's Gregorian Calendar class gives us the right
        // "first day of the month" for a given calendar & month
        // This accounts for Leap Year
        GregorianCalendar gc = new GregorianCalendar(year, month, 1);
        int firstDay = gc.get(Calendar.DAY_OF_WEEK);
        int daysInMonth = gc.getActualMaximum(Calendar.DAY_OF_MONTH);
        
        // We are "offsetting" our start depending on what the
        // first day of the month is.
        // For example: Sunday start, Monday start, Wednesday start.. etc
        int offset = firstDay;
        int gridCount = 1;
        int lblCount = 1;
        
       // Go through calendar grid
       for(Node node : calendarGrid.getChildren()){
           
           VBox day = (VBox) node;
           
           day.getChildren().clear();
           day.setStyle("-fx-backgroud-color: white");
           day.setStyle("-fx-font: 14px \"System\" ");
           
           // Start placing labels on the first day for the month
           if (gridCount < offset) {
               gridCount++;
               // Darken color of the offset days
               day.setStyle("-fx-background-color: #E9F2F5"); 
           } else {
            
            // Don't place a label if we've reached maximum label for the month
            if (lblCount > daysInMonth) {
                // Instead, darken day color
                day.setStyle("-fx-background-color: #E9F2F5"); 
            } else {
                
                // Make a new day label   
                Label lbl = new Label(Integer.toString(lblCount));
                lbl.setPadding(new Insets(5));
                lbl.setStyle("-fx-text-fill:darkslategray");

                day.getChildren().add(lbl);
            }
               
            lblCount++;           
           }
       }
    }
    
    public void calendarGenerate(){
        
        // Load year selection
        selectedYear.getItems().clear(); // Note: Invokes its change listener
        selectedYear.getItems().add(Integer.toString(Model.getInstance().calendar_start));
        selectedYear.getItems().add(Integer.toString(Model.getInstance().calendar_end));

        // Select the first YEAR as default     
        selectedYear.getSelectionModel().selectFirst();
        
        // Update the VIEWING YEAR
        Model.getInstance().viewing_year = Integer.parseInt(selectedYear.getSelectionModel().getSelectedItem());
        
        // Enable year selection box
        selectedYear.setVisible(true);
        
        // Set calendar name label
        calendarNameLbl.setText(Model.getInstance().calendar_name);
        
        // Get a list of all the months (1-12) in a year
        DateFormatSymbols dateFormat = new DateFormatSymbols();
        String months[] = dateFormat.getMonths();
        String[] spliceMonths = Arrays.copyOfRange(months, 0, 12);
        
        // Load month selection
        monthSelect.getItems().clear();
        monthSelect.getItems().addAll(spliceMonths);   
        
        // Select the first MONTH as default
        monthSelect.getSelectionModel().selectFirst();
        monthLabel.setText(monthSelect.getSelectionModel().getSelectedItem());
        
        // Update the VIEWING MONTH
        Model.getInstance().viewing_month = 
                Model.getInstance().getMonthIndex(monthSelect.getSelectionModel().getSelectedItem());
        
        // Update view
        repaintView();
    }
    
    public void repaintView(){
        // Purpose - To be usable anywhere to update view
        // 1. Correct calendar labels based on Gregorian Calendar 
        // 2. Display events known to database
        
        loadCalendarLabels();
        if (!checkBoxesHaveBeenClicked)
        {
            populateMonthWithEvents();
        }
        else
        {
            ActionEvent actionEvent = new ActionEvent();
            handleCheckBoxAction(actionEvent);
        }
        //populateMonthWithEvents();
    }
    
    private void populateMonthWithEvents(){
        
        // Get viewing calendar
        String calendarName = Model.getInstance().calendar_name;
        String currentMonth = monthLabel.getText();
        
        int currentMonthIndex = Model.getInstance().getMonthIndex(currentMonth) + 1;
        int currentYear = Integer.parseInt(selectedYear.getValue());
        
        // Query to get ALL Events from the selected calendar!!
        String getMonthEventsQuery = "SELECT * From EVENTS WHERE CalendarName='" + calendarName + "'";
        
        // Store the results here
        ResultSet result = databaseHandler.executeQuery(getMonthEventsQuery);
        
        try {
             while(result.next()){
                 
                 // Get date for the event
                 Date eventDate = result.getDate("EventDate");
                 String eventDescript = result.getString("EventDescription");
                 int eventTermID = result.getInt("TermID");
                 
                 // Check for year we have selected
                 if (currentYear == eventDate.toLocalDate().getYear()){
                     // Check for the month we already have selected (we are viewing)
                    if (currentMonthIndex == eventDate.toLocalDate().getMonthValue()){

                        // Get day for the month
                        int day = eventDate.toLocalDate().getDayOfMonth();

                        // Display decription of the event given it's day
                        showDate(day, eventDescript, eventTermID);
                    }         
                 }
             }
        } catch (SQLException ex) {
             Logger.getLogger(AddEventController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public void showDate(int dayNumber, String descript, int termID){
        
        Image img = new Image(getClass().getClassLoader().getResourceAsStream("icons/icon2.png"));
        ImageView imgView = new ImageView();
        imgView.setImage(img);
        
        for (Node node: calendarGrid.getChildren()) {
                
            // Get the current day    
            VBox day = (VBox) node;
            
            // Don't look at any empty days (they at least must have a day label!)
            if (!day.getChildren().isEmpty()) {
                
                // Get the day label for that day
                Label lbl = (Label) day.getChildren().get(0);
                
                // Get the number
                int currentNumber = Integer.parseInt(lbl.getText());
                
                // Did we find a match?
                if (currentNumber == dayNumber) {
                    
                    // Add an event label with the given description
                    Label eventLbl = new Label(descript); 
                    eventLbl.setGraphic(imgView);
                    eventLbl.getStyleClass().add("event-label");
                    
                    // Save the term ID in accessible text
                    eventLbl.setAccessibleText(Integer.toString(termID));
                    System.out.println(eventLbl.getAccessibleText());
                    
                    eventLbl.addEventHandler(MouseEvent.MOUSE_PRESSED, (e)->{
                        editEvent((VBox)eventLbl.getParent(), eventLbl.getText(), eventLbl.getAccessibleText());
                        
                    });
                    
                    // Get term color from term's table
                    String eventRGB = databaseHandler.getTermColor(termID);
                    
                    // Parse for rgb values
                    String[] colors = eventRGB.split("-");
                    String red = colors[0];
                    String green = colors[1];
                    String blue = colors[2];
                    
                    System.out.println("Color; " + red + green + blue);
                                      
                    eventLbl.setStyle("-fx-background-color: rgb(" + red+ 
                            ", " + green + ", " + blue + ", " + 1 + ");");
                    
                    // Stretch to fill box
                    eventLbl.setMaxWidth(Double.MAX_VALUE);
                    
                    // Mouse effects
                    eventLbl.addEventHandler(MouseEvent.MOUSE_ENTERED, (e)->{
                        eventLbl.getScene().setCursor(Cursor.HAND);
                    });
                    
                    eventLbl.addEventHandler(MouseEvent.MOUSE_EXITED, (e)->{
                        eventLbl.getScene().setCursor(Cursor.DEFAULT);
                    });
                    
                    // Add label to calendar
                    day.getChildren().add(eventLbl);
                }
            }
        }
    }
    
    public void exportCalendarPDF()
    {
         TableView<Event> table = new TableView<Event>();
         ObservableList<Event> data =FXCollections.observableArrayList();  
   
        
        double w = 500.00;
        // set width of table view
        table.setPrefWidth(w);
        // set resize policy
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        // intialize columns
        TableColumn<Event,String> term  = new TableColumn<Event,String>("Terme");
        TableColumn<Event,String> subject  = new TableColumn<Event,String>("Evenemets");
        TableColumn<Event,String> date = new TableColumn<Event,String>("Date");
        // set width of columns
        term.setMaxWidth( 1f * Integer.MAX_VALUE * 20 ); // 50% width
        subject.setMaxWidth( 1f * Integer.MAX_VALUE * 60 ); // 50% width
        date.setMaxWidth( 1f * Integer.MAX_VALUE * 20 ); // 50% width
        // 
        term.setCellValueFactory(new PropertyValueFactory<Event,String>("term"));
        subject.setCellValueFactory( new PropertyValueFactory<Event,String>("subject"));
        date.setCellValueFactory(new PropertyValueFactory<Event,String>("date"));
        
        // Add columns to the table
        table.getColumns().add(term);
        table.getColumns().add(subject);
        table.getColumns().add(date);
        
        String calendarName = Model.getInstance().calendar_name;
        
        // Query to get ALL Events from the selected calendar!!
        String getMonthEventsQuery = "SELECT * From EVENTS WHERE CalendarName='" + calendarName + "' ORDER BY EventDate";   
        
        // Store the results here
        ResultSet result = databaseHandler.executeQuery(getMonthEventsQuery);
        
         try {
             
             while(result.next()){
                //initalize temporarily strings
                 String tempTerm="";
                
                 
                //***** Get term, Event Description and Date *****
                
                //Get Event Description
                 String eventDescript = result.getString("EventDescription");
                //Get Term ID for an event
                 int termID = result.getInt("TermID");
                 
                //Get Event Date and format it as day-month-year
                 Date dDate=result.getDate("EventDate");
                 DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                 String eventDate = df.format(dDate);
                 
                  Date fDate=result.getDate("EventEndDate");
                 DateFormat fd = new SimpleDateFormat("M/dd/yyyy");
                 String EventEndDate = fd.format(fDate);
                 
                 //Query that will get the term name based on a term ID
                 String getTermQuery = "SELECT TermName FROM TERMS WHERE TermID=" + termID + ""; 
                 //Execute query to get TermName and store it in a ResultSet variable
                 ResultSet termResult = databaseHandler.executeQuery(getTermQuery);
                 
                 
                 while(termResult.next())
                 {
                      tempTerm=termResult.getString(1);
                     /*
                      while(programResult.next())
                        {
                           tempProgram = programResult.getString(1);
                        }
                      tempTerm+=" "+tempProgram;
                    */
                 }
                 
                
                //Get Term Name for an event
                //tempTerm = termResult.getString(1);
                
                
                //Add event information in a row
                data.add(new Event(tempTerm,eventDescript,eventDate,EventEndDate));
             
             }
        } catch (SQLException ex) {
             Logger.getLogger(AddEventController.class.getName()).log(Level.SEVERE, null, ex);
        } 
         
         
       
        table.getItems().setAll(data);
        // open dialog window and export table as pdf
        PrinterJob job = PrinterJob.createPrinterJob();
        if(job != null){
          job.printPage(table);
          job.endJob();
        }
       }
    
    
     public void exportCalendarExcel() 
    {
        
         FileChooser fileChooser = new FileChooser();

              //Set extension filter
              FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("excel files (*.xlsx)", "*.xlsx");
              fileChooser.getExtensionFilters().add(extFilter);

              //Show save file dialog
              File file = fileChooser.showSaveDialog(new Stage());

              if(file != null){
                  createExcelSheet(file);
                  System.out.println("hi");
              }
    }        
   public void createExcelSheet(File file){
        String calendarName = Model.getInstance().calendar_name;
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet =wb.createSheet(calendarName);
        
        XSSFRow row = sheet.createRow(1);
        XSSFCell cell;
        cell = row.createCell(0);
       
         XSSFCellStyle style1 = wb.createCellStyle();
    style1.setFillForegroundColor(new XSSFColor(new java.awt.Color(128, 0, 128)));
    style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
     cell.setCellStyle(style1);
     cell.setCellValue("Calendrier nom");
        cell = row.createCell(1);
        cell.setCellStyle(style1);
        cell.setCellValue("Terme d'évenement");
        cell = row.createCell(2);
         cell.setCellStyle(style1);
        cell.setCellValue("Discreption d'évenement");
        cell = row.createCell(3);
         cell.setCellStyle(style1);
        cell.setCellValue("Date ");
        
        // Query to get ALL Events from the selected calendar!!
        String getMonthEventsQuery = "SELECT * From EVENTS WHERE CalendarName='" + calendarName + "' ORDER BY EventDate ";   
        
        // Store the results here
        ResultSet result = databaseHandler.executeQuery(getMonthEventsQuery);
        
         try {
             int counter=2;
             while(result.next()){
                
                 //initalize temporarily strings
                 String tempTerm="";
                
                 
                //***** Get term, Event Description and Date *****
                
                //Get Event Description
              
                 String eventDescript = result.getString("EventDescription");
                //Get Term ID for an event
                 int termID = result.getInt("TermID");
                 
                //Get Event Date and format it as day-month-year
                 Date dDate=result.getDate("EventDate");
                 DateFormat df = new SimpleDateFormat("M/dd/yyyy");
                 String eventDate = df.format(dDate);
                 
                   Date fDate=result.getDate("EventEndDate");
                 DateFormat fd = new SimpleDateFormat("M/dd/yyyy");
                 String EventEndDate = fd.format(fDate);
                 
                 //Query that will get the term name based on a term ID
                 String getTermQuery = "SELECT TermName FROM TERMS WHERE TermID=" + termID + "";
                 //Execute query to get TermName and store it in a ResultSet variable
                 ResultSet termResult = databaseHandler.executeQuery(getTermQuery);
                 
                /* 
                //initalize temporarily strings
                 String tempTerm="";
                 String tempProgram="";
                 // Get term, program, Event Description and Date
                 
                 String eventDescript = result.getString("EventDescription");
                 int termID = result.getInt("TermID");
                 
                 
                 Date dDate=result.getDate("EventDate");
                 DateFormat df = new SimpleDateFormat("dd MMMM yyyy");
                 String eventDate = df.format(dDate);
                 
                 int programID = result.getInt("ProgramID");
                 String getProgramQuery = "SELECT ProgramName FROM PROGRAMS WHERE ProgramID=" + programID + "";
                 String getTermQuery = "SELECT TermName FROM TERMS WHERE TermID=" + termID + ""; 
                 ResultSet termResult = databaseHandler.executeQuery(getTermQuery);
                 ResultSet programResult = databaseHandler.executeQuery(getProgramQuery);
                 */
                
                 while(termResult.next())
                 {
                      tempTerm=termResult.getString(1);
                     
                      /*
                      while(programResult.next())
                        {
                           tempProgram = programResult.getString(1);
                        }
                      tempTerm+=" "+tempProgram;
                      */
                 }
                
                //Get Term Name for an event
                //tempTerm = termResult.getString("TermName");
                
                
                row = sheet.createRow(counter);
                 cell = row.createCell(0);
                 cell.setCellValue(calendarName);
                cell = row.createCell(1);
                cell.setCellValue(tempTerm);
                cell = row.createCell(2);
                cell.setCellValue(eventDescript);
                cell = row.createCell(3);
                cell.setCellValue(eventDate);
              for (int i=0; i<3; i++){
               sheet.autoSizeColumn(i);
            }
               
                counter++;
             
             }
        } catch (SQLException ex) {
             Logger.getLogger(AddEventController.class.getName()).log(Level.SEVERE, null, ex);
        } 
         try{
        FileOutputStream out = new FileOutputStream(file);
        wb.write(out);
        out.flush();
        out.close();
        
       
         }
         catch(Exception e) {
            e.printStackTrace();
         }  
       }
   
    private String getRGB(Color c){
        
        String rgb = Integer.toString((int)(c.getRed() * 255)) + "-"
                + Integer.toString((int)(c.getGreen() * 255)) + "-"
                + Integer.toString((int)(c.getBlue() * 255));
        
        return rgb;       
    }
    
    private void changeColors(){
        
        // Purpose - Take all chosen colors in the Colors menu and
        // update each term's color in the database
        
        Color vaccanceColor = vaccance.getValue();
        String vaccRGB = getRGB(vaccanceColor);
        databaseHandler.setTermColor("vaccance", vaccRGB);
        
        Color travailColor = travail.getValue();
        String travailRGB = getRGB(travailColor);
        databaseHandler.setTermColor("travail", travailRGB);
        
        Color AnnivColor = anniverssaire.getValue();
        String summerSemRGB = getRGB(AnnivColor);
        databaseHandler.setTermColor("annivessaire", summerSemRGB);
        
        Color formationColor = formation.getValue();
        String formationRGB = getRGB(formationColor);
        databaseHandler.setTermColor("formation", formationRGB);
        
        Color workshopColor = workshop.getValue();
        String workshopRGB = getRGB(workshopColor);
        databaseHandler.setTermColor("workshop", workshopRGB);
        
        Color certifColor = certif.getValue();
        String certifRGB = getRGB(certifColor);
        databaseHandler.setTermColor("certif", certifRGB);
        
        Color importantColor = important.getValue();
        String importantRGB = getRGB(importantColor);
        databaseHandler.setTermColor("important", importantRGB);
        
        Color urgentColor = urgent.getValue();
        String allHolidayRGB = getRGB(urgentColor);
        databaseHandler.setTermColor("urgent", allHolidayRGB);
        
       
        
    }
    
    private void initalizeColorPicker(){
        
        String vacc = databaseHandler.getTermColor(databaseHandler.getTermID("vaccance"));
        String work = databaseHandler.getTermColor(databaseHandler.getTermID("travail"));
        String annif = databaseHandler.getTermColor(databaseHandler.getTermID("annivessaire"));
        String form = databaseHandler.getTermColor(databaseHandler.getTermID("formation"));
        String worksh = databaseHandler.getTermColor(databaseHandler.getTermID("workshop"));
        String cert = databaseHandler.getTermColor(databaseHandler.getTermID("certif"));
        String importnt = databaseHandler.getTermColor(databaseHandler.getTermID("important"));        
        String urg = databaseHandler.getTermColor(databaseHandler.getTermID("urgent"));
    
        
        // Parse for rgb values for Vaccance
        String[] colors = vacc.split("-");
        String red = colors[0]; String green = colors[1]; String blue = colors[2];
        Color c = Color.rgb(Integer.parseInt(red),Integer.parseInt(green) ,Integer.parseInt(blue));
        vaccance.setValue(c);
        
        // Parse for rgb values for Travail
        colors = annif.split("-");
        red = colors[0]; green = colors[1]; blue = colors[2];
        c = Color.rgb(Integer.parseInt(red),Integer.parseInt(green) ,Integer.parseInt(blue));
        travail.setValue(c);
        
        // Parse for rgb values for Annivessaire
        colors = work.split("-");
        red = colors[0]; green = colors[1]; blue = colors[2];
        c = Color.rgb(Integer.parseInt(red),Integer.parseInt(green) ,Integer.parseInt(blue));
        anniverssaire.setValue(c);
        
        // Parse for rgb values for Formation
        colors = form.split("-");
        red = colors[0]; green = colors[1]; blue = colors[2];
        c = Color.rgb(Integer.parseInt(red),Integer.parseInt(green) ,Integer.parseInt(blue));
        formation.setValue(c);
        
        // Parse for rgb values for Workshop
        colors = worksh.split("-");
        red = colors[0]; green = colors[1]; blue = colors[2];
        c = Color.rgb(Integer.parseInt(red),Integer.parseInt(green) ,Integer.parseInt(blue));
        workshop.setValue(c);
        
        // Parse for rgb values for Certif
        colors = cert.split("-");
        red = colors[0]; green = colors[1]; blue = colors[2];
        c = Color.rgb(Integer.parseInt(red),Integer.parseInt(green) ,Integer.parseInt(blue));
        certif.setValue(c);
        
        // Parse for rgb values for Important
        colors = importnt.split("-");
        red = colors[0]; green = colors[1]; blue = colors[2];
        c = Color.rgb(Integer.parseInt(red),Integer.parseInt(green) ,Integer.parseInt(blue));
        important.setValue(c);
        
        // Parse for rgb values for Urgent
        colors = urg.split("-");
        red = colors[0]; green = colors[1]; blue = colors[2];
        c = Color.rgb(Integer.parseInt(red),Integer.parseInt(green) ,Integer.parseInt(blue));
        urgent.setValue(c);
        
       
        
    }
   
    public void initializeCalendarGrid(){
        
        // Go through each calendar grid location, or each "day" (7x6)
        int rows = 6;
        int cols = 7;
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                
                // Add VBox and style it
                VBox vPane = new VBox();
                vPane.getStyleClass().add("calendar_pane");
                vPane.setMinWidth(weekdayHeader.getPrefWidth()/7);
                
                vPane.addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->{
                    addEvent(vPane);
                });
                
                GridPane.setVgrow(vPane, Priority.ALWAYS);
                
                // Add it to the grid
                calendarGrid.add(vPane, j, i);  
            }
        }       
        
        // Set up Row Constraints
        for (int i = 0; i < 7; i++) {
         RowConstraints row = new RowConstraints();
         row.setMinHeight(scrollPane.getHeight()/7);
         calendarGrid.getRowConstraints().add(row);
        }
    }

    
    public void initializeCalendarWeekdayHeader(){
    
        // 7 days in a week
        int weekdays = 7;
        
        // Weekday names
        String[] weekAbbr = {"Sun","Mon","Tue", "Wed", "Thu", "Fri", "Sat"};
        
        for (int i = 0; i < weekdays; i++){
            
            // Make new pane and label of weekday
            StackPane pane = new StackPane();
            pane.getStyleClass().add("weekday-header");
            
            // Make panes take up equal space
            HBox.setHgrow(pane, Priority.ALWAYS);
            pane.setMaxWidth(Double.MAX_VALUE);
            
            // Note: After adding a label to this, it tries to resize itself..
            // So I'm setting a minimum width.
            pane.setMinWidth(weekdayHeader.getPrefWidth()/7);
            
            // Add it to the header
            weekdayHeader.getChildren().add(pane);
            
            // Add weekday name
            pane.getChildren().add(new Label(weekAbbr[i]));
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // Make empty calendar
        initializeCalendarGrid();
        initializeCalendarWeekdayHeader();
        initializeMonthSelector();
    
        // Set Depths
        JFXDepthManager.setDepth(scrollPane, 1);

        //*** Instantiate DBHandler object *******************
        databaseHandler = new ConnexionUtil();
        //****************************************************

        //Initialize all Color Pickers. Show saved colors for specific terms
        initalizeColorPicker();
     
        //If the user is not working on any new or existing calendar, disable the filtering check boxes and rules buttons
        disableCheckBoxes();
        // I am still working on this function and issue
        //disableButtons();  
        
    }

    //**********************************************************************************
    //**********************************************************************************
    //**********************************************************************************
    
    // Side - menu buttons 
    @FXML
    private void newCalendar(MouseEvent event) {
        newCalendarEvent();
    }
    
    @FXML
    private void manageCalendars(MouseEvent event) {
        listCalendarsEvent();
    }
    
   

    @FXML
    private void pdfBtn(MouseEvent event) {
        exportCalendarPDF();
    }

    @FXML
    private void excelBtn(MouseEvent event) {
        exportCalendarExcel();
    }

    @FXML
    private void updateColors(MouseEvent event) {
        changeColors();
        
        if (Model.getInstance().calendar_name != null)
            repaintView();
    }

    @FXML
    private void manageTermDates(MouseEvent event) {
        manageTermsEvent();
    }

    //******************************************************************************************
    //******************************************************************************************
    //******************************************************************************************
    
     ///******* I am still working on these functions and issues  ********
    /*
    public void disableButtons(){
        
        manageRulesButton.setDisable(true);
    }
    
    public void enableButtons(){
        
        manageRulesButton.setDisable(false);
    }
    */
    
    public void disableCheckBoxes(){
        
        //Disable all check boxes for filtering events
        fallSemCheckBox.setDisable(true);
        springSemCheckBox.setDisable(true);
        summerSemCheckBox.setDisable(true);
        allQtrCheckBox.setDisable(true);
        allMbaCheckBox.setDisable(true);
        allHalfCheckBox.setDisable(true);
        allCampusCheckBox.setDisable(true);
        allHolidayCheckBox.setDisable(true);
        fallSemCheckBox.setDisable(true);
       // allTraTrbCheckBox.setDisable(true);
        selectAllCheckBox.setDisable(true);
    }
    
    public void enableCheckBoxes(){
        
        //Enable all check boxes for filtering events
        fallSemCheckBox.setDisable(false);
        springSemCheckBox.setDisable(false);
        summerSemCheckBox.setDisable(false);
        allQtrCheckBox.setDisable(false);
        allMbaCheckBox.setDisable(false);
        allHalfCheckBox.setDisable(false);
        allCampusCheckBox.setDisable(false);
        allHolidayCheckBox.setDisable(false);
        fallSemCheckBox.setDisable(false);
//        allTraTrbCheckBox.setDisable(false);
        selectAllCheckBox.setDisable(false);
        //Set selection for select all check box to true
        selectAllCheckBox.setSelected(true);
    }
    
    public void selectCheckBoxes(){
        
        //Set ALL check boxes for filtering events to be selected
        fallSemCheckBox.setSelected(true);
        springSemCheckBox.setSelected(true);
        summerSemCheckBox.setSelected(true);
        allQtrCheckBox.setSelected(true);
        allMbaCheckBox.setSelected(true);
        allHalfCheckBox.setSelected(true);
        allCampusCheckBox.setSelected(true);
        allHolidayCheckBox.setSelected(true);
        fallSemCheckBox.setSelected(true);
//        allTraTrbCheckBox.setSelected(true);
    }
    
    public void unSelectCheckBoxes(){
        
        //Set ALL check boxes for filtering events to be selected
        fallSemCheckBox.setSelected(false);
        springSemCheckBox.setSelected(false);
        summerSemCheckBox.setSelected(false);
        allQtrCheckBox.setSelected(false);
        allMbaCheckBox.setSelected(false);
        allHalfCheckBox.setSelected(false);
        allCampusCheckBox.setSelected(false);
        allHolidayCheckBox.setSelected(false);
        fallSemCheckBox.setSelected(false);
//        allTraTrbCheckBox.setSelected(false);
    }
    
    //******************************************************************************************
    //******************************************************************************************
    //******************************************************************************************
    
    //Function filters all events. Make them all show up or disappear from the calendar
    @FXML
    private void selectAllCheckBoxes(ActionEvent e)
    {
        if (selectAllCheckBox.isSelected())
        {
            selectCheckBoxes();
        }
        else
        {
            unSelectCheckBoxes();
        }
        
        handleCheckBoxAction(new ActionEvent());
    }
    
    
    
    //This function is constantly checking if any of the checkboxes is selected or deselected
    //and therefore, populate the calendar with the events of the terms that are selected
    
    @FXML
    private void handleCheckBoxAction(ActionEvent e)
    {
        System.out.println("have check boxes been cliked: " + checkBoxesHaveBeenClicked);
        if (!checkBoxesHaveBeenClicked)
        {
            checkBoxesHaveBeenClicked = true;
            System.out.println("have check boxes been cliked: " + checkBoxesHaveBeenClicked);
        }
        
        //ArrayList that will hold all the filtered events based on the selection of what terms are visible
        ArrayList<String> termsToFilter = new ArrayList();
        
        //Check each of the checkboxes and call the appropiate queries to
        //show only the events that belong to the term(s) the user selects
        
        //vaccance
        if (fallSemCheckBox.isSelected())
        {
            System.out.println("vaccance checkbox is selected");
            termsToFilter.add("vaccance");
        }
        
        if (!fallSemCheckBox.isSelected())
        {
            System.out.println("vaccance checkbox is now deselected");
            int auxIndex = termsToFilter.indexOf("vaccance");
            if (auxIndex != -1)
            {
                termsToFilter.remove(auxIndex);
            }
        }
        
        
        //travail
        if (springSemCheckBox.isSelected())
        {
            System.out.println("travail Sem checkbox is selected");
            termsToFilter.add("travail");
        }
        if (!springSemCheckBox.isSelected())
        {
            System.out.println("travail checkbox is now deselected");
            int auxIndex = termsToFilter.indexOf("travail");
            if (auxIndex != -1)
            {
                termsToFilter.remove(auxIndex);
            }
        }
        
        //annivessaire
        if (summerSemCheckBox.isSelected())
        {
            System.out.println("annivessaire checkbox is selected");
            termsToFilter.add("annivessaire");
        }
        if (!summerSemCheckBox.isSelected())
        {
            System.out.println("annivessaire checkbox is now deselected");
            int auxIndex = termsToFilter.indexOf("annivessaire");
            if (auxIndex != -1)
            {
                termsToFilter.remove(auxIndex);
            }
        }
        
        //formation
        if (allQtrCheckBox.isSelected())
        {
            System.out.println("formation checkbox is selected");
            termsToFilter.add("formation");
        }
        if (!allQtrCheckBox.isSelected())
        {
            System.out.println("formation checkbox is now deselected");
            int auxIndex = termsToFilter.indexOf("formation");
            if (auxIndex != -1)
            {
                termsToFilter.remove(auxIndex);
            }
        }
        
        // certif
        if (allHalfCheckBox.isSelected())
        {
            System.out.println("certif checkbox is selected");
            termsToFilter.add("certif");
        }
        if (!allHalfCheckBox.isSelected())
        {
            System.out.println("importantcheckbox is now deselected");
            int auxIndex = termsToFilter.indexOf("certif");
            if (auxIndex != -1)
            {
                termsToFilter.remove(auxIndex);
            }
        }
        
        // imporatnt
        if (allCampusCheckBox.isSelected())
        {
            System.out.println("important checkbox is selected");
            termsToFilter.add("");
        }
        if (!allCampusCheckBox.isSelected())
        {
            System.out.println("important checkbox is now deselected");
            int auxIndex = termsToFilter.indexOf("important");
            if (auxIndex != -1)
            {
                termsToFilter.remove(auxIndex);
            }
        }
        
        
        // urgent
        if (allHolidayCheckBox.isSelected())
        {
            System.out.println("urgent checkbox is selected");
            termsToFilter.add("urgent");
        }
        if (!allHolidayCheckBox.isSelected())
        {
            System.out.println("urgent checkbox is now deselected");
            int auxIndex = termsToFilter.indexOf("urgent");
            if (auxIndex != -1)
            {
                termsToFilter.remove(auxIndex);
            }
        }
        
//        // workshop
        if (allMbaCheckBox.isSelected())
        {
            System.out.println("Workshop checkbox is selected");
            termsToFilter.add("workshop");
        }
        if (!allMbaCheckBox.isSelected())
        {
            System.out.println("workshop checkbox is now deselected");
            int auxIndex = termsToFilter.indexOf("workshop");
            if (auxIndex != -1)
            {
                termsToFilter.remove(auxIndex);
            }
        }
//       
//        // All TRA/TRB
//        if (allTraTrbCheckBox.isSelected())
//        {
//            System.out.println("All TRA/TRB checkbox is selected");
//            termsToFilter.add("TRA");
//            termsToFilter.add("TRB");
//        }
//        if (!allTraTrbCheckBox.isSelected())
//        {
//            System.out.println("All Holiday checkbox is now deselected");
//            int auxIndex = termsToFilter.indexOf("TRA");
//            int auxIndex2 = termsToFilter.indexOf("TRB");
//            if (auxIndex != -1)
//            {
//                termsToFilter.remove(auxIndex);
//            }
//            if (auxIndex2 != -1)
//            {
//                termsToFilter.remove(auxIndex2);
//            }
//        }
//        
        
        System.out.println("terms to filter list: " + termsToFilter);
        
        //Get name of the current calendar that the user is working on
        String calName = Model.getInstance().calendar_name;
        
        System.out.println("and calendarName is: " + calName);
        
        if (termsToFilter.isEmpty())
        {
            System.out.println("terms are not selected. No events have to appear on calendar. Just call loadCalendarLabels method in the RepaintView method");
            selectAllCheckBox.setSelected(false);
            loadCalendarLabels();
        }
        else
        {
            System.out.println("Call the appropiate function to populate the month with the filtered events");
            //Get List of Filtered Events and store all events in an ArrayList variable
            ArrayList<String> filteredEventsList = databaseHandler.getFilteredEvents(termsToFilter, calName);
            
            System.out.println("List of Filtered events is: " + filteredEventsList);
        
            //Repaint or reload the events based on the selected terms
            showFilteredEventsInMonth(filteredEventsList);
        }
        
    
    }
    
    
    
    public void showFilteredEventsInMonth(ArrayList<String> filteredEventsList) {
        
        System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
        System.out.println("I am in the show filtered events in month function");
        System.out.println("The list of filted events is: " + filteredEventsList);
        System.out.println("****------******-------******--------");
        
        
        
        String calendarName = Model.getInstance().calendar_name;
        
        String currentMonth = monthLabel.getText();
        System.out.println("currentMonth is: " + currentMonth);
        int currentMonthIndex = Model.getInstance().getMonthIndex(currentMonth) + 1;
        System.out.println("currentMonthIndex is: " + currentMonthIndex);
        
        int currentYear = Integer.parseInt(selectedYear.getValue());
        System.out.println("CurrentYear is: " + currentYear);
        System.out.println("****------******-------******--------");
        System.out.println("****------******-------******--------");
        
       
        System.out.println("Now the labels on the calendar are cleared");
        loadCalendarLabels();
        System.out.println("****------******-------******--------");
        System.out.println("****------******-------******--------");
        System.out.println("Now, the filtered events/labels will be shown/put on the calendar");
        System.out.println("****------******-------******--------");
        
        for (int i=0; i < filteredEventsList.size(); i++)
        {
            String[] eventInfo = filteredEventsList.get(i).split("~");
            String eventDescript = eventInfo[0];
            String eventDate = eventInfo[1];
            int eventTermID = Integer.parseInt(eventInfo[2]);
            String eventCalName = eventInfo[3];
            System.out.println(eventDescript);
            System.out.println(eventDate);
            System.out.println(eventTermID);
            System.out.println(eventCalName);
            
            String[] dateParts = eventDate.split("-");
            int eventYear = Integer.parseInt(dateParts[0]);
            int eventMonth = Integer.parseInt(dateParts[1]);
            int eventDay = Integer.parseInt(dateParts[2]);
            
            
            System.out.println("****------******-------******--------");
            System.out.println("Now I will check if currentYear equals eventYear. Result is:");
            if (currentYear == eventYear)
            {
                System.out.println("Yes, both year match.");
                System.out.println("Now I will check if the month index equals the event's month. REsult is");
                if (currentMonthIndex == eventMonth)
                {
                    System.out.println("Yes they are the same. Now I will call showDate function");
                    showDate(eventDay, eventDescript, eventTermID);
                }
            }
        }
    }

    
    
    @FXML
    private void deleteAllEvents(MouseEvent event){
        
        //Show confirmation dialog to make sure the user want to delete the selected rule
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("All Events Deletion");
        alert.setContentText("Are you sure you want to delete all events in this calendar?");
        //Customize the buttons in the confirmation dialog
        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");
        //Set buttons onto the confirmation dialog
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        
        //Get the user's answer on whether deleting or not
        Optional<ButtonType> result = alert.showAndWait();
        
        //If the user wants to delete the calendar, call the function that deletes the calendar. Otherwise, close the window
        if (result.get() == buttonTypeYes){
            deleteAllEventsInCalendar();
        } 
        
        
    }
    
    // Function that deletes all the events in the current calendar
    public void deleteAllEventsInCalendar() {
        
        //Variable that holds the name of the current calendar
        String calName = Model.getInstance().calendar_name;
        
        //Query that will delete all events that belong to the selected calendar
        String deleteAllEventsQuery = "DELETE FROM EVENTS "
                                 + "WHERE EVENTS.CalendarName='" + calName + "'";
        
        //Execute query that deletes all events associated to the selected calendar
        boolean eventsWereDeleted = databaseHandler.executeAction(deleteAllEventsQuery);
        
        //Check if events were successfully deleted and indicate the user if so
        if (eventsWereDeleted)
        {
            //Update the calendar. Show that events were actually deleted
            repaintView();
            
            //Show message indicating that the selected calendar was deleted
            Alert alertMessage = new Alert(Alert.AlertType.INFORMATION);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("All events were successfully deleted");
            alertMessage.showAndWait();
        }
        else
        {
            //Show message indicating that the calendar could not be deleted
            Alert alertMessage = new Alert(Alert.AlertType.ERROR);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("Deleting Events Failed!");
            alertMessage.showAndWait();
        }
    }
   
} //End of FXMLDocumentController class
