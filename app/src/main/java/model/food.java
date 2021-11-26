package model;

public class food {
    String name, price, rating,description,resturantname, imageurl,veg, category;


    public food(String name, String price, String rating, String resturantname, String imageurl, String veg, String description, String category){

        this.name = name;
        this.price = price;
        this.rating = rating;
        this.resturantname =resturantname;
        this.imageurl = imageurl;
        this.veg = veg;
        this.description = description;
        this.category = category;



    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public food(){}

}
