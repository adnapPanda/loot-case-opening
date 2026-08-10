package com.lootcaseopening;

import java.util.*;
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
public class LootCaseOpeningPlugin extends Plugin
{
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
	private ConfigManager configManager;

	@Inject
	private KeyManager keyManager;

	List<Integer> randomCGItem = Arrays.asList(23962, 4207, 25859, 23956, 23757);
	List<List<LootEntry>> randomLootTable = Arrays.asList(CORRUPTED_GAUNTLET);

	List<String> lootreceivedObjectNames = Arrays.asList("Corrupted Hunllef", "Crystalline Hunllef", "tob", "cox", "toa", "Lunar Chest", "Barrows");

	private static final Set<Integer> CG_MAP_REGION = ImmutableSet.of(11870, 11871, 11872, 12126, 12127, 12128, 12382, 12383, 12384);
	private static final Set<Integer> MOONS_MAP_REGION = ImmutableSet.of(5780, 5781, 5782, 6036, 6037, 6038, 6292, 6293, 6294);
	private static final Set<Integer> BARROWS_MAP_REGION = ImmutableSet.of(13974, 13975, 13976, 14230, 14231, 14232, 14486, 14487, 14488);


	@Override
	protected void startUp()
	{
		overlayManager.add(caseOpeningOverlay);
		keyManager.registerKeyListener(caseOpeningOverlay);
	}

	@Override
	protected void shutDown()
	{
		overlayManager.remove(caseOpeningOverlay);
		keyManager.unregisterKeyListener(caseOpeningOverlay);
	}

	@Subscribe
	public void onCommandExecuted(CommandExecuted commandExecuted)
	{
		if (commandExecuted.getCommand().equalsIgnoreCase("opencase"))
		{
			Random r = new Random();
			openCase(randomLootTable.get(r.nextInt(randomLootTable.size())), randomCGItem.get(r.nextInt(randomCGItem.size())));
		}
	}

	@Subscribe
	public void onWidgetLoaded(WidgetLoaded widgetLoaded)
	{
		System.out.println("Widget Loaded");
		System.out.println(widgetLoaded.getGroupId());
		switch (widgetLoaded.getGroupId())
		{
			case InterfaceID.BARROWS_REWARD:
				Widget barrowsOverlay = client.getWidget(InterfaceID.BARROWS_REWARD);
				System.out.println(barrowsOverlay.getActions());
				barrowsOverlay.setHidden(true);
				break;
			case InterfaceID.PMOON_REWARD:
				client.getWidget(InterfaceID.PMOON_REWARD).setHidden(true);
				break;
			case InterfaceID.RAIDS_REWARDS:
				client.getWidget(InterfaceID.RAIDS_REWARDS).setHidden(true);
				break;
			case InterfaceID.TOB_CHESTS:
				client.getWidget(InterfaceID.TOB_CHESTS).setHidden(true);
				break;
			case InterfaceID.TOA_CHESTS:
				client.getWidget(InterfaceID.TOA_CHESTS).setHidden(true);
				break;
			default:
				return;
		}
	}

