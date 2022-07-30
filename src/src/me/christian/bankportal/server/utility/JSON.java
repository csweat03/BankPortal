package me.christian.bankportal.server.utility;

import org.json.JSONObject;

import java.nio.file.Paths;

/**
 *
 * @author Christian Sweat
 * @since 9:05 PM 7/29/22
 */
public class JSON {

    public static class References {

        private static final String fileName = Paths.get("resources", "References.json").toString();

        public static JSONObject Obj(String key) {
            return JSON.Obj(fileName, key);
        }

        public static void Put(String string, Object object) {
            JSON.Put(fileName, string, object);
        }

    }

    public static JSONObject Obj(String fileName, String key) {
        return new JSONObject(Files.readContent(fileName)).getJSONObject(key);
    }

    public static void Put(String fileName, String string, Object object) {
        new JSONObject(Files.readContent(fileName)).put(string, object);
    }
}
