package com.vulpeus.VulpeusCarpet;

import static carpet.settings.RuleCategory.*;

import carpet.settings.Rule;

public class VulpeusCarpetSettings {

  private static final String VULPEUS = "vulpeus";

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

  @Rule(
      desc = "Set OP-permission when join to server.",
      category = {VULPEUS},
      options = {"0", "1", "2", "3", "4"})
  public static int defaultOpLevel = 0;

  @Rule(
      desc = "yeet ClassCastException crash.",
      category = {VULPEUS})
  public static boolean disableCCECrash = false;

  @Rule(
      desc = "yeet StackOverflowError crash.",
      category = {VULPEUS})
  public static boolean disableSOFCrash = false;

  @Rule(
      desc = "Optimize dragon respawn method. Ported from carpet AMS addition.",
      category = {OPTIMIZATION, VULPEUS})
  public static boolean optimizedDragonRespawn = false;
}
