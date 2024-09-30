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

package com.vulpeus.vulpeus_carpet;

import static carpet.api.settings.RuleCategory.*;

import carpet.api.settings.Rule;

public class VulpeusCarpetSettings {

  private static final String VULPEUS = "vulpeus";

  @Rule(categories = {OPTIMIZATION, COMMAND, VULPEUS})
  public static String commandCustomLoad = "ops";

  @Rule(categories = {SURVIVAL, COMMAND, VULPEUS})
  public static String commandHat = "ops";

  @Rule(categories = {SURVIVAL, COMMAND, VULPEUS})
  public static String commandSit = "ops";

  @Rule(categories = {SURVIVAL, COMMAND, VULPEUS})
  public static String commandView = "ops";

  @Rule(categories = {VULPEUS}, options = {"0", "1", "2", "3", "4"})
  public static int defaultOpLevel = 0;

  @Rule(categories = {VULPEUS})
  public static boolean disableCCECrash = false;

  @Rule(categories = {VULPEUS})
  public static boolean disableSOECrash = false;

  @Rule(categories = {VULPEUS})
  public static boolean disableIAECrash = false;

  //#if MC<=12101
  //$$ @Rule(categories = {BUGFIX, VULPEUS})
  //#endif
  public static boolean fixedBeeNotLeavingHive = false;

  //#if MC<=12006
  //$$ @Rule(categories = {BUGFIX, VULPEUS})
  //#endif
  public static boolean fixedFallingBlockCantUseNetherPortal = false;

  //#if MC<=12001
  //$$ @Rule(categories = {BUGFIX, VULPEUS})
  //#endif
  public static boolean fixedTickMemoriesEntityAI = false;

  @Rule(categories = {OPTIMIZATION, VULPEUS})
  public static boolean optimizedDragonRespawn = false;

  @Rule(categories = {SURVIVAL, VULPEUS})
  public static boolean visibleSpectators = false;

}
