package model;

public class Resturant {
    String name, rating, lon, lat,online, phone,type, minimumbudget, imageurl;

    public Resturant(String name, String rating, String lon, String lat, String online, String phone, String type, String minimumbudget,String imageurl){
        this.name = name;
        this.rating = rating;
        this.lon = lon;
        this.lat = lat;
        this.online = online;
        this.phone = phone;
        this.type = type;
        this.minimumbudget = minimumbudget;
         this.imageurl = imageurl;
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMinimumbudget() {
        return minimumbudget;
    }

    public void setMinimumbudget(String minimumbudget) {
        this.minimumbudget = minimumbudget;
    }

    public Resturant(){}
}
