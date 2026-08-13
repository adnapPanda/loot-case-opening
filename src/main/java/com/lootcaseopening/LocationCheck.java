package com.lootcaseopening;

import java.util.List;
import java.util.function.BooleanSupplier;

public class LocationCheck {
    private final List<LootEntry> lootTable;
    private final BooleanSupplier locationCheck;

    public LocationCheck(List<LootEntry> lootTable, BooleanSupplier locationCheck) {
        this.lootTable = lootTable;
        this.locationCheck = locationCheck;
    }

    public List<LootEntry> getLootTable() {
        return lootTable;
    }

    public boolean isActive() {
        return locationCheck.getAsBoolean();
    }
}