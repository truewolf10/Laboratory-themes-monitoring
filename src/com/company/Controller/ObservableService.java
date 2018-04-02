package com.company.Controller;

import com.company.Domain.Grade;
import com.company.Domain.Student;
import com.company.Domain.TemaLab;
import com.company.Repository.AbstractRepository;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventTarget;

import javax.swing.*;
import java.util.ArrayList;

public class ObservableService extends Service {

    private ArrayList<EventHandler<ActionEvent>> events;

    public ObservableService(AbstractRepository<Student, Integer> repoStud, AbstractRepository<TemaLab, Integer> repoLab, AbstractRepository<Grade, Integer> repoGrade) {
        super(repoStud, repoLab, repoGrade);
        events = new ArrayList<>();
    }

    public void addEventHandler(EventHandler<ActionEvent> eventEventHandler){
        if (events.contains(eventEventHandler))
            return;

        events.add(eventEventHandler);
    }

    public void removeEventHandler(EventHandler<ActionEvent> eventEventHandler){
        if (events.contains(eventEventHandler))
            events.remove(eventEventHandler);
    }

    public void triggerEvents(){

        ActionEvent actionEvent = new ActionEvent(this,null);

        for (EventHandler<ActionEvent> event : events )
            event.handle(actionEvent);

    }

    @Override
    public Student addStud(int idStudent, String nume, String email, int grupa, String profesor) throws ServiceException {
        Student student = super.addStud(idStudent, nume, email, grupa, profesor);
        triggerEvents();
        return student;
    }

    @Override
    public void deleteStudent(Integer id) throws ServiceException {
        super.deleteStudent(id);
        triggerEvents();
    }

    @Override
    public TemaLab addLaboratory(int idTema, String descriere, int deadline) throws ServiceException {
        TemaLab temaLab = super.addLaboratory(idTema, descriere, deadline);
        triggerEvents();
        return temaLab;
    }

    @Override
    public void deleteLaboratory(Integer id) throws ServiceException {
        super.deleteLaboratory(id);
        triggerEvents();
    }

    @Override
    public Grade addNota(int idGrade, int idStudent, int idTemaLab) throws ServiceException {
        Grade grade = super.addNota(idGrade, idStudent, idTemaLab);
        triggerEvents();
        return grade;
    }

    @Override
    public void setGrade(int idGrade, int idStudent, int idTemaLab, double valoare, int date) throws ServiceException {
        super.setGrade(idGrade, idStudent, idTemaLab, valoare, date);
        triggerEvents();
    }

    @Override
    public void deleteNota(Integer idGrade) throws ServiceException {
        super.deleteNota(idGrade);
        triggerEvents();
    }
}
