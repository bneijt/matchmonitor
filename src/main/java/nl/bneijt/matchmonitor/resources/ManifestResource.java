package nl.bneijt.matchmonitor.resources;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

@Path("/manifest")
public class ManifestResource {

    Logger logger = LoggerFactory.getLogger(ManifestResource.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> get() throws IOException {
        Enumeration<URL> resources = getClass().getClassLoader().getResources("META-INF/MANIFEST.MF");
        while (resources.hasMoreElements()) {
            try {
                Manifest manifest = new Manifest(resources.nextElement().openStream());
                Attributes attributes = manifest.getMainAttributes();
                if (attributes == null) {
                    continue;
                }
                if ("nl.bneijt".equals(attributes.getValue("Implementation-Vendor-Id"))) {
                    HashMap<String, String> stringAttributes = new HashMap<>();
                    for (Object oKey : attributes.keySet()) {
                        String key = oKey.toString();
                        stringAttributes.put(key, attributes.getValue(key));
                    }
                    return stringAttributes;
                }
            } catch (IOException e) {
                logger.error("IOException reading manifests from jar file: {e}", e);
            }
        }
        return Collections.emptyMap();
    }
}
