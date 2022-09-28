package com.example.groceryshop.Modal;

public class Cartlistdata {
    String productid,productname,productimage,productprice,productquantity;
    Integer rowid;

    public Cartlistdata(String productid, String productname, String productimage, String productprice,String productquantity,Integer rowid) {
        this.productid = productid;
        this.productname = productname;
        this.productimage = productimage;
        this.productprice = productprice;
        this.productquantity=productquantity;
        this.rowid=rowid;
    }

    public  Cartlistdata() {
    }

    public Integer getRowid() {
        return rowid;
    }

    public void setRowid(Integer rowid) {
        this.rowid = rowid;
    }

    public String getProductquantity() {
        return productquantity;
    }

    public void setProductquantity(String productquantity) {
        this.productquantity = productquantity;
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
