package nl.bneijt.matchmonitor;

public class MatchHistory {
    private Long lastMatched = 0l;
    private BoundedLongArray intervalHistory = new BoundedLongArray(5);

    public void matched(Long now) {
        Long newInterval = now - lastMatched;
        intervalHistory.add(newInterval);
        lastMatched = now;
    }
    public boolean insideHalfMean(Long now) {
        return (now - lastMatched) > (1.5 * intervalHistory.mean());
    }
}
