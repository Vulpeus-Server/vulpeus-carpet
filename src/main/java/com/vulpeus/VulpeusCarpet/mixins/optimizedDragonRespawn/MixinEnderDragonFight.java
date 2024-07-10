package com.vulpeus.VulpeusCarpet.mixins.optimizedDragonRespawn;

import com.vulpeus.VulpeusCarpet.VulpeusCarpetSettings;
import com.vulpeus.VulpeusCarpet.utils.rule.optimizedDragonRespawn.BlockPatternHelper;
import java.util.List;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EndGatewayBlockEntity;
import net.minecraft.block.entity.EndPortalBlockEntity;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.entity.boss.dragon.EnderDragonFight;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.Heightmap;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.gen.feature.EndPortalFeature;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EnderDragonFight.class, priority = 888)
public abstract class MixinEnderDragonFight {

  @Shadow @Final private ServerWorld world;

  @Shadow @Final private BlockPattern endPortalPattern;

  @Nullable @Shadow private BlockPos exitPortalLocation;

  @Shadow private boolean doLegacyCheck;

  @Unique private int cacheChunkIteratorX = -8;

  @Unique private int cacheChunkIteratorZ = -8;

  @Unique private int cacheOriginIteratorY = -1;

  /**
   * @author WenDavid
   * @reason Optimize the search of end portal
   */
  @Overwrite
  private @Nullable BlockPattern.Result findEndPortal() {
    int i;
    int j;
    if (!VulpeusCarpetSettings.optimizedDragonRespawn) {
      cacheChunkIteratorX = -8;
      cacheChunkIteratorZ = -8;
    }
    for (i = cacheChunkIteratorX; i <= 8; ++i) {
      for (j = cacheChunkIteratorZ; j <= 8; ++j) {
        WorldChunk worldChunk = this.world.getChunk(i, j);
        for (BlockEntity blockEntity : worldChunk.getBlockEntities().values()) {
          if (VulpeusCarpetSettings.optimizedDragonRespawn
              && blockEntity instanceof EndGatewayBlockEntity) {
            continue;
          }
          if (blockEntity instanceof EndPortalBlockEntity) {
            BlockPattern.Result result =
                this.endPortalPattern.searchAround(this.world, blockEntity.getPos());
            if (result != null) {
              BlockPos blockPos = result.translate(3, 3, 3).getBlockPos();
              if (this.exitPortalLocation == null) {
                this.exitPortalLocation = blockPos;
              }
              // No need to judge whether optimizing option is open
              cacheChunkIteratorX = i;
              cacheChunkIteratorZ = j;
              return result;
            }
          }
        }
      }
    }
    if (this.doLegacyCheck || this.exitPortalLocation == null) {
      if (VulpeusCarpetSettings.optimizedDragonRespawn && cacheOriginIteratorY != -1) {
        i = cacheOriginIteratorY;
      } else {
        i =
            this.world
                .getTopPosition(
                    Heightmap.Type.MOTION_BLOCKING, offsetOrigin(BlockPos.ORIGIN))
                .getY();
      }
      boolean notFirstSearch = false;
      for (j = i; j >= 0; --j) {
        BlockPattern.Result result2 = null;
        if (VulpeusCarpetSettings.optimizedDragonRespawn && notFirstSearch) {
          result2 =
              BlockPatternHelper.partialSearchAround(
                  this.endPortalPattern,
                  this.world,
                  new BlockPos(
                      offsetOrigin(BlockPos.ORIGIN).getX(),
                      j,
                      offsetOrigin(BlockPos.ORIGIN).getZ()));
        } else {
          result2 =
              this.endPortalPattern.searchAround(
                  this.world,
                  new BlockPos(
                      offsetOrigin(BlockPos.ORIGIN).getX(),
                      j,
                      offsetOrigin(BlockPos.ORIGIN).getZ()));
        }
        if (result2 != null) {
          if (this.exitPortalLocation == null) {
            this.exitPortalLocation = result2.translate(3, 3, 3).getBlockPos();
          }
          cacheOriginIteratorY = j;
          return result2;
        }
        notFirstSearch = true;
      }
    }

    return null;
  }

  @Inject(method = "respawnDragon(Ljava/util/List;)V", at = @At("HEAD"))
  private void resetCache(List<EndCrystalEntity> crystals, CallbackInfo ci) {
    cacheChunkIteratorX = -8;
    cacheChunkIteratorZ = -8;
    cacheOriginIteratorY = -1;
  }

  private static BlockPos offsetOrigin(BlockPos pos) {
    return EndPortalFeature.ORIGIN.add((Vec3i) pos);
  }
}