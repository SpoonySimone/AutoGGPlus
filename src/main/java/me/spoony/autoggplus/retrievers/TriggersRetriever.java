package me.spoony.autoggplus.retrievers;

import com.google.gson.Gson;
import me.spoony.autoggplus.config.ModConfig;
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
    private static final List<Pattern> generalPatterns = new ArrayList<>();
    private static final List<Pattern> minorPatterns = new ArrayList<>();
    private static final List<Pattern> compiledPatterns = new ArrayList<>();
    private static Pattern removeKarmaPattern = null;
    private static Pattern removeGGPattern = null;

    public static List<Pattern> getGeneralPatterns() {
        return generalPatterns;
    }

    public static List<Pattern> getMinorPatterns() {
        return minorPatterns;
    }

    public static List<Pattern> getCompiledPatterns() {
        return compiledPatterns;
    }

    public static Pattern getRemoveKarmaPattern() {
        return removeKarmaPattern;
    }

    public static Pattern getRemoveGGPattern() {
        return removeGGPattern;
    }

    @Override
    public void run() {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(TRIGGERS_URL).openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            if (connection.getResponseCode() == 200) {
                PatternsWrapper patternsWrapper = gson.fromJson(new InputStreamReader(connection.getInputStream()), PatternsWrapper.class);

                generalPatterns.clear();
                minorPatterns.clear();
                compiledPatterns.clear();

                LOGGER.info("Successfully fetched triggers. Compiling patterns...");

                // always add general patterns
                if (patternsWrapper.getGeneral() != null) {
                    for (String pattern : patternsWrapper.getGeneral()) {
                        generalPatterns.add(Pattern.compile(pattern));
                    }
                    LOGGER.info("Compiled {} general patterns", patternsWrapper.getGeneral().size());
                }

                // add minor patterns only if enabled in config
                if (ModConfig.minorEvents && patternsWrapper.getMinor() != null) {
                    for (String pattern : patternsWrapper.getMinor()) {
                        minorPatterns.add(Pattern.compile(pattern));
                    }
                    LOGGER.info("Compiled {} minor patterns", patternsWrapper.getMinor().size());
                } else if (!ModConfig.minorEvents) {
                    LOGGER.info("Minor events disabled, skipping minor patterns");
                }

                compiledPatterns.addAll(generalPatterns);
                compiledPatterns.addAll(minorPatterns);

                LOGGER.info("Total compiled patterns: {}", compiledPatterns.size());

                if (patternsWrapper.getRemoveKarma() != null) {
                    removeKarmaPattern = Pattern.compile(patternsWrapper.getRemoveKarma());
                    LOGGER.info("Compiled removeKarma pattern");
                }

                if (patternsWrapper.getRemoveGG() != null) {
                    removeGGPattern = Pattern.compile(patternsWrapper.getRemoveGG());
                    LOGGER.info("Compiled removeGG pattern");
                }
            } else {
                LOGGER.error("Failed to fetch triggers: HTTP " + connection.getResponseCode());
            }
        } catch (Exception e) {
            LOGGER.error("Failed to fetch triggers!", e);
        }
    }

    public static class PatternsWrapper {
        private List<String> general;
        private List<String> minor;
        private String removeKarma;
        private String removeGG;

        public List<String> getGeneral() {
            return general;
        }

        public List<String> getMinor() {
            return minor;
        }

        public String getRemoveKarma() {
            return removeKarma;
        }

        public String getRemoveGG() {
            return removeGG;
        }
    }
}