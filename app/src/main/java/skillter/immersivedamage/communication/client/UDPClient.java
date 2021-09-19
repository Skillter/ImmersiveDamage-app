package skillter.immersivedamage.communication.client;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import skillter.immersivedamage.communication.packet.Packet;
import skillter.immersivedamage.util.Multithreading;

public class UDPClient {

    private DatagramSocket sendSoc;

    public void sendPacket(String ip, int port, Packet packet) {
        handleSendPacket(ip, port, packet);
    }

    public void sendPacketAsync(String ip, int port, Packet packet) {
        Multithreading.runAsync(() -> handleSendPacket(ip, port, packet));
    }

    private void handleSendPacket(String ip, int port, Packet packet) {
        //Arguments required: server name/IP, recv port, message count

        InetAddress	serverIP = null;

        try {
            serverIP = InetAddress.getByName(ip);
        } catch (UnknownHostException ex) {
            System.out.println("Bad server address in UDPClient, " + ip + " caused an unknown host exception " + ex);
        }

        System.out.println("Sending a packet to " + ip + ":" + port + " " + packet.toString());
        this.doStuff(serverIP, port, packet);
    }



    public UDPClient() {
        // TO-DO: Initialise the UDP socket for sending data
        try {
            sendSoc = new DatagramSocket();
        } catch (SocketException ex) {
            System.out.println("Error creating socket for sending data.");
            ex.printStackTrace();
        }


    }

    private void doStuff(InetAddress serverIP, int port, Packet packet) {
        ByteArrayOutputStream byteStream;
        ObjectOutputStream os;

        byteStream = new ByteArrayOutputStream(5000);
        try {
            os = new ObjectOutputStream(new BufferedOutputStream(byteStream));
            os.flush();
            os.writeObject(packet);
            os.flush();
        } catch (IOException ex) {
            System.out.println("Error serializing object for transmition.");
            ex.printStackTrace();
        }

        //retrieves byte array
        byte[] sendBuf = byteStream.toByteArray();
        send(sendBuf, serverIP, port);



    }

    private void send(byte[] data, InetAddress destAddr, int destPort) {
        DatagramPacket pkt;

        // TO-DO: build the datagram packet and send it to the server
        pkt = new DatagramPacket(data, data.length, destAddr, destPort);
        try {
            sendSoc.send(pkt);
        } catch (IOException ex) {
            System.out.println("Error transmitting packet over network.");
            ex.printStackTrace();
        }
    }
}