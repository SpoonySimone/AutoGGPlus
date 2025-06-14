package me.spoony.autoggplus.tasks;

import me.spoony.autoggplus.config.ModConfig;
import net.minecraft.client.Minecraft;

import java.util.Timer;
import java.util.TimerTask;

public class SendGG {
    private static final Minecraft mc = Minecraft.getMinecraft();

    //string "auto" or "manual"
    public SendGG(int delay, String type) {
        String ggMessage;

        if (ModConfig.randomGG) {
            ggMessage = RandomizeCharacters.randomize(ModConfig.GGMessage);
        } else {
            ggMessage = ModConfig.GGMessage;
        }

        if (ModConfig.ModEnabled || type == "manual") {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    mc.thePlayer.sendChatMessage("/ac " + ggMessage);
                }
            }, delay * 1000L);
        }
    }
}
