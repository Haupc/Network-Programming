package Run;

import java.io.*;
import java.net.*;
import java.nio.file.Paths;
import java.util.Scanner;

import lib.*;


public class server {

    public static void main(String[] args) throws IOException {

        init(args);

        ServerSocket servsock = null;
        Socket sock = null;
        try {
            servsock = new ServerSocket(Protocol.SERVER_PORT);
            System.out.println("Server started at port: " + Protocol.SERVER_PORT);
            inputFileName();
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

    public static void init(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java Run.server [Server Port Number]");
            System.exit(1);
        }
        Protocol.SERVER_PORT = Integer.parseInt(new String(args[0]));
    }

    static void inputFileName() {
        while (true) {
            
            System.out.println("\t--- List File ---");
            Sender.ListLocalFile();
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter filename: ");
            Protocol.FILE_NAME = sc.nextLine();
            
            String filename = Protocol.FOLDER_NAME + "/" + Protocol.FILE_NAME;
            filename = Paths.get(filename).toAbsolutePath().toString();
            
            File myfile = new File(filename);
    
            if (!myfile.exists()) {
                System.out.println("FILE NOT FOUND");
                continue;
            }
            Protocol.FILE_SIZE = myfile.length();
            sc.close();
            return;
        }
    }

}