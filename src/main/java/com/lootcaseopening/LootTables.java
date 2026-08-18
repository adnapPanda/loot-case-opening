package com.lootcaseopening;

import net.runelite.api.gameval.ItemID;

import java.util.List;

public final class LootTables {
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

    public static final List<LootEntry> DOOM = List.of(
            new LootEntry(ItemID.AVERNIC_TREADS, Rarity.LEGENDARY),
            new LootEntry(ItemID.EYE_OF_AYAK_UNCHARGED, Rarity.ULTRA_RARE),
            new LootEntry(ItemID.MOKHAIOTL_CLOTH, Rarity.ULTRA_RARE),
            new LootEntry(ItemID.DOMPET, Rarity.RARE)
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
            new LootEntry(ItemID.BARROWS_KARIL_LEGS, Rarity.LEGENDARY),
            new LootEntry(ItemID.BARROWS_KARIL_BODY, Rarity.LEGENDARY),
            new LootEntry(ItemID.BARROWS_AHRIM_LEGS, Rarity.LEGENDARY),
            new LootEntry(ItemID.BARROWS_DHAROK_BODY, Rarity.ULTRA_RARE),
            new LootEntry(ItemID.BARROWS_DHAROK_HEAD, Rarity.ULTRA_RARE),
            new LootEntry(ItemID.BARROWS_DHAROK_LEGS, Rarity.ULTRA_RARE),
            new LootEntry(ItemID.BARROWS_DHAROK_WEAPON, Rarity.ULTRA_RARE),
            new LootEntry(ItemID.BARROWS_GUTHAN_BODY, Rarity.ULTRA_RARE),
            new LootEntry(ItemID.BARROWS_GUTHAN_HEAD, Rarity.ULTRA_RARE),
            new LootEntry(ItemID.BARROWS_TORAG_BODY, Rarity.ULTRA_RARE),
            new LootEntry(ItemID.BARROWS_TORAG_HEAD, Rarity.ULTRA_RARE),
            new LootEntry(ItemID.BARROWS_TORAG_LEGS, Rarity.ULTRA_RARE),
            new LootEntry(ItemID.BARROWS_GUTHAN_LEGS, Rarity.RARE),
            new LootEntry(ItemID.BARROWS_GUTHAN_WEAPON, Rarity.RARE),
            new LootEntry(ItemID.BARROWS_KARIL_HEAD, Rarity.RARE),
            new LootEntry(ItemID.BARROWS_KARIL_WEAPON, Rarity.RARE),
            new LootEntry(ItemID.BARROWS_AHRIM_HEAD, Rarity.RARE),
            new LootEntry(ItemID.BARROWS_TORAG_WEAPON, Rarity.RARE),
            new LootEntry(ItemID.BARROWS_VERAC_BODY, Rarity.RARE),
            new LootEntry(ItemID.BARROWS_VERAC_HEAD, Rarity.RARE),
            new LootEntry(ItemID.BARROWS_VERAC_LEGS, Rarity.RARE),
            new LootEntry(ItemID.BARROWS_VERAC_WEAPON, Rarity.RARE),
            new LootEntry(ItemID.BARROWS_AHRIM_WEAPON, Rarity.RARE),
            new LootEntry(ItemID.DRAGON_MED_HELM, Rarity.UNCOMMON),
            new LootEntry(ItemID.BLOODRUNE, Rarity.COMMON),
            new LootEntry(ItemID.DEATHRUNE, Rarity.COMMON),
            new LootEntry(ItemID.CHAOSRUNE, Rarity.COMMON),
            new LootEntry(ItemID.MINDRUNE, Rarity.COMMON),
            new LootEntry(ItemID.COINS, Rarity.COMMON)
    );

    public static final List<LootEntry> GRAND_COFFIN = List.of(
            new LootEntry(ItemID.RING_OF_ENDURANCE_UNCHARGED, Rarity.LEGENDARY),
            //Add both rings since I am unsure which ItemID will be returned
            new LootEntry(ItemID.RING_OF_ENDURANCE, Rarity.LEGENDARY),
            new LootEntry(ItemID.STRANGE_OLD_LOCKPICK_FULL, Rarity.ULTRA_RARE),
            new LootEntry(ItemID.SANFEW_SALVE_4_DOSE, Rarity.UNCOMMON),
            new LootEntry(ItemID.RANARR_SEED, Rarity.UNCOMMON),
            new LootEntry(ItemID.RUNE_2H_SWORD, Rarity.UNCOMMON),
            new LootEntry(ItemID.RUNE_PLATEBODY, Rarity.UNCOMMON),
            new LootEntry(ItemID.HALLOWED_MARK, Rarity.COMMON)
    );

