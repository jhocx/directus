package com.example.jhocx.directus;

public class Shop {
    private String name;
    private String address;
    private String photoURL;

    //Constructor
    Shop(String name, String address, String photoURL) {
        this.name = name;
        this.address = address;
        this.photoURL = photoURL;
    }

    public String getName() {
        return this.name;
    }

    public String getAddress(){
        return this.address;

    }

    public String getPhotoURL(){
        return this.photoURL;

    }


}
