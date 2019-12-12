package Run;

import java.io.DataOutputStream;
import java.net.Socket;

import lib.Protocol;
import lib.Sender;

/**
 * Innerserver
 */
class ClientSession extends Thread {

    protected Socket sock;
    public ClientSession(Socket clientSocket) {
        this.sock = clientSocket;
        System.out.println("client connected: " + sock.getInetAddress().getHostAddress());
    }
    public void run() {
        DataOutputStream out = null;
        try {
            out = new DataOutputStream(sock.getOutputStream());
        } catch (Exception e) {
            System.out.println("error on init in/out stream");
        }
        if (Greeting(out)) {
            Sender.SendFile(out);
            System.out.println("file sent to " + sock.getInetAddress().getHostAddress());
        } else {
            System.out.println("redirected");
        }
        
    }

    synchronized boolean Greeting(DataOutputStream out) {
        boolean isFirst = true;
        if (Protocol.LAST_CLIENT.equals("")) {
            try {
                out.writeUTF("true");
                isFirst = true;
            } catch (Exception e) {
                System.out.println("error on greeting");
            }
        }
        else {
            try {
                out.writeUTF(Protocol.LAST_CLIENT);
                isFirst = false;
            } catch (Exception e) {
                System.out.println("error on greeting");
            }
            
        }
        try {
            out.writeUTF(Protocol.FILE_NAME);
            out.writeLong(Protocol.FILE_SIZE);
        } catch (Exception e) {
            System.out.println("error sending file info");
        }
        Protocol.LAST_CLIENT = sock.getInetAddress().getHostAddress();
        return isFirst;
    }
}