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

package com.vulpeus.vulpeus_carpet.commands;

import carpet.utils.CommandHelper;
import com.mojang.brigadier.CommandDispatcher;
import com.vulpeus.vulpeus_carpet.VulpeusCarpetSettings;
import com.vulpeus.vulpeus_carpet.utils.sendMessage;
import net.minecraft.block.Block;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;

//#if MC>=12005
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.server.world.ServerWorld;
//#else
//$$ import net.minecraft.world.World;
//$$ import net.minecraft.enchantment.EnchantmentHelper;
//$$ import net.minecraft.nbt.NbtCompound;
//#endif

public class hatCommand {

	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		dispatcher.register(CommandManager.literal("hat")
				.requires(player -> CommandHelper.canUseCommand(player, VulpeusCarpetSettings.commandHat))
				.executes(context -> hatPlayer(context.getSource().getPlayer())));
	}

	public static int hatPlayer(@Nullable ServerPlayerEntity player) {
		if (player == null || player.isSpectator()) {
			return 0;
		}
		ItemStack hat = player.getEquippedStack(EquipmentSlot.HEAD);
		ItemStack stack = player.getMainHandStack();

		Item item = stack.getItem();

		//#if MC>=12005
		if (hat.getEnchantments().getEnchantments().contains(Enchantments.BINDING_CURSE))
		//#else
		//$$ if (EnchantmentHelper.fromNbt(hat.getEnchantments()).containsKey(Enchantments.BINDING_CURSE))
		//#endif
		{
			sendMessage.sendSourceMessage(player, "Already equipped with an Item with BINDING_CURSE");
			return 0;
		}
		if (item.equals(Items.TOTEM_OF_UNDYING)) {
			sendMessage.sendSourceMessage(player, "Items that cannot be equipped: TOTEM_OF_UNDYING");
			return 0;
		}
		if (item instanceof BlockItem blockItem) {
			Block block = blockItem.getBlock();
			if (block instanceof ShulkerBoxBlock) {
				//#if MC>=12005
				ContainerComponent blockEntityData = stack.get(DataComponentTypes.CONTAINER);
				//#else
				//$$ NbtCompound blockEntityData = stack.getNbt();
				//#endif

				if (blockEntityData != null) {
					//#if MC>=12005
					boolean isEmpty = blockEntityData.stream().toList().isEmpty();
					//#else
					//$$ boolean isEmpty = blockEntityData.getCompound("BlockEntityTag").contains("Items");
					//#endif

					if (isEmpty) {
						sendMessage.sendSourceMessage(player,
								"Items that cannot be equipped: SHULKER_BOX(notEmpty)");
						return 0;
					}
				}
			}
		}

		ItemStack stackCopy = stack.copy();
		stackCopy.setCount(1);
		player.equipStack(EquipmentSlot.HEAD, stackCopy);
		if (!player.isCreative()) {
			stack.decrement(1);
			if (player.getInventory().getEmptySlot() < 0) {
				//#if MC>=12005
				ServerWorld world = player.getServerWorld();
				//#else
				//$$ World world = player.getWorld();
				//#endif
				ItemEntity itemEntity = new ItemEntity(world, player.getX(), player.getY() + 1.0,
						player.getZ(), hat.copy());
				itemEntity.setToDefaultPickupDelay();
				world.spawnEntity(itemEntity);
			} else {
				player.getInventory().insertStack(hat.copy());
			}
		}
		player.playerScreenHandler.sendContentUpdates();
		return 1;
	}
}
