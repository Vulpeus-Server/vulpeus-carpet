package com.vulpeus.vulpeus_carpet.mixins.fixedFallingBlockCantUseNetherPortal;

import com.vulpeus.vulpeus_carpet.VulpeusCarpetSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FallingBlockEntity.class)
public abstract class MixinFallingBlockEntity extends Entity {

	public MixinFallingBlockEntity(EntityType<?> type, World world) {
		super(type, world);
	}

	@Inject(method = "tick", at = @At("HEAD"))
	private void tick(CallbackInfo ci) {
		if (VulpeusCarpetSettings.fixedFallingBlockCantUseNetherPortal) {
			//#if MC<=12006
			//$$ this.tickPortal();
			//#endif
		}
	}

}
