package me.spoony.autoggplus.retrievers;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class TriggersRetriever implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(TriggersRetriever.class);
    private static final String TRIGGERS_URL = "https://raw.githubusercontent.com/SpoonySimone/AutoGGPlus/refs/heads/main/triggers/latest.json";
    private final Gson gson = new Gson();
    private static final List<Pattern> compiledPatterns = new ArrayList<>();

    public static List<Pattern> getCompiledPatterns() {
        return compiledPatterns;
    }

    @Override
    public void run() {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(TRIGGERS_URL).openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            if (connection.getResponseCode() == 200) {
                PatternsWrapper patternsWrapper = gson.fromJson(new InputStreamReader(connection.getInputStream()), PatternsWrapper.class);

                LOGGER.info("Successfully fetched triggers. Compiling patterns...");
                for (String pattern : patternsWrapper.getPatterns()) {
                    compiledPatterns.add(Pattern.compile(pattern));
                }
            } else {
                LOGGER.error("Failed to fetch triggers: HTTP " + connection.getResponseCode());
            }
        } catch (Exception e) {
            LOGGER.error("Failed to fetch triggers!", e);
        }
    }

    public class PatternsWrapper {
        private List<String> patterns;

        public List<String> getPatterns() {
            return patterns;
        }
    }
}
