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

package com.vulpeus.vulpeus_carpet.utils.rule.commandCustomLoad;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.Unit;
import net.minecraft.util.WorldSavePath;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

public class CustomLoadingChunks {

	private static final ChunkTicketType<Unit> VULPEUS = ChunkTicketType.create("vulpeus", (unit, unit2) -> 0);
	public static List<VulpeusTicket> Tickets = new ArrayList<>();

	public static class VulpeusTicket {
		public ServerWorld serverWorld;
		public ChunkPos chunkPos;
		public int radius;

		VulpeusTicket(ServerWorld serverWorld, ChunkPos chunkPos, int radius) {
			this.serverWorld = serverWorld;
			this.chunkPos = chunkPos;
			this.radius = radius;
		}
	}

	public static int add(ServerWorld world, ChunkPos chunkPos, int radius) {
		return add(new VulpeusTicket(world, chunkPos, radius));
	}
	public static int add(VulpeusTicket ticket) {
		ticket.serverWorld.getChunkManager().addTicket(VULPEUS, ticket.chunkPos, ticket.radius, Unit.INSTANCE);
		Tickets.add(ticket);
		return Tickets.size()-1;
	}

	public static VulpeusTicket remove(int index) {
		VulpeusTicket ticket = Tickets.get(index);
		ticket.serverWorld.getChunkManager().removeTicket(VULPEUS, ticket.chunkPos, ticket.radius, Unit.INSTANCE);
		Tickets.remove(index);
		return ticket;
	}

	public static void importConfig(MinecraftServer server) throws IOException {
		for (VulpeusTicket ticket: load(server)) {
			add(ticket);
		}
	}
	public static void exportConfig(MinecraftServer server) throws IOException {
		write(server, Tickets);
	}

	public static List<VulpeusTicket> load(MinecraftServer server) throws IOException {
		List<String> lines = Files.readAllLines(getPath(server), StandardCharsets.UTF_8);

		List<VulpeusTicket> tickets = new ArrayList<>();

		for (String line: lines) {
			String[] args = line.split(" ");

			RegistryKey<World> registryKey = RegistryKey.of(
					RegistryKeys.WORLD,
					//#if MC>=12100
					Identifier.of(args[0])
					//#else
					//$$ new Identifier(args[0])
					//#endif
			);
			ServerWorld serverWorld = server.getWorld(registryKey);

			long packedPos = Long.parseLong(args[1]);
			ChunkPos chunkPos = new ChunkPos(
					ChunkPos.getPackedX(packedPos),
					ChunkPos.getPackedZ(packedPos)
			);

			int radius = Integer.parseInt(args[2]);

			tickets.add(
					new VulpeusTicket(serverWorld, chunkPos, radius)
			);
		}

		return tickets;
	}

	public static void write(MinecraftServer server, List<VulpeusTicket> tickets) throws IOException {
		StringBuilder raw = new StringBuilder();

		for (VulpeusTicket ticket: tickets) {
			String dim = ticket.serverWorld.getDimensionEntry().getKey().get().getValue().toString();
			long pos = ticket.chunkPos.toLong();
			int radius = ticket.radius;

			raw.append(
					String.format("%s %d %d\n", dim, pos, radius)
			);
		}

		Files.writeString(getPath(server), raw.toString());
	}

	private static Path getPath(MinecraftServer server) throws IOException {
		Path path = server.getSavePath(WorldSavePath.ROOT).resolve("custom_load.conf");
		path.toFile().createNewFile();
		return path;
	}
}
