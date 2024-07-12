package com.vulpeus.VulpeusCarpet;

import static carpet.settings.RuleCategory.*;

import carpet.settings.Rule;

public class VulpeusCarpetSettings {

  private static final String VULPEUS = "vulpeus";

  @Rule(
      desc = "Enable `/view` command to change viewDistance of carpet.",
      category = {SURVIVAL, COMMAND, VULPEUS})
  public static String commandView = "ops";

  @Rule(
      desc = "Enable `/hat` command. Ported from essential addons.",
      category = {SURVIVAL, COMMAND, VULPEUS})
  public static String commandHat = "ops";

  @Rule(
      desc = "Enable `/sit` command. Ported from PCA.",
      category = {SURVIVAL, COMMAND, VULPEUS})
  public static String commandSit = "ops";

  @Rule(
      desc = "Set the OP level granted when you join the server. can be re-granted by rejoining and can override `op-permission-level` in the server.properties.",
      category = {VULPEUS},
      options = {"0", "1", "2", "3", "4"})
  public static int defaultOpLevel = 0;

  @Rule(
      desc = "yeet the server crash caused by ClassCastException.",
      category = {VULPEUS})
  public static boolean disableCCECrash = false;

  @Rule(
      desc = "yeet the server crash caused by StackOverflowError.",
      category = {VULPEUS})
  public static boolean disableSOFCrash = false;

  @Rule(
      desc = "Optimize dragon respawn method. Ported from carpet AMS addition.",
      category = {OPTIMIZATION, VULPEUS})
  public static boolean optimizedDragonRespawn = false;
}
