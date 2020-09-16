package com.example.projektcrm;

import com.google.firebase.database.Exclude;


public class Task {
    private String documentID;
    private String company;
    private String address;
    private String description;
    private String phone;
    private String date;
    private String status;


    public Task(String company, String address, String phone, String description, String date, String status) {
        this.company = company;
        this.address = address;
        this.description = description;
        this.phone = phone;
        this.date = date;
        this.status=status;
    }

    @Exclude
    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    public Task() {
    }

//


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
