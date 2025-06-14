package me.spoony.autoggplus.config;

import cc.polyfrost.oneconfig.config.annotations.Dropdown;
import cc.polyfrost.oneconfig.config.annotations.Info;
import cc.polyfrost.oneconfig.config.annotations.Slider;
import cc.polyfrost.oneconfig.config.data.InfoType;
import me.spoony.autoggplus.AutoGGPlus;
import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.Switch;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;

public class ModConfig extends Config {

    @Switch(
            name = "Enable",
            subcategory = "General",
            description = "Enable/disable the mod"
    )
    public static boolean ModEnabled = true;

    @Switch(
            name = "Randomize GG message",
            subcategory = "General",
            description = "Whether the mod should randomize the GG message to bypass Hypixel's anti-spam system or not"
    )
    public static boolean randomGG = true;

    @Slider(
            name = "Delay",
            subcategory = "Message Options",
            description = "What should the delay before sending GG be?",
            min = 0, max = 10,
            step = 1
    )
    public static int delay = 1;

    @Info(
            text = "If you're using 'Randomize GG message', it's recommended to use 'Good Game' or 'Good Fight', as they have more characters that can be randomized.",
            subcategory = "General",
            size = 2,
            type = InfoType.INFO // Types are: INFO, WARNING, ERROR, SUCCESS
    )
    public static boolean ignored;

    @Dropdown(
            name = "GG Message",
            subcategory = "Message Options",
            description = "What message should the mod send when a game finishes?",
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
            text = "Keep this disabled unless you know what you're doing. Enabling this will break the mod functionality.",
            subcategory = "Utilities",
            size = 2,
            type = InfoType.ERROR // Types are: INFO, WARNING, ERROR, SUCCESS
    )
    public static boolean ignored1;

    @Switch(
            name = "Debug",
            subcategory = "Utilities",
            description = "Enable/disable mod debugging features. Leave off unless you know what you're doing."
    )
    public static boolean debug = false;

    public ModConfig() {
        super(new Mod(AutoGGPlus.NAME, ModType.HYPIXEL, "/icon.svg"), AutoGGPlus.MODID + ".json");
        initialize();
    }
}

