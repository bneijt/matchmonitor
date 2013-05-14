package nl.bneijt.matchmonitor.resources;


import nl.bneijt.matchmonitor.resources.elements.MonitorState;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Path("/overview")
public class MonitorStateResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public MonitorState get() throws IOException {
        return new MonitorState();
    }
}
