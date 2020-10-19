package com.example.practise.mvp.model;

public class IpData {
    private String country;
    private String city;
    private String area;

    public IpData() {

    }

    public IpData(String area, String city, String country) {
        this.area = area;
        this.city = city;
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public String getArea() {
        return area;
    }

    public String getCity() {
        return city;
    }
}
