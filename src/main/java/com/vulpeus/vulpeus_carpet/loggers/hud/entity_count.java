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

package com.vulpeus.vulpeus_carpet.loggers.hud;

import carpet.CarpetServer;
import carpet.utils.Messenger;
import com.google.common.collect.ImmutableList;
import com.vulpeus.vulpeus_carpet.loggers.AbstractHUDLogger;
import com.vulpeus.vulpeus_carpet.mixins.logEntityCount.MixinServerWorld;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.function.LazyIterationConsumer;
import net.minecraft.world.World;

public class entity_count extends AbstractHUDLogger {

	public static final String NAME = "entity_count";

	private static final entity_count INSTANCE = new entity_count();

	private entity_count() {
		super(NAME);
	}

	public static entity_count getInstance() {
		return INSTANCE;
	}

	@Override
	public Text[] onHudUpdate(String option, PlayerEntity playerEntity) {
		List<String> entities;
		if (option!=null) {
			entities = Arrays.asList(option.split("\s*,\s*"));
		} else {
			entities = new ArrayList<>();
		}
		Set<EntityType<?>> entitySet = parseEntityTypeFromString(entities);

		List<String> res = new ArrayList<>();

		int total = 0;
		for(RegistryKey<World> world : ImmutableList.of(World.OVERWORLD, World.NETHER, World.END)) {
			AtomicInteger v = new AtomicInteger();

			ServerWorld serverWorld = CarpetServer.minecraft_server.getWorld(world);
			((MixinServerWorld) serverWorld).getEntityManager().getLookup().forEach(
					TypeFilter.instanceOf(Entity.class),
					entity -> {
						if (entitySet.isEmpty() || entitySet.contains(entity.getType())) {
							v.getAndIncrement();
						}
						return LazyIterationConsumer.NextIteration.CONTINUE;
					}
			);

			if (world == World.OVERWORLD) {
				res.add("#8fce00 OW:"+v.get()+" ");
			} else if (world == World.NETHER) {
				res.add("#f44336 NE:"+v.get()+" ");
			} else if (world == World.END) {
				res.add("#6a329f END:"+v.get()+" ");
			}
			total += v.get();
		}
		res.add("w T:"+total+" ");

		return new Text[] {
				Messenger.c(res.toArray())
		};
	}

	private Set<EntityType<?>> parseEntityTypeFromString(List<String> names) {
		Set<EntityType<?>> result = new HashSet<>();
		for (String name : names)
		{
			Identifier rl = null;
			try
			{
				rl = Identifier.tryParse(name);
			}
			catch (Exception ignore) {}
			EntityType<?> item = rl != null ? Registries.ENTITY_TYPE.get(rl) : null;
			if (item != null)
			{
				result.add(item);
			}
		}

		return result;
	}
}