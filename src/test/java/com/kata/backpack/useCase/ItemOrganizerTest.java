package com.kata.backpack.useCase;

import com.kata.backpack.enums.Category;
import com.kata.backpack.models.Backpack;
import com.kata.backpack.models.Bag;
import com.kata.backpack.models.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemOrganizerTest {


    private final int BACKPACK_LIMIT_CAPACITY = 8;

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

        @Test
        void should_store_item_in_bag_if_backpack_is_full() {
            Item speedBoots = new Item("Speed Boots", Category.CLOTHES);
            Item leatherHat = new Item("Leather Hat", Category.CLOTHES);
            fillBackpack(backpack, speedBoots);

            itemOrganizer.store(leatherHat).fold(
                    error -> {
                        assertNull(error);
                        return null;
                    },
                    itemOrganizerSuccess -> {
                        assertEquals(
                                itemOrganizerSuccess.getBackpackItems().stream().filter(
                                        item -> item.equals(speedBoots)
                                ).count(),
                                BACKPACK_LIMIT_CAPACITY
                        );
                        assertTrue(itemOrganizerSuccess.getBags().getFirst().getItems().contains(leatherHat));
                        return itemOrganizerSuccess;
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