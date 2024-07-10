package com.vulpeus.VulpeusCarpet.utils;

// import com.vulpeus.VulpeusCarpet.TaichiCarpetSettings;
// import com.vulpeus.VulpeusCarpet.logging.HUD.notice;
import java.util.Objects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Util;

public class sendMassage {

  public static void sendGlobalMessage(ServerPlayerEntity source, String message) {
    Objects.requireNonNull(source.getServer())
        .getPlayerManager()
        .getPlayerList()
        .forEach(player -> player.sendSystemMessage(Text.of(message), Util.NIL_UUID));
  }

  public static void sendSourceMessage(ServerPlayerEntity source, String message) {
    source.sendSystemMessage(Text.of(message), Util.NIL_UUID);
  }
}
