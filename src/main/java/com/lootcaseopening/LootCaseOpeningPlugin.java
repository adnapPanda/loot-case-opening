package com.lootcaseopening;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;
import javax.inject.Inject;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

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
            TOMBS_OF_AMASCUT, MOONS_OF_PERIL, BARROWS_CHEST
    );

    List<String> lootreceivedObjectNames = Arrays.asList("Corrupted Hunllef", "Crystalline Hunllef", "Theatre of Blood", "Chambers of Xeric", "Tombs of Amascut", "Lunar Chest", "Barrows");

    private static final Set<Integer> CG_MAP_REGION = ImmutableSet.of(11870, 11871, 11872, 12126, 12127, 12128, 12382, 12383, 12384);
    private static final Set<Integer> MOONS_MAP_REGION = ImmutableSet.of(5780, 5781, 5782, 6036, 6037, 6038, 6292, 6293, 6294);
    private static final Set<Integer> BARROWS_MAP_REGION = ImmutableSet.of(13974, 13975, 13976, 14230, 14231, 14232, 14486, 14487, 14488);
    private static final Set<Integer> TOB_MAP_REGION = ImmutableSet.of(12867);
    private static final Set<Integer> TOA_MAP_REGION = ImmutableSet.of(15184, 15696, 14672);

    private List<LocationCheck> locationCheck;

    @Override
    protected void startUp() {
        overlayManager.add(caseOpeningOverlay);
        keyManager.registerKeyListener(caseOpeningOverlay);

        //Construct LocationCheck class to verify that client is in specific region when onLootReceived event is called
        locationCheck = Arrays.asList(
                new LocationCheck(CORRUPTED_GAUNTLET, () -> checkInRegion(CG_MAP_REGION)),
                new LocationCheck(MOONS_OF_PERIL, () -> checkInRegion(MOONS_MAP_REGION)),
                new LocationCheck(BARROWS_CHEST, () -> checkInRegion(BARROWS_MAP_REGION)),
                new LocationCheck(CHAMBERS_OF_XERIC, () -> client.getVarbitValue(VarbitID.RAIDS_CLIENT_INDUNGEON) == 1),
                new LocationCheck(THEATRE_OF_BLOOD, () -> checkInRegion(TOB_MAP_REGION)),
                new LocationCheck(TOMBS_OF_AMASCUT, () -> checkInRegion(TOA_MAP_REGION))
        );

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
            List<LootEntry> table = ALL_LOOT_TABLES.get(r.nextInt(ALL_LOOT_TABLES.size()));
            LootEntry entry = table.get(r.nextInt(table.size()));
            openCase(table, entry.getItemId(), interfaceIDFor(table));
        }
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
        if (!lootreceivedObjectNames.contains(lootReceived.getName())) return;

        for (LocationCheck check : locationCheck) {
            if (check.isActive()) {
                openCaseForTable(lootReceived, check.getLootTable());
            }
        }
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

    private boolean checkInRegion(Set<Integer> mapRegion) {
        GameState gameState = client.getGameState();
        if (gameState != GameState.LOGGED_IN && gameState != GameState.LOADING) {
            return false;
        }

        int[] currentMapRegions = client.getTopLevelWorldView().getMapRegions();

        for (int region : currentMapRegions) {
            if (!mapRegion.contains(region)) {
                return false;
            }
        }

        return true;
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
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
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