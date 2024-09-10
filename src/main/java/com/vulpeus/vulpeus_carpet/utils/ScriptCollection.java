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
