package com.vulpeus.vulpeus_carpet.mixins;

import java.util.List;
import java.util.Set;
import me.fallenbreath.conditionalmixin.api.mixin.RestrictiveMixinConfigPlugin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VulpeusCarpetMixinPlugin extends RestrictiveMixinConfigPlugin {
	private final Logger LOGGER = LogManager.getLogger();

	@Override
	protected void onRestrictionCheckFailed(String mixinClassName, String reason) {
		LOGGER.debug("[VCM] Disabled mixin {} due to {}", mixinClassName, reason);
	}

	@Override
	public String getRefMapperConfig() {
		return null;
	}

	@Override
	public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
	}

	@Override
	public List<String> getMixins() {
		return null;
	}
}