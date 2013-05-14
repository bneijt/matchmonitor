package nl.bneijt.matchmonitor.resources.elements;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class MonitorState {
    public List<State> states;
    public String upTime;
}
