package lib;
import java.io.*;
import java.nio.file.Paths;

public class Sender {
    public static String ListLocalFile() {
        File[] files = new File(Protocol.FOLDER_NAME).listFiles();
        String filesInFolder = "";
        for (File file : files) {
            if (file.isFile()) {
                filesInFolder += ( file.getName() + "\n");
            }
        }
        System.out.println(filesInFolder);
        return filesInFolder;
    }
    public static void ListFile(DataOutputStream out) {
        String filesInFolder = ListLocalFile();
        try {
            out.writeUTF(filesInFolder);
        } catch (IOException i) 
        { 
            System.out.println(i); 
        } 
    }

    public static void SendFile(DataOutputStream out) {
        // System.out.println(Protocol.CMD);
        // Protocol.FILE_NAME = Protocol.FOLDER_NAME + "/" + Protocol.CMD.substring(5);
        while (!Protocol.CAN_SEND) {}
        String filename = Protocol.FOLDER_NAME + "/" + Protocol.FILE_NAME;
        filename = Paths.get(filename).toAbsolutePath().toString();
        
        try {
            System.out.println("Sending file...");
            FileInputStream fis = new FileInputStream(filename);
            byte[] buffer = new byte[4096];
            int fetch = 0;
            long totalSent = 0;
            long remaining = Protocol.FILE_SIZE;
            while ( totalSent < Protocol.FILE_SIZE) {
                if (fis.available() > 0 && (fetch = fis.read(buffer)) > 0){
                    out.write(buffer, 0, (int) Math.min(fetch, remaining));
                    totalSent += fetch;
                    remaining -= fetch;
                }
            }
            fis.close();
            System.out.println("Done!");
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}