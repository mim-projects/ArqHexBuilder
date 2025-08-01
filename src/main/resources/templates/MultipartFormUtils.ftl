package ${package};

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import java.io.InputStream;

public class MultipartFormUtils {
    public static String GetString(MultipartFormDataInput input, String key) {
        try {
            return input.getFormDataMap().get(key).get(0).getBodyAsString();
        } catch (Exception ignore) {
            return null;
        }
    }

    public static Integer GetInt(MultipartFormDataInput input, String key) {
        try {
            String result = GetString(input, key);
            if (result == null) return null;
            return Integer.parseInt(result);
        } catch (Exception ignore) {
            return null;
        }
    }

    public static InputStream GetInputStream(MultipartFormDataInput input, String key) {
        try {
            InputPart inputParts = input.getFormDataMap().get(key).get(0);
            return inputParts.getBody(InputStream.class, null);
        } catch (Exception ignore) {
            return null;
        }
    }

    public static String GetFileNameForInputStream(MultipartFormDataInput input, String key) {
        try {
            InputPart inputParts = input.getFormDataMap().get(key).get(0);
            for (String part : inputParts.getHeaders().getFirst("Content-Disposition").split(";")) {
                if ((part.trim().startsWith("filename"))) {
                    return part.split("=")[1].trim().replaceAll("\"", "");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}