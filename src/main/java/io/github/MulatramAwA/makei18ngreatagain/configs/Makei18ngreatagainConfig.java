package io.github.MulatramAwA.makei18ngreatagain.configs;

import net.fabricmc.loader.api.FabricLoader;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;

import static io.github.MulatramAwA.makei18ngreatagain.Makei18ngreatagain.LOGGER;

public class Makei18ngreatagainConfig {
    public static boolean enableAutoTranslate;
    public static boolean enableAutoTranslateWhitelist;
    public static HashSet<String> autoTranslateWhitelist=new HashSet<String>();
    public static HashSet<String> textRenderWhitelist =new HashSet<String>();
    public static void reload(){
        try {
            Path path = FabricLoader.getInstance().getConfigDir().resolve("miga.json");
            if (path.toFile().createNewFile()) {
                Files.writeString(path, "{\n    \"enableAutoTranslate\": true,\n    \"enableAutoTranslateWhitelist\": false,\n    \"autoTranslateWhitelist\": [],\n    \"textRenderWhitelist\": []\n}", StandardCharsets.UTF_8);
            }
            JSONObject jsonObject = new JSONObject(Files.readString(path, StandardCharsets.UTF_8));
            enableAutoTranslate = jsonObject.getBoolean("enableAutoTranslate");
            enableAutoTranslateWhitelist = jsonObject.getBoolean("enableAutoTranslateWhitelist");
            List<String> list = (List<String>) (List) jsonObject.getJSONArray("autoTranslateWhitelist").toList();
            autoTranslateWhitelist = new HashSet<>(list);
            list = (List<String>) (List) jsonObject.getJSONArray("textRenderWhitelist").toList();
            textRenderWhitelist = new HashSet<>(list);
        }catch (Exception e){
            LOGGER.info("Failed while reloading config: {}", (Object) e.getStackTrace());
        }
    }
}
