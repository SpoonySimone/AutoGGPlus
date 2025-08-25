package me.spoony.autoggplus.tasks;

import cc.polyfrost.oneconfig.utils.hypixel.HypixelUtils;
import me.spoony.autoggplus.config.ModConfig;
import me.spoony.autoggplus.utils.ConvertDropdown;
import me.spoony.autoggplus.utils.RandomizeCharacters;
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
    private static long lastMessageTime = 0;
    private static final long cooldown_ms = 3000; //to avoid spam
    String ggMessage;
    String secondMessage = ModConfig.secondMessage;

    //String trigger "general", "minor", "manual"
    public SendGG(int delay, String trigger) {
        //--------------------
        //anti-spam checks
        //--------------------
        // check if we're still in cooldown period. used to avoid spam and false positives (such as mm)
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastMessageTime < cooldown_ms) {
            // why this? in mm sometimes it triggers twice, therefore we only log if its higher
            if (currentTime - lastMessageTime > 1) {
                LOGGER.error("SendGG called too soon, won't send message. Time since last message: {}ms", currentTime - lastMessageTime);
            }
            return;
        }

        //--------------------
        //custom gg message
        //--------------------
        if (ModConfig.customGGMessageEnabled) {
            ggMessage = ModConfig.customGGMessage;
        } else {
            ggMessage = ConvertDropdown.getGGMessage(ModConfig.ggMessage);
        }

        //--------------------
        //randomization
        //--------------------
        //main gg message
        if (ModConfig.randomGG) {
            ggMessage = RandomizeCharacters.randomize(ggMessage);
        }
        //second message
        if (ModConfig.randomSecondMessage) {
            secondMessage = RandomizeCharacters.randomize(secondMessage);
        }

        //--------------------
        //debug
        //--------------------
        //if debug is enabled, just show the user the message(s) instead of sending in chat
        if (ModConfig.debug) {
            mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + ggMessage));
            if (ModConfig.secondMessageEnabled) {
                mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + secondMessage));
            }
            return;
        }

        //if event is minor, set ggMessage to "gg" no matter what because Hypixel accepts only that
        if (trigger.equals("minor")) {
            ggMessage = "gg";
        }

        //--------------------
        //send message(s)
        //--------------------
        if ((ModConfig.modEnabled || trigger.equals("manual")) && HypixelUtils.INSTANCE.isHypixel()) {
            // update the timestamp for cooldown
            lastMessageTime = currentTime;
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    mc.thePlayer.sendChatMessage("/ac " + ggMessage);
                }
            }, delay * 1000L);
        }

        if (ModConfig.modEnabled && ModConfig.secondMessageEnabled && trigger.equals("general") && HypixelUtils.INSTANCE.isHypixel()) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    mc.thePlayer.sendChatMessage("/ac " + secondMessage);
                }
            }, ModConfig.secondDelay * 1000L);
        }

    }
}