    public static final List<LootEntry> ELVEN_CRYSTAL_CHEST = List.of(
            new LootEntry(ItemID.UNCUT_ONYX, Rarity.LEGENDARY),
            new LootEntry(ItemID.DRAGONSTONE_HELMET, Rarity.LEGENDARY),
            new LootEntry(ItemID.DRAGONSTONE_GAUNTLETS, Rarity.LEGENDARY),
            new LootEntry(ItemID.DRAGONSTONE_PLATEBODY, Rarity.LEGENDARY),
            new LootEntry(ItemID.DRAGONSTONE_ARMOURED_BOOTS, Rarity.LEGENDARY),
            new LootEntry(ItemID.DRAGONSTONE_PLATELEGS, Rarity.LEGENDARY),
            new LootEntry(ItemID.DRAGON_PLATELEGS, Rarity.RARE),
            new LootEntry(ItemID.DRAGON_PLATESKIRT, Rarity.RARE),
            new LootEntry(ItemID.DRAGONSHIELD_A, Rarity.RARE),
            new LootEntry(ItemID.CRYSTAL_TREE_SEED, Rarity.UNCOMMON),
            new LootEntry(ItemID.RUNITE_ORE, Rarity.UNCOMMON),
            new LootEntry(ItemID.GOLD_ORE, Rarity.UNCOMMON),
            new LootEntry(ItemID.RAW_SHARK, Rarity.UNCOMMON),
            new LootEntry(ItemID.PRIF_CRYSTAL_SHARD, Rarity.COMMON),
            new LootEntry(ItemID.CRYSTAL_KEY, Rarity.COMMON),
            new LootEntry(ItemID.UNCUT_DRAGONSTONE, Rarity.COMMON)
    );

    public static final List<LootEntry> MOON_CHEST = List.of(
            new LootEntry(ItemID.MOON_HELMET, Rarity.LEGENDARY),
            new LootEntry(ItemID.UNCUT_ONYX, Rarity.LEGENDARY),
            new LootEntry(ItemID.VARLAMORE_KEY, Rarity.RARE),
            new LootEntry(ItemID.XBOWS_CROSSBOW_BOLTS_RUNITE_TIPPED_ONYX, Rarity.RARE),
            new LootEntry(ItemID.HUASCA_SEED, Rarity.UNCOMMON),
            new LootEntry(ItemID.DRAGON_PLATELEGS, Rarity.UNCOMMON),
            new LootEntry(ItemID.RUNE_PLATEBODY, Rarity.UNCOMMON),
            new LootEntry(ItemID.SPINACH_ROLL, Rarity.COMMON),
            new LootEntry(ItemID.CRYSTAL_KEY, Rarity.COMMON),
            new LootEntry(ItemID.CABBAGE, Rarity.COMMON),
            new LootEntry(ItemID.COAL, Rarity.COMMON),
            new LootEntry(ItemID.GOLD_ORE, Rarity.COMMON),
            new LootEntry(ItemID.UNCUT_DIAMOND, Rarity.COMMON),
            new LootEntry(ItemID.RAW_MONKFISH, Rarity.COMMON),
            new LootEntry(ItemID.SUN_KISSED_BONE, Rarity.COMMON),
            new LootEntry(ItemID.WATERMELON_SEED, Rarity.COMMON),
            new LootEntry(ItemID.NATURERUNE, Rarity.COMMON),
            new LootEntry(ItemID.SUNFIRESPLINTER, Rarity.COMMON)
    );

