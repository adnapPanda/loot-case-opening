package com.lootcaseopening;

import java.awt.Color;

public enum Rarity {
    COMMON(new Color(75, 105, 255), 80),
    UNCOMMON(new Color(136, 71, 255), 15),
    RARE(new Color(211, 44, 230), 10),
    ULTRA_RARE(new Color(235, 75, 75), 5),
    LEGENDARY(new Color(255, 230, 0), 1);

    private final Color color;
    private final int weight;

    Rarity(Color color, int weight) {
        this.color = color;
        this.weight = weight;
    }

    public Color getColor() {
        return color;
    }

    public int getWeight() {
        return weight;
    }
}