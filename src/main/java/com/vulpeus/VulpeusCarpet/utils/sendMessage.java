package com.vulpeus.VulpeusCarpet.utils;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class sendMessage {

  public static void sendGlobalMessage(MinecraftServer server, String message) {
    server.getPlayerManager().getPlayerList().forEach(player -> sendSourceMessage(player, message));
  }

  public static void sendSourceMessage(ServerPlayerEntity player, String message) {
    player.sendMessage(Text.of(message), false);
  }
}
