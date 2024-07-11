package com.vulpeus.VulpeusCarpet;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import com.mojang.brigadier.CommandDispatcher;
import com.vulpeus.VulpeusCarpet.commands.*;
import com.vulpeus.VulpeusCarpet.utils.rule.defaultOpLevel.PlayerUtil;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

public class VulpeusCarpetExtension implements CarpetExtension, ModInitializer {

  public static final String MOD_ID = "vulpeus-carpet";
  public static final String MOD_NAME = "VulpeusCarpet";
  public static final Version MOD_VERSION;

  static {
    ModMetadata metadata =
        FabricLoader.getInstance()
            .getModContainer(MOD_ID)
            .orElseThrow(RuntimeException::new)
            .getMetadata();
    MOD_VERSION = metadata.getVersion();
  }

  @Override
  public String version() {
    return MOD_ID;
  }

  public static void loadExtension() {
    CarpetServer.manageExtension(new VulpeusCarpetExtension());
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
  public void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
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
}
