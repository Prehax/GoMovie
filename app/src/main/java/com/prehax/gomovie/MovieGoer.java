package com.prehax.gomovie;

import androidx.annotation.Nullable;

public class MovieGoer {
    private String Fname="", Lname="", Address="", City="", State="", Zip="";


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
}
