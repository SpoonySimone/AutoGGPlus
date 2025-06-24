package me.spoony.autoggplus.tasks;

import cc.polyfrost.oneconfig.utils.hypixel.HypixelUtils;
import me.spoony.autoggplus.config.ModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Timer;
import java.util.TimerTask;

public class SendGG {
    private static final Minecraft mc = Minecraft.getMinecraft();
    private static final Logger LOGGER = LogManager.getLogger(SendGG.class);
    String ggMessage;

    //String trigger "general", "minor", "manual"
    public SendGG(int delay, String trigger) {
        //set ggmessage to custom if enabled
        if (ModConfig.customGGMessageEnabled) {
            ggMessage = ModConfig.customGGMessage;
        } else {
            ggMessage = ConvertDropdown.getGGMessage(ModConfig.ggMessage);
        }

        //randomize if enabled in config
        if (ModConfig.randomGG) {
            ggMessage = RandomizeCharacters.randomize(ggMessage);
        }

        //if debug is enabled, just show the user the message instead of sending in chat
        if (ModConfig.debug) {
            mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + ggMessage));
            return;
        }

        //if event is minor, set ggMessage to "gg" no matter what because Hypixel accepts only that
        if (trigger == "minor") {
            ggMessage = "gg";
        }

        if ((ModConfig.ModEnabled || trigger == "manual") && HypixelUtils.INSTANCE.isHypixel()) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    mc.thePlayer.sendChatMessage("/ac " + ggMessage);
                }
            }, delay * 1000L);
        }
    }
}
