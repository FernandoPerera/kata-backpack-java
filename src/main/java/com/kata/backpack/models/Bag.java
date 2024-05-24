package com.kata.backpack.models;

import com.kata.backpack.enums.Category;
import io.vavr.control.Either;

import java.util.ArrayList;
import java.util.List;

public class Bag {

    private List<Item> items = new ArrayList<>();
    private Category category;

    public Bag(Category category) {
        this.category = category;
    }

    public Either<Error, Bag> store(Item item) {
        throw new RuntimeException("Not implemented yet");
    }

    public List<Item> getItems() {
        return this.items;
    }

}
