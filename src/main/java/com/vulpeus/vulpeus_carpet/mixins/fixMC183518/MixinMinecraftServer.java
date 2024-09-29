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

package com.vulpeus.vulpeus_carpet.mixins.fixMC183518;

import com.vulpeus.vulpeus_carpet.VulpeusCarpetSettings;
import java.util.concurrent.locks.LockSupport;
import java.util.function.BooleanSupplier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Util;
import net.minecraft.util.thread.ThreadExecutor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(MinecraftServer.class)
public abstract class MixinMinecraftServer {

	@Unique
	private boolean waitingForNextTickNew;
	@Shadow
	private long tickStartTimeNanos;
	@Shadow
	private long waitTime;

	@SuppressWarnings("all")
	@Overwrite
	public void runTasksTillTickEnd() {
		((ThreadExecutor) (Object) this).runTask();
		this.waitingForNextTickNew = true;
		this.runTasks(() -> !this.shouldKeepTicking());
		this.waitingForNextTickNew = false;
	}

	@SuppressWarnings("all")
	@Overwrite
	public void waitForTasks() {
		final boolean pushTickTimeLog = this.shouldPushTickTimeLog();
		final long beforeParkNanosMeasuringTimeNano = pushTickTimeLog ? Util.getMeasuringTimeNano() : 0L;

		final long nanos;
		if (VulpeusCarpetSettings.fixMC183518 && this.waitingForNextTickNew) {
			nanos = this.tickStartTimeNanos - Util.getMeasuringTimeNano();
		} else {
			nanos = 100000L;
		}

		LockSupport.parkNanos("waiting for tasks", nanos);

		if (pushTickTimeLog) {
			this.waitTime += Util.getMeasuringTimeNano() - beforeParkNanosMeasuringTimeNano;
		}
	}

	@Shadow
	public abstract void runTasks(final BooleanSupplier stopCondition);

	@Shadow
	protected abstract boolean shouldKeepTicking();

	@Shadow
	public abstract boolean shouldPushTickTimeLog();
}