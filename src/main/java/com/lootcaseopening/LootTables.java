package com.lootcaseopening;

import net.runelite.api.gameval.ItemID;

import java.util.List;

public final class LootTables
{
    public static final List<LootEntry> CORRUPTED_GAUNTLET = List.of(
            new LootEntry(ItemID.PRIF_WEAPON_SEED_ENHANCED, Rarity.LEGENDARY),
            new LootEntry(ItemID.PRIF_ARMOUR_SEED, Rarity.RARE),
            new LootEntry(ItemID.GAUNTLETPET, Rarity.ULTRA_RARE),
            new LootEntry(ItemID.CRYSTAL_SEED_OLD, Rarity.UNCOMMON),
            new LootEntry(ItemID.PRIF_CRYSTAL_SHARD, Rarity.COMMON)
            );

    public static final List<LootEntry> THEATRE_OF_BLOOD = List.of(
            new LootEntry(ItemID.INFERNAL_DEFENDER, Rarity.UNCOMMON),
            new LootEntry(ItemID.JUSTICIAR_CHESTGUARD, Rarity.RARE),
            new LootEntry(ItemID.JUSTICIAR_LEG_GUARDS, Rarity.RARE),
            new LootEntry(ItemID.JUSTICIAR_FACEGUARD, Rarity.RARE),
            new LootEntry(ItemID.GHRAZI_RAPIER, Rarity.ULTRA_RARE),
            new LootEntry(ItemID.SANGUINESTI_STAFF_UNCHARGED, Rarity.ULTRA_RARE),
            new LootEntry(ItemID.SCYTHE_OF_VITUR_UNCHARGED, Rarity.LEGENDARY)
    );

    public static final List<LootEntry> CHAMBERS_OF_XERIC = List.of(
            new LootEntry(ItemID.RAIDS_PRAYERSCROLL_AUGURY, Rarity.COMMON),
            new LootEntry(ItemID.RAIDS_PRAYERSCROLL, Rarity.COMMON),
            new LootEntry(ItemID.TWISTED_BUCKLER, Rarity.UNCOMMON),
            new LootEntry(ItemID.DRAGONHUNTER_XBOW, Rarity.UNCOMMON),
            new LootEntry(ItemID.DINHS_BULWARK, Rarity.RARE),
            new LootEntry(ItemID.ANCESTRAL_HAT, Rarity.RARE),
            new LootEntry(ItemID.ANCESTRAL_ROBE_TOP, Rarity.RARE),
            new LootEntry(ItemID.ANCESTRAL_ROBE_BOTTOM, Rarity.RARE),
            new LootEntry(ItemID.DRAGON_CLAWS, Rarity.RARE),
            new LootEntry(ItemID.ELDER_MAUL, Rarity.ULTRA_RARE),
            new LootEntry(ItemID.KODAI_INSIGNIA, Rarity.ULTRA_RARE),
            new LootEntry(ItemID.TWISTED_BOW, Rarity.LEGENDARY)
    );

    public static final List<LootEntry> TOMBS_OF_AMASCUT = List.of(
            new LootEntry(ItemID.LIGHTBEARER, Rarity.UNCOMMON),
            new LootEntry(ItemID.ELIDINIS_WARD, Rarity.UNCOMMON),
            new LootEntry(ItemID.OSMUMTENS_FANG, Rarity.RARE),
            new LootEntry(ItemID.MASORI_BODY, Rarity.ULTRA_RARE),
            new LootEntry(ItemID.MASORI_CHAPS, Rarity.ULTRA_RARE),
            new LootEntry(ItemID.MASORI_MASK, Rarity.ULTRA_RARE),
            new LootEntry(ItemID.TUMEKENS_SHADOW_UNCHARGED, Rarity.LEGENDARY)
    );

