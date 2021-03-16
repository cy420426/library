package com.cy.bean;

public class Queryinfo {
    private String query;//查询信息
    private int pagenum=1;//当前页
    private int pagesize =1;//每页最大数

    public Queryinfo(String query, int pagenum, int pagesize) {
        this.query = query;
        this.pagenum = pagenum;
        this.pagesize = pagesize;
    }

    public Queryinfo() {
    }

    @Override
    public String toString() {
        return "Queryinfo{" +
                "query='" + query + '\'' +
                ", pagenum=" + pagenum +
                ", pagesize=" + pagesize +
                '}';
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getPagenum() {
        return pagenum;
    }

    public void setPagenum(int pagenum) {
        this.pagenum = pagenum;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }
}
