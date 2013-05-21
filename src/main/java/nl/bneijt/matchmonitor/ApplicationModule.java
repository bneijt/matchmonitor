package nl.bneijt.matchmonitor;


import com.google.inject.AbstractModule;
import nl.bneijt.matchmonitor.processing.PacketContentsHandler;
import nl.bneijt.matchmonitor.processing.RegexMatcher;

public class ApplicationModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(StateDatabase.class).toInstance(new StateDatabase());
        bind(PacketContentsHandler.class).to(RegexMatcher.class);
        bind(MatchHistoryFactory.class).toInstance(new MatchHistoryFactory(5));
    }
}
