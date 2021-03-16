package com.cy.bean;

import java.util.Date;

public class result {
    private int id;
    private String username;
    private int result;
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "result{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", result=" + result +
                ", date=" + date +
                '}';
    }
}
