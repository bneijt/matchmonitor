package nl.bneijt.matchmonitor.resources.elements;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class MonitorState {
    public List<State> states;
    public Long lastUpdate;

    public static MonitorState emptyInstance() {
        MonitorState ms = new MonitorState();
        ms.states = new ArrayList<>();
        return ms;
    }
}
