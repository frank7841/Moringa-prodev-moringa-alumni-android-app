package com.prodev.moringaalumni;

public class ModelUser {

    String name, email, search, phone, image, cover;

    public ModelUser(){

    }

    public ModelUser(String name, String email, String search, String phone, String image, String cover){
        this.name= name;
        this.email=email;
        this.search= search;
        this.phone = phone;
        this.image= image;
        this.cover= cover;
    }

    public String getName(){
        return name;
    }
    public void setName (String name){
        this.name = name;
    }
    public String getPhone(){
        return phone;

    }
    public void setPhone(String phone){
        this.phone = phone;
    }
    public String getImage(){
        return image = image;
    }
    public void setImage(String image){
        this.image = image;
    }
    public String getCover(){
        return cover;
    }
    public void setCover(String cover){
        this.cover = cover;
    }

}
