package nl.bneijt.matchmonitor.processing;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class RegexMatcherTest {
    @Test
    public void matchShouldFindLargestIP() {
        RegexMatcher rm = new RegexMatcher();
        assertThat(rm.match("13.1.1.1"), is("13.1.1.1"));
        assertThat(rm.match("IN=eth0 OUT= MAC=bc:00 SRC=10.30.1.26 DST=192.168.168.215 "), is("10.30.1.26"));
    }
}
