package com.company.Domain;

import java.util.regex.Pattern;

public class Student implements HasdId<Integer>, FileConverter{
    private int idStudent;
    private String nume;
    private String email;
    private int grupa;
    private String profesor;

    public Student(int idStudent, String nume, String email, int grupa, String profesor) {
        this.idStudent = idStudent;
        this.nume = nume;
        this.email = email;
        this.grupa = grupa;
        this.profesor = profesor;
    }
    public Student(String s){

        String[] strings = s.split(Pattern.quote("::"));
        this.idStudent = Integer.parseInt(strings[0]);
        this.nume = strings[1];
        this.email = strings[2];
        this.grupa = Integer.parseInt(strings[3]);
        this.profesor = strings[4];
    }

    public String getNume() {
        return nume;
    }

    public String getEmail() {
        return email;
    }

    public int getGrupa() {
        return grupa;
    }

    public String getProfesor() {
        return profesor;
    }

    @Override
    public String toString() {
        return "Student{" +
                "idStudent=" + idStudent +
                ", nume='" + nume + '\'' +
                ", email='" + email + '\'' +
                ", grupa=" + grupa +
                ", profesor='" + profesor + '\'' +
                '}';
    }

    @Override
    public Integer getId() {
        return idStudent;
    }

    @Override
    public void setId(Integer integer) {
        idStudent = integer;
    }

    @Override
    public String toFileString() {
        final String conventie = "::";
        return idStudent + conventie + nume + conventie + email + conventie + grupa + conventie + profesor;
    }
}
