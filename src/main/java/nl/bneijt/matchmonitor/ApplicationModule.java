package nl.bneijt.matchmonitor;


import com.google.inject.AbstractModule;
import net.sourceforge.argparse4j.inf.Namespace;
import nl.bneijt.matchmonitor.processing.IpAddressMatcher;
import nl.bneijt.matchmonitor.processing.PacketContentsHandler;
import nl.bneijt.matchmonitor.processing.RegexMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ApplicationModule extends AbstractModule {
    private Namespace arguments;
    Logger logger = LoggerFactory.getLogger(ApplicationModule.class);

    public ApplicationModule(Namespace arguments) {
        this.arguments = arguments;
    }

    @Override
    protected void configure() {
        bind(StateDatabase.class).toInstance(new StateDatabase());
        try {
            if (arguments.getString("conf") != null) {
                bind(PacketContentsHandler.class).toInstance(RegexMatcher.fromFile(arguments.getString("conf")));
            } else {
                bind(PacketContentsHandler.class).toInstance(new IpAddressMatcher());
            }
        } catch (IOException e) {
            logger.error("Failed to load configuration file: {}", e);
            throw new RuntimeException("Configuration file loading failed");
        }
        bind(MatchHistoryFactory.class).toInstance(new MatchHistoryFactory(arguments.getInt("historySize")));
    }
}
