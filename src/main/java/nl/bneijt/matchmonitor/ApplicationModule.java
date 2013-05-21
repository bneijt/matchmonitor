package nl.bneijt.matchmonitor;


import com.google.inject.AbstractModule;
import net.sourceforge.argparse4j.inf.Namespace;
import nl.bneijt.matchmonitor.processing.PacketContentsHandler;
import nl.bneijt.matchmonitor.processing.RegexMatcher;

public class ApplicationModule extends AbstractModule {
    private Namespace arguments;

    public ApplicationModule(Namespace arguments) {
        this.arguments = arguments;
    }

    @Override
    protected void configure() {
        bind(StateDatabase.class).toInstance(new StateDatabase());
        bind(PacketContentsHandler.class).to(RegexMatcher.class);
        bind(MatchHistoryFactory.class).toInstance(new MatchHistoryFactory(arguments.getInt("historySize")));
    }
}
