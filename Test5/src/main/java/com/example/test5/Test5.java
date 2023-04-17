package com.example.test5;

import com.example.fileReader.FileReader;
import difflib.Delta;
import difflib.DiffUtils;
import difflib.Patch;

import java.io.FileWriter;
import java.util.List;

public class Test5 {
    public static void main(String[] args) throws Exception {
        //genero due istanze di File reader oggetti che mi permettono di leggere un file dato un path in input
        FileReader allegato3 = new FileReader("src/main/resources/allegato3.html");
        FileReader allegato4 = new FileReader("src/main/resources/allegato4.html");

        //Leggo i file
        List<String> allegato3Read = allegato3.readFile();
        List<String> allegato4Read = allegato4.readFile();


        Patch p = DiffUtils.diff(allegato3Read, allegato4Read);
        List<Delta> deltaList = p.getDeltas();

        //Switch case per indicare le operazioni da svolgere tra (INSERT REMOVE o la combinazione di entrambe per effettuare il Change)

        StringBuilder stringBuilder = new StringBuilder();
        for (Delta d : deltaList) {
            switch (d.getType()) {
                case INSERT:
                    stringBuilder.append("INSERT " + d.getRevised() + "\n");
                    break;
                case DELETE:
                    stringBuilder.append("REMOVE " + d.getOriginal() + "\n");
                    break;
                case CHANGE:
                    stringBuilder.append("INSERT " + d.getRevised() + "\n");
                    stringBuilder.append("REMOVE " + d.getOriginal() + "\n");
                    break;

            }
        }

        //In un file txt inserisco la lista di regole per trasformare il file allegato3 nel file allegato 4

        FileWriter writer = new FileWriter("src/main/resources/rules.txt");
        writer.write(stringBuilder.toString());
        writer.close();
    }

}