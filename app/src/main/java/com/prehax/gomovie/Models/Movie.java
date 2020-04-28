package com.prehax.gomovie.Models;

public class Movie {
    private String title;
    private String rating;
    private String genre;
    private String date;
    private String imageurl;
    private String descrption;
    private String backdropurl;
    private String id;

    public Movie(){

    }



    public Movie(String title, String rating, String genre, String date, String imageurl, String descrption, String backdropurl, String id) {
        this.title = title;
        this.date = date;
        this.genre = genre;
        this.imageurl = imageurl;
        this.rating = rating;
        this.descrption = descrption;
        this.backdropurl = backdropurl;
        this.id = id;
    }
    public String getTitle(){
        return title;
    }
    public String getGenre(){
        return genre;
    }
    public String getRating(){
        return rating;
    }
    public String getDate(){
        return date;
    }
    public String getImageurl(){
        return imageurl;
    }
    public String getBackdropurl() { return backdropurl; }
    public String getDescrption(){ return descrption; }
    public String getId() { return id; }

    public void setDescrption(String descrption) {
        this.descrption = descrption;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setRating(String rating) {
        this.rating = rating;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
    public void setBackdropurl(String backdropurl) { this.backdropurl = backdropurl; }
    public void setId(String id){ this.id = id;}
}
