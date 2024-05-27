package com.kata.backpack.models;

import com.kata.backpack.common.Error;
import com.kata.backpack.errors.CannotStoreItem;
import io.vavr.control.Either;

import java.util.ArrayList;
import java.util.List;

public class Backpack {

    private List<Item> items = new ArrayList<Item>();

    private final int BACKPACK_LIMIT_CAPACITY = 8;

    public Backpack() {}

    public Either<Error, Backpack> store(Item item) {

        boolean backpackIsNotFull = items.size() < BACKPACK_LIMIT_CAPACITY;
        if (backpackIsNotFull) {
            items.add(item);
            return Either.right(this);
        }
        return Either.left(new CannotStoreItem("Cannot store more items, backpack is full !!"));
    }

    public List<Item> getItems() {
        return this.items;
    }

}
