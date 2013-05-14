package nl.bneijt.matchmonitor;

import com.google.inject.Singleton;

import java.util.HashMap;

@Singleton
public class StateDatabase {
    private  HashMap<String, MatchHistory> historyOfMatches = new HashMap<>();
    private Long lastUpdate = 0l;

    public void justMatched(String value) {
        Long now = System.currentTimeMillis();

        synchronized (historyOfMatches) {
               if(historyOfMatches.containsKey(value)){
                  historyOfMatches.get(value).matched(now);
               }   else {
                   MatchHistory mh = new MatchHistory();
                   mh.matched(now);
                   historyOfMatches.put(value, mh);
               }
           }
        lastUpdate = now;
    }
    public HashMap<String, MatchHistory> copy() {
        synchronized (historyOfMatches) {
            return new HashMap<>(historyOfMatches);
        }
    }

    public Long upTime() {
        return new Long(lastUpdate);

    }
}
