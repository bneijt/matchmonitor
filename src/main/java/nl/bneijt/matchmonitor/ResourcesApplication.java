package nl.bneijt.matchmonitor;

import nl.bneijt.matchmonitor.resources.MonitorStateResource;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class ResourcesApplication extends Application {


    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> s = new HashSet<>();
        s.add(MonitorStateResource.class);
        return s;
    }

}
