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

package com.vulpeus.vulpeus_carpet.utils.rule.playerActions;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ShulkerBoxScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.server.network.ServerPlayerEntity;

public class Container {

  public static final int THROW_CTRL_Q = 1;

  public static class fill {

    private static final List<ServerPlayerEntity> players = new ArrayList<>();

    public static void fillAll(ServerPlayerEntity player, ShulkerBoxScreenHandler screenHandler) {
      for (int index = 63 - 36; index < 63; index++) { // 63-36=27
        // for (int index = 63 - 36; index < screenHandler.slots.size(); index++) {
        Slot slot = screenHandler.getSlot(index);
        // Slot slot = screenHandler.slots.get(index);
        if (slot.hasStack()) {
          ItemStack itemStack = slot.getStack();
          if (itemStack.getItem().canBeNested()) {
            screenHandler.quickMove(player, index);
            if (slot.hasStack()) {
              player.closeHandledScreen();
            }
          } else {
            screenHandler.onSlotClick(index, THROW_CTRL_Q, SlotActionType.THROW, player);
          }
        }
      }
    }

    public static void addPlayer(ServerPlayerEntity player) {
      players.add(player);
    }

    public static void removePlayer(ServerPlayerEntity player) {
      players.remove(player);
    }

    public static boolean containsPlayer(ServerPlayerEntity player) {
      return players.contains(player);
    }
  }

  public static class clean {

    private static final List<ServerPlayerEntity> players = new ArrayList<>();

    public static void cleanAll(ServerPlayerEntity player, ShulkerBoxScreenHandler screenHandler) {
      for (int index = 0; index < 27; index++) {
        // for (int index = 63 - 36; index < screenHandler.slots.size(); index++) {
        Slot slot = screenHandler.getSlot(index);
        // Slot slot = screenHandler.slots.get(index);
        if (slot.hasStack()) {
          screenHandler.onSlotClick(index, THROW_CTRL_Q, SlotActionType.THROW, player);
        }
      }
      screenHandler.onSlotClick(0, THROW_CTRL_Q, SlotActionType.THROW, player);
    }

    public static void addPlayer(ServerPlayerEntity player) {
      players.add(player);
    }

    public static void removePlayer(ServerPlayerEntity player) {
      players.remove(player);
    }

    public static boolean containsPlayer(ServerPlayerEntity player) {
      return players.contains(player);
    }
  }
}
