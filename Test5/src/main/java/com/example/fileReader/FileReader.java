package com.example.fileReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileReader {

    //VARIABILI D'ISTANZA
    private String filePath;

    //COSTRUTTORE
    public FileReader(String filePath){
        this.filePath = filePath;
    }

    //Metodo
    public List<String> readFile() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new java.io.FileReader(this.filePath));
        List<String> linesList = new ArrayList<>();
        String l;
        while ((l = bufferedReader.readLine()) != null) {
            linesList.add(l);
        }
        bufferedReader.close();
        return linesList;
    }

}
