package skillter.immersivedamage.communication;

import java.net.SocketTimeoutException;

import skillter.immersivedamage.communication.client.UDPClient;
import skillter.immersivedamage.communication.server.UDPServer;
import skillter.immersivedamage.util.Multithreading;

public class UDPManager {
    public static UDPServer udpServer = null;
    public static UDPClient udpClient = null;

    // This runs the UDP server in sync
    public static void runUDPServer(int port) {
        startServer(port);
    }

    // This runs the UDP server in async
    public static void runUDPServerAsync(int port) {
        Multithreading.runAsync(() -> {
            startServer(port);
        });
    }

    public static void stopUDPServer() {
        System.out.println("Stopping the server...");
        if (udpServer != null) udpServer.close = true;
        if (udpServer != null && udpServer.socket != null) udpServer.socket.close();
        udpServer = null;
    }




    private static void startServer(int port) {
        System.out.println("Starting the server...");
        udpServer = new UDPServer(port);
        try {
            udpServer.run();
        } catch (SocketTimeoutException ex) {
            System.out.println(ex);
        }
    }

}
