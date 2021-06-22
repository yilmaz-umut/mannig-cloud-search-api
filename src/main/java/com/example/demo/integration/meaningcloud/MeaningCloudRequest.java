package com.example.demo.integration.meaningcloud;


public class MeaningCloudRequest {

    private String key;
    private String lang;
    private String of;
    private String txt;
    private String tt;
    private String uw;


    public MeaningCloudRequest(String key, String txt) {
        this.key = key;
        this.txt = txt;
        this.of = "json";
        this.tt = "a";
        this.uw = "y";
        this.lang = "en";
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getOf() {
        return of;
    }

    public void setOf(String of) {
        this.of = of;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getTt() {
        return tt;
    }

    public void setTt(String tt) {
        this.tt = tt;
    }

    public String getUw() {
        return uw;
    }

    public void setUw(String uw) {
        this.uw = uw;
    }
}
