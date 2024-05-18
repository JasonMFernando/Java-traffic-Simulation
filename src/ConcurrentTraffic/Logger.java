package ConcurrentTraffic;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files; // Add this import
import java.nio.file.Paths; // Add this import
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    //private static final String LOG_DIRECTORY = "logs/";
    private static final String LOG_FILE_EXTENSION = ".txt";

    public static void log(String message) {
        String logFileName = generateLogFileName();
        try (PrintWriter writer = new PrintWriter(new FileWriter(logFileName, true))) {
            writer.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void clearLogFile() {
        String logFileName = generateLogFileName();
        File logFile = new File(logFileName);
        try {
            Files.write(Paths.get(logFileName), new byte[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String generateLogFileName() {
        return "log_" + LOG_FILE_EXTENSION;
    }
}
