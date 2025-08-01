package ${package};

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils {
    public static void WriteToFile(InputStream inputStream, String path) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(path)) {
            int read;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                fileOutputStream.write(bytes, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String GetExtension(String filename) {
        if (filename == null) return "";
        int indexPoint = filename.lastIndexOf(".");
        if (indexPoint >= 0 && indexPoint < filename.length() - 1) {
            return filename.substring(indexPoint + 1);
        }
        return "";
    }
}