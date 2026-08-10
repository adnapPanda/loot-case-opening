package com.lootcaseopening;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class LootCaseOpeningPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(LootCaseOpeningPlugin.class);
		RuneLite.main(args);
	}
}