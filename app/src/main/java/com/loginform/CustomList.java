package com.loginform;

public class CustomList {
        String name;
        String description;
        int image;
        String price;

    public CustomList(int image, String name,String price,String description) {
            this.image=image;
            this.name=name;
            this.price=price;
            this.description=description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
