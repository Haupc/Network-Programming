package Run;
import java.net.*;
import java.io.*;
import lib.*;

public class client {
    public static void main(final String[] args) {

        new SubServerService().start();
        new ClientService(args).start();
    }
}

/**
 * clientService
 */
class ClientService extends Thread {
    Socket sock = null;
    DataInputStream in = null;
    DataOutputStream out = null;
    public ClientService (String[] args) {
        init(args);
    }

    public void run() {
        
        
        try {
            sock = new Socket(Protocol.SERVER_IP, Protocol.SERVER_PORT);
        } catch (Exception e) {
            System.out.println("error on connect to server");
            return;
        }
        try {
            in = new DataInputStream(
                    new BufferedInputStream(sock.getInputStream()));

            out = new DataOutputStream(sock.getOutputStream());
            
        } catch (Exception e) {
            System.out.println("error on init in/out stream");
            return;
        }

        Greeting();
        if (!Protocol.SERVER_IP.equals("true")) {
            System.out.println("connecting to new server: " + Protocol.SERVER_IP);
            run();
            return;
        }
        Receiver.GetFile(in);
    }
    void init(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java Run.client [Server Ip] [Server Port]");
            System.exit(1);
        }
        Protocol.SERVER_IP = new String(args[0]);
        Protocol.SERVER_PORT = Integer.parseInt(new String(args[1]));

    }
    void Greeting() {
        try {
        Protocol.SERVER_IP = in.readUTF();
        Protocol.FILE_NAME = in.readUTF();
        Protocol.FILE_SIZE = in.readLong();
        } catch (Exception e) {
            System.out.println("error on greeting");
        }
        
    }
}
/**
 * subServerService
 */
class SubServerService extends Thread {

    ServerSocket servsock = null;
    Socket sock = null;
    public void run() {
        try {
            servsock = new ServerSocket(Protocol.SERVER_PORT);
            System.out.println("Server started at port: " + Protocol.SERVER_PORT);
            System.out.println("Waiting for client");
        } catch (IOException e) {
            System.out.println("init port error");
        }
        while (true) {
            try {
                sock = servsock.accept();
            } catch (Exception e) {
                System.out.println("error on accept");
            }
            new ClientSession(sock).start();
        }
    }
        
}