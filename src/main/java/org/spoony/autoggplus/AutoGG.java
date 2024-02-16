/*
 * AutoGG - Automatically say a selectable phrase at the end of a game on supported servers.
 * Copyright (C) 2020  Sk1er LLC
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.spoony.autoggplus;

import org.spoony.autoggplus.command.AutoGGCommand;
import org.spoony.autoggplus.config.AutoGGConfig;
import org.spoony.autoggplus.handlers.gg.AutoGGHandler;
import org.spoony.autoggplus.handlers.patterns.PlaceholderAPI;
import org.spoony.autoggplus.tasks.RetrieveTriggersTask;
import org.spoony.autoggplus.tasks.data.TriggersSchema;
import gg.essential.api.EssentialAPI;
import gg.essential.api.utils.JsonHolder;
import gg.essential.api.utils.Multithreading;
import gg.essential.api.utils.WebUtil;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.*;

/**
 * Contains the main class for AutoGG which handles trigger schema setting/getting and the main initialization code.
 *
 * @author ChachyDev
 */
@Mod(modid = "autoggplus", name = "AutoGG+", version = "1.0.0")
public class AutoGG {

    @Mod.Instance
    public static AutoGG INSTANCE;
    private final String[] primaryGGStrings = {"gg", "GG", "gf", "Good Game", "Good Fight", "Good Round! :D"};
    private final String[] primaryGGStringsRandom = {"good game", "good gamE", "good gaMe", "good gaME", "good gAme", "good gAmE", "good gAMe", "good gAME", "good Game", "good GamE", "good GaMe", "good GaME", "good GAme", "good GAmE", "good GAMe", "good GAME", "gooD game", "gooD gamE", "gooD gaMe", "gooD gaME", "gooD gAme", "gooD gAmE", "gooD gAMe", "gooD gAME", "gooD Game", "gooD GamE", "gooD GaMe", "gooD GaME", "gooD GAme", "gooD GAmE", "gooD GAMe", "gooD GAME", "goOd game", "goOd gamE", "goOd gaMe", "goOd gaME", "goOd gAme", "goOd gAmE", "goOd gAMe", "goOd gAME", "goOd Game", "goOd GamE", "goOd GaMe", "goOd GaME", "goOd GAme", "goOd GAmE", "goOd GAMe", "goOd GAME", "goOD game", "goOD gamE", "goOD gaMe", "goOD gaME", "goOD gAme", "goOD gAmE", "goOD gAMe", "goOD gAME", "goOD Game", "goOD GamE", "goOD GaMe", "goOD GaME", "goOD GAme", "goOD GAmE", "goOD GAMe", "goOD GAME", "gOod game", "gOod gamE", "gOod gaMe", "gOod gaME", "gOod gAme", "gOod gAmE", "gOod gAMe", "gOod gAME", "gOod Game", "gOod GamE", "gOod GaMe", "gOod GaME", "gOod GAme", "gOod GAmE", "gOod GAMe", "gOod GAME", "gOoD game", "gOoD gamE", "gOoD gaMe", "gOoD gaME", "gOoD gAme", "gOoD gAmE", "gOoD gAMe", "gOoD gAME", "gOoD Game", "gOoD GamE", "gOoD GaMe", "gOoD GaME", "gOoD GAme", "gOoD GAmE", "gOoD GAMe", "gOoD GAME", "gOOd game", "gOOd gamE", "gOOd gaMe", "gOOd gaME", "gOOd gAme", "gOOd gAmE", "gOOd gAMe", "gOOd gAME", "gOOd Game", "gOOd GamE", "gOOd GaMe", "gOOd GaME", "gOOd GAme", "gOOd GAmE", "gOOd GAMe", "gOOd GAME", "gOOD game", "gOOD gamE", "gOOD gaMe", "gOOD gaME", "gOOD gAme", "gOOD gAmE", "gOOD gAMe", "gOOD gAME", "gOOD Game", "gOOD GamE", "gOOD GaMe", "gOOD GaME", "gOOD GAme", "gOOD GAmE", "gOOD GAMe", "gOOD GAME", "Good game", "Good gamE", "Good gaMe", "Good gaME", "Good gAme", "Good gAmE", "Good gAMe", "Good gAME", "Good Game", "Good GamE", "Good GaMe", "Good GaME", "Good GAme", "Good GAmE", "Good GAMe", "Good GAME", "GooD game", "GooD gamE", "GooD gaMe", "GooD gaME", "GooD gAme", "GooD gAmE", "GooD gAMe", "GooD gAME", "GooD Game", "GooD GamE", "GooD GaMe", "GooD GaME", "GooD GAme", "GooD GAmE", "GooD GAMe", "GooD GAME", "GoOd game", "GoOd gamE", "GoOd gaMe", "GoOd gaME", "GoOd gAme", "GoOd gAmE", "GoOd gAMe", "GoOd gAME", "GoOd Game", "GoOd GamE", "GoOd GaMe", "GoOd GaME", "GoOd GAme", "GoOd GAmE", "GoOd GAMe", "GoOd GAME", "GoOD game", "GoOD gamE", "GoOD gaMe", "GoOD gaME", "GoOD gAme", "GoOD gAmE", "GoOD gAMe", "GoOD gAME", "GoOD Game", "GoOD GamE", "GoOD GaMe", "GoOD GaME", "GoOD GAme", "GoOD GAmE", "GoOD GAMe", "GoOD GAME", "GOod game", "GOod gamE", "GOod gaMe", "GOod gaME", "GOod gAme", "GOod gAmE", "GOod gAMe", "GOod gAME", "GOod Game", "GOod GamE", "GOod GaMe", "GOod GaME", "GOod GAme", "GOod GAmE", "GOod GAMe", "GOod GAME", "GOoD game", "GOoD gamE", "GOoD gaMe", "GOoD gaME", "GOoD gAme", "GOoD gAmE", "GOoD gAMe", "GOoD gAME", "GOoD Game", "GOoD GamE", "GOoD GaMe", "GOoD GaME", "GOoD GAme", "GOoD GAmE", "GOoD GAMe", "GOoD GAME", "GOOd game", "GOOd gamE", "GOOd gaMe", "GOOd gaME", "GOOd gAme", "GOOd gAmE", "GOOd gAMe", "GOOd gAME", "GOOd Game", "GOOd GamE", "GOOd GaMe", "GOOd GaME", "GOOd GAme", "GOOd GAmE", "GOOd GAMe", "GOOd GAME", "GOOD game", "GOOD gamE", "GOOD gaMe", "GOOD gaME", "GOOD gAme", "GOOD gAmE", "GOOD gAMe", "GOOD gAME", "GOOD Game", "GOOD GamE", "GOOD GaMe", "GOOD GaME", "GOOD GAme", "GOOD GAmE", "GOOD GAMe", "GOOD GAME", "goodgame","goodgamE","goodgaMe","goodgaME","goodgAme","goodgAmE","goodgAMe","goodgAME","goodGame","goodGamE","goodGaMe","goodGaME","goodGAme","goodGAmE","goodGAMe","goodGAME","gooDgame","gooDgamE","gooDgaMe","gooDgaME","gooDgAme","gooDgAmE","gooDgAMe","gooDgAME","gooDGame","gooDGamE","gooDGaMe","gooDGaME","gooDGAme","gooDGAmE","gooDGAMe","gooDGAME","goOdgame","goOdgamE","goOdgaMe","goOdgaME","goOdgAme","goOdgAmE","goOdgAMe","goOdgAME","goOdGame","goOdGamE","goOdGaMe","goOdGaME","goOdGAme","goOdGAmE","goOdGAMe","goOdGAME","goODgame","goODgamE","goODgaMe","goODgaME","goODgAme","goODgAmE","goODgAMe","goODgAME","goODGame","goODGamE","goODGaMe","goODGaME","goODGAme","goODGAmE","goODGAMe","goODGAME","gOodgame","gOodgamE","gOodgaMe","gOodgaME","gOodgAme","gOodgAmE","gOodgAMe","gOodgAME","gOodGame","gOodGamE","gOodGaMe","gOodGaME","gOodGAme","gOodGAmE","gOodGAMe","gOodGAME","gOoDgame","gOoDgamE","gOoDgaMe","gOoDgaME","gOoDgAme","gOoDgAmE","gOoDgAMe","gOoDgAME","gOoDGame","gOoDGamE","gOoDGaMe","gOoDGaME","gOoDGAme","gOoDGAmE","gOoDGAMe","gOoDGAME","gOOdgame","gOOdgamE","gOOdgaMe","gOOdgaME","gOOdgAme","gOOdgAmE","gOOdgAMe","gOOdgAME","gOOdGame","gOOdGamE","gOOdGaMe","gOOdGaME","gOOdGAme","gOOdGAmE","gOOdGAMe","gOOdGAME","gOODgame","gOODgamE","gOODgaMe","gOODgaME","gOODgAme","gOODgAmE","gOODgAMe","gOODgAME","gOODGame","gOODGamE","gOODGaMe","gOODGaME","gOODGAme","gOODGAmE","gOODGAMe","gOODGAME","Goodgame","GoodgamE","GoodgaMe","GoodgaME","GoodgAme","GoodgAmE","GoodgAMe","GoodgAME","GoodGame","GoodGamE","GoodGaMe","GoodGaME","GoodGAme","GoodGAmE","GoodGAMe","GoodGAME","GooDgame","GooDgamE","GooDgaMe","GooDgaME","GooDgAme","GooDgAmE","GooDgAMe","GooDgAME","GooDGame","GooDGamE","GooDGaMe","GooDGaME","GooDGAme","GooDGAmE","GooDGAMe","GooDGAME","GoOdgame","GoOdgamE","GoOdgaMe","GoOdgaME","GoOdgAme","GoOdgAmE","GoOdgAMe","GoOdgAME","GoOdGame","GoOdGamE","GoOdGaMe","GoOdGaME","GoOdGAme","GoOdGAmE","GoOdGAMe","GoOdGAME","GoODgame","GoODgamE","GoODgaMe","GoODgaME","GoODgAme","GoODgAmE","GoODgAMe","GoODgAME","GoODGame","GoODGamE","GoODGaMe","GoODGaME","GoODGAme","GoODGAmE","GoODGAMe","GoODGAME","GOodgame","GOodgamE","GOodgaMe","GOodgaME","GOodgAme","GOodgAmE","GOodgAMe","GOodgAME","GOodGame","GOodGamE","GOodGaMe","GOodGaME","GOodGAme","GOodGAmE","GOodGAMe","GOodGAME","GOoDgame","GOoDgamE","GOoDgaMe","GOoDgaME","GOoDgAme","GOoDgAmE","GOoDgAMe","GOoDgAME","GOoDGame","GOoDGamE","GOoDGaMe","GOoDGaME","GOoDGAme","GOoDGAmE","GOoDGAMe","GOoDGAME","GOOdgame","GOOdgamE","GOOdgaMe","GOOdgaME","GOOdgAme","GOOdgAmE","GOOdgAMe","GOOdgAME","GOOdGame","GOOdGamE","GOOdGaMe","GOOdGaME","GOOdGAme","GOOdGAmE","GOOdGAMe","GOOdGAME","GOODgame","GOODgamE","GOODgaMe","GOODgaME","GOODgAme","GOODgAmE","GOODgAMe","GOODgAME","GOODGame","GOODGamE","GOODGaMe","GOODGaME","GOODGAme","GOODGAmE","GOODGAMe","GOODGAME", "gg", "gG", "Gg", "GG"};
    private final String[] secondaryGGStrings = {"Have a good day!", "<3", "gf", "Good Fight", "Good Round", ":D", "Well played!", "wp"};
    private TriggersSchema triggers;
    private AutoGGConfig autoGGConfig;

