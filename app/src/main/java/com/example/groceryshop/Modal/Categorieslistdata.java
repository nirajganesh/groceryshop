package com.example.groceryshop.Modal;

public class Categorieslistdata {
    String author,download_url,id;
    int rowid;

    public Categorieslistdata(String author, String download_url, String id , int rowid) {
        this.author = author;
        this.download_url = download_url;
        this.id=id;
        this.rowid=rowid;
    }

    public int getRowid() {
        return rowid;
    }

    public void setRowid(int rowid) {
        this.rowid = rowid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Categorieslistdata() {
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }
}
