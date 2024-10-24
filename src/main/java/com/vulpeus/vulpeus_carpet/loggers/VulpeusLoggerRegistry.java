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

package com.vulpeus.vulpeus_carpet.loggers;

import carpet.logging.HUDLogger;
import carpet.logging.LoggerRegistry;
import com.vulpeus.vulpeus_carpet.loggers.hud.autosave;
import com.vulpeus.vulpeus_carpet.loggers.hud.entity_count;
import java.lang.reflect.Field;

public class VulpeusLoggerRegistry {

	public static boolean __autosave;
	public static boolean __entity_count;

	public static void registerLoggers(){
		LoggerRegistry.registerLogger(autosave.NAME, standardHUDLogger(autosave.NAME, null, null));
		LoggerRegistry.registerLogger(entity_count.NAME, standardHUDLogger(entity_count.NAME, null, null));
	}

	public static void updateHUD() {
		doHudLogging(__autosave, autosave.getInstance());
		doHudLogging(__entity_count, entity_count.getInstance());
	}

	public static HUDLogger standardHUDLogger(String logName, String def, String [] options) {
		return new HUDLogger(getLoggerField(logName), logName, def, options, false);
	}

	public static Field getLoggerField(String logName) {
		try {
			return VulpeusLoggerRegistry.class.getField("__" + logName);
		}
		catch (NoSuchFieldException e) {
			throw new RuntimeException();
		}
	}

	private static void doHudLogging(boolean condition, AbstractHUDLogger logger) {
		if (condition) {
			LoggerRegistry.getLogger(logger.getName()).log(logger::onHudUpdate);
		}
	}
}
