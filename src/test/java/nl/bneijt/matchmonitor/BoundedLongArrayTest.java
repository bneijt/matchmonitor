package nl.bneijt.matchmonitor;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class BoundedLongArrayTest {


    @Test
    public void meanOfNewArrayIsZero() throws Exception {
        BoundedLongArray boundedLongArray = new BoundedLongArray(20);
        assertThat(boundedLongArray.mean(), is(0l));
    }
    @Test
    public void meanOfNumberShouldBeMean() throws Exception {
        BoundedLongArray boundedLongArray = new BoundedLongArray(3);
        boundedLongArray.add(4l);
        boundedLongArray.add(4l);
        boundedLongArray.add(4l);
        assertThat(boundedLongArray.mean(), is(4l));

        boundedLongArray.add(1l);
        boundedLongArray.add(1l);
        boundedLongArray.add(1l);
        assertThat(boundedLongArray.mean(), is(1l));

        boundedLongArray.add(1l);
        boundedLongArray.add(2l);
        boundedLongArray.add(3l);
        assertThat(boundedLongArray.mean(), is(2l));
    }

    @Test
    public void meanOfSingleLongIsLastLong() throws Exception {
        BoundedLongArray boundedLongArray = new BoundedLongArray(1);
        boundedLongArray.add(11l);
        boundedLongArray.add(12l);
        boundedLongArray.add(13l);
        assertThat(boundedLongArray.mean(), is(13l));
        assertThat(boundedLongArray.last(), is(13l));
    }

    @Test
    public void lastShouldReturnLastValueRegardlessOfSize() throws Exception {
        BoundedLongArray boundedLongArray = new BoundedLongArray(1);
        boundedLongArray.add(1l);
        assertThat(boundedLongArray.last(), is(1l));

        boundedLongArray = new BoundedLongArray(10);
        boundedLongArray.add(1l);
        assertThat(boundedLongArray.last(), is(1l));

        boundedLongArray = new BoundedLongArray(5);
        for (long i = 0; i < 100; ++i) {
            boundedLongArray.add(i);
            assertThat(boundedLongArray.last(), is(i));
        }
    }
}
