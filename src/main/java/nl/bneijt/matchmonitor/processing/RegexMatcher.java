package nl.bneijt.matchmonitor.processing;

import com.google.inject.Inject;
import nl.bneijt.matchmonitor.StateDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMatcher implements PacketContentsHandler {
    static Logger logger = LoggerFactory.getLogger(RegexMatcher.class);

    List<Pattern> patterns;

    @Inject
    StateDatabase stateDatabase;

    public RegexMatcher(List<String> regularExpressions) {
        patterns = new ArrayList<>();
        for (String regularExpression : regularExpressions) {
            logger.info("Adding pattern {}", regularExpression);
            patterns.add(Pattern.compile(regularExpression));
        }
    }

    @Override
    public void apply(String content) {
        String match = match(content);
        if (match != null) {
            stateDatabase.matched(System.currentTimeMillis(), match);
        }
    }

    public String match(String content) {
        for (Pattern pattern : patterns) {
            Matcher matcher = pattern.matcher(content);
            if (matcher.find()) {
                return matcher.group(1);
            }
        }
        return null;
    }

    public static RegexMatcher fromFile(String configurationFileName) throws IOException {
        File configurationFile = new File(configurationFileName);
        Yaml yaml = new Yaml();
        try(InputStream input = new FileInputStream(configurationFile)) {
            HashMap<String, Object> data = (HashMap<String, Object>) yaml.load(input);
            ArrayList<String> regexes = (ArrayList<String>) data.get("regexes");
            return new RegexMatcher(regexes);
        } catch (FileNotFoundException e) {
            logger.error("File not found: {}", configurationFileName);
            throw e;
        } catch (IOException e) {
            logger.error("IOException {}", e);
            throw e;
        }
    }
}
