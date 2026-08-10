package com.lootcaseopening;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class LootItem
{
    private final int itemId;
    private final String name;
    private final BufferedImage image;
    private final Rarity rarity;

    public LootItem(int itemId, String name, BufferedImage image, Rarity rarity)
    {
        this.itemId = itemId;
        this.name = name;
        this.image = image;
        this.rarity = rarity;
    }

    public int getItemId()
    {
        return itemId;
    }

    public String getName()
    {
        return name;
    }

    public BufferedImage getImage()
    {
        return image;
    }

    public Rarity getRarity()
    {
        return rarity;
    }

    public Color getRarityColor()
    {
        return rarity.getColor();
    }
}