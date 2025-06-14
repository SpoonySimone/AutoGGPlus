package me.spoony.autoggplus.command;

import me.spoony.autoggplus.AutoGGPlus;
import cc.polyfrost.oneconfig.utils.commands.annotations.Command;
import cc.polyfrost.oneconfig.utils.commands.annotations.Main;

@Command(value = AutoGGPlus.MODID, description = "Access the " + AutoGGPlus.NAME + " GUI.")
public class ConfigCommand {
    @Main
    private void handle() {
        AutoGGPlus.config.openGui();
    }
}