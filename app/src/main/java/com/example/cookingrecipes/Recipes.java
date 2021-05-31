package com.example.cookingrecipes;

public class Recipes {
    public String title;
    public String category;
    public String products;
    public String description;
    public int favourites;

    public Recipes() {}

    public Recipes(String title, String category, String products, String description, int favourites) {
        this.title = title;
        this.category = category;
        this.products = products;
        this.description = description;
        this.favourites = favourites;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFavourites() {
        return favourites;
    }

    public void setFavourites(int favourites) {
        this.favourites = favourites;
    }
}
