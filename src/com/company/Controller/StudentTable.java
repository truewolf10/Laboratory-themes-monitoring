package com.company.Controller;

import com.company.Domain.Student;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class StudentTable extends Stage {
    private final ObservableService service;
    private TableView<Student> table;
    private Button delete, add, edit;
    private AddStudentFx addStudentFx;

    public StudentTable(ObservableService service) {
        super();
        this.service = service;
        super.setTitle("Students");
        addStudentFx = new AddStudentFx();
        super.setScene(new Scene(getView(), 600, 600));
    }

    private BorderPane getView(){

        table = new TableView<>();
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        table.getSelectionModel().getSelectedItems().addListener(this::SelectionChanged);

        add = new Button("Add:");
        add.setMinWidth(100);
        add.setOnAction(this::addPressed);

        delete = new Button("Remove:");
        delete.setMinWidth(100);
        delete.setDisable(true);
        delete.setOnAction(this::deletePressed);

        edit = new Button("Edit:");
        edit.setMinWidth(100);
        edit.setDisable(true);
        edit.setOnAction(this::editPressed);

        StackPane stackPaneTable = new StackPane();
        initTable();
        stackPaneTable.getChildren().add(table);

        GridPane gridPaneButoane = new GridPane();
        gridPaneButoane.addColumn(0,add,delete,edit);

        GridPane gridPaneFields = addStudentFx.getView();

        GridPane gridPaneFinalRightSide =  new GridPane();
        gridPaneFinalRightSide.addRow(0, gridPaneButoane);
        gridPaneFinalRightSide.addRow(1,gridPaneFields);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(stackPaneTable);
        borderPane.setRight(gridPaneFinalRightSide);

        return borderPane;
    }

    private void addPressed(ActionEvent actionEvent) {
        //addStudentFx.show(false,null);
        submitSubstitute();

    }

    private void editPressed(ActionEvent actionEvent) {
        //addStudentFx.show(true, table.getSelectionModel().getSelectedItem());
        submitSubstitute();

    }

    private void submitSubstitute() {
        try {
            boolean editMode = addStudentFx.isEditMode();
            boolean checked = service.addStud(Integer.parseInt(addStudentFx.getId()), addStudentFx.getName(),
                    addStudentFx.getEmail(),Integer.parseInt(addStudentFx.getGrupa()),
                    addStudentFx.getTeacher()) != null;

            if (checked && !editMode) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "An item with this id already existed!",
                        ButtonType.CLOSE);
                alert.showAndWait();
            }

        } catch (ServiceException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.FINISH);
            alert.showAndWait();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid data entered", ButtonType.CLOSE);
            alert.showAndWait();
        }
    }


    private void SelectionChanged(ListChangeListener.Change<? extends Student> students){
        int size = 0;
        if (students != null)
            size = students.getList().size();

        delete.setDisable(size < 1);
        if (size == 1) {
            addStudentFx.show(true, students.getList().get(0));
            edit.setDisable(false);
        }
        else {
            addStudentFx.show(false, null);
            edit.setDisable(true);
        }
    }

    void initTable(){
        TableColumn<Student, String> id = new TableColumn<>("Id");
        TableColumn<Student, String> name= new TableColumn<>("Name");
        TableColumn<Student, String> grupa = new TableColumn<>("Grupa");
        TableColumn<Student, String> email = new TableColumn<>("Email");
        TableColumn<Student, String> teacher = new TableColumn<>("Teacher");

        ArrayList<TableColumn<Student,?>> tableColumns = new ArrayList<>();
        tableColumns.add(id);
        tableColumns.add(name);
        tableColumns.add(grupa);
        tableColumns.add(email);
        tableColumns.add(teacher);

        id.setCellValueFactory(x-> new ReadOnlyObjectWrapper<>(x.getValue().getId().toString()));
        name.setCellValueFactory(x-> new ReadOnlyObjectWrapper<>(x.getValue().getNume()));
        grupa.setCellValueFactory(x-> new ReadOnlyObjectWrapper<>(String.valueOf(x.getValue().getGrupa())));
        email.setCellValueFactory(x-> new ReadOnlyObjectWrapper<>(x.getValue().getEmail()));
        teacher.setCellValueFactory(x-> new ReadOnlyObjectWrapper<>(x.getValue().getProfesor()));

        table.getColumns().addAll(tableColumns);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setItems(FXCollections.observableArrayList());

        service.addEventHandler(this::studentsChanged);
        service.triggerEvents();
    }

    private void studentsChanged(ActionEvent event) {
        table.getItems().clear();
        for (Student st : service.getStudents()) {
            table.getItems().add(st);
        }
    }

    private void deletePressed(ActionEvent actionEvent) {
        Iterable<Student> students = table.getSelectionModel().getSelectedItems();
        for (Student x : students)
        try{
            service.deleteStudent(x.getId());
        } catch (ServiceException e) {
            
        }
    }
}
