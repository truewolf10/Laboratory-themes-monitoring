package com.company.Ui;

import com.company.Controller.Service;
import com.company.Controller.ServiceException;
import com.company.Domain.Grade;
import com.company.Domain.Student;
import com.company.Domain.TemaLab;
import com.company.Domain.ValidatorException;

import java.text.NumberFormat;
import java.util.List;
import java.util.Scanner;

public class UI {
    private Service controller;
    private Scanner input;

    public UI(Service controller) {
        this.controller = controller;
        this.input = new Scanner(System.in);
    }

    public void showOptions() {
        System.out.println("=========Student Options=========\n" +
                "\t1.Add student\n" +
                "\t2.Delete Student\n" +
                "\t3.Update student\n" +
                "\t4.Show all students\n" +
                "\t5.Filtare dupa nume\n" +
                "\t6.Filtrare dupa id\n" +
                "\n \n" +
                "===================Laborator Options==========\n" +
                "\t7.Add tema laborator\n" +
                "\t8.Delete tema laborator\n" +
                "\t9.Update tema laborator\n" +
                "\t10.Show all teme laborator\n" +
                "\t11.Change deadline for an existing laboratory\n" +
                "\t12.Filtrare dupa descriere\n" +
                "\t13.Filtrare dupa id\n" +
                "\n \n" +
                "==================Nota Options=============\n" +
                "\t14.Add nota \n" +
                "\t15.Delete nota \n" +
                "\t16.Update nota \n" +
                "\t17.Show all note\n" +
                "\t18.Filtrare dupa id\n" +
                "\n \n"+
                "================DeadlineOptions===============\n" +
                "\t19.IncrementCurrentWeek\n" +
                "\t20.ResetWeek\n");

    }

    public void showAddStudent() {
        try {
            System.out.println("Enter Id");
            Integer id = Integer.parseInt(input.nextLine());

            System.out.println("Enter name :");
            String name = input.nextLine();

            System.out.println("Enter email :");
            String email = input.nextLine();

            System.out.println("Enter grupa :");
            Integer grupa = Integer.parseInt(input.nextLine());

            System.out.println("Enter profesor :");
            String profesor = input.nextLine();

            this.controller.addStud(id, name, email, grupa, profesor);
            System.out.println("Student succesfully added");

        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Nu ati introdus datele corect! One more time: ");
        }
    }

    public void showDeleteStudent() {
        try {
            System.out.println("Enter Id");
            Integer id = Integer.parseInt(input.nextLine());

            this.controller.deleteStudent(id);
            System.out.println("Student succesfully deleted");
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Nu ati introdus datele corect! One more time: ");
        }
    }

    public void showUpdateStudent() {
        try {
            System.out.println("Enter Id");
            Integer id = Integer.parseInt(input.nextLine());

            System.out.println("Enter name :");
            String name = input.nextLine();

            System.out.println("Enter email :");
            String email = input.nextLine();

            System.out.println("Enter grupa :");
            Integer grupa = Integer.parseInt(input.nextLine());

            System.out.println("Enter profesor :");
            String profesor = input.nextLine();

            this.controller.addStud(id, name, email, grupa, profesor);
            System.out.println("Student succesfully updated");

        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Nu ati introdus datele corect! One more time: ");
        }
    }

    public void showAllStudents() {
        Iterable<Student> students = controller.getStudents();
        for (Student student : students)
            System.out.println(student);
    }

    public void showAddTemaLab() {
        try {
            System.out.println("Enter Id");
            Integer id = Integer.parseInt(input.nextLine());

            System.out.println("Enter descriere :");
            String descriere = input.nextLine();

            System.out.println("Enter deadline :");
            Integer deadline = Integer.parseInt(input.nextLine());

            controller.addLaboratory(id, descriere, deadline);
            System.out.println("Laboratory succesfully added");
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Nu ati introdus datele corect! One more time: ");
        }
    }

    public void showDeleteTemaLab() {
        try {
            System.out.println("Enter Id");
            Integer id = Integer.parseInt(input.nextLine());

            controller.deleteLaboratory(id);
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Nu ati introdus datele corect! One more time: ");
        }
    }

    public void showUpdateTemaLab() {
        try {
            System.out.println("Enter Id");
            Integer id = Integer.parseInt(input.nextLine());

            System.out.println("Enter descriere :");
            String descriere = input.nextLine();

            System.out.println("Enter deadline :");
            Integer deadline = Integer.parseInt(input.nextLine());

            controller.addLaboratory(id, descriere, deadline);
            System.out.println("Laboratory succesfully updated");
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Nu ati introdus datele corect! One more time: ");
        }
    }

    public void showAllLaboratories() {
        Iterable<TemaLab> temaLabs = controller.getLaboratories();
        for (TemaLab temaLab : temaLabs)
            System.out.println(temaLab);
    }

    public void showAddGrade() {
        try {
            System.out.println("Enter IdGrade: ");
            Integer idGrade = Integer.parseInt(input.nextLine());

            System.out.println("Enter IdStudent: ");
            Integer idStudent = Integer.parseInt(input.nextLine());

            System.out.println("Enter IdLaboratory: ");
            Integer idLaboratory = Integer.parseInt(input.nextLine());

            this.controller.addNota(idGrade,idStudent, idLaboratory);
            System.out.println("Grade succesfully added");

        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Nu ati introdus corect datele! One more time");
        }
    }

