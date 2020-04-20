package com.prehax.gomovie;

public class Facilities {
    private String name = "",address="",include = "";
    private double pay = 0;
    public String getAddress(){return address;}
    public void setAddress(String address){this.address = address;}
    public String getName() {
        return name;
    }

    public String getInclude() {
        return include;
    }

    public void setInclude(String include) {
        this.include = include;
    }

    public double getPay() {
        return pay;
    }

    public void setPay(double pay) {
        this.pay = pay;
    }

    public void setName(String name) {
        this.name = name;
    }
}
