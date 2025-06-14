package me.spoony.autoggplus.command;

import cc.polyfrost.oneconfig.utils.commands.annotations.Command;
import cc.polyfrost.oneconfig.utils.commands.annotations.Main;
import me.spoony.autoggplus.tasks.SendGG;

@Command(value = "gg", description = "Send a GG message in chat")
public class SendGGCommand {
    @Main
    private void queue() {
        new SendGG(0, "manual");
    }
}
