package units;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logs {
    private static boolean show = true;

    public static void writeLog(String text) {
        String date = new SimpleDateFormat("dd.MM.yy HH:mm:ss").format(new Date());
        text = date + " " + text + "\n";

        System.out.print(text);

        if (show) {
            try {
                Files.write(Paths.get("logs.txt"), text.getBytes(), StandardOpenOption.APPEND);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public static void setShow(boolean show) {
        Logs.show = show;
    }
}
