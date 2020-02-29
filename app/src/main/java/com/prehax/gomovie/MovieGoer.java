package com.prehax.gomovie;

import androidx.annotation.Nullable;

public class MovieGoer {
    private String FName="", LName="", Address="", City="", State="", Zip="";


    public String getAddress() {
        return Address;
    }
    public String getCity() {
        return City;
    }
    public String getFirstName() {
        return FName;
    }
    public String getLastName() {
        return LName;
    }
    public String getState() {
        return State;
    }
    public String getZipcode() {
        return Zip;
    }
    public void setAddress(String address) {
        this.Address = address;
    }
    public void setCity(String city) {
        this.City = city;
    }
    public void setFirstName(String firstName) {
        this.FName = firstName;
    }
    public void setLastName(String lastName) {
        this.LName = lastName;
    }
    public void setState(String state) {
        this.State = state;
    }
    public void setZipcode(String zipcode) {
        this.Zip = zipcode;
    }
}
