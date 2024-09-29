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

package com.vulpeus.vulpeus_carpet.mixins.visibleSpectators;

import com.mojang.authlib.GameProfile;
import com.vulpeus.vulpeus_carpet.VulpeusCarpetSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public abstract class MixinServerPlayerEntity extends PlayerEntity {

	public MixinServerPlayerEntity(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
		super(world, pos, yaw, gameProfile);
	}

	@Shadow
	public abstract Entity getCameraEntity();
	@Shadow
	public abstract boolean isSpectator();

	@Inject(method = "updatePotionVisibility", at = @At("HEAD"), cancellable = true)
	private void noInvisibleSpectators(CallbackInfo ci) {
		if (VulpeusCarpetSettings.visibleSpectators) {
			if (isSpectator()) clearPotionSwirls();
			else super.updatePotionVisibility();
			ci.cancel();
		}
	}

	@Inject(method = "canBeSpectated", at = @At("HEAD"), cancellable = true)
	private void allowSpectatorsToBeSpectated(ServerPlayerEntity spectator, CallbackInfoReturnable<Boolean> cir) {
		if (VulpeusCarpetSettings.visibleSpectators) {
			if (spectator.isSpectator()) cir.setReturnValue(getCameraEntity() == this);
			else cir.setReturnValue(super.canBeSpectated(spectator));
		}
	}
}
