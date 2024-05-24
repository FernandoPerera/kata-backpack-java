package com.kata.backpack.models;

import com.kata.backpack.enums.Category;
import com.kata.backpack.errors.CannotStoreItem;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BagTest {

    private final int BAG_LIMIT_CAPACITY = 4;

    @Nested
    class StoreUseCases {

        /*
         *  store(item) => store item in bag
         *  store(fifthItem) => store items to the limit, and response with CannotStoreItem error
         */

        @Test
        void should_store_item_in_bag() {
            Bag bag = new Bag(Category.METAL);
            Item iron = new Item("Iron", Category.METAL);

            bag.store(iron).fold(
                    error -> {
                        assertNull(error);
                        return null;
                    },
                    bagSuccess -> {
                        assertTrue(bagSuccess.getItems().contains(iron));
                        return bagSuccess;
                    }
            );
        }

        @Test
        void should_cannot_store_an_item_if_bag_is_full() {
            Bag bag = new Bag(Category.METAL);
            Item iron = new Item("Iron", Category.METAL);
            Item poisonousLeaf = new Item("Poisonous Leaf", Category.HERB);

            fillBag(bag, iron);

            bag.store(poisonousLeaf).fold(
                    error -> {
                        assertInstanceOf(CannotStoreItem.class, error);
                        assertEquals(error.getMessage(), "Cannot store more items, bag is full !!");
                        return error;
                    },
                    bagSuccess -> {
                        assertEquals(
                                bagSuccess.getItems().stream().filter(
                                        item -> item.equals(iron)
                                ).count(),
                                BAG_LIMIT_CAPACITY
                        );
                        assertFalse(bagSuccess.getItems().contains(poisonousLeaf));
                        return bagSuccess;
                    }
            );
        }

    }

    private void fillBag(Bag bag, Item item) {

        List<Item> items = new ArrayList<>();
        for (int i = 0; i < BAG_LIMIT_CAPACITY; i++) {
            items.add(item);
        }

        items.forEach(currentItem -> bag.store(item));
    }

}