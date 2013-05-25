package nl.bneijt.matchmonitor.processing;

import com.google.inject.Inject;
import nl.bneijt.matchmonitor.StateDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IpAddressMatcher implements PacketContentsHandler {
    Pattern pattern;

    @Inject
    StateDatabase stateDatabase;

    public IpAddressMatcher() {
        pattern = Pattern.compile("([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3})");
    }

    @Override
    public void apply(String content) {

        String match = match(content);
        if(match != null) {
            stateDatabase.matched(System.currentTimeMillis(), match);
        }
    }

    public String match(String content) {
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
