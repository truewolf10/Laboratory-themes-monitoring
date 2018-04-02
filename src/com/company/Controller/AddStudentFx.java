package com.company.Controller;

import com.company.Domain.Student;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

class AddStudentFx{

    private TextField id, name, grupa, email, teacher, idField, nameField;
    private CheckBox idCheck, nameCheck;
    private boolean editMode;

    AddStudentFx() {
    }

    void show(boolean edit, Student student){
        id.setDisable(edit);
        if (edit){
            id.setText(student.getId().toString());
            name.setText(student.getNume());
            grupa.setText(String.valueOf(student.getGrupa()));
            email.setText(student.getEmail());
            teacher.setText(student.getProfesor());
        }
        else {
            id.setText("");
            name.setText("");
            grupa.setText("");
            email.setText("");
            teacher.setText("");
        }
        editMode = edit;
    }

    GridPane getView(){
        Label idL, nameL, grupaL, emailL, teacherL;

        id = new TextField();
        id.setMinWidth(100);
        idL = new Label("Id: ");


        name = new TextField();
        name.setMinWidth(100);
        nameL = new Label("Name: ");


        grupa = new TextField();
        grupa.setMinWidth(100);
        grupaL = new Label("Grupa: ");


        email = new TextField();
        email.setMinWidth(100);
        emailL = new Label("Email: ");

        teacher = new TextField();
        teacher.setMinWidth(100);
        teacherL = new Label("Teacher: ");

        idCheck = new CheckBox("IdFiltrare:");
        idField = new TextField();
        idField.setMinWidth(100);

        nameCheck = new CheckBox("nameFiltrare");
        nameField = new TextField();
        nameField.setMinWidth(100);

        GridPane pane = new GridPane();
        pane.addColumn(0,
                        idL, id,
                        nameL, name,
                        grupaL, grupa,
                        emailL, email,
                        teacherL, teacher,
                        idCheck, idField,
                        nameCheck, nameField
                        );
        pane.setAlignment(Pos.TOP_CENTER);
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(10,10,10,10));

        return pane;

    }

    String getId() {
        return id.getText();
    }

    String getName() {
        return name.getText();
    }

    String getGrupa() {
        return grupa.getText();
    }

    String getEmail() {
        return email.getText();
    }

    String getTeacher() {
        return teacher.getText();
    }

    boolean isEditMode() {
        return editMode;
    }
}
