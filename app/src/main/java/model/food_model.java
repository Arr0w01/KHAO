package model;

public class food_model {

    String name,description;
    String price;
    Integer imageUrl;
    String rating;
    String resturantname;
    Boolean veg;


    public food_model(String name, String price, String description, String rating, String resturantname, Boolean veg , Integer imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.rating =  rating;
        this.resturantname = resturantname;
        this.veg = veg;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getVeg() { return veg; }

    public void setVeg(Boolean veg) { this.veg = veg; }

    public String getRating() { return rating; }

    public void setRating(String rating) { this.rating = rating; }

    public String getResturantname() { return resturantname; }

    public void setResturantname(String resturantname) { this.resturantname = resturantname; }

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

    public Integer getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Integer imageUrl) {
        this.imageUrl = imageUrl;
    }
}
