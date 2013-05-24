package nl.bneijt.matchmonitor;

public class MatchHistory {
    protected Long lastMatched;
    protected BoundedLongArray intervalHistory;

    public MatchHistory(Long now, int historySize) {
        lastMatched = now;
        intervalHistory = new BoundedLongArray(historySize);
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
    public boolean insideQuadrupalVarience(Long now) {
        Long currentInterval = (now - lastMatched);
        Long currentInterval2 = (currentInterval * currentInterval);
        Long mu = intervalHistory.mean();
        Long mu2 = (mu * mu);
        Long var = intervalHistory.variance(mu);
        return currentInterval2 <= (mu2 + 4 * var);
    }
}
