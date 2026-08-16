package com.lootcaseopening;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

import java.awt.*;

@ConfigGroup("lootcaseopening")
public interface LootCaseOpeningConfig extends Config
{
	@ConfigSection(
			name = "Rarity Colors",
			description = "Colors corresponding to each rarity tier",
			position = 0
	)
	String rarityColors = "rarityColors";

	@ConfigItem(
		keyName = "commonColor",
		name = "Common",
		description = "Color for Common rarity tier",
		section = rarityColors,
		position = 1
	)
	default Color commonColor()
	{
		return Rarity.COMMON.getColor();
	}

	@ConfigItem(
			keyName = "uncommonColor",
			name = "Uncommon",
			description = "Color for Uncommon rarity tier",
			section = rarityColors,
			position = 2
	)
	default Color uncommonColor()
	{
		return Rarity.UNCOMMON.getColor();
	}

	@ConfigItem(
			keyName = "rareColor",
			name = "Rare",
			description = "Color for Rare rarity tier",
			section = rarityColors,
			position = 3
	)
	default Color rareColor()
	{
		return Rarity.RARE.getColor();
	}

	@ConfigItem(
			keyName = "ultraRareColor",
			name = "Ultra Rare",
			description = "Color for Ultra Rare rarity tier",
			section = rarityColors,
			position = 4
	)
	default Color ultraRareColor()
	{
		return Rarity.ULTRA_RARE.getColor();
	}

	@ConfigItem(
			keyName = "legendaryColor",
			name = "Legendary",
			description = "Color for Legendary rarity tier",
			section = rarityColors,
			position = 5
	)
	default Color legendaryColor()
	{
		return Rarity.LEGENDARY.getColor();
	}

	@ConfigItem(
			keyName = "playLegendaryJingle",
			name = "Jingle plays for legendary reward",
			description = "Decides whether a jingle is played when the spin lands on a legendary item",
			position = 6
	)
	default boolean playLegendaryJingle()
	{
		return true;
	}

	@ConfigItem(
			keyName = "showWheelSpinInWilderness",
			name = "Enable wheel spin in wilderness",
			description = "Show the wheel spin animation for Larran's and Zombie Pirate Chests",
			position = 7
	)
	default boolean showWheelSpinInWilderness()
	{
		return false;
	}

	@ConfigItem(
			keyName = "showWheelSpinForKeyChests",
			name = "Enable wheel spin for spammable chests",
			description = "Enables the wheel spin animation for chests that can be spam opened. (Ex: Elven crystal chest, Moon chest)",
			position = 8
	)
	default boolean showWheelSpinForKeyChests()
	{
		return true;
	}
}
