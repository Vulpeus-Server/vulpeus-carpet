package com.vulpeus.VulpeusCarpet.mixins.playerActions;

import com.vulpeus.VulpeusCarpet.utils.rule.playerActions.Container;
import java.util.OptionalInt;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ShulkerBoxScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {

  @Inject(method = "openHandledScreen", at = @At("RETURN"))
  public void openHandledScreen(NamedScreenHandlerFactory factory,
      CallbackInfoReturnable<OptionalInt> cir) {
    ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
    if (player.currentScreenHandler instanceof ShulkerBoxScreenHandler screenHandler) {
      if (Container.fill.containsPlayer(player)) {
        Container.fill.fillAll(player, screenHandler);
      } else if (Container.clean.containsPlayer(player)) {
        Container.clean.cleanAll(player, screenHandler);
      }
    }
  }
}
