package com.lootcaseopening;

import net.runelite.client.config.*;

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

	@ConfigSection(
			name = "Sound Effect Settings",
			description = "Settings for sound effects",
			position = 6
	)
	String soundEffect = "Sound Effect Settings";
	@ConfigItem(
			keyName = "playLegendaryJingle",
			name = "Jingle plays for legendary reward",
			description = "Decides whether a jingle is played when the spin lands on a legendary item",
			section = soundEffect,
			position = 7
	)
	default boolean playLegendaryJingle()
	{
		return true;
	}

	@Range(
			max = 200
	)
	@ConfigItem(
			keyName = "legendaryJingleVolume",
			name = "Jingle Volume",
			description = "Adjust how loud the legendary jingle is played",
			section = soundEffect,
			position = 8
	)
	default int legendaryJingleVolume()
	{
		return 32;
	}

	@ConfigItem(
			keyName = "showWheelSpinInWilderness",
			name = "Enable wheel spin in wilderness",
			description = "Show the wheel spin animation for Larran's and Zombie Pirate Chests",
			position = 9
	)
	default boolean showWheelSpinInWilderness()
	{
		return false;
	}

	@ConfigItem(
			keyName = "showWheelSpinForKeyChests",
			name = "Enable wheel spin for spammable chests",
			description = "Enables the wheel spin animation for chests that can be spam opened. (Ex: Elven crystal chest, Moon chest)",
			position = 10
	)
	default boolean showWheelSpinForKeyChests()
	{
		return true;
	}
}
