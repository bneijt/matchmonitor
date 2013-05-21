package nl.bneijt.matchmonitor;

public class MatchHistoryFactory {
    private int defaultSize;

    public MatchHistoryFactory(int defaultSize) {
        this.defaultSize = defaultSize;
    }

    public MatchHistory newDefaultInstance(Long now) {
        return new MatchHistory(now, defaultSize);
    }
}
