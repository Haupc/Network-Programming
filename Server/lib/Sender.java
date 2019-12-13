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
        String filename = Protocol.FOLDER_NAME + "/" + Protocol.FILE_NAME;
        filename = Paths.get(filename).toAbsolutePath().toString();
        
        try {
            System.out.println(java.time.LocalTime.now());
            System.out.println("Sending file...");
            FileInputStream fis = new FileInputStream(filename);
            byte[] buffer = new byte[4096];
            int n;
            while ( (n = fis.read(buffer)) > 0) {
                out.write(buffer, 0, Math.min(buffer.length, n));
            }
            fis.close();
            System.out.println("Sent Done!");
            System.out.println(java.time.LocalTime.now());
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}