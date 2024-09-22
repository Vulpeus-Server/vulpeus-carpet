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

package com.vulpeus.vulpeus_carpet.mixins.fixedTickMemoriesEntityAI;

import com.vulpeus.vulpeus_carpet.VulpeusCarpetSettings;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.entity.ai.brain.Memory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Restriction(require = @Condition(value = "minecraft", versionPredicates = "<=1.20.1"))
@Mixin(Memory.class)
public abstract class MixinMemory {

	@Unique
	private boolean tickFlag = false;

	@Shadow
	public abstract void tick();

	@Inject(method = "isExpired", at = @At(value = "HEAD", target = "Lnet/minecraft/entity/ai/brain/Brain;tickMemories()V"))
	public void isExpired(CallbackInfoReturnable<Boolean> cir) {
		if (VulpeusCarpetSettings.fixedTickMemoriesEntityAI) {
			this.tickFlag = true;
			tick();
		}
	}

	@Inject(method = "tick", at = @At(value = "HEAD", target = "Lnet/minecraft/entity/ai/brain/Brain;tickMemories()V"), cancellable = true)
	public void tick(CallbackInfo ci) {
		if (VulpeusCarpetSettings.fixedTickMemoriesEntityAI && this.tickFlag) {
			this.tickFlag = false;
			ci.cancel();
		}
	}

}
