package com.lootcaseopening;

import java.awt.*;
import java.util.*;
import java.util.List;
import javax.inject.Inject;

import com.google.inject.Provides;
import net.runelite.api.Client;
import net.runelite.api.events.CommandExecuted;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.events.WidgetLoaded;
import net.runelite.api.gameval.InterfaceID;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetUtil;
import net.runelite.client.callback.ClientThread;
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
import static java.util.Map.entry;

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
    private ClientThread clientThread;

    private Widget hiddenRewardWidget;
    private Integer hideComponentID;

    private final Set<Integer> processedDoomItemIds = new HashSet<>();


    private static final List<List<LootEntry>> ALL_LOOT_TABLES = Arrays.asList(
            CORRUPTED_GAUNTLET, THEATRE_OF_BLOOD, CHAMBERS_OF_XERIC,
            TOMBS_OF_AMASCUT, DOOM, MOONS_OF_PERIL, BARROWS_CHEST, GRAND_COFFIN, ELVEN_CRYSTAL_CHEST, MOON_CHEST, LARRANS_BIG_CHEST, ZOMBIE_PIRATES_LOCKER
    );

    private static final List<List<LootEntry>> SPAMMABLE_CHESTS = Arrays.asList(ELVEN_CRYSTAL_CHEST, MOON_CHEST, LARRANS_BIG_CHEST, ZOMBIE_PIRATES_LOCKER);
    private static final List<List<LootEntry>> WILDERNESS_CHESTS = Arrays.asList(LARRANS_BIG_CHEST, ZOMBIE_PIRATES_LOCKER);

    private static final Map<String, List<LootEntry>> LOOT_TABLE_BY_OBJECT_NAME = Map.ofEntries(
            entry("Corrupted Hunllef", CORRUPTED_GAUNTLET),
            entry("Crystalline Hunllef", CORRUPTED_GAUNTLET),
            entry("Theatre of Blood", THEATRE_OF_BLOOD),
            entry("Chambers of Xeric", CHAMBERS_OF_XERIC),
            entry("Tombs of Amascut", TOMBS_OF_AMASCUT),
            entry("Lunar Chest", MOONS_OF_PERIL),
            entry("Barrows", BARROWS_CHEST),
            entry("Hallowed Sepulchre Grand Coffin", GRAND_COFFIN),
            entry("Elven Crystal Chest", ELVEN_CRYSTAL_CHEST),
            entry("Chest (Moon key)", MOON_CHEST),
            entry("Larran's big chest", LARRANS_BIG_CHEST),
            entry("Zombie Pirate's Locker", ZOMBIE_PIRATES_LOCKER)
    );

    private static final Map<String, List<LootEntry>> LOOT_TABLE_BY_ARGS = Map.ofEntries(
            entry("cg", CORRUPTED_GAUNTLET),
            entry("moons", MOONS_OF_PERIL),
            entry("barrows", BARROWS_CHEST),
            entry("cox", CHAMBERS_OF_XERIC),
            entry("tob", THEATRE_OF_BLOOD),
            entry("toa", TOMBS_OF_AMASCUT),
            entry("doom", DOOM),
            entry("sepulchre", GRAND_COFFIN),
            entry("crystal", ELVEN_CRYSTAL_CHEST),
            entry("moon", MOON_CHEST),
            entry("larrans", LARRANS_BIG_CHEST),
            entry("zombie", ZOMBIE_PIRATES_LOCKER)
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
        if (lootTable == DOOM) {
            return InterfaceID.DomEndLevelUi.UNIVERSE;
        }
        return null;
    }

    @Subscribe
    public void onWidgetLoaded(WidgetLoaded widgetLoaded) {
        if (hideComponentID != null) {
            int pendingGroupId = WidgetUtil.componentToInterface(hideComponentID);
            if (widgetLoaded.getGroupId() == pendingGroupId) {
                hideRewardWidget(hideComponentID);
            }
        }

        if (widgetLoaded.getGroupId() == WidgetUtil.componentToInterface(InterfaceID.DomEndLevelUi.UNIVERSE)) {
            hideRewardWidget(InterfaceID.DomEndLevelUi.UNIVERSE);
            //Delay because loot isn't loaded into widget yet
            clientThread.invokeLater(this::checkDoomLoot);
        }
    }

    @Subscribe
    public void onMenuOptionClicked(MenuOptionClicked event) {
        Widget widget = event.getWidget();
        if (widget != null && widget.getId() == InterfaceID.DomEndLevelUi.BTN_LEAVE) {
            //clear when player leaves doom by claiming loot
            processedDoomItemIds.clear();
        }
    }

    private void checkDoomLoot() {
        if (caseOpeningOverlay.isActive()) {
            return;
        }

        Widget lootContents = client.getWidget(InterfaceID.DomEndLevelUi.LOOT_CONTENTS);
        if (lootContents == null || lootContents.getChildren() == null) {
            unhideRewardWidget();
            return;
        }

        Widget[] children = lootContents.getChildren();

        for (Widget itemWidget : children) {
            int itemId = itemWidget.getItemId();
            if (itemId <= 0) {
                continue;
            }

            int normalizedID = itemManager.canonicalize(itemId);
            if (!processedDoomItemIds.add(normalizedID)) {
                continue;
            }

            for (LootEntry entry : DOOM) {
                if (entry.getItemId() == normalizedID) {
                    openCase(DOOM, entry.getItemId(), interfaceIDFor(DOOM));
                    return;
                }
            }
        }
        unhideRewardWidget();
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
                int normalizedID = itemManager.canonicalize(item.getId());
                if (entry.getItemId() == normalizedID) {
                    openCase(lootTable, entry.getItemId(), interfaceIDFor(lootTable));
                    return;
                }
            }
        }
    }

    public void openCase(List<LootEntry> lootTable, int wonItemId, Integer widgetComponentID) {
        //Skip roll for spammable chests if config item is disabled
        if (SPAMMABLE_CHESTS.contains(lootTable) && !config.showWheelSpinForKeyChests()) return;
        //Skip roll for wildy chests if config is disabled
        if (WILDERNESS_CHESTS.contains(lootTable) && !config.showWheelSpinInWilderness()) return;
        List<LootItem> pool = buildItemPool(lootTable);
        LootItem winner = resolveWinner(pool, wonItemId);

        if (widgetComponentID != null) {
            hideComponentID = widgetComponentID;
            hideRewardWidget(widgetComponentID);
        }

        caseOpeningOverlay.open(pool, winner, result ->
                {
                    if (config.playLegendaryJingle() && result.getRarity() == Rarity.LEGENDARY) playLegendarySound();
                },
                this::unhideRewardWidget
        );
    }

    private void playLegendarySound() {
        try {
            client.playSoundEffect(6765, config.legendaryJingleVolume());
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