package com.example.pius712.appassingnment.object;

import android.net.Uri;

import java.io.Serializable;

public class Obj_lost_info implements Serializable {
    public String stuff;
    public String seekerID;
    public String phone;
    public String description;
//    public String imageURI;


    public String getStuff() {
        return stuff;
    }

    public void setStuff(String stuff) {
        this.stuff = stuff;
    }

    public String getSeekerID() {
        return seekerID;
    }

    public void setSeekerID(String seekerID) {
        this.seekerID = seekerID;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public Obj_lost_info() {

    }
//
    public Obj_lost_info(String stuff, String seekerID, String phone, String description) {
        this.stuff = stuff;
        this.seekerID = seekerID;
        this.phone = phone;
        this.description = description;
    }

}
