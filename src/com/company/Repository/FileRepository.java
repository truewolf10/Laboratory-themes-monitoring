package com.company.Repository;


import com.company.Domain.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileRepository<E extends HasdId<Id> & FileConverter,Id extends Comparable<Id>> extends AbstractRepository<E, Id> {
    private String fileName;
    private FileConverterFactory<E> factory;

    public FileRepository(IValidator<E> validator, FileConverterFactory<E> factory, String fileName) {
        super(validator);
        this.fileName = fileName;
        this.factory = factory;
        loadData();
    }

    @Override
    public void loadData() {

        BufferedReader bufferedReader = null;
        Stream<String> lines = null;
        try {
            //bufferedReader = new BufferedReader(new FileReader(fileName));
            lines = Files.lines(Paths.get(fileName));
        } catch (Exception e) {
            try {
                (new BufferedWriter(new FileWriter(fileName))).close();
                //bufferedReader = new BufferedReader(new FileReader(fileName));
                lines = Files.lines(Paths.get(fileName));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        try{
            //for (String string = bufferedReader.readLine(); string !=null; string = bufferedReader.readLine() ) {
              for(Object string : lines.toArray()) {
                  E object = factory.create((String) string);
                  add(object);
              }


        } catch (ValidatorException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void saveData() {
        try{
            FileWriter fileWriter = null;
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter = new FileWriter(fileName));
            for (E element : this ) { // sau findAll;
                bufferedWriter.write(element.toFileString());
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
            fileWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
