package nl.bneijt.matchmonitor;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class MatchSource extends Thread {
    @Inject
    StateDatabase stateDatabase;

    Random random = new Random();
    Logger logger = LoggerFactory.getLogger(Application.class);

    @Override
    public void run() {
        if (stateDatabase == null) {
            throw new IllegalStateException("Should have an injected stateDatabase, use Guice");
        }
        for (int i = 0; i < 100; ++i) {
            Long now = System.currentTimeMillis();
            stateDatabase.matched(now, "A");
            randomSleep();
        }
    }

    private void randomSleep() {
        int duration = random.nextInt(5000);
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            logger.warn("Interrupted during sleep");
        }

    }

}
