package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import model.da.PersonDa;
import model.entity.City;
import model.entity.Gender;
import model.entity.Person;
import sun.security.x509.OtherName;


import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class PersonController implements Initializable {

    @FXML
    private TextField idTxt,nameTxt,familyTxt;

    @FXML
    private DatePicker birthdateTxt;

    @FXML
    private CheckBox maleChk, femaleChk;

    @FXML
    private ToggleGroup GenderToggle;

    @FXML
    private ComboBox cityCmb;

    @FXML
    private CheckBox seChk, eeChk;

    @FXML
    private Button saveBtn,editBtn,removeBtn;

    @FXML
    private TableView<Person> personTbl;

    @FXML
    private TableColumn<Person,String> nameCol,familyCol,genderCol,cityCol;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        for(City city : City.values()){
            cityCmb.getItems().add(city);
        }

        resetForm();

        ///////////////////SAVE//////////////////////////
        saveBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                try {

                    PersonDa personDa = new PersonDa();
                    RadioButton selectedRadioButton = (RadioButton) GenderToggle.getSelectedToggle();

                    Person person = Person
                            .builder()
                            .name(nameTxt.getText())
                            .family(familyTxt.getText())
                            .birthdate(birthdateTxt.getValue())
                            .gender(Gender.valueOf(selectedRadioButton.getText()))
                            .city(City.valueOf(cityCmb.getSelectionModel().getSelectedItem().toString()))
                            .seSkill(seChk.isSelected())
                            .eeSkill(eeChk.isSelected())
                            .build();

                    personDa.savePerson(person);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Person Saved");
                    alert.show();

                    resetForm();

                } catch (SQLException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Person Saved error");
                    alert.show();
                    //throw new RuntimeException(e);
                }

            }
        });


        ////////////////EDIT//////////////////
        editBtn.setOnAction(new EventHandler() {

            @Override
            public void handle(Event event) {

                try {
                    PersonDa personDa = new PersonDa();
                    RadioButton selectedRadioButton = (RadioButton) GenderToggle.getSelectedToggle();

                    Person person = Person
                            .builder()
                            .id(Integer.parseInt(idTxt.getText()))
                            .name(nameTxt.getText())
                            .family(familyTxt.getText())
                            .birthdate(birthdateTxt.getValue())
                            .gender(Gender.valueOf(selectedRadioButton.getText()))
                            .city(City.valueOf(cityCmb.getSelectionModel().getSelectedItem().toString()))
                            .seSkill(seChk.isSelected())
                            .eeSkill(eeChk.isSelected())
                            .build();

                    personDa.editPerson(person);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Person Edited");
                    alert.show();

                    //resetForm();

                } catch (SQLException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Person Edited error");
                    alert.show();
                    //throw new RuntimeException(e);
                }
            }

        });

        removeBtn.setOnAction(new EventHandler() {

            @Override
            public void handle(Event event) {

                try {
                    Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this person?");
                    if (confirmAlert.showAndWait().get() == ButtonType.OK) {
                        PersonDa personDa = new PersonDa();
                        personDa.deletePerson(Integer.parseInt(idTxt.getText()));
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Person Removed");
                        alert.show();
                        resetForm();
                    }

                } catch (SQLException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Person Removed error");
                    alert.show();
                    //throw new RuntimeException(e);
                }

            }
        });

        personTbl.setOnMouseReleased(event -> {
            Person person = personTbl.getSelectionModel().getSelectedItem();
            idTxt.setText(String.valueOf(person.getId()));
            nameTxt.setText(person.getName());
            familyTxt.setText(person.getFamily());
//            if (person.getGender().equals("Male")) {
//                maleChk.setSelected(true);
//            }
//            else{
//                femaleChk.setSelected(false);
//            }
            birthdateTxt.setValue(person.getBirthdate());
            cityCmb.getSelectionModel().select(person.getCity());
            if (person.isSeSkill()) {
                seChk.setSelected(true);}
            else{
                seChk.setSelected(false);
            }
            if (person.isEeSkill()) {
                seChk.setSelected(true);}
            else{
                seChk.setSelected(false);
            }



        });


    }

    private void resetForm(){
        idTxt.clear();
        nameTxt.clear();
        familyTxt.clear();
        birthdateTxt.setValue(LocalDate.now());
        cityCmb.getSelectionModel().select(0);
        //maleChk.setSelected(true);
        seChk.setSelected(false);
        eeChk.setSelected(false);
        try {
            PersonDa personDa = new PersonDa();
            refreshTable(personDa.getAllPersons());

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Find Persons Error\n" + e.getMessage());
            alert.show();
        }

    }

    private void refreshTable(List<Person> personList){

        ObservableList<Person> persons = FXCollections.observableList(personList);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        familyCol.setCellValueFactory(new PropertyValueFactory<>("family"));
        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        cityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
        personTbl.setItems(persons);

    }

}


