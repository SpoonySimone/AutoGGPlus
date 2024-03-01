package me.spoony.autoggplus.detectors.ip;

import me.spoony.autoggplus.handlers.patterns.PatternHandler;
import me.spoony.autoggplus.detectors.IDetector;
import net.minecraft.client.Minecraft;

public class ServerIPDetector implements IDetector {
    @Override
    public boolean detect(String data) {
        return Minecraft.getMinecraft().thePlayer != null && PatternHandler.INSTANCE.getOrRegisterPattern(data).matcher(Minecraft.getMinecraft().getCurrentServerData().serverIP).matches();
    }
}
