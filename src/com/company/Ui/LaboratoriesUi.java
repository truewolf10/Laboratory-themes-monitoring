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
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;


public class LaboratoriesUi {

    public CheckBox descriereCheck;
    public CheckBox idCheck;
    public TextField descriereFieldFiltrare;
    public TextField idFieldFiltrare;
    private ObservableList<TemaLab> list;
    public TableView<TemaLab> tableView;
    public TableColumn<TemaLab, String> idCol;
    public TableColumn<TemaLab, String> deadlineCol;
    public TableColumn<TemaLab, String> descriereCol;
    public Button addButtonId;
    public Button deleteButtonId;
    public Button editButtonId;
    public TextField idTextField;
    public TextField descriereTextField;
    public TextField deadlineTextField;
    private ObservableService service;
    private boolean editMode;
    private ObservableList<TemaLab> filteredList;


    public static Stage create(Application app, ObservableService service){
        FXMLLoader fxmlLoader = new FXMLLoader();
        Stage stage = new Stage();
        fxmlLoader.setLocation(app.getClass().getResource("Ui/laboratories.fxml"));
        Scene scene = null;

        try {
            scene = new Scene(fxmlLoader.load(),600,600);
            ((LaboratoriesUi)fxmlLoader.getController()).initialize(service);

        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(scene);
        return stage;
    }




    private void initialize(ObservableService observableService){
        this.service = observableService;
        service.addEventHandler(this::laboratoriesChanged);
        service.triggerEvents();
        //filterChanged(null);
    }

    private void laboratoriesChanged(ActionEvent actionEvent) {
        tableView.getItems().clear();
        for (TemaLab temaLab : service.getLaboratories()) {
            tableView.getItems().add(temaLab);
            list.add(temaLab);
        }
    }

    @FXML
    void initialize(){

        list = FXCollections.observableArrayList();
        filteredList = FXCollections.observableArrayList();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView.getSelectionModel().getSelectedItems().addListener(this::onChanged);

        deleteButtonId.setDisable(true);
        editButtonId.setDisable(true);

        descriereCheck.setOnAction(this::filterChanged);
        idCheck.setOnAction(this::filterChanged);
//        addButtonId.setOnAction(this::addPressed);
//        deleteButtonId.setOnAction(this::deletePressed);
//        editButtonId.setOnAction(this::editPressed);

        idCol.setCellValueFactory(x -> new ReadOnlyObjectWrapper<>(String.valueOf(x.getValue().getId())));
        descriereCol.setCellValueFactory(x->new ReadOnlyObjectWrapper<>(x.getValue().getDescriere()));
        deadlineCol.setCellValueFactory(x->new ReadOnlyObjectWrapper<>(String.valueOf(x.getValue().getDeadline())));

        tableView.setItems(filteredList);
        //list.addListener(this::listListener);
    }

    public void listListener(ListChangeListener.Change<? extends TemaLab> c) {
        filterChanged(null);

    }
    private void filterChanged(ActionEvent event) {

        //filteredList.clear();

        if (descriereCheck.isSelected()) {

//            filteredList = (ObservableList<TemaLab>) service.filtrareSortLab(descriereTextField.getText());
            filteredList = FXCollections.observableArrayList((ArrayList<TemaLab>) service.filtrareSortLab(descriereFieldFiltrare.getText()));
            tableView.setItems(filteredList);
        }
        else
            if (idCheck.isSelected()) {
                try {
                    filteredList = FXCollections.observableArrayList((ArrayList<TemaLab>) service.filtrareLab(Integer.parseInt(idFieldFiltrare.getText())));
                    tableView.setItems(filteredList);
                            //(ObservableList<TemaLab>) service.filtrareLab(Integer.parseInt(idFieldFiltrare.getText()));
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid data entered", ButtonType.CLOSE);
                    alert.showAndWait();
                }
            }

            else {
                tableView.setItems(list);
            }

    }

    public void onChanged(ListChangeListener.Change<? extends TemaLab> laboratories){
        int size = 0;
        if( laboratories != null)
            size = laboratories.getList().size();

        deleteButtonId.setDisable(size < 1);

        if (size == 1){
            show(true, laboratories.getList().get(0));
            editButtonId.setDisable(false);
        }
        else{
            show(false, null);
            editButtonId.setDisable(true);
        }
    }


    private void show(boolean edit, TemaLab temaLab){
        idTextField.setDisable(edit);

        if (edit){
            idTextField.setText(temaLab.getId().toString());
            descriereTextField.setText(temaLab.getDescriere());
            deadlineTextField.setText(String.valueOf(temaLab.getDeadline()));
        }
        else{
            idTextField.setText("");
            descriereTextField.setText("");
            deadlineTextField.setText("");
        }
        editMode = edit;

    }
    @FXML
    private void deletePressed(ActionEvent event){
        ArrayList<TemaLab> laboratories = new ArrayList<>();  // Faci un deep copy pt ca lista de tableView se modifica

        laboratories.addAll(tableView.getSelectionModel().getSelectedItems());
        //Iterable<TemaLab> laboratories = tableView.getSelectionModel().getSelectedItems();
        for (TemaLab temaLab : laboratories)
            try{
                service.deleteLaboratory(temaLab.getId());
            }
            catch (ServiceException e){
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CLOSE);
                alert.showAndWait();
            }
        list = FXCollections.observableArrayList((Collection<TemaLab>) service.getLaboratories());
//            list.clear();
//////            for (TemaLab temaLab : service.getLaboratories()){
//////                list.add(temaLab);
//////            }
//            service.getLaboratories().forEach(x-> list.add(x));
    }
    @FXML
    private void addPressed(ActionEvent event){
        submitSubstitute();
    }
    @FXML
    private void editPressed(ActionEvent event){
        submitSubstitute();
    }


    private void submitSubstitute(){
        try{
            boolean secundar = editMode;
            boolean checked = service.addLaboratory(Integer.parseInt(idTextField.getText()), descriereTextField.getText(),
                    Integer.parseInt(deadlineTextField.getText())) != null;
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
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid data entered", ButtonType.CLOSE);
            alert.showAndWait();
        }
    }
}
