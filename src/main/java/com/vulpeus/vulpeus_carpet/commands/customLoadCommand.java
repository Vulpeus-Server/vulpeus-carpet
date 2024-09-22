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

import carpet.utils.CommandHelper;
import carpet.utils.Messenger;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.vulpeus.vulpeus_carpet.VulpeusCarpetSettings;
import com.vulpeus.vulpeus_carpet.utils.rule.commandCustomLoad.CustomLoadingChunks;
import com.vulpeus.vulpeus_carpet.utils.rule.commandCustomLoad.CustomLoadingChunks.VulpeusTicket;
import net.minecraft.command.argument.DimensionArgumentType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.DimensionTypes;

public class customLoadCommand {

	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		dispatcher.register(
				CommandManager.literal("custom-load")
						.requires(player -> CommandHelper.canUseCommand(player, VulpeusCarpetSettings.commandCustomLoad))
						.then(
								CommandManager.literal("add")
										.then(
												CommandManager.argument("dim", DimensionArgumentType.dimension())
														.then(
																CommandManager.argument("cx", IntegerArgumentType.integer())
																		.then(
																				CommandManager.argument("cz", IntegerArgumentType.integer())
																						.then(
																								CommandManager.argument("size", IntegerArgumentType.integer(0, 33))
																										.executes(
																												customLoadCommand::add
																										)
																						)
																		)
														)
										)
						)
						.then(
								CommandManager.literal("remove")
										.then(
												CommandManager.argument("index", IntegerArgumentType.integer(1))
														.executes(
																customLoadCommand::remove
														)
										)
						)
						.executes(
								customLoadCommand::status
						)
		);
	}

	private static int add(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		ServerWorld world = DimensionArgumentType.getDimensionArgument(context, "dim").toServerWorld();
		int cx = IntegerArgumentType.getInteger(context, "cx");
		int cz = IntegerArgumentType.getInteger(context, "cz");
		int size = IntegerArgumentType.getInteger(context, "size");

		ChunkPos chunk = new ChunkPos(cx, cz);

		int index = CustomLoadingChunks.add(world, chunk, size);

		ServerCommandSource source = context.getSource();

		source.sendMessage(Messenger.c("lb Success to add custom-loading chunk ticket:"));
		source.sendMessage(Messenger.c("w Index: " + index+1));
		source.sendMessage(Messenger.c(
				"w Pos: ",
				"g ["+cx+","+cz+"]"
		));
		source.sendMessage(Messenger.c(
				"w Radius: " + size
		));
		source.sendMessage(Messenger.c());
		status(context);

		return 0;
	}

	private static int remove(CommandContext<ServerCommandSource> context) {
		int index = IntegerArgumentType.getInteger(context, "index") -1;

		VulpeusTicket ticket = CustomLoadingChunks.remove(index);

		ServerCommandSource source = context.getSource();
		source.sendMessage(Messenger.c("lb Success to remove custom-loading chunk ticket:"));
		source.sendMessage(Messenger.c("w Index: " + index+1));
		source.sendMessage(Messenger.c(
				"w Pos: ",
				"g ["+ticket.chunkPos.x+","+ticket.chunkPos.z+"]"
		));
		source.sendMessage(Messenger.c(
				"w Radius: " + ticket.size
		));
		source.sendMessage(Messenger.c());
		status(context);

		return 0;
	}

	private static int status(CommandContext<ServerCommandSource> context) {

		int index = 0;

		context.getSource().sendMessage(
				Messenger.c(
						"nb "+CustomLoadingChunks.Tickets.size()+" ",
						"w custom-load Tickets."
				)
		);
		for (VulpeusTicket ticket : CustomLoadingChunks.Tickets) {
			index++;
			/*
			 * Dimension
			 */
			String coloredDim;
			RegistryEntry<DimensionType> dim = ticket.serverWorld.getDimensionEntry();
			if (dim.matchesKey(DimensionTypes.OVERWORLD)) {
				coloredDim = "#8fce00 Overworld";
			} else if (dim.matchesKey(DimensionTypes.THE_NETHER)) {
				coloredDim = "#f44336 Nether";
			} else if (dim.matchesKey(DimensionTypes.THE_END)) {
				coloredDim = "#6a329f End";
			} else {
				coloredDim = dim.getKey().get().getValue().toString();
			}

			/*
			 * Chunk Pos
			 */
			String chunkPos = ticket.chunkPos.x + "," + ticket.chunkPos.z;

			/*
			 * Size
			 */
			String size = ticket.size + "";

			context.getSource().sendMessage(
					Messenger.c(
							"w|-  ",
							"w "+index+": ",
							"w "+chunkPos+" ",
							"w "+size+" ",
							coloredDim
					)
			);
		}

		return 0;
	}
}
