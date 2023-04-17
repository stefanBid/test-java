package com.example.objectToWrite;

/**
 * Questa classe istanzia un ogetto che dovrà essere scritto in un file JSON
 */
public class WriteObject {
    //variabili d'istanza
    private String crawlId;
    private int total;
    private int totalInt;
    private int totalExt;

    //COSTRUTTORE
    public WriteObject(){
        this.crawlId = " ";
        this.total = this.totalInt = this.totalExt = 0;
    }

    //GETTER e SETTER

    public String getCrawlId() {
        return crawlId;
    }

    public void setCrawlId(String crawlId) {
        this.crawlId = crawlId;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalInt() {
        return totalInt;
    }

    public void setTotalInt(int totalInt) {
        this.totalInt = totalInt;
    }

    public int getTotalExt() {
        return totalExt;
    }

    public void setTotalExt(int totalExt) {
        this.totalExt = totalExt;
    }

    @Override
    public String toString() {
        return "WriteObject{" +
                "crawlId='" + crawlId + '\'' +
                ", total=" + total +
                ", totalInt=" + totalInt +
                ", totalExt=" + totalExt +
                '}';
    }
}
