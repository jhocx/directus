package com.example.jhocx.directus;

public class Shop {
    private String name;
    private String address;
    private String photoURL;
    private String shoppingMall;
    private String category;

    //Constructor
    Shop(String shoppingMall, String name, String address, String photoURL, String category) {
        this.shoppingMall = shoppingMall;
        this.name = name;
        this.address = address;
        this.photoURL = photoURL;
        this.category = category;
    }

    public String getShoppingMall() {
        return shoppingMall;
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

    public String getCategory() {
        return category;
    }

}
