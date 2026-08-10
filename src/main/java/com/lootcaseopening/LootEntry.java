package com.lootcaseopening;

public class LootEntry
{
    private final int itemId;
    private final Rarity rarity;

    public LootEntry(int itemId, Rarity rarity)
    {
        this.itemId = itemId;
        this.rarity = rarity;
    }

    public int getItemId()
    {
        return itemId;
    }

    public Rarity getRarity()
    {
        return rarity;
    }
}