    private boolean usingEnglish;

    @Mod.EventHandler
    public void onFMLPreInitialization(FMLPreInitializationEvent event) {
        Multithreading.runAsync(this::checkUserLanguage);
    }

    @Mod.EventHandler
    public void onFMLInitialization(FMLInitializationEvent event) {
        autoGGConfig = new AutoGGConfig();
        autoGGConfig.preload();

        Set<String> joined = new HashSet<>();
        joined.addAll(Arrays.asList(primaryGGStrings));
        joined.addAll(Arrays.asList(primaryGGStringsRandom));
        joined.addAll(Arrays.asList(secondaryGGStrings));

        PlaceholderAPI.INSTANCE.registerPlaceHolder("antigg_strings", String.join("|", joined));

        Multithreading.runAsync(new RetrieveTriggersTask());
        MinecraftForge.EVENT_BUS.register(new AutoGGHandler());
        EssentialAPI.getCommandRegistry().registerCommand(new AutoGGCommand());

        // fix settings that were moved to seconds instead of ms
        // so users aren't waiting 5000 seconds to send GG
        if (autoGGConfig.getAutoGGDelay() > 5) autoGGConfig.setAutoGGDelay(1);
        if (autoGGConfig.getSecondaryDelay() > 5) autoGGConfig.setSecondaryDelay(1);
    }

    @Mod.EventHandler
    public void loadComplete(FMLLoadCompleteEvent event) {
        if (!usingEnglish) {
            EssentialAPI.getNotifications().push(
                "AutoGG",
                "We've detected your Hypixel language isn't set to English! AutoGG will not work on other languages.\n" +
                    "If this is a mistake, feel free to ignore it.", 6
            );
        }
    }

    private void checkUserLanguage() {
        final String username = Minecraft.getMinecraft().getSession().getUsername();
        final JsonHolder json = WebUtil.fetchJSON("https://api.sk1er.club/player/" + username);
        final String language = json.optJSONObject("player").defaultOptString("userLanguage", "ENGLISH");
        this.usingEnglish = "ENGLISH".equals(language);
    }

    public TriggersSchema getTriggers() {
        return triggers;
    }

    public void setTriggers(TriggersSchema triggers) {
        this.triggers = triggers;
    }

    public AutoGGConfig getAutoGGConfig() {
        return autoGGConfig;
    }

    public String[] getPrimaryGGStrings() {
        return primaryGGStrings;
    }

    public String[] getPrimaryGGStringsRandom() {
        return primaryGGStringsRandom;
    }

    public String[] getSecondaryGGStrings() {
        return secondaryGGStrings;
    }
}
