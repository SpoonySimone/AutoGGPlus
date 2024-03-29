package me.spoony.autoggplus.tasks;

import me.spoony.autoggplus.AutoGG;
import me.spoony.autoggplus.tasks.data.Server;
import me.spoony.autoggplus.handlers.patterns.PatternHandler;
import me.spoony.autoggplus.tasks.data.Trigger;
import me.spoony.autoggplus.tasks.data.TriggersSchema;
import com.google.gson.Gson;
import gg.essential.api.utils.WebUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Runnable class to fetch the AutoGG triggers on startup.
 *
 * @author ChachyDev
 */
public class RetrieveTriggersTask implements Runnable {

    private final Logger LOGGER = LogManager.getLogger(this);
    private final Gson gson = new Gson();
    private static final String TRIGGERS_URL = "https://static.sk1er.club/autogg/regex_triggers_3.json";

    /**
     * Runs a task which fetches the triggers JSON from the internet.
     *
     * @author ChachyDev
     */
    @Override
    public void run() {
        try {
            AutoGG.INSTANCE.setTriggers(gson.fromJson(WebUtil.fetchString(TRIGGERS_URL), TriggersSchema.class));
        } catch (Exception e) {
            // Prevent the game from melting when the triggers are not available
            LOGGER.error("Failed to fetch the AutoGG triggers! This isn't good...", e);
            AutoGG.INSTANCE.setTriggers(new TriggersSchema(new Server[0]));
            e.printStackTrace();
        }

        LOGGER.info("Registering patterns...");
        for (Server server : AutoGG.INSTANCE.getTriggers().getServers()) {
            for (Trigger trigger : server.getTriggers()) {
                String pattern = trigger.getPattern();
                PatternHandler.INSTANCE.getOrRegisterPattern(pattern);
            }
        }
    }
}
