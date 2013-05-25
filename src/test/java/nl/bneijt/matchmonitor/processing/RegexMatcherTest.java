package nl.bneijt.matchmonitor.processing;

import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class RegexMatcherTest {
    @Test
    public void matchShouldFindLargestIP() {
        RegexMatcher rm = new RegexMatcher(Arrays.asList("(ABC)D"));
        assertThat(rm.match("ABCD"), is("ABC"));
        assertThat(rm.match("IN=eth0 OUT= MAC=bc:00 SRC=ABCDE DST=192.168.168.215 "), is("ABC"));
    }
    @Test
    public void shouldLoadAllRegularExpressionsFromConfigurationFile() throws IOException {
        RegexMatcher.fromFile(getClass().getResource("/configuration.yaml").getPath().toString());
    }
}
