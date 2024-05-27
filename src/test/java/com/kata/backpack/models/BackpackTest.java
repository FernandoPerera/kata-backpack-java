package com.kata.backpack.models;

import com.kata.backpack.enums.Category;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BackpackTest {

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

    }

}