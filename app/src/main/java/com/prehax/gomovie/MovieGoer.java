package com.prehax.gomovie;

import androidx.annotation.Nullable;

public class MovieGoer {
    private String Fname="", Lname="", Address="", City="", State="", Zip="";
    private String CardHoldername="", CardNumber="", ExpDate="", CVV="";


    public String getAddress() {
        return Address;
    }
    public String getCity() {
        return City;
    }
    public String getFname() {
        return Fname;
    }
    public String getLname() {
        return Lname;
    }
    public String getState() {
        return State;
    }
    public String getZip() {
        return Zip;
    }
    public String getCardHoldername(){ return CardHoldername;}
    public String getCardNumber(){ return CardNumber;}
    public String getExpDate(){ return ExpDate;}
    public String getCVV(){return CVV;}
    public void setAddress(String address) {
        this.Address = address;
    }
    public void setCity(String city) {
        this.City = city;
    }
    public void setFname(String firstName) {
        this.Fname = firstName;
    }
    public void setLname(String lastName) {
        this.Lname = lastName;
    }
    public void setState(String state) {
        this.State = state;
    }
    public void setZip(String zipcode) {
        this.Zip = zipcode;
    }
    public void setCardHoldername(String CardHoldername){this.CardHoldername = CardHoldername;}
    public void setCardNumber(String CardNumber){this.CardNumber = CardNumber;}
    public void setExpDate(String ExpDate){this.ExpDate = ExpDate;}
    public void setCvv(String CVV){this.CVV = CVV;}
}
