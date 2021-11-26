package model;

public class Cart {
    String uid, name, price,totalprice, rating,description,resturantname, imageurl,veg, itemcount;

    public Cart(String uid, String name, String price,String totalprice, String itemcount, String rating, String resturantname, String imageurl, String veg, String description){
        this.uid = uid;
        this.name = name;
        this.price = price;
        this.totalprice = totalprice;
        this.itemcount = itemcount;
        this.rating = rating;
        this.resturantname =resturantname;
        this.imageurl = imageurl;
        this.veg = veg;
        this.description = description;

    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getItemcount() {
        return itemcount;
    }

    public void setItemcount(String itemcount) {
        this.itemcount = itemcount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVeg() {
        return veg;
    }

    public void setVeg(String veg) {
        this.veg = veg;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getResturantname() {
        return resturantname;
    }

    public void setResturantname(String resturantname) {
        this.resturantname = resturantname;
    }

    public Cart(){}
}
