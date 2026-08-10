package com.lootcaseopening;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("lootcaseopening")
public interface LootCaseOpeningConfig extends Config
{
	@ConfigItem(
		keyName = "testSpin",
		name = "Test Spin",
		description = "Test spin"
	)
	default boolean testSpin()
	{
		return false;
	}
}
