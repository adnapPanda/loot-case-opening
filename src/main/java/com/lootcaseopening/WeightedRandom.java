package com.lootcaseopening;

import java.util.List;
import java.util.Random;

public final class WeightedRandom {
    private static final Random RANDOM = new Random();

    public static LootItem pick(List<LootItem> pool) {
        int totalWeight = 0;
        for (LootItem item : pool) {
            totalWeight += item.getRarity().getWeight();
        }

        int roll = RANDOM.nextInt(totalWeight);
        int cumulative = 0;
        for (LootItem item : pool) {
            cumulative += item.getRarity().getWeight();
            if (roll < cumulative) {
                return item;
            }
        }

        // Shouldn't happen, but guard against float/rounding edge cases.
        return pool.get(pool.size() - 1);
    }

    private WeightedRandom() {
    }
}