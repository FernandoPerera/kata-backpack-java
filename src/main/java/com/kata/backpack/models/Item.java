package com.kata.backpack.models;

import com.kata.backpack.enums.Category;

public class Item {

    private String name;
    private Category category;

    public Item(String name, Category category) {
        this.name = name;
        this.category = category;
    }

}
