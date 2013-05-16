package nl.bneijt.matchmonitor;

public class MatchHistory {
    private Long lastMatched;
    private BoundedLongArray intervalHistory;

    public MatchHistory(Long now) {
        lastMatched = now;
        intervalHistory = new BoundedLongArray(5);
    }

    public MatchHistory(MatchHistory other) {
        lastMatched = other.lastMatched;
        intervalHistory = new BoundedLongArray(other.intervalHistory);

    }

    public void matchedAgain(Long now) {
        Long newInterval = now - lastMatched;
        intervalHistory.add(newInterval);
        lastMatched = now;
    }

    public boolean insideHalfMean(Long now) {
        return (now - lastMatched) < (1.5 * intervalHistory.mean());
    }

}
