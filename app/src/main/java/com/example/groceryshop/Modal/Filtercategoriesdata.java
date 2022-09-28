package com.example.groceryshop.Modal;

public class Filtercategoriesdata {
    String productid,productname,productimage,productprice,categoriestype;
    Integer rowid;
    public Filtercategoriesdata(String productid, String productname, String productimage, String productprice,String categoriestype,Integer rowid) {
        this.productid = productid;
        this.productname = productname;
        this.productimage = productimage;
        this.productprice = productprice;
        this.categoriestype=categoriestype;
        this.rowid=rowid;
    }

    public Filtercategoriesdata() {
    }

    public Integer getRowid() {
        return rowid;
    }

    public void setRowid(Integer rowid) {
        this.rowid = rowid;
    }

    public String getCategoriestype() {
        return categoriestype;
    }

    public void setCategoriestype(String categoriestype) {
        this.categoriestype = categoriestype;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductimage() {
        return productimage;
    }

    public void setProductimage(String productimage) {
        this.productimage = productimage;
    }

    public String getProductprice() {
        return productprice;
    }

    public void setProductprice(String productprice) {
        this.productprice = productprice;
    }
}
