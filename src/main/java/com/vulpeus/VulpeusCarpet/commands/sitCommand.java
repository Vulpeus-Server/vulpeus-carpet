package com.vulpeus.VulpeusCarpet.commands;

import carpet.settings.SettingsManager;
import com.mojang.brigadier.CommandDispatcher;
import com.vulpeus.VulpeusCarpet.VulpeusCarpetSettings;
import com.vulpeus.VulpeusCarpet.utils.rule.commandSit.SitEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

public class sitCommand {

  public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
    dispatcher.register(
        CommandManager.literal("sit")
            .requires(
                player -> SettingsManager.canUseCommand(player, VulpeusCarpetSettings.commandSit))
            .executes(context -> sitPlayer(context.getSource().getPlayer())));
  }

  public static int sitPlayer(ServerPlayerEntity player) {
    if (!player.isOnGround()) {
      return 0;
    }

    ServerWorld world = player.getServerWorld();

    ArmorStandEntity armorStandEntity =
        new ArmorStandEntity(world, player.getX(), player.getY() - 0.16, player.getZ());

    ((SitEntity) armorStandEntity).setSitEntity(true);
    world.spawnEntity(armorStandEntity);
    player.setSneaking(false);
    player.startRiding(armorStandEntity);

    return 1;
  }
}