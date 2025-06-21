package me.spoony.autoggplus.config;

import cc.polyfrost.oneconfig.config.annotations.*;
import cc.polyfrost.oneconfig.config.data.*;
import me.spoony.autoggplus.AutoGGPlus;
import cc.polyfrost.oneconfig.config.Config;
import me.spoony.autoggplus.retrievers.TriggersRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;
import java.util.regex.Pattern;

public class ModConfig extends Config {

    @Switch(
            name = "Enable",
            subcategory = "General",
            description = "Enable/disable the mod"
    )
    public static boolean ModEnabled = true;

    @Info(
            text = "If you're using 'Randomize GG message', it's recommended to use 'Good Game' or 'Good Fight', as they have more characters that can be randomized.",
            subcategory = "Message Options",
            size = 2,
            type = InfoType.INFO // Types are: INFO, WARNING, ERROR, SUCCESS
    )
    public static boolean ignored;

    @Switch(
            name = "Randomize GG message",
            subcategory = "Message Options",
            description = "Whether the mod should randomize the GG message to bypass Hypixel's anti-spam system or not"
    )
    public static boolean randomGG = true;

    @Slider(
            name = "Delay",
            subcategory = "Message Options",
            description = "What should the delay before sending the GG message be?",
            min = 0, max = 10,
            step = 1
    )
    public static int delay = 1;

    @Dropdown(
            name = "GG Message",
            subcategory = "Message Options",
            description = "What message should the mod send when a game finishes?",
            size = OptionSize.DUAL,
            options = {
                    "gg",
                    "GG",
                    "gg wp",
                    "GG WP",
                    "gf",
                    "gg gf",
                    "Good Game",
                    "Good Fight",
            }
    )
    public static int ggMessage = 6;

    @Info(
            text = "The 'GG Message' selector above will be ignored if you enable custom GG message.",
            subcategory = "Message Options",
            size = 2,
            type = InfoType.INFO // Types are: INFO, WARNING, ERROR, SUCCESS
    )
    public static boolean ignored1;

    @Switch(
            name = "Use Custom GG Message",
            subcategory = "Message Options",
            description = "Whether the mod should use a custom GG message or not. If enabled, the 'GG Message' selector above will be ignored."
    )
    public static boolean customGGMessageEnabled = false;

    @Text(
            name = "Custom GG Message",
            subcategory = "Message Options",
            description = "What custom message should the mod send when a game finishes?",
            placeholder = "Enter your custom message here"
    )
    public static String customGGMessage = "";

    @Switch(
            name = "Minor Events",
            subcategory = "Extras",
            description = "Should a GG message be sent for minor events (The Pit events)?"
    )
    public static boolean minorEvents = false;

//    @Switch(
//            name = "Remove Karma messages",
//            subcategory = "Extras",
//            description = "Should karma messages be hidden in the chat?"
//    )
//    public static boolean hideKarma = false;
//
//    @Switch(
//            name = "Remove GG messages",
//            subcategory = "Extras",
//            description = "Should GG messages be hidden in the chat?"
//    )
//    public static boolean hideGG = false;

    @Button(
            name = "Refresh Patterns",
            text = "Refresh Patterns",
            subcategory = "Extras",
            description = "Click this to refresh the patterns used by the mod. This will fetch the latest patterns from the server."
    )
    Runnable runnable = () -> {
        final Minecraft mc = Minecraft.getMinecraft();
        new Thread(new TriggersRetriever()).start();
        mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Refreshed patterns!"));
    };

    @Info(
            text = "Keep this disabled unless you know what you're doing. Enabling this will break the mod functionality.",
            category = "Debug",
            subcategory = "Utilities",
            size = 2,
            type = InfoType.ERROR // Types are: INFO, WARNING, ERROR, SUCCESS
    )
    public static boolean ignored2;

    @Switch(
            name = "Debug",
            category = "Debug",
            subcategory = "Utilities",
            description = "Makes the mod show the GG message only in the player's chat without actually sending it to the server."
    )
    public static boolean debug = false;

    @Button(
            name = "Print patterns",
            text = "Print patterns",
            category = "Debug",
            subcategory = "Utilities",
            description = "Click this to refresh the patterns used by the mod. This will fetch the latest patterns from the server."
    )
    Runnable runnable1 = () -> {
        final Minecraft mc = Minecraft.getMinecraft();
        mc.ingameGUI.getChatGUI().clearChatMessages();
        List<Pattern> patterns = TriggersRetriever.getCompiledPatterns();
        for (Pattern pattern : patterns) {
            mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + pattern.pattern()));
        }
    };

    public ModConfig() {
        super(new Mod(AutoGGPlus.NAME, ModType.HYPIXEL, "/autoggplus_icon.svg"), AutoGGPlus.MODID + ".json");
        initialize();
        addDependency("customGGMessage", "customGGMessageEnabled");
    }
}

