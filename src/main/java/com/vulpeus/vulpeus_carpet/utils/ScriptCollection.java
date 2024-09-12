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

package com.vulpeus.vulpeus_carpet.utils;

import static com.vulpeus.vulpeus_carpet.VulpeusCarpetExtension.*;

import carpet.script.CarpetScriptServer;
import carpet.script.Module;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;

public class ScriptCollection {

	public static void load() {
		URL scripts = CLASS_LOADER.getResource(String.format("%s/scripts/", ASSETS_PATH));
		File scriptDir = new File(scripts.getPath());
		File[] scriptFiles = scriptDir.listFiles();
		for (File scriptFile : scriptFiles) {
			Module module = fromScriptFilename(scriptFile.getName());
			CarpetScriptServer.registerBuiltInApp(module);
		}
	}

	private static Module fromScriptFilename(String filename) {
		int index = filename.lastIndexOf(".");

		String ext = filename.substring(index);
		String scriptName = filename.substring(0, index);

		if (ext.equals(".sc") || ext.equals(".scl")) {
			return nativeScript(
					filename,
					scriptName,
					ext.equals(".scl")
			);
		}
		return null;
	}
	public static Module nativeScript(String filename, String scriptName, boolean isLibrary) {
		try {
			String code = IOUtils.toString(
					CLASS_LOADER.getResourceAsStream(String.format("%s/scripts/%s", ASSETS_PATH, filename)),
					StandardCharsets.UTF_8
			);
			return new Module(scriptName, code, isLibrary);
		} catch (IOException e) {
			throw new IllegalArgumentException("Failed to load bundled module", e);
		}
	}
}
