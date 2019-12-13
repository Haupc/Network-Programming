package lib;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Receiver
 */
public class Receiver {

    public static void GetFile(DataInputStream in) {
        try {
            // Protocol.FILE_NAME = Protocol.CMD.substring(5);
            String fileName = Protocol.FOLDER_NAME + "/" + Protocol.FILE_NAME;
            FileOutputStream fos = new FileOutputStream(fileName);
            Protocol.CAN_SEND = true;
            
            byte[] buffer = new byte[4096];
            int read = 0;
            long totalRead = 0;
            long remaining = Protocol.FILE_SIZE;
            System.out.println(java.time.LocalTime.now());
            System.out.println("Reading file: " + Protocol.FILE_NAME);
            while( (read = in.read(buffer, 0, (int)Math.min(buffer.length, remaining))) > 0 
                    && totalRead < Protocol.FILE_SIZE) {
                totalRead += read;
                remaining -= read;
                fos.write(buffer, 0, read);
            }
            System.out.println(java.time.LocalTime.now());
            System.out.println("receiveed done!");
            fos.close();
            return;
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void ListFile(DataInputStream in) {
        try {
            System.out.println(in.readUTF());
        } catch (final IOException i) {
            System.out.println(i);
        }
    }
}