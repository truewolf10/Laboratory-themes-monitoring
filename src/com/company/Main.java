package com.company;

import com.company.Controller.Service;
import com.company.Controller.ServiceException;
import com.company.Domain.*;
import com.company.Repository.FileRepository;
import com.company.Repository.InMemoryRepository;
import com.company.Ui.UI;

public class Main {

    public static void main(String[] args) {
	// write your code here
        StudentValidator StudentVal = new StudentValidator();
        LaboratoryValidator LabVal = new LaboratoryValidator();
        GradeValidator gradeValidator = new GradeValidator();

        InMemoryRepository repo_s = new InMemoryRepository(StudentVal);
        InMemoryRepository repo_l = new InMemoryRepository(LabVal);
        InMemoryRepository repo_n = new InMemoryRepository(gradeValidator);

        FileConverterFactory<Student> studentFileConverterFactory = Student::new;
        FileRepository repof_s = new FileRepository(StudentVal, Student::new, "Studenti.txt");
        FileRepository repof_l = new FileRepository(LabVal, TemaLab::new, "Laboratoare.txt");
        FileRepository repof_n = new FileRepository(gradeValidator, Grade::new,"Grade.txt");

        Service controller = new Service(repof_s,repof_l, repof_n);
        UI ui = new UI(controller);
        ui.showUi();
    }
}
