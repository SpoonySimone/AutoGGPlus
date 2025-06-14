package me.spoony.autoggplus.tasks;

import cc.polyfrost.oneconfig.utils.hypixel.HypixelUtils;
import me.spoony.autoggplus.config.ModConfig;
import net.minecraft.client.Minecraft;

import java.util.Timer;
import java.util.TimerTask;

public class SendGG {
    private static final Minecraft mc = Minecraft.getMinecraft();

    //string "auto" or "manual"
    public SendGG(int delay, String type) {
        String ggMessage = ConvertDropdown.getGGMessage(ModConfig.ggMessage);

        if (ModConfig.randomGG) {
            ggMessage = RandomizeCharacters.randomize(ggMessage);
        }

        if ((ModConfig.ModEnabled || type == "manual") && HypixelUtils.INSTANCE.isHypixel()) {
            String finalGgMessage = ggMessage;
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    mc.thePlayer.sendChatMessage("/ac " + finalGgMessage);
                }
            }, delay * 1000L);
        }
    }
}
