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
import com.mojang.brigadier.CommandDispatcher;
import com.vulpeus.vulpeus_carpet.VulpeusCarpetSettings;
import com.vulpeus.vulpeus_carpet.utils.rule.commandSit.SitEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;

//#if MC>=12000
import net.minecraft.server.world.ServerWorld;
//#else
//$$ import net.minecraft.world.World;
//#endif

public class sitCommand {

	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		dispatcher.register(CommandManager.literal("sit")
				.requires(player -> CommandHelper.canUseCommand(player, VulpeusCarpetSettings.commandSit))
				.executes(context -> sitPlayer(context.getSource().getPlayer())));
	}

	public static int sitPlayer(@Nullable ServerPlayerEntity player) {
		if (player == null || !player.isOnGround()) {
			return 0;
		}

		//#if MC>=12000
		ServerWorld world = player.getServerWorld();
		//#else
		//$$ World world = player.getWorld();
		//#endif

		ArmorStandEntity armorStandEntity = new ArmorStandEntity(world, player.getX(),
				player.getY() - 0.16, player.getZ());

		((SitEntity) armorStandEntity).setSitEntity(true);
		world.spawnEntity(armorStandEntity);
		player.setSneaking(false);
		player.startRiding(armorStandEntity);

		return 1;
	}
}