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

package com.vulpeus.vulpeus_carpet.loggers.hud;

import carpet.CarpetServer;
import carpet.utils.Messenger;
import com.vulpeus.vulpeus_carpet.loggers.AbstractHUDLogger;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class autosave extends AbstractHUDLogger {

	public static final String NAME = "autosave";

	private static final autosave INSTANCE = new autosave();

	private autosave() {
		super(NAME);
	}

	public static autosave getInstance() {
		return INSTANCE;
	}

	@Override
	public Text[] onHudUpdate(String option, PlayerEntity playerEntity) {

		int gameTick = CarpetServer.minecraft_server.getTicks();
		int previous = gameTick % 6000;

		if (gameTick != 0 && previous == 0) {
			previous = 6000;
		}
		int next = 6000 - previous;

		String color =  next <= 100 ? "§d" : next <= 500 ? "§c" : next <= 1000 ? "§e" : "§2";

		return new Text[] {
				Messenger.c(String.format("g Prev:%s %d §rNext:%s %d",color, previous, color, next))
		};
	}
}
