/*
 * This file is part of the VulpeusCarpet project, licensed under the
 * GNU Lesser General Public License v3.0
 *
 * Copyright (C) 2024  VulpeusServer and contributors
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

package com.vulpeus.vulpeus_carpet.utils.rule.defaultOpLevel;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.OperatorEntry;
import net.minecraft.server.OperatorList;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerUtil {

  public static void setOpLevel(ServerPlayerEntity player, int opLevel) {
    setOpLevel(player, opLevel, false);
  }

  public static void setOpLevel(ServerPlayerEntity player, int opLevel, boolean bypassPlayerLimit) {
    MinecraftServer server = player.getServer();
    PlayerManager manager = server.getPlayerManager();
    OperatorList ops = manager.getOpList();
    ops.add(new OperatorEntry(player.getGameProfile(), opLevel, bypassPlayerLimit));
    manager.sendCommandTree(player);
  }
}
