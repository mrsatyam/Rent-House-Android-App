package com.example.myself.findme.model;

/**
 * Created by GB on 11/8/2015.
 */
public class ProductModel {

    private String image;
    private String id;
    private String title;
    private String area;
    private String address;
    private String city;
    private String created_at;
    private  String price;

    public ProductModel(String image, String id, String title, String area, String address, String city, String created_at, String price) {
        this.image = image;
        this.id = id;
        this.title = title;
        this.area = area;
        this.address = address;
        this.city = city;
        this.created_at = created_at;
        this.price=price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
