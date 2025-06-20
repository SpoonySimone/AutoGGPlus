package me.spoony.autoggplus.config;

import cc.polyfrost.oneconfig.config.annotations.*;
import cc.polyfrost.oneconfig.config.data.InfoType;
import cc.polyfrost.oneconfig.config.data.OptionSize;
import me.spoony.autoggplus.AutoGGPlus;
import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;

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

    @Info(
            text = "Keep this disabled unless you know what you're doing. Enabling this will break the mod functionality.",
            subcategory = "Utilities",
            size = 2,
            type = InfoType.ERROR // Types are: INFO, WARNING, ERROR, SUCCESS
    )
    public static boolean ignored2;

    @Switch(
            name = "Debug",
            subcategory = "Utilities",
            description = "Makes the mod show the GG message only in the player's chat without actually sending it to the server."
    )
    public static boolean debug = false;

    public ModConfig() {
        super(new Mod(AutoGGPlus.NAME, ModType.HYPIXEL, "/autoggplus_icon.svg"), AutoGGPlus.MODID + ".json");
        initialize();
        addDependency("customGGMessage", "customGGMessageEnabled");
    }
}

