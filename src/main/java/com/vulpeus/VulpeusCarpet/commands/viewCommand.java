package com.vulpeus.VulpeusCarpet.commands;

import static com.mojang.brigadier.arguments.IntegerArgumentType.getInteger;
import static com.mojang.brigadier.arguments.IntegerArgumentType.integer;

import carpet.CarpetSettings;
import carpet.settings.SettingsManager;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.vulpeus.VulpeusCarpet.VulpeusCarpetSettings;
import com.vulpeus.VulpeusCarpet.utils.sendMassage;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class viewCommand {

  public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
    dispatcher.register(
        CommandManager.literal("view")
            .requires(
                player -> SettingsManager.canUseCommand(player, VulpeusCarpetSettings.commandView))
            .then(
                CommandManager.argument("distance", integer(0, 32))
                    .executes(
                        context ->
                            executeChangeView(
                                context.getSource().withLevel(3),
                                getInteger(context, "distance")))));
  }

  public static int executeChangeView(ServerCommandSource source, int distance)
      throws CommandSyntaxException {

    MinecraftServer server = source.getServer();

    server.getPlayerManager().setViewDistance(distance);
    CarpetSettings.viewDistance = distance;

    sendMassage.sendGlobalMessage(source.getPlayer(), "viewDistance is now " + distance);

    return 1;
  }
}
