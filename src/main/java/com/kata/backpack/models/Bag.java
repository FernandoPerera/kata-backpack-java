package com.kata.backpack.models;

import com.kata.backpack.common.Error;
import com.kata.backpack.enums.Category;
import com.kata.backpack.errors.CannotStoreItem;
import io.vavr.control.Either;

import java.util.ArrayList;
import java.util.List;

public class Bag {

    private List<Item> items = new ArrayList<>();
    private Category category;

    private final int BAG_LIMIT_CAPACITY = 4;

    public Bag(Category category) {
        this.category = category;
    }

    public Either<Error, Bag> store(Item item) {

        boolean bagIsNotFull = items.size() < BAG_LIMIT_CAPACITY;
        if (bagIsNotFull) {
            items.add(item);
            return Either.right(this);
        }
        return Either.left(new CannotStoreItem("Cannot store more items, bag is full !!"));
    }

    public List<Item> getItems() {
        return this.items;
    }

}
