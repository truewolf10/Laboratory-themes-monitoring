package com.company.Ui;

import com.company.App;
import com.company.Controller.ObservableService;
import com.company.Controller.Service;
import com.company.Controller.StudentTable;
import com.company.Domain.*;
import com.company.Repository.FileRepository;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.event.ActionEvent;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MainUI {

    public Button laboratoriesBtn;
    public Button gradesBtn;
    public Button studentsBtn;

    ObservableService service;
    Application application;

    public static Scene create(App pinte, ObservableService service) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(pinte.getClass().getResource("Ui/mainUI.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(loader.load(),600,600);
            ((MainUI)loader.getController()).initialize(service,pinte);

        } catch (IOException e) {
            e.printStackTrace();
        }

       /* File file = new File("backroundMain.css");
        URL url = null;
        try {
            url = file.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        scene.getStylesheets().add(url.toExternalForm());
*/
        return scene;
    }

    @FXML
    void initialize(ObservableService service, App pinte) {
        this.service = service;
        application = pinte;
        studentsBtn.setOnAction(this::studentBtnPressed);
        laboratoriesBtn.setOnAction(this::laboratoriesBtnPressed);
        gradesBtn.setOnAction(this::gradesBtnPressed);
    }

    void initialize(ObservableService service) {
        this.service = service;
    }

    private void studentBtnPressed(ActionEvent actionEvent) {
        (new StudentTable(service)).show();
    }


    private void laboratoriesBtnPressed(ActionEvent event) {
        LaboratoriesUi.create(application, service).show();
    }

    private void gradesBtnPressed(ActionEvent event) {
        GradesUi.create(application, service).show();
    }
}
