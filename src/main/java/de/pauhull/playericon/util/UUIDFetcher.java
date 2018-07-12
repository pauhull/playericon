package de.pauhull.playericon.util;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.UUID;

public class UUIDFetcher {

    public static UUID fetchUUID(String playerName) throws Exception {
        URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + playerName);
        InputStream inputStream = url.openStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }

        inputStream.close();
        String result = stringBuilder.toString();

        JSONObject object = new JSONObject(result);

        return UUID.fromString(object.getString("id").
                replaceFirst("(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5"));
    }

}