	@Subscribe
	public void onLootReceived(LootReceived lootReceived)
	{
		if (checkInCG()) {
			System.out.println("In CG");
			if (lootreceivedObjectNames.contains(lootReceived.getName())) {
				// For each item defined in CORRUPTED_GAUNTLET, check whether it is part of the loot
				// Checks from more rare to least rare. Ensures that the rarest item takes priority
				outerloop:
				for (LootEntry cgLoot: CORRUPTED_GAUNTLET)
				{
					for (ItemStack item : lootReceived.getItems())
					{
						int itemID = item.getId();
						// Will always roll unless player died and didn't get crystal shards
						if (cgLoot.getItemId() == itemID)
						{
							openCase(CORRUPTED_GAUNTLET, itemID);
							//Stop both for loops since we only want to roll once
							break outerloop;
						}
					}
				}
			}
		}

		if (checkInMoons()) {
			System.out.println("In Moons");
			if (lootreceivedObjectNames.contains(lootReceived.getName())) {
				outerloop:
				for (LootEntry moonsLoot: MOONS_OF_PERIL)
				{
					for (ItemStack item : lootReceived.getItems())
					{
						int itemID = item.getId();
						if (moonsLoot.getItemId() == itemID)
						{
							openCase(MOONS_OF_PERIL, itemID);
							//Stop both for loops since we only want to roll once
							break outerloop;
						}
					}
				}
			}

		}

		if (checkInBarrows()) {
			System.out.println("In Barrows");
			if (lootreceivedObjectNames.contains(lootReceived.getName())) {
				outerloop:
				for (LootEntry barrowsLoot: BARROWS_CHEST)
				{
					for (ItemStack item : lootReceived.getItems())
					{
						int itemID = item.getId();
						if (barrowsLoot.getItemId() == itemID)
						{
							openCase(BARROWS_CHEST, itemID);
							//Stop both for loops since we only want to roll once
							break outerloop;
						}
					}
				}
			}
		}
		if (client.getVarbitValue(VarbitID.RAIDS_CLIENT_INDUNGEON) == 1)
		{
			System.out.println("In raid");
			System.out.println("loot received: " + lootReceived.getItems());
			System.out.println("loot received: " + lootReceived.getName());
			if (lootreceivedObjectNames.contains(lootReceived.getName())) {
				// For each item defined in CORRUPTED_GAUNTLET, check whether it is part of the loot
				// Checks from more rare to least rare. Ensures that the rarest item takes priority
				outerloop:
				for (LootEntry raidsLoot: CHAMBERS_OF_XERIC)
				{
					for (ItemStack item : lootReceived.getItems())
					{
						int itemID = item.getId();
						if (raidsLoot.getItemId() == itemID)
						{
							openCase(CHAMBERS_OF_XERIC, itemID);
							//Stop both for loops since we only want to roll once
							break outerloop;
						}
					}
				}
			}
		}
	}

	private boolean checkInBarrows() {
		GameState gameState = client.getGameState();
		if (gameState != GameState.LOGGED_IN && gameState != GameState.LOADING)
		{
			return false;
		}

		int[] currentMapRegions = client.getTopLevelWorldView().getMapRegions();

		// Verify that player is in Moons reward room
		for (int region : currentMapRegions)
		{
			if (!BARROWS_MAP_REGION.contains(region))
			{
				return false;
			}
		}

		return true;
	}

	private boolean checkInMoons() {
		GameState gameState = client.getGameState();
		if (gameState != GameState.LOGGED_IN && gameState != GameState.LOADING)
		{
			return false;
		}

		int[] currentMapRegions = client.getTopLevelWorldView().getMapRegions();

		// Verify that player is in Moons reward room
		for (int region : currentMapRegions)
		{
			if (!MOONS_MAP_REGION.contains(region))
			{
				return false;
			}
		}

		return true;
	}

	private boolean checkInCG() {
		GameState gameState = client.getGameState();
		if (gameState != GameState.LOGGED_IN && gameState != GameState.LOADING)
		{
			return false;
		}

		int[] currentMapRegions = client.getTopLevelWorldView().getMapRegions();

		// Verify that player is in CG reward room
		for (int region : currentMapRegions)
		{
			if (!CG_MAP_REGION.contains(region))
			{
				return false;
			}
		}

		return true;
	}

	public void openCase(List<LootEntry> lootTable, int wonItemId)
	{
		List<LootItem> pool = buildItemPool(lootTable);
		LootItem winner = resolveWinner(pool, wonItemId);

		caseOpeningOverlay.open(pool, winner, result ->
		{
			// Fires once, when the reel settles on `result`.
			// e.g. show a chat message, play a sound, etc.
		});
	}

	private LootItem resolveWinner(List<LootItem> pool, int wonItemId)
	{
		for (LootItem item : pool)
		{
			if (item.getItemId() == wonItemId)
			{
				return item;
			}
		}

		return new LootItem(
				wonItemId,
				itemManager.getItemComposition(wonItemId).getName(),
				itemManager.getImage(wonItemId),
				Rarity.COMMON
		);
	}


	private List<LootItem> buildItemPool(List<LootEntry> lootTable)
	{
		List<LootItem> pool = new ArrayList<>();
		for (LootEntry entry : lootTable)
		{
			pool.add(new LootItem(
					entry.getItemId(),
					itemManager.getItemComposition(entry.getItemId()).getName(),
					itemManager.getImage(entry.getItemId()),
					entry.getRarity()
			));
		}
		return pool;
	}


	@Provides
	LootCaseOpeningConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(LootCaseOpeningConfig.class);
	}
}