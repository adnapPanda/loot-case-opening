package com.lootcaseopening;

import java.awt.*;
import java.util.*;
import java.util.List;
import javax.inject.Inject;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Provides;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.CommandExecuted;
import net.runelite.api.events.WidgetLoaded;
import net.runelite.api.gameval.InterfaceID;
import net.runelite.api.gameval.VarbitID;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetUtil;
import net.runelite.client.audio.AudioPlayer;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.ItemManager;
import net.runelite.client.game.ItemStack;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.loottracker.LootReceived;
import net.runelite.client.ui.overlay.OverlayManager;

import static com.lootcaseopening.LootTables.*;

@PluginDescriptor(
        name = "Loot Case Opening"
)
public class LootCaseOpeningPlugin extends Plugin {
    @Inject
    private OverlayManager overlayManager;

    @Inject
    private ItemManager itemManager;

    @Inject
    private Client client;

    @Inject
    private LootCaseOpeningOverlay caseOpeningOverlay;

    @Inject
    private LootCaseOpeningConfig config;

    @Inject
    private KeyManager keyManager;

    @Inject
    private AudioPlayer audioPlayer;

    //TODO: Placeholder
    private static final String LEGENDARY_SOUND_FILE = "legendary.wav";

    private Widget hiddenRewardWidget;
    private Integer hideComponentID;

    private static final List<List<LootEntry>> ALL_LOOT_TABLES = Arrays.asList(
            CORRUPTED_GAUNTLET, THEATRE_OF_BLOOD, CHAMBERS_OF_XERIC,
            TOMBS_OF_AMASCUT, MOONS_OF_PERIL, BARROWS_CHEST, GRAND_COFFIN
    );

    List<String> lootreceivedObjectNames = Arrays.asList("Corrupted Hunllef", "Crystalline Hunllef", "Theatre of Blood", "Chambers of Xeric", "Tombs of Amascut", "Lunar Chest", "Barrows",
            "Hallowed Sepulchre Grand Coffin");

    private static final Map<String, List<LootEntry>> LOOT_TABLE_BY_OBJECT_NAME = Map.of(
            "Corrupted Hunllef", CORRUPTED_GAUNTLET,
            "Crystalline Hunllef", CORRUPTED_GAUNTLET,
            "Theatre of Blood", THEATRE_OF_BLOOD,
            "Chambers of Xeric", CHAMBERS_OF_XERIC,
            "Tombs of Amascut", TOMBS_OF_AMASCUT,
            "Lunar Chest", MOONS_OF_PERIL,
            "Barrows", BARROWS_CHEST,
            "Hallowed Sepulchre Grand Coffin", GRAND_COFFIN
    );

    private static final Map<String, List<LootEntry>> LOOT_TABLE_BY_ARGS = Map.of(
            "cg", CORRUPTED_GAUNTLET,
            "moons", MOONS_OF_PERIL,
            "barrows", BARROWS_CHEST,
            "cox", CHAMBERS_OF_XERIC,
            "tob", THEATRE_OF_BLOOD,
            "toa", TOMBS_OF_AMASCUT,
            "sepulchre", GRAND_COFFIN
    );

    @Override
    protected void startUp() {
        overlayManager.add(caseOpeningOverlay);
        keyManager.registerKeyListener(caseOpeningOverlay);
    }

    @Override
    protected void shutDown() {
        overlayManager.remove(caseOpeningOverlay);
        keyManager.unregisterKeyListener(caseOpeningOverlay);
    }

    @Subscribe
    public void onCommandExecuted(CommandExecuted commandExecuted) {
        if (commandExecuted.getCommand().equalsIgnoreCase("opencase")) {
            Random r = new Random();
            List<LootEntry> table = getTableLootEntry(commandExecuted.getArguments(), r);
            LootEntry entry = table.get(r.nextInt(table.size()));
            openCase(table, entry.getItemId(), interfaceIDFor(table));
        }
    }

    private List<LootEntry> getTableLootEntry(String[] args, Random r) {
        if (args.length > 0) {
            List<LootEntry> lootEntry = LOOT_TABLE_BY_ARGS.get(args[0].toLowerCase());
            if (lootEntry != null) {
                return lootEntry;
            }
        }
        return ALL_LOOT_TABLES.get(r.nextInt(ALL_LOOT_TABLES.size()));
    }


