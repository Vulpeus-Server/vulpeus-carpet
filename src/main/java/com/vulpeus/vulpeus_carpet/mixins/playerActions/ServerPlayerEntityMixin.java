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

package com.vulpeus.vulpeus_carpet.mixins.playerActions;

import com.vulpeus.vulpeus_carpet.utils.rule.playerActions.Container;
import java.util.OptionalInt;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ShulkerBoxScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {

  @Inject(method = "openHandledScreen", at = @At("RETURN"))
  public void openHandledScreen(NamedScreenHandlerFactory factory,
      CallbackInfoReturnable<OptionalInt> cir) {
    ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
    if (player.currentScreenHandler instanceof ShulkerBoxScreenHandler screenHandler) {
      if (Container.fill.containsPlayer(player)) {
        Container.fill.fillAll(player, screenHandler);
      } else if (Container.clean.containsPlayer(player)) {
        Container.clean.cleanAll(player, screenHandler);
      }
    }
  }
}
