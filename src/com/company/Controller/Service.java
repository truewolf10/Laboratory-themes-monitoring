package com.company.Controller;

import com.company.Domain.*;
import com.company.Repository.AbstractRepository;
import com.company.Repository.IRepository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Service {

    private AbstractRepository<Student,Integer> repoStud = null;
    private AbstractRepository<TemaLab,Integer> repoLab = null;
    private AbstractRepository<Grade, Integer> repoGrade = null;

    private int currentWeek = 1;


    public Service(AbstractRepository<Student, Integer> repoStud, AbstractRepository<TemaLab, Integer> repoLab, AbstractRepository<Grade, Integer> repoGrade) {
        this.repoStud = repoStud;
        this.repoLab = repoLab;
        this.repoGrade  = repoGrade;
    }

    // ----------------------- Students --------------------------------------

    public Student addStud(int idStudent, String nume, String email, int grupa, String profesor) throws ServiceException{
        Student student = new Student(idStudent,nume, email,grupa, profesor);
        try{
            return repoStud.add(student);
        }
        catch (ValidatorException e){
            throw new ServiceException(e.getMessage());
        }
    }

    public void deleteStudent(Integer id) throws ServiceException{
        if (id != null){
            Student student = repoStud.delete(id);
            if (student != null) {
                for (Grade grade : repoGrade)
                    repoGrade.delete(grade.getId());
                return;
            }
        }
        throw new ServiceException("No student found with that id!");
    }

    public Iterable<Student> getStudents(){
//        Student[] result = new Student[(int) repoStud.size()];
//
//        int i=0;
//
//        for(Student s : repoStud){
//            result[i] = s;
//        }
//        return result;
         return repoStud.findAll();
    }

    public Iterable<Student> filtrareStudNume(String nume){

        // cream predicatul
        Predicate<Student> predicate = (Student student) ->{
            return student.getNume().toLowerCase().startsWith(nume.toLowerCase());
        };
        // cream comparatorul
        Comparator<Student> comparator = (Student s1, Student s2)->{
            return (s1.getNume().compareTo(s2.getNume()));
        };

        Comparator<Student> comparator1 = Comparator.comparing(Student::getNume);

        return filterSort(predicate, comparator, repoStud);
    }

    public Iterable<Student> filterStudID(Integer id){

        // cream predicatul
        Predicate<Student> predicate = student -> {
            return student.getId().equals(id);
        };

        // cream predicatul2
        Predicate<Student> predicate1 = s -> s.getId().equals(id);

        // cream predicatul3
        Predicate<Student> predicate2 = (Student student)->{
            return student.getId().equals(id);
        };

        return filterSort(predicate,null, repoStud);
    }

    //---------------------------------- LaboratoriesUi --------------------------------------
    public TemaLab addLaboratory(int idTema, String descriere, int deadline) throws  ServiceException{
        TemaLab tema1 = new TemaLab(idTema, descriere, deadline);
        try{
            return repoLab.add(tema1);
        }
        catch (ValidatorException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    public void deleteLaboratory(Integer id) throws ServiceException{
        if (id != null){
            TemaLab tema1 = repoLab.delete(id);
            if (tema1 != null) {
                for (Grade grade : repoGrade)
                    repoGrade.delete(grade.getId());
                return;
            }
        }
        throw new ServiceException("No laboratory found with this id");
    }

    public Iterable<TemaLab> getLaboratories(){
//        TemaLab[] result = new TemaLab[(int) repoLab.size()];
//
//        int i=0;
//
//        for(TemaLab tema : repoLab)
//            result[i++] = tema;
//
//        return result;
        return repoLab.findAll();
    }

    public TemaLab changeDeadline(int id, int newDeadline) throws ServiceException{
        TemaLab tema = repoLab.findExistingObject(id);

        if (tema == null)
            throw new ServiceException("No laboratory founded with that id");

        if (tema.getDeadline() <= currentWeek)
            throw new ServiceException("You cannot deliver the lab from now on! ");

        if (tema.getDeadline() >= newDeadline)
            throw new ServiceException("You entered the newDeadline wrong! ");

        return addLaboratory(tema.getId(), tema.getDescriere(), newDeadline);
    }

    public Iterable<TemaLab> filtrareSortLab(String descriere){

        //cream predicatul
        Predicate<TemaLab> predicate = (TemaLab temaLab)->{
          return temaLab.getDescriere().toLowerCase().startsWith(descriere.toLowerCase());
        };

        //cream comparator
        Comparator<TemaLab> comparator = (TemaLab temaLab1,TemaLab temaLab2)->{
            return temaLab1.getDescriere().compareTo(temaLab2.getDescriere());
        };

        return filterSort(predicate, comparator, repoLab);
    }

    public Iterable<TemaLab> filtrareLab(Integer id){

        Predicate<TemaLab> predicate = (TemaLab temaLab)->{
            return temaLab.getId().equals(id);
        };

        return filterSort(predicate, null, repoLab);
    }

    //--------------------------------- Grades ---------------------------------------

    public Grade addNota(int idGrade, int idStudent, int idTemaLab) throws ServiceException {

        if (repoStud.findExistingObject(idStudent) == null)
            throw new ServiceException("Student Id does not exist");
        if (repoLab.findExistingObject(idTemaLab) == null)
            throw new ServiceException("Laboratory Id does not exist");
        if (repoGrade.findExistingObject(idGrade) != null)
            throw new ServiceException("Already exist a note for this homework! ");

        Grade grade = new Grade(idGrade, idStudent, idTemaLab, null, null);
        try {
            return repoGrade.add(grade);
        }
        catch (ValidatorException e) {
            throw new ServiceException(e.getMessage());
        }

    }
    public void setGrade(int idGrade, int idStudent, int idTemaLab, double valoare,int date ) throws ServiceException {

        Grade grade = null;
        TemaLab lab = null;
        Student stud = null;
        stud=repoStud.findExistingObject(idStudent);
        if (stud == null)
            throw new ServiceException("Student Id does not exist");
        lab = repoLab.findExistingObject(idTemaLab);
        if (lab == null)
            throw new ServiceException("Laboratory Id does not exist");
        grade = repoGrade.findExistingObject(idGrade);
        if (grade == null)
            throw new ServiceException("Grade Id does not exist!");

        Double oldValue = 0.0;
        if (grade.getValoare()!=null)
            oldValue = grade.getValoare();

        Grade grade1 = new Grade(idGrade, idStudent, idTemaLab, valoare, date);
        Double newValue = Grade.GradeValue(grade1,lab.getDeadline());

        if(newValue>oldValue){
            boolean modified = (grade.getValoare() != null);
            grade.setValoare(newValue);
            grade.setDate(date);
            writeInfo(stud, lab, grade, modified);
            try {
                repoGrade.add(grade);
            } catch (ValidatorException e) {
                e.printStackTrace();
            }
        }

    }

    public void writeInfo(Student stud, TemaLab lab, Grade nota,boolean modified){

        try{
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(stud.getId() + "Student.txt", true));
            bufferedWriter.write(modified ? "Changed grade\n" : "Added grade \n");
            bufferedWriter.write("Laboratory's Id: " + lab.getId()+ "\n");
            bufferedWriter.write("Grade's value " + nota.getValoare()+ "\n");
            bufferedWriter.write("With the deadline" +lab.getDeadline()+ "\n");
            bufferedWriter.write("delivered on week" + nota.getDate() + "\n");
            if (lab.getDeadline() < nota.getDate())
                bufferedWriter.write("The student has been penalized" + "\n");
            if (modified)
                bufferedWriter.write("\n"+ "\n");
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void deleteNota(Integer idGrade) throws ServiceException {
        if (idGrade != null) {
            Grade grade = repoGrade.delete(idGrade);
            if (grade != null)
                return;
        }
        throw new ServiceException("Nu exista o nota cu acest Id");
    }

    public Iterable<Grade> getGrades(){
        return repoGrade.findAll();
    }

    public Iterable<Grade> filtrareGrade(Integer id){

        //List<Grade> list = (List<Grade>) repoGrade.findAll();

        //cream predicatul
        Predicate<Grade> predicate = grade -> grade.getId().equals(id);

        return filterSort(predicate, null, repoGrade);
    }

    // ----------------------------------

    public int getCurrentWeek() {
        return currentWeek;
    }

    public int incrementCurrentWeek(){
        System.out.println("CurrentWeek: " + (++currentWeek));
        return currentWeek;
    }

    public int resetCurrentWeek(){
        currentWeek =1;
        System.out.println("CurrentWeek: " + currentWeek);
        return currentWeek;
    }

    // -----------------------------------Filtrare & Sortare generice-------------------------------------

    public static <T extends HasdId<K>,K extends Comparable<K>> Iterable<T> filterSort(Predicate<T> predicate, Comparator<T> comparator, IRepository<T,K> iRepository) {

        Iterable<T> lista = iRepository.findAll();
        if (lista == null)
            return null;
        // Cream acel izvor de date.
        Stream stream = StreamSupport.stream(lista.spliterator(), false);

        if (predicate != null)
            stream = stream.filter(predicate);

        if (comparator != null)
            stream = stream.sorted(comparator);

        return (Iterable<T>) stream.collect(Collectors.toList());
    }






}


