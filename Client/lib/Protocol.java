package lib;


public class Protocol {
    public final static String LIST_CMD = "ls";
    public final static String LISTLOCAL_CMD = "ls --local";
    public final static String DOWNLOAD_CMD = "getf ";
    public final static String UPLOAD_CMD = "putf ";
    public final static String FOLDER_NAME = "SharedFolder";
    public final static String TEMP_FOLDER = "Temp";
    public final static String END_SIGNAL = "@logout";
    public static int SERVER_PORT = 8899;
    public static String SERVER_IP = "127.0.0.1";
    public static String FILE_NAME = "myfile.zip";
    public static String CMD = "";
    public static long FILE_SIZE = 0;
    public static int N_UPLOADED = 0;
    public static int N_DOWNLOADED = 0;
    public static String LAST_CLIENT = "";
    public static boolean CAN_SEND = false;
    public static synchronized void CountUpload() {
        N_UPLOADED++;
        System.out.printf("total uploaded: %s file(s)\n", N_UPLOADED);
    }
    public static synchronized void CountDownload() {
        N_DOWNLOADED++;
        System.out.printf("total downloaded: %s file(s)\n", N_DOWNLOADED);
    }
}

   