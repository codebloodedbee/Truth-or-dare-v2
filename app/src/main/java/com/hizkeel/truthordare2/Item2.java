package com.hizkeel.truthordare2;

public class Item2 {

    private String title;



    private String category;
    private String author;
    private String code;

    public Item2() {
    }

    public Item2(String title, String category, String author, String code) {
        this.title = title;
        this.category = category;
        this.author = author;
        this.code = code;


    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }




}
