package com.kata.backpack.models;

import com.kata.backpack.enums.Category;
import com.kata.backpack.errors.CannotStoreItem;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BackpackTest {

    private final int BACKPACK_LIMIT_CAPACITY = 8;

    @Nested
    class StoreUseCases {

        /*
         *   store(item) => store in backpack
         *   store(ninthItem) => store items to the limit, and response with CannotStoreItem error
         */

        @Test
        void should_store_item_in_backpack() {
            Backpack backpack = new Backpack();
            Item enchantedBreastplate = new Item("Enchanted Breastplate", Category.CLOTHES);

            backpack.store(enchantedBreastplate).fold(
                    error -> {
                        assertNull(error);
                        return null;
                    },
                    backpackSuccess -> {
                        assertTrue(backpackSuccess.getItems().contains(enchantedBreastplate));
                        return backpackSuccess;
                    }
            );
        }

        @Test
        void should_cannot_store_an_item_if_backpack_is_full() {
            Backpack backpack = new Backpack();
            Item enchantedBreastplate = new Item("Enchanted Breastplate", Category.CLOTHES);
            Item speedBoots = new Item("Speed Boots", Category.CLOTHES);

            fillBackpack(backpack, enchantedBreastplate);
            backpack.store(speedBoots).fold(
                    error -> {
                        assertInstanceOf(CannotStoreItem.class, error);
                        assertEquals(error.getMessage(), "Cannot store more items, backpack is full !!");
                        return error;
                    },
                    backpackSuccess -> {
                        assertEquals(backpackSuccess.getItems().stream().filter(
                                        item -> item.equals(enchantedBreastplate)
                                ).count(),
                                BACKPACK_LIMIT_CAPACITY
                        );
                        assertFalse(backpackSuccess.getItems().contains(speedBoots));
                        return backpackSuccess;
                    }
            );
        }

    }

    private void fillBackpack(Backpack backpack, Item item) {
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < BACKPACK_LIMIT_CAPACITY; i++) {
            items.add(item);
        }

        items.forEach(currentItem -> backpack.store(item));
    }

}