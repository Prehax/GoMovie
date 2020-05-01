package com.prehax.gomovie;

public class Facilities {
    private String name = "",address="",include = "",pay = "";

    public String getAddress(){return address;}
    public void setAddress(String address){this.address = address;}
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getInclude() {
        return include;
    }

    public void setInclude(String include) {
        this.include = include;
    }

    public String  getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }


}
