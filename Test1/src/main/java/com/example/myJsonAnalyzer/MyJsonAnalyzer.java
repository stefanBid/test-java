package com.example.myJsonAnalyzer;

import com.example.objectToWrite.WriteObject;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe dedicata alla lettura e scrittura di un file JSON
 */
public class MyJsonAnalyzer {
    //Variabili d'istanza
    private final String fileName;
    private JSONObject jsonObject;

    //Costruttore

    /**
     * Costruttore di un oggetto MyJsonAnalyzer
     * @param fileName nome del file .JSON da analizzare
     */
    public MyJsonAnalyzer(String fileName){
        this.fileName = fileName;
    }

    //Metodi

    /**
     * Crea un JSON parser
     */
    public void loadData() throws IOException, ParseException {
        //Creo un parser JSON
        JSONParser parser = new JSONParser();

        //Inizializzo l'oggetto JSON
        this.jsonObject = (JSONObject) parser.parse(new FileReader(this.fileName));
    }

    /**
     * Questa funzione scorre il file.JSON fino ad arrivare a recuperare i valori più annidati
     */
    public List<WriteObject> getListOfObjectFromJsonFile(String key){
        //Creo una lista vuota di oggetti che dovrò andare a scrivere nel file JSON
        List<WriteObject> list = new ArrayList<>();
        int count = 0;
        boolean specialCase = !key.contains("MIME") && !key.contains("NO");

        //Primo FOR itera il primo livello del file JSON
        for (Object o : this.jsonObject.keySet()) {

            //Creo un oggetto che riempirò mentre scorro il file JSON
            WriteObject wo = new WriteObject();

            JSONObject secondJsonObject = (JSONObject) this.jsonObject.get(o);

            //Setto il crawlId
            wo.setCrawlId((String) o);
            //System.out.println(wo.toString());

            // Secondo while itera elementi annidati
            for (Object secondKey : secondJsonObject.keySet()) {
                //Itero solo alcuni campi utili del file JSON escludo ad esempio "tot": 2581
                if (!(secondJsonObject.get(secondKey) instanceof Long)) {

                    JSONObject thirdJsonObject = (JSONObject) secondJsonObject.get(secondKey);

                    for (Object thirdKey : thirdJsonObject.keySet()) {

                        //Logica di popolamento dell'oggetto da scrivere nel nuovo file JSON
                        if (key.equals(thirdKey) && !specialCase) {
                            // Setto il Totale
                            wo.setTotal(Integer.parseInt(thirdJsonObject.get(thirdKey).toString()));

                        } else if (("INT-" + key).equals(thirdKey) && !specialCase) {
                            //Setto il totale Int
                            wo.setTotalInt(Integer.parseInt(thirdJsonObject.get(thirdKey).toString()));

                        } else if (specialCase) {
                            if (key.equals(thirdKey) && count == 0) {
                                // Setto il Totale
                                wo.setTotal(Integer.parseInt(thirdJsonObject.get(thirdKey).toString()));
                                count++;

                            } else if (key.equals(thirdKey)) {
                                //Setto il totale Int
                                wo.setTotalInt(Integer.parseInt(thirdJsonObject.get(thirdKey).toString()));

                            }
                        }
                    }
                }
            }
            count = 0;
            // Setto Total Ext
            if (wo.getTotal() >= wo.getTotalInt())
                wo.setTotalExt(wo.getTotal() - wo.getTotalInt());
            else
                wo.setTotalExt(wo.getTotalInt() - wo.getTotal());
            // Aggiungo il write object alla lista di Write object se la chiave di ricerca ha fornito risultati
            if (wo.getTotal() != 0 && wo.getTotalInt() != 0)
                list.add(wo);
        }

        return list;
    }
}
