package com.cy.bean;
//此分枝导航
public class submenu {
    private int id;
    private String title;
    private String path;

    public submenu() {
    }

    public submenu(int id, String title, String path) {
        this.id = id;
        this.title = title;
        this.path = path;
    }

    public submenu(String title, String path) {
        this.title = title;
        this.path = path;
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

    @Override
    public String toString() {
        return "submenu{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
