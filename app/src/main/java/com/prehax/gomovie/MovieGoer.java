package com.prehax.gomovie;

import androidx.annotation.Nullable;

public class MovieGoer {
    private String Fname="", Lname="", Address="", City="", State="", Zip="", pref="";

    private String Time="";
    private String CardHoldername="", CardNumber="", ExpDate="", CVV="";



    public String getPref() {
        return pref;
    }

    public void setPref(String pref) {
        this.pref = pref;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        this.Time = time;
    }

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

class Ticket {
    private String movieName, theaterName, showTime, seat, tAmount, status;
    private int numOfTic, numOfCok, numOfPop, theaterID;
    private boolean rated;

    public String getMovieName() {
        return movieName;
    }
    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }
    public String getTheaterName() {
        return theaterName;
    }
    public void setTheaterName(String theaterName) {
        this.theaterName = theaterName;
    }
    public String getShowTime() {
        return showTime;
    }
    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }
    public String getSeat() {
        return seat;
    }
    public void setSeat(String seat) {
        this.seat = seat;
    }
    public String gettAmount() {
        return tAmount;
    }
    public void settAmount(String tAmount) {
        this.tAmount = tAmount;
    }
    public int getNumOfTic() {
        return numOfTic;
    }
    public void setNumOfTic(int numOfTic) {
        this.numOfTic = numOfTic;
    }
    public int getNumOfCok() {
        return numOfCok;
    }
    public void setNumOfCok(int numOfCok) {
        this.numOfCok = numOfCok;
    }
    public int getNumOfPop() {
        return numOfPop;
    }
    public void setNumOfPop(int numOfPop) {
        this.numOfPop = numOfPop;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public int getTheaterID() {
        return theaterID;
    }
    public void setTheaterID(int theaterID) {
        this.theaterID = theaterID;
    }
    public boolean isRated() {
        return rated;
    }
    public void setRated(boolean rated) {
        this.rated = rated;
    }
}

