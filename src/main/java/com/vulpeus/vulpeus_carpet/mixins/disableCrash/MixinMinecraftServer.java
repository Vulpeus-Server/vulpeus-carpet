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

package com.vulpeus.vulpeus_carpet.mixins.disableCrash;

import com.vulpeus.vulpeus_carpet.VulpeusCarpetSettings;
import com.vulpeus.vulpeus_carpet.utils.sendMessage;
import java.util.Iterator;
import java.util.function.BooleanSupplier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(MinecraftServer.class)
public class MixinMinecraftServer {

  @Inject(method = {"tickWorlds"}, at = {
      @At(value = "INVOKE", target = "Lnet/minecraft/util/crash/CrashReport;create(Ljava/lang/Throwable;Ljava/lang/String;)Lnet/minecraft/util/crash/CrashReport;")}, locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
  public void injectTickWorlds(BooleanSupplier shouldKeepTicking, CallbackInfo ci,
      @SuppressWarnings("all") Iterator var2, ServerWorld serverWorld, Throwable throwable) {

    Throwable causeErr = throwable.getCause();

    if (VulpeusCarpetSettings.disableCCECrash && causeErr instanceof ClassCastException) {
      ci.cancel();
      sendMessage.sendGlobalMessage((MinecraftServer) (Object) this, "now CCE Crashed.");
      return;
    }
    if (VulpeusCarpetSettings.disableSOECrash && causeErr instanceof StackOverflowError) {
      ci.cancel();
      sendMessage.sendGlobalMessage((MinecraftServer) (Object) this, "now SOE Crashed.");
    }
  }
}