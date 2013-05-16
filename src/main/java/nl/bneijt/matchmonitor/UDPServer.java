package nl.bneijt.matchmonitor;

import com.google.common.base.Function;
import nl.bneijt.matchmonitor.processing.PacketContentsHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPServer implements Runnable {
    static Logger logger = LoggerFactory.getLogger(UDPServer.class);

    private int port;
    PacketContentsHandler handler;

    UDPServer(int port, PacketContentsHandler handler) {
        this.port = port;
        this.handler = handler;
    }

    @Override
    public void run() {
        try {
            logger.info("Starting UDP server on port {}", port);
            DatagramSocket serverSocket = new DatagramSocket(port);

            byte[] receiveBuffer = new byte[1024];
            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                serverSocket.receive(receivePacket);
                String packet = new String(receivePacket.getData(), 0, receivePacket.getLength(), "UTF-8");
                if (handler != null) {
                    handler.apply(packet);
                } else {
                    logger.error("No handler set, and received {} bytes", receivePacket.getLength());
                }
            }
        } catch (SocketException e) {
            logger.error("Unable to create socket: {}", e);
        } catch (UnsupportedEncodingException e) {
            logger.error("Unsupported encoding: {}", e);
        } catch (IOException e) {
            logger.error("IO error: {}", e);
        }

    }

    public static void main(String args[]) throws Exception {
        int port = 8081;
        logger.info("Starting UDP server on port {}", port);
        logger.info("Consider sending packets using: nc -u localhost {}", port);
        UDPServer udpServer = new UDPServer(port, new PacketContentsHandler() {
            Logger functionLogger = LoggerFactory.getLogger(Function.class);

            public void apply(String s) {
                functionLogger.info("Received: {}", s);
            }
        });
        udpServer.run();
    }


}
