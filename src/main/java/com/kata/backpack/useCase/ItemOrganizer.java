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
        return backpack.store(item).fold(
                backpackError -> {
                    this.getBags().getFirst().store(item);
                    return Either.right(this);
                },
                backpackSuccess -> Either.right(this)
        );
    }

    public List<Item> getBackpackItems() {
        return this.backpack.getItems();
    }
    public List<Bag> getBags() {
        return this.bags;
    }
}
