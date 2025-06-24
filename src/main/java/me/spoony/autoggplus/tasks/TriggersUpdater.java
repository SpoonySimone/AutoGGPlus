package me.spoony.autoggplus.tasks;

import me.spoony.autoggplus.config.ModConfig;
import me.spoony.autoggplus.retrievers.TriggersRetriever;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TriggersUpdater {
    private boolean lastMinorEventsState;
    private volatile boolean running = true;
    private static final Logger LOGGER = LogManager.getLogger(TriggersUpdater.class);

    public TriggersUpdater() {
        this.lastMinorEventsState = ModConfig.minorEvents;
        startMonitoring();
    }

    private void startMonitoring() {
        new Thread(() -> {
            while (running) {
                try {
                    if (ModConfig.minorEvents != lastMinorEventsState) {
                        lastMinorEventsState = ModConfig.minorEvents;
                        new Thread(new TriggersRetriever()).start();
                        if (ModConfig.debug) {
                            LOGGER.warn("Refreshed patterns!");
                        }
                    }
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }).start();
    }

    public void stop() {
        running = false;
    }
}