package me.spoony.autoggplus.listener;

import me.spoony.autoggplus.config.ModConfig;
import me.spoony.autoggplus.retrievers.TriggersRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import me.spoony.autoggplus.tasks.SendGG;

import java.util.List;
import java.util.regex.Pattern;

public class ChatListener {
    private static final Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onClientChatReceived(ClientChatReceivedEvent event) {

        if (event.type == 2) return; // no action bar

        String strippedMessage = EnumChatFormatting.getTextWithoutFormattingCodes(event.message.getUnformattedText());
        List<Pattern> patterns = TriggersRetriever.getCompiledPatterns();
        for (Pattern pattern : patterns) {
            if (pattern.matcher(strippedMessage).matches()) { //check if pattern matches
                new SendGG(ModConfig.delay, "auto");
                return;
            }
        }
    }
}