    private Integer interfaceIDFor(List<LootEntry> lootTable) {
        if (lootTable == BARROWS_CHEST) {
            return InterfaceID.BarrowsReward.UNIVERSE;
        }
        if (lootTable == MOONS_OF_PERIL) {
            return InterfaceID.PmoonReward.UNIVERSAL;
        }
        if (lootTable == CHAMBERS_OF_XERIC) {
            return InterfaceID.RaidsRewards.UNIVERSE;
        }
        if (lootTable == THEATRE_OF_BLOOD) {
            return InterfaceID.TobChests.UNIVERSE;
        }
        if (lootTable == TOMBS_OF_AMASCUT) {
            return InterfaceID.ToaChests.UNIVERSE;
        }
        //CG has no widget
        return null;
    }


    @Subscribe
    public void onWidgetLoaded(WidgetLoaded widgetLoaded) {
        if (hideComponentID == null) {
            return;
        }

        int pendingGroupId = WidgetUtil.componentToInterface(hideComponentID);
        if (widgetLoaded.getGroupId() == pendingGroupId) {
            hideRewardWidget(hideComponentID);
        }

    }

    private void hideRewardWidget(int componentID) {
        Widget widget = client.getWidget(componentID);
        if (widget == null) {
            return;
        }
        widget.setHidden(true);
        hiddenRewardWidget = widget;
        hideComponentID = null;
    }

    private void unhideRewardWidget() {
        if (hiddenRewardWidget != null) {
            hiddenRewardWidget.setHidden(false);
            hiddenRewardWidget = null;
        }
        hideComponentID = null;
    }


    @Subscribe
    public void onLootReceived(LootReceived lootReceived) {
        List<LootEntry> lootTable = LOOT_TABLE_BY_OBJECT_NAME.get(lootReceived.getName());

        if (lootTable == null) return;
        openCaseForTable(lootReceived, lootTable);
    }

    private void openCaseForTable(LootReceived lootReceived, List<LootEntry> lootTable) {
        for (LootEntry entry : lootTable) {
            for (ItemStack item : lootReceived.getItems()) {
                if (entry.getItemId() == item.getId()) {
                    openCase(lootTable, entry.getItemId(), interfaceIDFor(lootTable));
                    return;
                }
            }
        }
    }

    public void openCase(List<LootEntry> lootTable, int wonItemId, Integer widgetComponentID) {
        List<LootItem> pool = buildItemPool(lootTable);
        LootItem winner = resolveWinner(pool, wonItemId);

        if (widgetComponentID != null) {
            hideComponentID = widgetComponentID;
            hideRewardWidget(widgetComponentID);
        }

        caseOpeningOverlay.open(pool, winner, result ->
                {
                    // TODO: skip playing sound for now
//			if (result.getRarity() == Rarity.LEGENDARY) playLegendarySound();
                },
                this::unhideRewardWidget
        );
    }

    private void playLegendarySound() {
        try {
            audioPlayer.play(getClass(), LEGENDARY_SOUND_FILE, 0f);
        } catch (Exception e) {
            //Do nothing and skip playing sound file
        }
    }

    private LootItem resolveWinner(List<LootItem> pool, int wonItemId) {
        for (LootItem item : pool) {
            if (item.getItemId() == wonItemId) {
                return item;
            }
        }

        return new LootItem(
                wonItemId,
                itemManager.getItemComposition(wonItemId).getName(),
                itemManager.getImage(wonItemId),
                Rarity.COMMON,
                rarityColor(Rarity.COMMON)
        );
    }


    private List<LootItem> buildItemPool(List<LootEntry> lootTable) {
        List<LootItem> pool = new ArrayList<>();
        for (LootEntry entry : lootTable) {
            pool.add(new LootItem(
                    entry.getItemId(),
                    itemManager.getItemComposition(entry.getItemId()).getName(),
                    itemManager.getImage(entry.getItemId()),
                    entry.getRarity(),
                    rarityColor(entry.getRarity())
            ));
        }
        return pool;
    }

    private Color rarityColor(Rarity rarity) {
        switch (rarity) {
            case COMMON:
                return config.commonColor();
            case UNCOMMON:
                return config.uncommonColor();
            case RARE:
                return config.rareColor();
            case ULTRA_RARE:
                return config.ultraRareColor();
            case LEGENDARY:
                return config.legendaryColor();
            default:
                return rarity.getColor();
        }
    }

    @Provides
    LootCaseOpeningConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(LootCaseOpeningConfig.class);
    }
}