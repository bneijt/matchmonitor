package nl.bneijt.matchmonitor;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class MatchHistoryTest {
    @Test
    public void aSingleValueShouldNotBeInsideHalfMean() throws Exception {
        long factor = 100000000l;
        MatchHistory mh = new MatchHistory(0l);
        mh.matchedAgain(100l * factor);
        mh.matchedAgain(200l * factor);
        mh.matchedAgain(300l * factor);
        //Interval is 100, timeout should be around 150
        assertThat(mh.insideHalfMean(400l * factor), is(true));
        assertThat(mh.insideHalfMean(440l * factor), is(true));
        assertThat(mh.insideHalfMean(460l * factor), is(false));
    }
}
