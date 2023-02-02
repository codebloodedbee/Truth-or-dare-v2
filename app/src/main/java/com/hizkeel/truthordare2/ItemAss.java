package com.hizkeel.truthordare2;

public class ItemAss {

    private String title;
    private String code;
    private String exp;


    public ItemAss() {
    }

    public ItemAss(String title, String exp, String code) {
        this.title = title;
        this.exp = exp;
        this.code = code;

    }

    public ItemAss(String title, String exp) {
        this.title = title;
        this.exp = exp;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String icon) {
        this.code = code;
    }
}
