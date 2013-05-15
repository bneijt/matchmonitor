package nl.bneijt.matchmonitor;


import com.google.inject.AbstractModule;
import nl.bneijt.matchmonitor.processing.PacketContentsHandler;
import nl.bneijt.matchmonitor.processing.PacketLetterMatcher;

public class ApplicationModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(StateDatabase.class).toInstance(new StateDatabase());
        bind(PacketContentsHandler.class).to(PacketLetterMatcher.class);
    }
}
