package org.deftserver.web;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;

import org.deftserver.io.DefaultChannelContext;
import org.deftserver.io.IOAcceptLoop;
import org.deftserver.io.IOHandler;
import org.deftserver.web.http.HttpProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultiThreadedHttpServer {

    private final Logger logger = LoggerFactory
            .getLogger(MultiThreadedHttpServer.class);

    private static final int MIN_PORT_NUMBER = 1;
    private static final int MAX_PORT_NUMBER = 65535;

    private ServerSocketChannel serverChannel;

    private final IOHandler protocol;

    private final IOAcceptLoop loop;

    public MultiThreadedHttpServer(Application application) {
        protocol = new HttpProtocol(application);
        try {
            serverChannel = ServerSocketChannel.open();
            serverChannel.configureBlocking(false);

        } catch (IOException e) {
            logger.error("Error creating ServerSocketChannel: {}", e);
        }
        loop = new IOAcceptLoop(2);
    }

    /**
     * @return this for chaining purposes
     */
    public void listen(int port) {
        if (port <= MIN_PORT_NUMBER || port > MAX_PORT_NUMBER) {
            throw new IllegalArgumentException(
                    "Invalid port number. Valid range: [" + MIN_PORT_NUMBER
                            + ", " + MAX_PORT_NUMBER + ")");
        }
        InetSocketAddress endpoint = new InetSocketAddress(port); // use "any"
                                                                  // address
        try {
            serverChannel.socket().bind(endpoint);
        } catch (IOException e) {
            logger.error("Could not bind socket: {}", e);
        }

        loop.registerAcceptChannel(serverChannel, new DefaultChannelContext(
                protocol));
    }

    public void start() {
        loop.start();
    }

}
