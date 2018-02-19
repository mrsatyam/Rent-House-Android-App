package com.example.myself.findme.adapter;

/**
 * Created by MySelf on 5/11/2016.
 */
public class MessageModel {

    String messageid;
    String from;
    String to;
    String message;
    String category;
    String createdat;
    String productid;
    String name;


    public MessageModel(String messageid, String from, String to, String message, String category, String createdat, String productid,String name) {
        this.messageid = messageid;
        this.from = from;
        this.to = to;
        this.message = message;
        this.category = category;
        this.createdat = createdat;
        this.productid = productid;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessageid() {
        return messageid;
    }

    public void setMessageid(String messageid) {
        this.messageid = messageid;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCreatedat() {
        return createdat;
    }

    public void setCreatedat(String createdat) {
        this.createdat = createdat;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }
}
