package nl.bneijt.matchmonitor;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class MatchHistoryTest {
    class ShowableMatchHistory extends MatchHistory {
        public ShowableMatchHistory(Long now, int historySize) {
            super(now, historySize);
        }

        public String toString() {
            Long mu = intervalHistory.mean();
            return String.format("mean=%d variance=%d lastMatched=%d", mu, intervalHistory.variance(mu), lastMatched);
        }

    }
    @Test
    public void aSingleValueShouldNotBeInsideHalfMean() throws Exception {
        long factor = 100000000l;
        MatchHistory mh = new MatchHistory(0l, 5);
        mh.matchedAgain(100l * factor);
        mh.matchedAgain(200l * factor);
        mh.matchedAgain(300l * factor);
        //Interval is 100, timeout should be around 150
        assertThat(mh.insideHalfMean(400l * factor), is(true));
        assertThat(mh.insideHalfMean(440l * factor), is(true));
        assertThat(mh.insideHalfMean(460l * factor), is(false));
    }
    @Test
    public void normalShouldBeConsideredAlive() {
        ShowableMatchHistory mh = new ShowableMatchHistory(0l, 10);
        for (int i = 1; i < 10; i++) {
            mh.matchedAgain(100l * i);
        }
        System.out.println(mh);
        assertThat(mh.insideQuadrupalVarience(10 * 100l), is(true)); //Exactly on time
        assertThat(mh.insideQuadrupalVarience(11 * 100l), is(false));//One period later
    }
}
