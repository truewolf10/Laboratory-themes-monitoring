package com.company.Controller;

import com.company.Domain.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class StudentMVCController {
    private Service service;
    private ObservableList<Student> model;

    public StudentMVCController(Service service) {
        this.service = service;
        this.model = FXCollections.observableArrayList();
    }

    boolean add(int id,String name, int grupa, String email, String teacherName) throws ServiceException {
        Student student = service.addStud(id, name, email, grupa, teacherName);
        populateList();
        return student != null;

    }

    void delete(int id) throws ServiceException{

        service.deleteStudent(id);
        populateList();
    }

    ObservableList<Student> getModel(){
        populateList();
        return model;
    }

    void populateList(){
        model.clear();
        for(Student student : service.getStudents())
            model.add(student);
    }
}


