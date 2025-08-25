package me.spoony.autoggplus.config;

import cc.polyfrost.oneconfig.config.annotations.*;
import cc.polyfrost.oneconfig.config.data.*;
import me.spoony.autoggplus.AutoGGPlus;
import cc.polyfrost.oneconfig.config.Config;
import me.spoony.autoggplus.retrievers.TriggersRetriever;
import me.spoony.autoggplus.utils.Updater;
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
    public static boolean modEnabled = true;

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

    @Info(
            text = "If you're using 'Randomize GG message', it's better to choose 'Good Game' or 'Good Fight' as they have more characters to randomize.",
            subcategory = "Message Options",
            size = 2,
            type = InfoType.INFO // Types are: INFO, WARNING, ERROR, SUCCESS
    )
    public static boolean ignored;

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
            name = "Custom GG Message",
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
            name = "Enable second message",
            subcategory = "Second Message Options",
            description = "Enable/disable the second message"
    )
    public static boolean secondMessageEnabled = false;

    @Switch(
            name = "Randomize second message",
            subcategory = "Second Message Options",
            description = "Whether the mod should randomize the second message to bypass Hypixel's anti-spam system or not"
    )
    public static boolean randomSecondMessage = false;

    @Slider(
            name = "Delay",
            subcategory = "Second Message Options",
            description = "What should the delay before sending the second message be?",
            min = 0, max = 10,
            step = 1
    )
    public static int secondDelay = 2;

    @Text(
            name = "Second Message",
            subcategory = "Second Message Options",
            description = "What second message should the mod send when a game finishes?",
            size = OptionSize.DUAL,
            placeholder = "Enter your second message here"
    )
    public static String secondMessage = "AutoGG+ by SpoonySimone!";

    @Info(
            text = "The 'Minor Events' message is always 'gg' because Hypixel only considers that valid.",
            subcategory = "Extras",
            size = 2,
            type = InfoType.INFO // Types are: INFO, WARNING, ERROR, SUCCESS
    )
    public static boolean ignored3;

    @Switch(
            name = "Minor Events",
            size = OptionSize.DUAL,
            subcategory = "Extras",
            description = "Should a GG message be sent for minor events (The Pit events)?"
    )
    public static boolean minorEvents = false;

    @Switch(
            name = "Remove Karma messages",
            subcategory = "Extras",
            description = "Should the karma messages be hidden from chat?"
    )
    public static boolean removeKarma = false;

    @Switch(
            name = "Remove GG messages",
            subcategory = "Extras",
            description = "Should the GG messages be hidden from chat?"
    )
    public static boolean removeGG = false;

    @Switch(
            name = "Automatic update checker",
            category = "Updates",
            subcategory = "General",
            description = "Should the mod check for updates on startup?"
    )
    public static boolean updateCheck = true;

    @Button(
            name = "Check for updates",
            text = "Check for updates",
            category = "Updates",
            subcategory = "General",
            description = "Click this to check for updates"
    )
    Runnable runnableUpdate = () -> {
        Updater.run(1);
    };

    @Info(
            text = "Keep this disabled unless you know what you're doing. Enabling this will break the mod functionality.",
            category = "Debug",
            subcategory = "General",
            size = 2,
            type = InfoType.ERROR // Types are: INFO, WARNING, ERROR, SUCCESS
    )
    public static boolean ignored2;

    @Switch(
            name = "Debug",
            category = "Debug",
            subcategory = "General",
            description = "Makes the mod show the GG message only in the player's chat without actually sending it to the server."
    )
    public static boolean debug = false;

    @Button(
            name = "Force triggers refresh",
            text = "Run",
            category = "Debug",
            subcategory = "Utilities",
            description = "Click this to refresh the triggers used by the mod. This will fetch the latest triggers from the server."
    )
    Runnable runnable = () -> {
        final Minecraft mc = Minecraft.getMinecraft();
        new Thread(new TriggersRetriever()).start();
        mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Refreshed triggers!"));
    };

    @Button(
            name = "Print triggers",
            text = "Print",
            category = "Debug",
            subcategory = "Utilities",
            description = "Click this to print the triggers used by the mod."
    )
    Runnable runnable1 = () -> {
        final Minecraft mc = Minecraft.getMinecraft();
        mc.ingameGUI.getChatGUI().clearChatMessages();
        List<Pattern> patterns = TriggersRetriever.getCompiledPatterns();
        for (Pattern pattern : patterns) {
            mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + pattern.pattern()));
        }
    };

    @Button(
            name = "Remove Karma Trigger",
            text = "Print",
            category = "Debug",
            subcategory = "Utilities",
            description = "Click this to print the remove karma trigger used by the mod."
    )
    Runnable runnablekarma = () -> {
        final Minecraft mc = Minecraft.getMinecraft();
        mc.ingameGUI.getChatGUI().clearChatMessages();
        mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + TriggersRetriever.getRemoveKarmaPattern().toString()));
    };


    @Button(
            name = "Remove GG Trigger",
            text = "Print",
            category = "Debug",
            subcategory = "Utilities",
            description = "Click this to print the remove gg trigger used by the mod."
    )
    Runnable runnablegg = () -> {
        final Minecraft mc = Minecraft.getMinecraft();
        mc.ingameGUI.getChatGUI().clearChatMessages();
        mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + TriggersRetriever.getRemoveGGPattern().toString()));
    };

    public ModConfig() {
        super(new Mod(AutoGGPlus.NAME, ModType.HYPIXEL, "/autoggplus_icon.svg"), AutoGGPlus.MODID + ".json");
        initialize();
        addDependency("customGGMessage", "customGGMessageEnabled");
        addDependency("randomSecondMessage","secondMessageEnabled");
        addDependency("secondDelay","secondMessageEnabled");
        addDependency("secondMessage","secondMessageEnabled");

        // disable everything if mod is disabled
        addDependency("randomGG", "modEnabled");
        addDependency("delay", "modEnabled");
        addDependency("ggMessage", "modEnabled");
        addDependency("customGGMessageEnabled", "modEnabled");
        addDependency("customGGMessage", "modEnabled");
        addDependency("secondMessageEnabled", "modEnabled");
        addDependency("randomSecondMessage", "modEnabled");
        addDependency("secondDelay", "modEnabled");
        addDependency("secondMessage", "modEnabled");
        addDependency("minorEvents", "modEnabled");
        addDependency("removeKarma", "modEnabled");
        addDependency("removeGG", "modEnabled");
    }
}

