package com.prehax.gomovie;

public class Theaters {
    private String Address = "",Name = "",Rate = "";
    private int numCol=0,numRows=0;
    public String getAddress(){
        return Address;
    }
    public void setAddress(String address){
        this.Address = address;
    }
    public String getName(){
        return Name;
    }
    public void setName(String name){
        this.Name = name;
    }
    public String getRate(){
        return Rate;
    }
    public void setRate(String rate){
        this.Rate = rate;
    }


    public int getnumCol(){
        return numCol;
    }
    public void setnumCol(int numCol){
        this.numCol = numCol;
    }
    public int getnumRows(){
        return numRows;
    }
    public void setnumRows(int numRows){
        this.numRows = numRows;
    }
}
