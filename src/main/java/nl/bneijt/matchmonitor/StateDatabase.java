package nl.bneijt.matchmonitor;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class StateDatabase {
    private HashMap<String, MatchHistory> historyOfMatches = new HashMap<>();
    private Long lastUpdate = 0l;

    @Inject
    MatchHistoryFactory matchHistoryFactory;

    public void matched(Long now, String value) {

        synchronized (historyOfMatches) {
            if (historyOfMatches.containsKey(value)) {
                historyOfMatches.get(value).matchedAgain(now);
            } else {
                historyOfMatches.put(value, matchHistoryFactory.newDefaultInstance(now));
            }
        }
        lastUpdate = now;
    }

    public HashMap<String, MatchHistory> copy() {
        synchronized (historyOfMatches) {
            HashMap<String, MatchHistory> hm = new HashMap<>();
            for (Map.Entry<String, MatchHistory> entry : historyOfMatches.entrySet()) {
                hm.put(entry.getKey(), new MatchHistory(entry.getValue()));
            }
            return hm;
        }
    }

    public Long lastUpdate() {
        return new Long(lastUpdate);
    }
}