    public static final List<LootEntry> MOONS_OF_PERIL = List.of(
            new LootEntry(ItemID.BLOOD_MOON_CHESTPLATE, Rarity.ULTRA_RARE),
            new LootEntry(ItemID.BLOOD_MOON_HELM, Rarity.ULTRA_RARE),
            new LootEntry(ItemID.BLOOD_MOON_TASSETS, Rarity.ULTRA_RARE),
            new LootEntry(ItemID.DUAL_MACUAHUITL, Rarity.ULTRA_RARE),
            new LootEntry(ItemID.ECLIPSE_ATLATL, Rarity.RARE),
            new LootEntry(ItemID.ECLIPSE_MOON_CHESTPLATE, Rarity.RARE),
            new LootEntry(ItemID.ECLIPSE_MOON_HELM, Rarity.RARE),
            new LootEntry(ItemID.ECLIPSE_MOON_TASSETS, Rarity.RARE),
            new LootEntry(ItemID.FROSTMOON_SPEAR, Rarity.RARE),
            new LootEntry(ItemID.FROST_MOON_CHESTPLATE, Rarity.RARE),
            new LootEntry(ItemID.FROST_MOON_HELM, Rarity.RARE),
            new LootEntry(ItemID.FROST_MOON_TASSETS, Rarity.RARE),
            new LootEntry(ItemID.ATLATL_DART, Rarity.COMMON),
            new LootEntry(ItemID.SUN_KISSED_BONE, Rarity.COMMON),
            new LootEntry(ItemID.SWAMP_TAR, Rarity.COMMON),
            new LootEntry(ItemID.SOFTCLAY, Rarity.COMMON),
            new LootEntry(ItemID.BUCKET_SUPERCOMPOST, Rarity.COMMON),
            new LootEntry(ItemID.UNIDENTIFIED_HARRALANDER, Rarity.COMMON),
            new LootEntry(ItemID.BLESSED_BONE_SHARD, Rarity.COMMON),
            new LootEntry(ItemID.WATER_ORB, Rarity.COMMON),
            new LootEntry(ItemID.MAPLE_SEED, Rarity.COMMON),
            new LootEntry(ItemID.BABYWYRM_BONES, Rarity.COMMON),
            new LootEntry(ItemID.UNIDENTIFIED_IRIT, Rarity.COMMON),
            new LootEntry(ItemID.YEW_SEED, Rarity.COMMON)
    );

    public static final List<LootEntry> BARROWS_CHEST = List.of(
            new LootEntry(ItemID.BARROWS_AHRIM_BODY, Rarity.LEGENDARY),
            new LootEntry(ItemID.BARROWS_AHRIM_HEAD, Rarity.RARE),
            new LootEntry(ItemID.BARROWS_AHRIM_LEGS, Rarity.LEGENDARY),
            new LootEntry(ItemID.BARROWS_AHRIM_WEAPON, Rarity.RARE),
            new LootEntry(ItemID.BARROWS_DHAROK_BODY, Rarity.ULTRA_RARE),
            new LootEntry(ItemID.BARROWS_DHAROK_HEAD, Rarity.ULTRA_RARE),
            new LootEntry(ItemID.BARROWS_DHAROK_LEGS, Rarity.ULTRA_RARE),
            new LootEntry(ItemID.BARROWS_DHAROK_WEAPON, Rarity.ULTRA_RARE),
            new LootEntry(ItemID.BARROWS_GUTHAN_BODY, Rarity.ULTRA_RARE),
            new LootEntry(ItemID.BARROWS_GUTHAN_HEAD, Rarity.ULTRA_RARE),
            new LootEntry(ItemID.BARROWS_GUTHAN_LEGS, Rarity.RARE),
            new LootEntry(ItemID.BARROWS_GUTHAN_WEAPON, Rarity.RARE),
            new LootEntry(ItemID.BARROWS_KARIL_BODY, Rarity.LEGENDARY),
            new LootEntry(ItemID.BARROWS_KARIL_HEAD, Rarity.RARE),
            new LootEntry(ItemID.BARROWS_KARIL_LEGS, Rarity.LEGENDARY),
            new LootEntry(ItemID.BARROWS_KARIL_WEAPON, Rarity.RARE),
            new LootEntry(ItemID.BARROWS_TORAG_BODY, Rarity.ULTRA_RARE),
            new LootEntry(ItemID.BARROWS_TORAG_HEAD, Rarity.ULTRA_RARE),
            new LootEntry(ItemID.BARROWS_TORAG_LEGS, Rarity.ULTRA_RARE),
            new LootEntry(ItemID.BARROWS_TORAG_WEAPON, Rarity.RARE),
            new LootEntry(ItemID.BARROWS_VERAC_BODY, Rarity.RARE),
            new LootEntry(ItemID.BARROWS_VERAC_HEAD, Rarity.RARE),
            new LootEntry(ItemID.BARROWS_VERAC_LEGS, Rarity.RARE),
            new LootEntry(ItemID.BARROWS_VERAC_WEAPON, Rarity.RARE),
            new LootEntry(ItemID.MINDRUNE, Rarity.COMMON),
            new LootEntry(ItemID.CHAOSRUNE, Rarity.COMMON),
            new LootEntry(ItemID.DEATHRUNE, Rarity.COMMON),
            new LootEntry(ItemID.BLOODRUNE, Rarity.COMMON),
            new LootEntry(ItemID.DRAGON_MED_HELM, Rarity.UNCOMMON)
    );

    private LootTables()
    {
    }
}