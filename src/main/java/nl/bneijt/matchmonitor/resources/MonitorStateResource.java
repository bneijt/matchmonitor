package nl.bneijt.matchmonitor.resources;


import com.google.inject.Injector;
import nl.bneijt.matchmonitor.MatchHistory;
import nl.bneijt.matchmonitor.StateDatabase;
import nl.bneijt.matchmonitor.resources.elements.MonitorState;
import nl.bneijt.matchmonitor.resources.elements.State;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Path("/overview")
public class MonitorStateResource {

    StateDatabase stateDatabase;


    public MonitorStateResource(Injector injector) {
        stateDatabase = injector.getInstance(StateDatabase.class);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public MonitorState get() throws IOException {
        HashMap<String, MatchHistory> states = stateDatabase.copy();
        MonitorState ms = MonitorState.emptyInstance();
        Long now = System.currentTimeMillis();
        for (Map.Entry<String, MatchHistory> entry : states.entrySet()) {
            State s = new State();
            s.match = entry.getKey();
            s.alive = entry.getValue().insideHalfMean(now);
            ms.states.add(s);
        }
        ms.upTime = stateDatabase.upTime();
        return ms;
    }
}
