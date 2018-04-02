package com.company.Domain;

import java.util.regex.Pattern;

public class TemaLab implements HasdId<Integer>, FileConverter{

    private int idTema;
    private String descriere;
    private int deadline;

    public TemaLab(int idTema, String descriere, int deadline) {
        this.idTema = idTema;
        this.descriere = descriere;
        this.deadline = deadline;
    }

    public TemaLab(String s){
        String[] strings = s.split(Pattern.quote("::"));
        this.idTema = Integer.parseInt(strings[0]);
        this.descriere = strings[1];
        this.deadline = Integer.parseInt(strings[2]);
    }

    public String getDescriere() {
        return descriere;
    }


    public int getDeadline() {
        return deadline;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }

    @Override
    public Integer getId() {
        return idTema;
    }

    @Override
    public void setId(Integer integer) {
        this.idTema = integer;
    }

    @Override
    public String toFileString() {
        final String conventie = "::";
        return idTema + conventie + descriere + conventie + deadline;
    }

    @Override
    public String toString() {
        return "TemaLab{" +
                "idTema=" + idTema +
                ", descriere='" + descriere + '\'' +
                ", deadline=" + deadline +
                '}';
    }
}
