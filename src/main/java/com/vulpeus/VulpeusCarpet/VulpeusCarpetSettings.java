package com.vulpeus.VulpeusCarpet;

import static carpet.settings.RuleCategory.*;

import carpet.settings.Rule;

public class VulpeusCarpetSettings {

  private static final String VULPEUS = "vulpeus";
  private static final String LOGGER = "logger";
  private static final String PROTOCOL = "protocol";

  @Rule(
      desc = "Enable /view command to changes the view distance of the server without op.",
      category = {SURVIVAL, COMMAND, VULPEUS})
  public static String commandView = "ops";

  @Rule(
      desc = "Allows you to equip items to your head slot using /hat. Ported from EssentialAddons.",
      category = {SURVIVAL, COMMAND, VULPEUS})
  public static String commandHat = "ops";

  @Rule(
      desc = "Player can sit down when using /sit. Ported from PCA.",
      category = {SURVIVAL, COMMAND, VULPEUS})
  public static String commandSit = "ops";
}
