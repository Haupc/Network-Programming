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
            Protocol.FILE_NAME = Protocol.CMD.substring(5);
            FileOutputStream fos = new FileOutputStream(Protocol.FILE_NAME);
            
            long filesize = in.readLong();
            if (filesize == -1) {
                System.out.println("FILE NOT FOUND");
                fos.close();
                return;
            }
            
            byte[] buffer = new byte[4096];
            int read = 0;
            long totalRead = 0;
            long remaining = filesize;
            System.out.println("Reading file: " + Protocol.FILE_NAME);
            while( (read = in.read(buffer, 0, (int)Math.min(buffer.length, remaining))) > 0 
                    && totalRead < filesize) {
                totalRead += read;
                remaining -= read;
                fos.write(buffer, 0, read);
            }
            System.out.println("done!");
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