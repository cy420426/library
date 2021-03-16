package com.cy.bean;

import java.util.List;
//主导航
public class mainmenu {
    private int id;
    private String title;
    private String path;
    private List<submenu> slist;

    public mainmenu(int id, String title, String path, List<submenu> slist) {
        this.id = id;
        this.title = title;
        this.path = path;
        this.slist = slist;
    }

    public mainmenu(String title, String path, List<submenu> slist) {
        this.title = title;
        this.path = path;
        this.slist = slist;
    }

    public mainmenu() {
    }

    @Override
    public String toString() {
        return "mainmenu{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", path='" + path + '\'' +
                ", slist=" + slist +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<submenu> getSlist() {
        return slist;
    }

    public void setSlist(List<submenu> slist) {
        this.slist = slist;
    }
}
