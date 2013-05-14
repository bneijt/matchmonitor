package nl.bneijt.matchmonitor;


import com.google.inject.AbstractModule;

public class ApplicationModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(StateDatabase.class).toInstance(new StateDatabase());
    }
}
