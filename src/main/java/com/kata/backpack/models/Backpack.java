package com.kata.backpack.models;

import com.kata.backpack.common.Error;
import io.vavr.control.Either;

import java.util.ArrayList;
import java.util.List;

public class Backpack {

    private List<Item> items = new ArrayList<Item>();

    public Backpack() {}

    public Either<Error, Backpack> store(Item item) {
        items.add(item);
        return Either.right(this);
    }

    public List<Item> getItems() {
        return this.items;
    }

}
