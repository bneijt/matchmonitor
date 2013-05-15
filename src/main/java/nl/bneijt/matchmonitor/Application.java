package nl.bneijt.matchmonitor;


import com.google.inject.Guice;
import com.google.inject.Injector;
import com.sun.jersey.spi.container.servlet.ServletContainer;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;


class Application {
    static final Logger logger;

    static {
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "all");
        logger = LoggerFactory.getLogger(Application.class);
    }

    public static void main(String[] args) throws Exception {

        Injector injector = Guice.createInjector(new ApplicationModule());

        Server server = new Server(4000);

        ResourceHandler resource_handler = new ResourceHandler();
        File localResources = new File("src/main/resources/webapp/");
        if (localResources.isDirectory()) {
            logger.warn("Using local resources from: {}", localResources);
            resource_handler.setBaseResource(Resource.newResource(localResources));
        } else {
            resource_handler.setBaseResource(Resource.newClassPathResource("/webapp"));
        }
        resource_handler.setDirectoriesListed(true);
        resource_handler.setWelcomeFiles(new String[]{"index.html"});

        ServletHolder jerseyServlet = new ServletHolder("jersey-servlet", new ServletContainer(new ResourcesApplication(injector)));
        //jerseyServlet.setInitParameter("com.sun.jersey.api.json.POJOMappingFeature", "true");
        jerseyServlet.setInitParameter("com.sun.jersey.config.property.packages", "org.codehaus.jackson.jaxrs");

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        context.addServlet(jerseyServlet, "/api/*");


        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context, new DefaultHandler()});
        server.setHandler(handlers);

        try {
            server.start();
        } catch (Exception e) {
            logger.error("Server could not be started", e);
        }

        MatchSource matchSource = injector.getInstance(MatchSource.class);
        matchSource.start();
        server.join();
    }
}
