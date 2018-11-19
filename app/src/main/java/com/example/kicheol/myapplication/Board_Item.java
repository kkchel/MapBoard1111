package com.example.kicheol.myapplication;

public class Board_Item {
    String id;
    String date;
    String content;

    public Board_Item(String id, String date, String content) {
        this.id = id;
        this.date = date;
        this.content = content;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {

        return id;
    }

    public String getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }
}
