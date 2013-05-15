package nl.bneijt.matchmonitor.processing;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PacketLetterMatcherTest {

    @Test
    public void lettersShouldContainAlphabet() {
        assertThat(PacketLetterMatcher.letters.size(), is(26));
    }
}
