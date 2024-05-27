package com.kata.backpack.useCase;

import com.kata.backpack.enums.Category;
import com.kata.backpack.models.Backpack;
import com.kata.backpack.models.Bag;
import com.kata.backpack.models.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemOrganizerTest {


    @Nested
    class StoreUseCases {

        /*
         *   store(item) -> store item in backpack
         *   store(ninthItem) -> store items in backpack to the limit, and another one in first bag
         *   store(thirteenthItem) -> store items in backpack to the limit, and rest in multiple bags
         *   store(twentyFifthItem) -> store items in backpack to the limit, the rest store in
         *                             multiple bags to the limit, and return AllBagsAreFull error
         */

        private Backpack backpack;
        private List<Bag> bags;
        private ItemOrganizer itemOrganizer;


        @BeforeEach
        void setUp() {
            backpack = new Backpack();
            bags = List.of(
                    new Bag(Category.CLOTHES),
                    new Bag(Category.UNKNOWN),
                    new Bag(Category.WEAPON),
                    new Bag(Category.WEAPON)
            );
            itemOrganizer = ItemOrganizer.of(backpack, bags);
        }

        @Test
        void should_store_item() {
            Item speedBoots = new Item("Speed Boots", Category.CLOTHES);

            itemOrganizer.store(speedBoots).fold(
                    error -> {
                        assertNull(error);
                        return null;
                    },
                    itemOrganizerSuccess -> {
                        assertTrue(itemOrganizerSuccess.getBackpackItems().contains(speedBoots));
                        return itemOrganizerSuccess;
                    }
            );
        }

    }

}