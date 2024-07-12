package com.vulpeus.VulpeusCarpet.mixins.disableCrash;

import com.vulpeus.VulpeusCarpet.VulpeusCarpetSettings;
import com.vulpeus.VulpeusCarpet.utils.sendMessage;
import java.util.Iterator;
import java.util.function.BooleanSupplier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(MinecraftServer.class)
public class MixinMinecraftServer {
  @Inject(
      method = {"tickWorlds"},
      at = {
        @At(
            value = "INVOKE",
            target =
                "Lnet/minecraft/util/crash/CrashReport;create(Ljava/lang/Throwable;Ljava/lang/String;)Lnet/minecraft/util/crash/CrashReport;")
      },
      locals = LocalCapture.CAPTURE_FAILHARD,
      cancellable = true)
  public void injectTickWorlds(
      BooleanSupplier shouldKeepTicking,
      CallbackInfo ci,
      @SuppressWarnings("all") Iterator var2,
      ServerWorld serverWorld,
      Throwable throwable) {

    Throwable causeErr = throwable.getCause();

    if (VulpeusCarpetSettings.disableCCECrash && causeErr instanceof ClassCastException) {
      ci.cancel();
      sendMessage.sendGlobalMessage((MinecraftServer) (Object) this, "now CCE Crashed.");
      return;
    }
    if (VulpeusCarpetSettings.disableSOECrash && causeErr instanceof StackOverflowError) {
      ci.cancel();
      sendMessage.sendGlobalMessage((MinecraftServer) (Object) this, "now SOE Crashed.");
      return;
    }
  }
}