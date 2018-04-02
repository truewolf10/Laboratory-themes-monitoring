package com.company.Domain;

import java.util.regex.Pattern;

public class Grade implements HasdId<Integer>,FileConverter {

    private int idGrade;
    private int idStudent;
    private int idLaborator;
    private Double valoare;
    private Integer date;

    public Grade(int idGrade, int idStudent, int idLaborator, Double valoare, Integer date){
        this.idGrade = idGrade;
        this.idStudent = idStudent;
        this.idLaborator = idLaborator;
        this.valoare = valoare;
        this.date = date;
    }

    public Grade(String s){
        String[] strings = s.split(Pattern.quote("::"));
        this.idGrade = Integer.parseInt(strings[0]);
        this.idStudent = Integer.parseInt(strings[1]);
        this.idLaborator = Integer.parseInt(strings[2]);
        if (strings[3].equals("null"))
            this.valoare = null;
        else
            this.valoare = Double.parseDouble(strings[3]);
        if (strings[4].equals("null"))
            this.date = null;
        else
            this.date = Integer.parseInt(strings[4]);
    }

    public int getIdStudent() {
        return idStudent;
    }

    public int getIdLaborator() {
        return idLaborator;
    }

    public Double getValoare() {
        return valoare;
    }

    public Integer getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "idGrade=" + idGrade +
                ", idStudent=" + idStudent +
                ", idLaborator=" + idLaborator +
                ", valoare=" + valoare +
                ", date=" + date +
                '}';
    }

    @Override
    public String toFileString() {
        final String conventie = "::";
        return idGrade+ conventie+ idStudent + conventie + idLaborator + conventie + valoare + conventie + date;
    }
    public static double GradeValue(Grade g, int deadline){
        int currentWeek = g.getDate();
        if (currentWeek - deadline <= 0)
            return g.getValoare();
        else if(currentWeek - deadline == 1)
            return g.getValoare() - 2.0;
        else
            return 1.0;
    }

    @Override
    public Integer getId() {
        return idGrade;
    }

    @Override
    public void setId(Integer id) {
        this.idGrade = id;
    }

    public void setValoare(double valoare) {
        this.valoare = valoare;
    }

    public void setDate(int date) {
        this.date = date;
    }
}