    public static final List<LootEntry> LARRANS_BIG_CHEST = List.of(
            new LootEntry(ItemID.DAGONHAI_HAT, Rarity.LEGENDARY),
            new LootEntry(ItemID.DAGONHAI_ROBE_BOTTOM, Rarity.LEGENDARY),
            new LootEntry(ItemID.DAGONHAI_ROBE_TOP, Rarity.LEGENDARY),
            new LootEntry(ItemID.DRAGONFRUIT_TREE_SEED, Rarity.RARE),
            new LootEntry(ItemID.MAGIC_TREE_SEED, Rarity.RARE),
            new LootEntry(ItemID.SNAPDRAGON_SEED, Rarity.RARE),
            new LootEntry(ItemID.RANARR_SEED, Rarity.RARE),
            new LootEntry(ItemID.TORSTOL_SEED, Rarity.RARE),
            new LootEntry(ItemID.CELASTRUS_TREE_SEED, Rarity.RARE),
            new LootEntry(ItemID.PALM_TREE_SEED, Rarity.RARE),
            new LootEntry(ItemID.DRAGON_DART_TIP, Rarity.RARE),
            new LootEntry(ItemID.RUNITE_ORE, Rarity.RARE),
            new LootEntry(ItemID.MAGIC_LOGS, Rarity.RARE),
            new LootEntry(ItemID.STEEL_BAR, Rarity.RARE),
            new LootEntry(ItemID.DRAGON_ARROWHEADS, Rarity.RARE),
            new LootEntry(ItemID.RAW_MANTARAY, Rarity.RARE),
            new LootEntry(ItemID.RAW_SEATURTLE, Rarity.RARE),
            new LootEntry(ItemID.RAW_SHARK, Rarity.RARE),
            new LootEntry(ItemID.SHARK_LURE, Rarity.RARE),
            new LootEntry(ItemID.REDWOOD_TREE_SEED, Rarity.UNCOMMON),
            new LootEntry(ItemID.RUNE_FULL_HELM, Rarity.UNCOMMON),
            new LootEntry(ItemID.RUNE_PLATEBODY, Rarity.UNCOMMON),
            new LootEntry(ItemID.RUNE_PLATELEGS, Rarity.UNCOMMON),
            new LootEntry(ItemID.COINS, Rarity.UNCOMMON),
            new LootEntry(ItemID.UNCUT_DIAMOND, Rarity.UNCOMMON),
            new LootEntry(ItemID.UNCUT_RUBY, Rarity.COMMON),
            new LootEntry(ItemID.COAL, Rarity.COMMON),
            new LootEntry(ItemID.GOLD_ORE, Rarity.COMMON),
            new LootEntry(ItemID.IRON_ORE, Rarity.COMMON),
            new LootEntry(ItemID.BLANKRUNE_HIGH, Rarity.COMMON),
            new LootEntry(ItemID.RAW_TUNA, Rarity.COMMON),
            new LootEntry(ItemID.RAW_LOBSTER, Rarity.COMMON),
            new LootEntry(ItemID.RAW_SWORDFISH, Rarity.COMMON),
            new LootEntry(ItemID.RAW_MONKFISH, Rarity.COMMON)
    );

    public static final List<LootEntry> ZOMBIE_PIRATES_LOCKER = List.of(
            new LootEntry(ItemID.WILDERNESS_BLIP_BLOCKING_SCROLL, Rarity.LEGENDARY),
            new LootEntry(ItemID.DRAGON_SCIMITAR, Rarity.RARE),
            new LootEntry(ItemID.DRAGON_LONGSWORD, Rarity.RARE),
            new LootEntry(ItemID.DRAGON_DAGGER, Rarity.RARE),
            new LootEntry(ItemID.MCANNONBALL, Rarity.UNCOMMON),
            new LootEntry(ItemID.RUNE_LONGSWORD, Rarity.UNCOMMON),
            new LootEntry(ItemID.RUNE_BATTLEAXE, Rarity.UNCOMMON),
            new LootEntry(ItemID.RUNE_WARHAMMER, Rarity.UNCOMMON),
            new LootEntry(ItemID.BLOODRUNE, Rarity.COMMON),
            new LootEntry(ItemID.DEATHRUNE, Rarity.COMMON),
            new LootEntry(ItemID.CHAOSRUNE, Rarity.COMMON),
            new LootEntry(ItemID.MINDRUNE, Rarity.COMMON),
            new LootEntry(ItemID.BATTLESTAFF, Rarity.COMMON),
            new LootEntry(ItemID.ADAMANT_PLATEBODY, Rarity.COMMON),
            new LootEntry(ItemID.RUNE_MED_HELM, Rarity.COMMON),
            new LootEntry(ItemID.RUNE_SWORD, Rarity.COMMON),
            new LootEntry(ItemID.RUNE_MACE, Rarity.COMMON),
            new LootEntry(ItemID.BLIGHTED_SACK_ICEBARRAGE, Rarity.COMMON),
            new LootEntry(ItemID.BLIGHTED_ANGLERFISH, Rarity.COMMON),
            new LootEntry(ItemID.BLIGHTED_MANTARAY, Rarity.COMMON),
            new LootEntry(ItemID.BLIGHTED_KARAMBWAN, Rarity.COMMON),
            new LootEntry(ItemID.BLIGHTED_4DOSE2RESTORE, Rarity.COMMON),
            new LootEntry(ItemID.COINS, Rarity.COMMON),
            new LootEntry(ItemID.GOLD_ORE, Rarity.COMMON),
            new LootEntry(ItemID.ADAMANT_SEED, Rarity.COMMON)
    );

    private LootTables() {
    }
}