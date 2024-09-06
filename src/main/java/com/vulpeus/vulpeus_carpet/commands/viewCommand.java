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

package com.vulpeus.vulpeus_carpet.commands;

import static com.mojang.brigadier.arguments.IntegerArgumentType.getInteger;
import static com.mojang.brigadier.arguments.IntegerArgumentType.integer;

import carpet.CarpetSettings;
import carpet.utils.CommandHelper;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.vulpeus.vulpeus_carpet.VulpeusCarpetSettings;
import com.vulpeus.vulpeus_carpet.utils.sendMessage;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class viewCommand {

  public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
    dispatcher.register(CommandManager.literal("view")
        .requires(player -> CommandHelper.canUseCommand(player, VulpeusCarpetSettings.commandView))
        .then(CommandManager.argument("distance", integer(0, 32)).executes(
            context -> executeChangeView(context.getSource().withLevel(3),
                getInteger(context, "distance")))));
  }

  public static int executeChangeView(ServerCommandSource source, int distance)
      throws CommandSyntaxException {

    MinecraftServer server = source.getServer();

    server.getPlayerManager().setViewDistance(distance);
    CarpetSettings.viewDistance = distance;

    sendMessage.sendGlobalMessage(server, "viewDistance is now " + distance);

    return 1;
  }
}
