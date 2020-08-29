package com.prodev.moringaalumni.notifications;

public class Sender {
    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    private Data data;
    private String to;

    public Sender(){

    }
    public Sender(Data data, String to){
        this.data =data;
        this.to = to;
    }

}
