package me.spoony.autoggplus.utils;

import me.spoony.autoggplus.AutoGGPlus;
import net.minecraft.client.Minecraft;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

import java.net.URL;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import static me.spoony.autoggplus.AutoGGPlus.LOGGER;

public class Updater {
    private static final Minecraft mc = Minecraft.getMinecraft();
    public static String latestVersion;
    public static boolean updateChecked = false;
    public static boolean updateAvailable = false;
    public static boolean sentUpdateMessage = false;
    public static String modrinthLink = "https://modrinth.com/project/j9cYkjUb/versions";

    //0 = automatic, 1 = manual
    public static void run(int mode) {
        if (!updateChecked) {
            checkUpdate();
        }

        if (mode == 1 && updateAvailable) {
            sendUpdateMessage(mode);
        } else if (mode == 0) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    sendUpdateMessage(mode);
                }
            }, 3000);
        }
    }

    public static void checkUpdate() {
        updateChecked = true;

        String currentVersion = AutoGGPlus.VERSION;
        try {
            Properties prop = new Properties();
            prop.load(new URL(
                    "https://raw.githubusercontent.com/SpoonySimone/AutoGGPlus/master/gradle.properties")
                    .openStream());
            latestVersion = prop.getProperty("mod_version");

            if (latestVersion.equals("0")) {
                LOGGER.warn(
                        "Version checker is 0. Version checker disabled.");
                return;
            }

            if (!currentVersion.equals(latestVersion)) {
                LOGGER.warn("A newer version " + latestVersion + " is available! Please consider updating! ("
                        + currentVersion + ")");
                updateAvailable = true;
            } else {
                LOGGER.info("Already on latest version (" + latestVersion + ")");
            }
        } catch (Exception e) {
            // e.printStackTrace();
            LOGGER.error(e);
            LOGGER.error("Failed to check version. Assuming we're on latest version.");
        }
    }

    public static void sendUpdateMessage(int mode) {
        if (!sentUpdateMessage || mode == 1) {
            mc.thePlayer.playSound("minecraft:random.successful_hit", 1.0F, 1.0F);

            mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.WHITE + "A newer version of " + EnumChatFormatting.GREEN + "AutoGGPlus " + EnumChatFormatting.WHITE + "is available!"));
            mc.thePlayer.addChatMessage(new ChatComponentText("You are on version " + EnumChatFormatting.BLUE + AutoGGPlus.VERSION + EnumChatFormatting.WHITE + ". The latest version is " + EnumChatFormatting.BLUE + Updater.latestVersion + EnumChatFormatting.WHITE + "."));

            IChatComponent link = new ChatComponentText("Click here to download from Modrinth");
            ChatStyle style = new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, modrinthLink));
            style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                    new ChatComponentText(EnumChatFormatting.BOLD.toString() + EnumChatFormatting.UNDERLINE + EnumChatFormatting.GREEN + "Opens in your default browser")));
            style.setColor(EnumChatFormatting.GREEN);
            style.setBold(true);
            style.setUnderlined(true);
            link.setChatStyle(style);

            mc.thePlayer.addChatMessage(link);

            sentUpdateMessage = true;
        }
    }
}
