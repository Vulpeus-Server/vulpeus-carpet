/*
 * This file is part of the VulpeusCarpet project, licensed under the
 * GNU Lesser General Public License v3.0
 *
 * Copyright (C) 2024  Fallen_Breath and contributors
 *
 * VulpeusCarpet is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * VulpeusCarpet is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with VulpeusCarpet.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.vulpeus.vulpeus_carpet;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import carpet.utils.Translations;
import com.mojang.brigadier.CommandDispatcher;
import com.vulpeus.vulpeus_carpet.commands.hatCommand;
import com.vulpeus.vulpeus_carpet.commands.sitCommand;
import com.vulpeus.vulpeus_carpet.commands.viewCommand;
import com.vulpeus.vulpeus_carpet.utils.rule.defaultOpLevel.PlayerUtil;
import java.util.Map;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.minecraft.GameVersion;
import net.minecraft.MinecraftVersion;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

public class VulpeusCarpetExtension implements CarpetExtension, ModInitializer {

	public static final String MOD_ID = "vulpeus_carpet";
	public static final String MOD_NAME;
	public static final Version MOD_VERSION;
	public static final GameVersion GAME_VERSION;

	static {
		ModMetadata metadata =
				FabricLoader.getInstance()
						.getModContainer(MOD_ID)
						.orElseThrow(RuntimeException::new)
						.getMetadata();
		MOD_NAME = metadata.getName();
		MOD_VERSION = metadata.getVersion();
		GAME_VERSION = MinecraftVersion.CURRENT;
	}

	public static void loadExtension() {
		CarpetServer.manageExtension(new VulpeusCarpetExtension());
	}

	@Override
	public String version() {
		return MOD_ID;
	}

	@Override
	public void onInitialize() {
		VulpeusCarpetExtension.loadExtension();
	}

	@Override
	public void onGameStarted() {
		CarpetServer.settingsManager.parseSettingsClass(VulpeusCarpetSettings.class);
	}

	@Override
	public void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess) {
		viewCommand.register(dispatcher);
		hatCommand.register(dispatcher);
		sitCommand.register(dispatcher);
	}

	@Override
	public void onPlayerLoggedIn(ServerPlayerEntity player) {
		if (VulpeusCarpetSettings.defaultOpLevel != 0) {
			PlayerUtil.setOpLevel(player, VulpeusCarpetSettings.defaultOpLevel);
		}
	}

	@Override
	public Map<String, String> canHasTranslations(String lang) {
		return Translations.getTranslationFromResourcePath(
				String.format("assets/%s/lang/%s.json", MOD_ID, lang));
	}
}