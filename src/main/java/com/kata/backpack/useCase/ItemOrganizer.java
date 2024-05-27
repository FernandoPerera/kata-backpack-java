package com.kata.backpack.useCase;

import com.kata.backpack.common.Error;
import com.kata.backpack.models.Backpack;
import com.kata.backpack.models.Bag;
import com.kata.backpack.models.Item;
import io.vavr.control.Either;

import java.util.List;

public class ItemOrganizer {

    private Backpack backpack;
    private List<Bag> bags;

    private ItemOrganizer(Backpack backpack, List<Bag> bags) {
        this.backpack = backpack;
        this.bags = bags;
    }

    public static ItemOrganizer of(Backpack backpack, List<Bag> bags) {
        return new ItemOrganizer(backpack, bags);
    }

    public Either<Error, ItemOrganizer> store(Item item) {
        throw new RuntimeException("Not implemented yet");
    }

    public List<Item> getBackpackItems() {
        return this.backpack.getItems();
    }

}
