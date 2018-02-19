package com.example.myself.findme.model;

/**
 * Created by MySelf on 5/9/2016.
 */
public class ProductListModel {


    private String image;
    private String id;
    private String title;
    private String area;
    private String address;
    private String city;
    private String created_at;
    private  String price;
    private String short_tag;
    private String description;
    private String contact_no;
    private String userid;

    public ProductListModel(String image, String id, String title, String area, String address, String city, String created_at, String price, String short_tag, String description, String contact_no,String userid) {
        this.image = image;
        this.id = id;
        this.title = title;
        this.area = area;
        this.address = address;
        this.city = city;
        this.created_at = created_at;
        this.price = price;
        this.short_tag = short_tag;
        this.description = description;
        this.contact_no = contact_no;
        this.userid=userid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getShort_tag() {
        return short_tag;
    }

    public void setShort_tag(String short_tag) {
        this.short_tag = short_tag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }
}
