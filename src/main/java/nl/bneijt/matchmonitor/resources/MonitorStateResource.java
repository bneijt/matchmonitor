package nl.bneijt.matchmonitor.resources;


import com.google.inject.Inject;
import nl.bneijt.matchmonitor.MatchHistory;
import nl.bneijt.matchmonitor.StateDatabase;
import nl.bneijt.matchmonitor.resources.elements.MonitorState;
import nl.bneijt.matchmonitor.resources.elements.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Path("/overview")
public class MonitorStateResource {
    @Inject
    StateDatabase stateDatabase;

    Logger logger = LoggerFactory.getLogger(MonitorStateResource.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public MonitorState get() throws IOException {
        logger.warn("Reading state from: {}", stateDatabase);
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
