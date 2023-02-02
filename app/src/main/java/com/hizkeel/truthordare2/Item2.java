package com.hizkeel.truthordare2;

public class Item2 {

    private String title;
    private String description;



    private String creator;
    private String views;
    private String rating;

    private String code;

    public Item2() {
    }

    public Item2(String title, String description, String creator, String views, String rating, String code) {
        this.title = title;
        this.description = description;
        this.creator = creator;
        this.views = views;
        this.rating = rating;
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String category) {
        this.description = description;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }





}
