package nl.bneijt.matchmonitor.processing;

import com.google.inject.Inject;
import nl.bneijt.matchmonitor.StateDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMatcher implements PacketContentsHandler {
    Pattern pattern;

    @Inject
    StateDatabase stateDatabase;

    public RegexMatcher() {
        pattern = Pattern.compile("(([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3}))");
    }

    @Override
    public void apply(String content) {
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            stateDatabase.matched(System.currentTimeMillis(), matcher.group(1));
        }
    }
}
