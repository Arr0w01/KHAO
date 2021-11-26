package model;

public class popularFood {

    String name,description;
    String price;
    String rating;
    String resturant;
    Boolean veg;
    Integer imageUrl;


    public popularFood(String name, String price,String description,String rating, String resturant,Boolean veg, Integer imageUrl) {
        this.name = name;
        this.price = price;
        this.rating = rating;
        this.imageUrl = imageUrl;
        this.veg = veg;
        this.resturant = resturant;
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

    public String getResturant() { return resturant; }

    public void setResturant(String resturant) { this.resturant = resturant; }

    public String getRating() { return rating; }

    public void setRating() { this.rating = rating; }

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
