package com.kata.backpack.models;

import io.vavr.control.Either;

import java.util.ArrayList;
import java.util.List;

public class Backpack {

    private List<Item> items = new ArrayList<Item>();

    public Backpack() {}

    public Either<Error, Backpack> store(Item item) {
        throw new RuntimeException("Not implemented yet");
    }

    public List<Item> getItems() {
        return this.items;
    }

}
