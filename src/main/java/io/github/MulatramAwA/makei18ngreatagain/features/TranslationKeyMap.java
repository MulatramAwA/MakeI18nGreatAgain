package io.github.MulatramAwA.makei18ngreatagain.features;

import net.fabricmc.loader.api.FabricLoader;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static io.github.MulatramAwA.makei18ngreatagain.Makei18ngreatagain.LOGGER;

public class TranslationKeyMap {
    private final static Path path= FabricLoader.getInstance().getGameDir().resolve("makei18ngreatagain");
    public static Map<String,String> map=new HashMap<>();
    public static void reload(){
        try{
            path.toFile().mkdir();
            File file=path.resolve("translation_key_map.json").toFile();
            if (file.createNewFile()){
                FileOutputStream stream=new FileOutputStream(file);
                stream.write("{\n    \n}".getBytes(StandardCharsets.UTF_8));
                stream.flush();
                stream.close();
            }
            FileInputStream stream=new FileInputStream(file);
            String str=new String(stream.readAllBytes());
            stream.close();
            JSONObject json=new JSONObject(str);
            json.keySet().forEach(key-> map.put(json.getString(key),key));
        }catch (Exception e){
            LOGGER.error(e.getMessage());
        }
    }
    public static String getTranslationKey(String value){
        reload();
        return map.getOrDefault(value,String.format("makei18ngreatagain.[%s]",value));
    }
}
