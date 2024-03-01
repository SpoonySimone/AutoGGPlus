package me.spoony.autoggplus.detectors.branding;

import me.spoony.autoggplus.detectors.IDetector;
import me.spoony.autoggplus.handlers.patterns.PatternHandler;
import net.minecraft.client.Minecraft;

public class ServerBrandingDetector implements IDetector {
    @Override
    public boolean detect(String data) {
        return Minecraft.getMinecraft().thePlayer != null && PatternHandler.INSTANCE.getOrRegisterPattern(data).matcher(Minecraft.getMinecraft().thePlayer.getClientBrand()).matches();
    }
}
