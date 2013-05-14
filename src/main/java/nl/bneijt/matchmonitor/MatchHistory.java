package nl.bneijt.matchmonitor;

public class MatchHistory {
    private Long lastMatched;
    private BoundedLongArray intervalHistory = new BoundedLongArray(5);

    public void matched(Long now) {
        Long newInterval = now - lastMatched;
        intervalHistory.add(newInterval);
        lastMatched = now;
    }
    public boolean lastIntervalInsideHalfMean() {
        return intervalHistory.last() > (1.5 * intervalHistory.mean());
    }
}
