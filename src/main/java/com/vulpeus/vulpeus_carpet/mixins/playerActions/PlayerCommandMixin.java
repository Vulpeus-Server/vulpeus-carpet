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

package com.vulpeus.vulpeus_carpet.mixins.playerActions;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

import carpet.CarpetSettings;
import carpet.commands.PlayerCommand;
import carpet.helpers.EntityPlayerActionPack;
import carpet.utils.CommandHelper;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.vulpeus.vulpeus_carpet.utils.rule.playerActions.Container;
import java.util.function.Consumer;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerCommand.class)
public abstract class PlayerCommandMixin {

  @Shadow(remap = false)
  private static int manipulate(CommandContext<ServerCommandSource> context,
      Consumer<EntityPlayerActionPack> action) {
    return 0;
  }

  @Unique
  private static int action(CommandContext<ServerCommandSource> context,
      EntityPlayerActionPack.ActionType type, EntityPlayerActionPack.Action action) {
    return manipulate(context, ap -> ap.start(type, action));
  }

  @Inject(method = "register", at = @At("RETURN"), remap = false)
  private static void registerEnhancements(CommandDispatcher<ServerCommandSource> dispatcher,
      CommandRegistryAccess commandBuildContext, CallbackInfo ci) {

    LiteralArgumentBuilder<ServerCommandSource> command = literal("player").requires(
        (player) -> CommandHelper.canUseCommand(player, CarpetSettings.commandPlayer)).then(
        argument("player", StringArgumentType.word()).then(
            literal("fill").then(argument("fill", BoolArgumentType.bool()).executes((ctx) -> {
              boolean value = BoolArgumentType.getBool(ctx, "fill");
              ServerPlayerEntity player = getPlayer(ctx);
              if (value) {
                Container.fill.addPlayer(player);
              } else {
                Container.fill.removePlayer(player);
              }
              return 1;
            }))).then(
            literal("clean").then(argument("clean", BoolArgumentType.bool()).executes((ctx) -> {
              boolean value = BoolArgumentType.getBool(ctx, "clean");
              ServerPlayerEntity player = getPlayer(ctx);
              if (value) {
                Container.clean.addPlayer(player);
              } else {
                Container.clean.removePlayer(player);
              }
              return 1;
            }))));

    dispatcher.register(command);
  }

  @Inject(method = "makeActionCommand", at = @At("RETURN"), remap = false)
  private static void makeActionCommandEnhancements(String actionName,
      EntityPlayerActionPack.ActionType type,
      CallbackInfoReturnable<LiteralArgumentBuilder<ServerCommandSource>> cir) {
  }

  @Unique
  private static ServerPlayerEntity getPlayer(CommandContext<ServerCommandSource> ctx) {
    String playerName = StringArgumentType.getString(ctx, "player");
    MinecraftServer server = ctx.getSource().getServer();
    return server.getPlayerManager().getPlayer(playerName);
  }
}
