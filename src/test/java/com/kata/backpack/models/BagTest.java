package com.kata.backpack.models;

import com.kata.backpack.enums.Category;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class BagTest {

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

    }

}