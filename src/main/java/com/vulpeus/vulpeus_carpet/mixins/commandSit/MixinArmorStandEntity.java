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

package com.vulpeus.vulpeus_carpet.mixins.commandSit;

import com.vulpeus.vulpeus_carpet.utils.rule.commandSit.SitEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorStandEntity.class)
public abstract class MixinArmorStandEntity extends LivingEntity implements SitEntity {

  private boolean sitEntity = false;

  protected MixinArmorStandEntity(EntityType<? extends LivingEntity> entityType, World world) {
    super(entityType, world);
  }

  @Shadow
  protected abstract void setMarker(boolean marker);

  @Override
  public boolean isSitEntity() {
    return sitEntity;
  }

  @Override
  public void setSitEntity(boolean isSitEntity) {
    this.sitEntity = isSitEntity;
    this.setMarker(isSitEntity);
    this.setInvisible(isSitEntity);
  }

  @Override
  protected void removePassenger(Entity passenger) {
    if (this.isSitEntity()) {
      this.setPosition(this.getX(), this.getY() + 0.16, this.getZ());
      this.kill();
    }
    super.removePassenger(passenger);
  }

  @Inject(method = "tick", at = @At(value = "RETURN"))
  private void onTick(CallbackInfo ci) {
    Entity player = this.getFirstPassenger();
    if (this.sitEntity && player != null) {
      this.setYaw(player.getHeadYaw());
    }
  }

  @Inject(method = "writeCustomDataToNbt", at = @At(value = "RETURN"))
  private void postWriteCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
    if (this.sitEntity) {
      nbt.putBoolean("SitEntity", true);
    }
  }

  @Inject(method = "readCustomDataFromNbt", at = @At(value = "RETURN"))
  private void postReadCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
    if (nbt.contains("SitEntity", NbtElement.BYTE_TYPE)) {
      this.sitEntity = nbt.getBoolean("SitEntity");
    }
  }
}