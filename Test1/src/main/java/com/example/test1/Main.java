package com.example.test1;

import com.example.myJsonAnalyzer.MyJsonAnalyzer;
import com.example.objectToWrite.WriteObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Stefano Biddau
 */
public class Main {
    public static void main(String[] args){
        //Istanzio un oggetto MyJsonAnalyzer
        MyJsonAnalyzer myJsonAnalyzer = new MyJsonAnalyzer("src/main/resources/allegato1.json");

        String[] array = new String[4];
        array[0] = "HTTP_302";
        array[1] = "HTTP_NO";
        array[2] = "MIME_ALL";
        array[3] = "abcde";

        try {
            FileWriter file = new FileWriter("src/main/resources/allegato2.json");
            myJsonAnalyzer.loadData();
            List<WriteObject> list;
            JSONObject jsonObjectToWrite = new JSONObject();
            for (String s : array) {
                JSONArray jsonArray = new JSONArray();
                list = myJsonAnalyzer.getListOfObjectFromJsonFile(s);
                if(!list.isEmpty()){
                    for (WriteObject writeObject : list) {
                        JSONObject jsonSubObject = new JSONObject();
                        jsonSubObject.put("totalExt", writeObject.getTotalExt());
                        jsonSubObject.put("crawlId", writeObject.getCrawlId());
                        jsonSubObject.put("total", writeObject.getTotal());
                        jsonSubObject.put("totalInt", writeObject.getTotalInt());
                        jsonArray.add(jsonSubObject);
                    }
                    jsonObjectToWrite.put(s,jsonArray);
                }
            }
            file.write(jsonObjectToWrite.toJSONString());
            file.close();
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
