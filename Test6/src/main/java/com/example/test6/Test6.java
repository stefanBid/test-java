package com.example.test6;

import com.example.fileReader.FileReader;
import difflib.Delta;
import difflib.DiffUtils;
import difflib.Patch;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Test6 {
    public static void main(String[] args) throws IOException {
        //genero l'istanza di fileReader per leggere la pagina HTML allegato3
        FileReader allegato3 = new FileReader("src/main/resources/allegato3.html");
        FileReader rules = new FileReader("src/main/resources/rules.txt");

        //Leggo i file
        List<String> allegato3Read = allegato3.readFile();
        List<String> rulesRead = rules.readFile();

        //Applico le regole per generare la seconda pagina HTML
        List<String> allegato4Read = new ArrayList<>();
        Patch patch = DiffUtils.parseUnifiedDiff(rulesRead);
        for (Delta d : patch.getDeltas()) {
            //PROBLEMA
            //d.applyTo(allegato3Read,allegato4Read);
        }

        //Scrivo la pagina HTML
        FileWriter w = new FileWriter("src/main/assets/allegato4.html");
        for (String line : allegato4Read) {
            w.write(line + "\n");
        }
        w.close();

    }
}