package me.spoony.autoggplus;

import me.spoony.autoggplus.command.ConfigCommand;
import me.spoony.autoggplus.command.SendGGCommand;
import me.spoony.autoggplus.config.ModConfig;
import me.spoony.autoggplus.listener.ChatListener;
import me.spoony.autoggplus.retrievers.TriggersRetriever;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import cc.polyfrost.oneconfig.utils.commands.CommandManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = AutoGGPlus.MODID, name = AutoGGPlus.NAME, version = AutoGGPlus.VERSION)
public class AutoGGPlus {

    // Sets the variables from `gradle.properties`. See the `blossom` config in `build.gradle.kts`.
    public static final String MODID = "@ID@";
    public static final String NAME = "@NAME@";
    public static final String VERSION = "@VER@";
    @Mod.Instance(MODID)
    public static AutoGGPlus INSTANCE; // Adds the instance of the mod, so we can access other variables.
    public static ModConfig config;
    public static final Logger LOGGER = LogManager.getLogger(AutoGGPlus.class);

    // Register the config and commands.
    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        config = new ModConfig();
        CommandManager.INSTANCE.registerCommand(new ConfigCommand());
        CommandManager.INSTANCE.registerCommand(new SendGGCommand());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new ChatListener());
        new Thread(new TriggersRetriever()).start();
    }
}
