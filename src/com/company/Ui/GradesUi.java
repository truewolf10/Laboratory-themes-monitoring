package com.company.Ui;

import com.company.Controller.ObservableService;
import com.company.Controller.ServiceException;
import com.company.Domain.Grade;
import com.company.Domain.Student;
import com.company.Domain.TemaLab;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.AccessibleRole;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GradesUi {
    public TableColumn<Grade, String> idGradeCol;
    public TableColumn<Grade, String> idStudentCol;
    public TableColumn<Grade, String> idLaboratoryCol;
    public TableColumn<Grade, String> valueCol;
    public TableColumn<Grade, String> dateCol;
    public Button addBtn;
    public Button deleteBtn;
    public Button setBtn;
    public TextField idGradeField;
    public ComboBox<String> studentComboBox;
    public ComboBox<String> labComboBox;
    public TextField valueField;
    public TextField dateField;
    public TableView<Grade> tableView;
    public CheckBox idCheck;
    public TextField idField;
    private ObservableList<Grade> list;
    private ObservableList<Grade> filteredList;
    private ObservableService service;
    private boolean editMode;




    public static Stage create(Application app, ObservableService service){
        FXMLLoader fxmlLoader = new FXMLLoader();
        Stage stage = new Stage();


//        String css = GradesUi.class.getResource("Ui/Cssuri/buttons.css").toExternalForm();

        fxmlLoader.setLocation(app.getClass().getResource("Ui/grades.fxml"));
        Scene scene = null;

        try{
            scene = new Scene(fxmlLoader.load(),700, 600);
            ((GradesUi)fxmlLoader.getController()).initialize(service);
        } catch (IOException e) {
            e.printStackTrace();
        }


        stage.setScene(scene);
        return stage;
    }

    private void initialize(ObservableService observableService){
        service = observableService;
        service.addEventHandler(this::gradesChanged);
        service.triggerEvents();
        itemsChanged(null);
    }


    private void gradesChanged(ActionEvent event) {
        tableView.getItems().clear();
        for (Grade grade : service.getGrades()) {
            tableView.getItems().add(grade);
            list.add(grade);
        }
    }


    @FXML
    public void initialize(){
        list = FXCollections.observableArrayList();
        filteredList = FXCollections.observableArrayList();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView.getSelectionModel().getSelectedItems().addListener(this::itemsChanged);

        deleteBtn.setDisable(true);
        setBtn.setDisable(true);
        idCheck.setOnAction(this::filterChanged);

        addBtn.setOnAction(this::addBtnPressed);
        deleteBtn.setOnAction(this::deleteBtnPressed);
        setBtn.setOnAction(this::setBtnPressed);

        idGradeCol.setCellValueFactory(x -> new ReadOnlyObjectWrapper<>(x.getValue().getId().toString()));
        idLaboratoryCol.setCellValueFactory(x -> new ReadOnlyObjectWrapper<>(String.valueOf(x.getValue().getIdLaborator())));
        idStudentCol.setCellValueFactory(x -> new ReadOnlyObjectWrapper<>(String.valueOf(x.getValue().getIdStudent())));
        valueCol.setCellValueFactory(x -> new ReadOnlyObjectWrapper<>(String.valueOf(x.getValue().getValoare() != null ? x.getValue().getValoare() : "")));
        dateCol.setCellValueFactory(x -> new ReadOnlyObjectWrapper<>(String.valueOf(x.getValue().getDate() != null ? x.getValue().getDate() : "")));
        tableView.setItems(filteredList);
        //list.addListener(this::listChanged);
    }


//    public void listChanged(ListChangeListener.Change<? extends Grade> c) {
//
//    }

    private void filterChanged(ActionEvent event) {

        if (idCheck.isSelected()){
            filteredList = FXCollections.observableArrayList((ArrayList<Grade>) service.filtrareGrade(Integer.parseInt(idField.getText())));
            tableView.setItems(filteredList);
        }
        else{
            tableView.setItems(list);
        }
    }

    public void itemsChanged(ListChangeListener.Change<? extends Grade> grades) {
        int size = 0;
        if( grades != null)
            size = grades.getList().size();

        deleteBtn.setDisable(size < 1);

        if (size == 1){
            show(true, grades.getList().get(0));
            setBtn.setDisable(false);
            valueField.setDisable(false);
            dateField.setDisable(false);
        }
        else{
            show(false, null);
            setBtn.setDisable(true);
            valueField.setDisable(true);
            dateField.setDisable(true);
        }
    }

    private void show(boolean edit, Grade grade){

        idGradeField.setDisable(edit);
        studentComboBox.setDisable(edit);
        labComboBox.setDisable(edit);

        if (edit){
            idGradeField.setText(grade.getId().toString());
        }
        else{
            idGradeField.setText("");
            ObservableList<String> studentsList = FXCollections.observableArrayList();
            ObservableList<String> labsList = FXCollections.observableArrayList();

            for (Student stud : service.getStudents())
                studentsList.add(stud.getId() + " - " + stud.getNume());

            for (TemaLab temaLab : service.getLaboratories())
                labsList.add("Laboratory " + temaLab.getId());

            studentComboBox.setItems(studentsList);
            labComboBox.setItems(labsList);

        }

        editMode = edit;
    }

    private void setBtnPressed(ActionEvent actionEvent) {
        //String[] studentdId = studentComboBox.getSelectionModel().getSelectedItem().split(" ");
        Grade selectedGrade=tableView.getSelectionModel().getSelectedItem();
        //String[] studentdId = studentComboBox.getValue().split(" ");
        //String[] laboratoryId = labComboBox.getValue().split(" ");
        try {
             service.setGrade(Integer.parseInt(idGradeField.getText()),
                    selectedGrade.getIdStudent(), selectedGrade.getIdLaborator(),
                    Double.parseDouble(valueField.getText()),Integer.parseInt(dateField.getText()));
        } catch (ServiceException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CLOSE);
            alert.showAndWait();
        }
    }


    private void addBtnPressed(ActionEvent actionEvent) {
        submitSubstitute();
    }


    private void deleteBtnPressed(ActionEvent actionEvent) {
        ArrayList<Grade> grades = new ArrayList<>();
        grades.addAll(tableView.getSelectionModel().getSelectedItems());

        for (Grade grade : grades)
            try{
                service.deleteNota(grade.getId());
            }
            catch (ServiceException e){
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CLOSE);
                alert.showAndWait();
            }

        list = FXCollections.observableArrayList((Collection<? extends Grade>) service.getGrades());
    }

    private void submitSubstitute(){
        try{
            boolean secundar = editMode;
            String[] studentdId = studentComboBox.getSelectionModel().getSelectedItem().split(" ");
            String[] laboratoryId = labComboBox.getSelectionModel().getSelectedItem().split(" ");
            boolean checked = service.addNota(Integer.parseInt(idGradeField.getText()),
                    Integer.parseInt(studentdId[0]), Integer.parseInt(laboratoryId[1])) != null;

            if (checked && !secundar){
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "An item with this id already existed!",
                        ButtonType.CLOSE);
                alert.showAndWait();
            }

        }
        catch (ServiceException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CLOSE);
            alert.showAndWait();
        }
        catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid data entered", ButtonType.CLOSE);
            alert.showAndWait();
        }
        catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Select smth, barbiducule!", ButtonType.OK);
            alert.showAndWait();
        }
    }
}
