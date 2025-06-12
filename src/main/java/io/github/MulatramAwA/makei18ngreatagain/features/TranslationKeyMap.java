package io.github.MulatramAwA.makei18ngreatagain.features;

import net.fabricmc.loader.api.FabricLoader;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static io.github.MulatramAwA.makei18ngreatagain.Makei18ngreatagain.LOGGER;

public class TranslationKeyMap {
    private final static Path path= FabricLoader.getInstance().getGameDir().resolve("makei18ngreatagain/mappings");
    public static Map<String,String> map=new HashMap<>();
    public static void load(File file){
        try{
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
    public static void reload() {
        path.toFile().mkdirs();
        try {
            Arrays.stream(Objects.requireNonNull(path.toFile().listFiles((dir, name) -> name.endsWith(".mappings.json")))).toList().forEach(TranslationKeyMap::load);
        }catch (Exception e){}
    }
    public static String getTranslationKey(String value){
        reload();
        return map.getOrDefault(value,String.format("makei18ngreatagain.[%s]",value));
    }
}
