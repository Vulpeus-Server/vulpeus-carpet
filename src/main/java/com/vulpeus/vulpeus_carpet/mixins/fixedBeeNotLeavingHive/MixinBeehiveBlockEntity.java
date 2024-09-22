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

package com.vulpeus.vulpeus_carpet.mixins.fixedBeeNotLeavingHive;

import com.vulpeus.vulpeus_carpet.VulpeusCarpetSettings;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Restriction(require = @Condition(value = "minecraft", versionPredicates = "<=1.21.1"))
@Mixin(BeehiveBlockEntity.class)
public class MixinBeehiveBlockEntity {
	@Redirect(
			method = "releaseBee",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/World;isRaining()Z"
			)
	)
	private static boolean releaseBee(World world) {
		if (VulpeusCarpetSettings.fixedBeeNotLeavingHive) {
			return !world.getDimension().hasFixedTime() && world.getDimension().hasSkyLight() && world.isRaining();
		} else {
			return world.isRaining();
		}
	}
}
