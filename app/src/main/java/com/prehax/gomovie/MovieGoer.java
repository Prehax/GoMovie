package com.prehax.gomovie;

import androidx.annotation.Nullable;

public class MovieGoer {
    private String firstName, lastName, address, city, state, zipcode, email, password, uuid;


    public String getAddress() {
        return address;
    }
    public String getCity() {
        return city;
    }
    public String getEmail() {
        return email;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getPassword() {
        return password;
    }
    public String getState() {
        return state;
    }
    public String getUuid() {
        return uuid;
    }
    public String getZipcode() {
        return zipcode;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setState(String state) {
        this.state = state;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}