    public void showDeleteGrade() {
        try {
            System.out.println("Enter IdGrade: ");
            Integer idGrade = Integer.parseInt(input.nextLine());

            this.controller.deleteNota(idGrade);
            System.out.println("Grade succesfully deleted");

        } catch (ServiceException e) {
            System.out.println(e.getMessage());

        } catch (NumberFormatException e){
            System.out.println("Nu ati introdus datele corect! One more time:" );
        }
    }

    public void showAllGrades(){
        Iterable<Grade> grades = this.controller.getGrades();
        for (Grade grade : this.controller.getGrades())
            System.out.println(grade);
    }

    public void showChangeDeadline(){
        try {
            System.out.println("Enter Id");
            Integer id = Integer.parseInt(input.nextLine());

            System.out.println("Enter the new deadline :");
            Integer deadline = Integer.parseInt(input.nextLine());

            this.controller.changeDeadline(id,deadline);
        }
        catch (ServiceException e){
            System.out.println(e.getMessage());
        }
        catch(NumberFormatException e){
            System.out.println("Nu ati introdus corect datele! One more time");
        }

    }

    public void showSetGrade(){
        try {
            System.out.println("Enter IdGrade: ");
            Integer idGrade = Integer.parseInt(input.nextLine());

            System.out.println("Enter IdStudent: ");
            Integer idStudent = Integer.parseInt(input.nextLine());

            System.out.println("Enter IdLaboratory: ");
            Integer idLaboratory = Integer.parseInt(input.nextLine());

            System.out.println("Enter the date: ");
            Integer date = Integer.parseInt(input.nextLine());

            System.out.println("Enter Valoare: ");
            Double valoare = Double.parseDouble(input.nextLine());

            this.controller.setGrade(idGrade,idStudent, idLaboratory, valoare, date);
        }
        catch (NumberFormatException e) {
            System.out.println("Nu ati introdus corect datele! One more time");
        } catch (ServiceException e) {
            System.out.println(e.getMessage());;
        }
    }

    public void showFiltrareStudName(){

        try {
            System.out.println("Enter name for student:");
            String name = input.nextLine();

            Iterable<Student> list = this.controller.filtrareStudNume(name);
            for (Student student : list)
                System.out.println(student);

        }
        catch (NumberFormatException e){
        System.out.println("Nu ati introdus corect datele! One more time");
        }
    }

    public void showFiltrareStudId(){

        try {
            System.out.println("Enter the student's id : ");
            Integer id = Integer.parseInt(input.nextLine());

            Iterable<Student> list = this.controller.filterStudID(id);
            for (Student student : list)
                System.out.println(student);

        }
        catch (NumberFormatException e){
            System.out.println("Nu ati introdus corect datele! One more time");
        }
    }

    public void showFiltrareTemaLabDescription(){

        try {
            System.out.println("Enter descriere of laboratory :");
            String descriere = input.nextLine();

            Iterable<TemaLab> list = this.controller.filtrareSortLab(descriere);
            for (TemaLab temaLab : list)
                System.out.println(temaLab);

        }
        catch (NumberFormatException e){
            System.out.println("Nu ati introdus corect datele! One more time");
        }
    }

    public void showFiltrareTemaLabId(){

        try {
            System.out.println("Enter lab's id: ");
            Integer id = Integer.parseInt(input.nextLine());

            List<TemaLab> list = (List<TemaLab>) this.controller.filtrareLab(id);
            for (TemaLab temaLab : list)
                System.out.println(temaLab);

        }
        catch (NumberFormatException e){
            System.out.println("Nu ati introdus corect datele! One more time");
        }
    }

    public void showFiltrareGradeId(){

        try {
            System.out.println("Enter grade's id: ");
            Integer id = Integer.parseInt(input.nextLine());

            Iterable<Grade> list = this.controller.filtrareGrade(id);
            for (Grade grade : list)
                System.out.println(grade);

        }
        catch (NumberFormatException e){
            System.out.println("Nu ati introdus corect datele! One more time");
        }
    }





    public void showUi(){
        String cmd;
        boolean ok = true;

        while(ok){
            showOptions();
            System.out.println("Enter your option: ");
            cmd = input.nextLine();

            switch (cmd){
                case "1":
                    showAddStudent();
                    break;
                case "2":
                    showDeleteStudent();
                    break;
                case "3":
                    showUpdateStudent();
                    break;
                case "4":
                    showAllStudents();
                    break;
                case "5":
                    showFiltrareStudName();
                    break;
                case "6":
                    showFiltrareStudId();
                    break;
                case "7":
                    showAddTemaLab();
                    break;
                case "8":
                    showDeleteTemaLab();
                    break;
                case "9":
                    showUpdateTemaLab();
                    break;
                case "10":
                    showAllLaboratories();
                    break;
                case "11":
                    showChangeDeadline();
                    break;
                case "12":
                    showFiltrareTemaLabDescription();
                    break;
                case "13":
                    showFiltrareTemaLabId();
                    break;
                case "14":
                    showAddGrade();
                    break;
                case "15":
                    showDeleteGrade();
                    break;
                case "16":
                    showSetGrade();
                    break;
                case "17":
                    showAllGrades();
                    break;
                case "18":
                    showFiltrareGradeId();
                    break;
                case "19":
                    this.controller.incrementCurrentWeek();
                    break;
                case "20":
                    this.controller.resetCurrentWeek();
                case "0":
                    ok = false;
                    break;
                default:
                    System.out.println("Invalid command! ");
            }
        }
    }

}
