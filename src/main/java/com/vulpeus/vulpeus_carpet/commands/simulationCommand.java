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

import carpet.CarpetSettings;
import carpet.utils.CommandHelper;
import carpet.utils.Messenger;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.vulpeus.vulpeus_carpet.VulpeusCarpetSettings;
import com.vulpeus.vulpeus_carpet.utils.sendMessage;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class simulationCommand {

	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		dispatcher.register(
				CommandManager.literal("simulation")
						.requires(player -> CommandHelper.canUseCommand(player, VulpeusCarpetSettings.commandSimulation))
						.executes(simulationCommand::execute_status)
						.then(
								CommandManager.argument("distance", IntegerArgumentType.integer(0, 32))
										.executes(simulationCommand::execute_change)
						)
		);
	}

	private static int execute_status(CommandContext<ServerCommandSource> context) {
		context.getSource().sendMessage(Messenger.c(
				"w simulationDistance is now " + CarpetSettings.simulationDistance
		));
		return 1;
	}

	private static int execute_change(CommandContext<ServerCommandSource> context) {
		ServerCommandSource source = context.getSource().withLevel(3);
		int distance = IntegerArgumentType.getInteger(context, "distance");

		MinecraftServer server = source.getServer();

		server.getPlayerManager().setSimulationDistance(distance);
		CarpetSettings.simulationDistance = distance;

		sendMessage.sendGlobalMessage(server, "simulationDistance is now " + distance);

		return 1;
	}
}
