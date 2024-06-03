package com.kata.backpack.useCase;

import com.kata.backpack.enums.Category;
import com.kata.backpack.errors.NoSpaceAvailable;
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
    private final int BAG_LIMIT_CAPACITY = 4;

    @Nested
    class StoreUseCases {

        /*
         *   store(item) -> store item in backpack
         *   store(ninthItem) -> store items in backpack to the limit, and another one in first bag
         *   store(thirteenthItem) -> store items in backpack to the limit, and rest in multiple bags
         *   store(twentyFifthItem) -> store items in backpack to the limit, the rest store in
         *                             multiple bags to the limit, and return NoSpaceAvailable error
         */

        private Backpack backpack;
        private List<Bag> bags;
        private ItemOrganizer itemOrganizer;


        @BeforeEach
        void setUp() {
            backpack = new Backpack();
            bags = List.of(
                    new Bag(Category.CLOTHES),
                    new Bag(Category.UNKNOWN)
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
            fillContainer(itemOrganizer, speedBoots, BACKPACK_LIMIT_CAPACITY);

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

        @Test
        void should_store_items_in_multiple_bags_if_first_bag_is_full() {
            Item speedBoots = new Item("Speed Boots", Category.CLOTHES);
            Item leatherHat = new Item("Leather Hat", Category.CLOTHES);
            Item woodenArch = new Item("Wooden Arch", Category.WEAPON);
            fillContainer(itemOrganizer, speedBoots, BACKPACK_LIMIT_CAPACITY);
            fillContainer(itemOrganizer, leatherHat, BAG_LIMIT_CAPACITY);

            itemOrganizer.store(woodenArch).fold(
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
                        assertTrue(itemOrganizerSuccess.getBags().stream().anyMatch(
                                bag -> bag.getItems().stream().allMatch(item -> item.equals(leatherHat))
                        ));
                        assertTrue(itemOrganizerSuccess.getBags().stream().anyMatch(
                                bag -> bag.getItems().contains(woodenArch)
                        ));
                        return itemOrganizerSuccess;
                    }
            );
        }

        @Test
        void should_not_allow_to_store_more_items_if_no_more_capacity_is_available() {
            Item speedBoots = new Item("Speed Boots", Category.CLOTHES);
            Item leatherHat = new Item("Leather Hat", Category.CLOTHES);
            Item woodenArch = new Item("Wooden Arch", Category.WEAPON);
            Item strangerStone = new Item("Stranger Stone", Category.UNKNOWN);
            fillContainer(itemOrganizer, speedBoots, BACKPACK_LIMIT_CAPACITY);
            fillContainer(itemOrganizer, leatherHat, BAG_LIMIT_CAPACITY);
            fillContainer(itemOrganizer, woodenArch, BAG_LIMIT_CAPACITY);

            itemOrganizer.store(strangerStone).fold(
                    error -> {
                        assertInstanceOf(NoSpaceAvailable.class, error);
                        return null;
                    },
                    itemOrganizerSuccess -> {
                        assertEquals(
                                itemOrganizerSuccess.getBackpackItems().stream().filter(
                                        item -> item.equals(speedBoots)
                                ).count(),
                                BACKPACK_LIMIT_CAPACITY
                        );
                        assertTrue(itemOrganizerSuccess.getBags().stream().anyMatch(
                                bag -> bag.getItems().stream().allMatch(item -> item.equals(leatherHat))
                        ));
                        assertTrue(itemOrganizerSuccess.getBags().stream().anyMatch(
                                bag -> bag.getItems().stream().allMatch(item -> item.equals(woodenArch))
                        ));
                        assertTrue(itemOrganizerSuccess.getBackpackItems().stream().noneMatch(
                                item -> item.equals(strangerStone)
                        ));
                        assertTrue(itemOrganizerSuccess.getBags().stream().allMatch(
                                bag -> bag.getItems().stream().noneMatch(item -> item.equals(strangerStone))
                        ));
                        return itemOrganizerSuccess;
                    }
            );
        }

    }

    private void fillContainer(ItemOrganizer itemOrganizer, Item item, int limit) {
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < limit; i++) {
            items.add(item);
        }

        items.forEach(currentItem -> itemOrganizer.store(item));
    }

}