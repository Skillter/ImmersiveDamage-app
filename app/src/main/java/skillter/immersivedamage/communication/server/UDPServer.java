package skillter.immersivedamage.communication.server;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import skillter.immersivedamage.communication.ReceivedPacketsHandler;
import skillter.immersivedamage.communication.packet.Packet;


public class UDPServer {

    public DatagramSocket socket;
    public boolean close;

    public void run() throws SocketTimeoutException {
        int pacSize;
        byte[] pacData;
        DatagramPacket packet;

        System.out.println("Listening to packets on the port: " + socket.getLocalPort());

        while(!close && !socket.isClosed()){

            // Receive a request and handle it
            pacSize = 5000;
            pacData = new byte[5000];

            packet = new DatagramPacket(pacData, pacSize);
            try {
                //socket.setSoTimeout(30000);
                socket.receive(packet);
            } catch (IOException ex) {
                System.out.println("The I/O Exception has occurred while listening to packets. Note: This is normal if the server is stopping on purpose.");
            }

            if (!close && !socket.isClosed())
                processRequest(packet.getData());

        }

    }

    public void processRequest(byte[] data) {

        Packet packet = null;

        // Use the data to construct a new Packet object
        ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
        ObjectInputStream is;

        try {
            is = new ObjectInputStream(new BufferedInputStream(byteStream));
            packet = (Packet) is.readObject();
            is.close();
        } catch (ClassNotFoundException | IOException ex) {
            ex.printStackTrace();
        }

        ReceivedPacketsHandler.handle(packet);
    }


    public UDPServer(int port) {
        // Initialize UDP socket for receiving data
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException ex) {
            ex.printStackTrace();
        }

        close = false;
    }


}
