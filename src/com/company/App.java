package com.company;

import com.company.Controller.ObservableService;
import com.company.Domain.*;
import com.company.Repository.FileRepository;
import com.company.Ui.LaboratoriesUi;
import com.company.Ui.MainUI;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        ObservableService service = new ObservableService(new FileRepository<>(new StudentValidator(), Student::new, "Studenti.txt"),
                                        new FileRepository<>(new LaboratoryValidator(), TemaLab::new,"Laboratoare.txt"),
                                        new FileRepository<>(new GradeValidator(), Grade::new,"Grade.txt" ));
//        primaryStage = new StudentTable(new Service(new FileRepository<>(new StudentValidator(), Student::new, "Studenti.txt"),
//                                        new FileRepository<>(new LaboratoryValidator(), TemaLab::new,"Laboratoare.txt"),
//                                        new FileRepository<>(new GradeValidator(), Grade::new,"Grade.txt" )));
//
//        primaryStage.show();

        primaryStage.setScene(MainUI.create(this, service));
//        primaryStage = LaboratoriesUi.create(this, service);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
