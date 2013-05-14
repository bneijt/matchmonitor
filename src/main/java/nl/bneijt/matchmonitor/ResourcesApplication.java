package nl.bneijt.matchmonitor;

import com.google.inject.Injector;
import nl.bneijt.matchmonitor.resources.MonitorStateResource;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class ResourcesApplication extends Application {


    private Injector injector;

    public ResourcesApplication(Injector injector) {


        this.injector = injector;
    }

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> s = new HashSet<>();
        //s.add(MonitorStateResource.class);
        return s;
    }
    @Override
    public Set<Object> getSingletons() {
        HashSet<Object> singletons = new HashSet<Object>();
        singletons.add(new MonitorStateResource(injector));
          return singletons;
    }

}
