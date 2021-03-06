package nl.bneijt.matchmonitor;
/*
    Matchmonitor - monitor matching frequency of UDP packets
    Copyright (C) 2013  A. Bram Neijt

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.sun.jersey.spi.container.servlet.ServletContainer;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import nl.bneijt.matchmonitor.processing.PacketContentsHandler;
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
import java.io.IOException;


class Application {
    static final Logger logger;

    static {
        //System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "all");
        System.setProperty("java.util.logging.SimpleFormatter.format","%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS %4$s %5$s%6$s%n");
        logger = LoggerFactory.getLogger(Application.class);
    }

    public static void main(String[] args) throws Exception {
        ArgumentParser parser = ArgumentParsers.newArgumentParser("matchmonitor")
                .defaultHelp(true)
                .description("Light up regularly matching elements on a web GUI");
        parser.addArgument("--httpPort")
                .type(Integer.class)
                .setDefault(8080)
                .help("HTTP web server port (GUI)");
        parser.addArgument("--udpPort")
                .type(Integer.class)
                .setDefault(8081)
                .help("UDP packet receiving port");
        parser.addArgument("--historySize")
                .type(Integer.class)
                .setDefault(30)
                .help("Number of match intervals to factor into the period calculation");
        parser.addArgument("--conf")
                .type(String.class)
                .help("Yaml configuration file with regexes list containing regular expressions to match. Default matching is ip addresses.");

        Namespace arguments = null;
        try {
            arguments = parser.parseArgs(args);
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }


        Injector injector = Guice.createInjector(new ApplicationModule(arguments));

        Server jettyServer = createJettyServer(injector, arguments.getInt("httpPort"));


        Thread udpServerThread = new Thread(new UDPServer(Integer.valueOf(arguments.getInt("udpPort")), injector.getInstance(PacketContentsHandler.class)));
        udpServerThread.start();

        try {
            jettyServer.start();
        } catch (Exception e) {
            logger.error("Server could not be started", e);
        }

        jettyServer.join();
        udpServerThread.interrupt();
    }

    private static Server createJettyServer(Injector injector, int httpPort) throws IOException {
        Server server = new Server(Integer.valueOf(httpPort));

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
        jerseyServlet.setInitParameter("com.sun.jersey.config.property.packages", "org.codehaus.jackson.jaxrs");

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        context.addServlet(jerseyServlet, "/api/*");


        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context, new DefaultHandler()});
        server.setHandler(handlers);
        return server;
    }
}
