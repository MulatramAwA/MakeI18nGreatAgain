package io.github.MulatramAwA.makei18ngreatagain.features;

import net.minecraft.util.Util;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import static io.github.MulatramAwA.makei18ngreatagain.Makei18ngreatagain.LOGGER;
import static io.github.MulatramAwA.makei18ngreatagain.Makei18ngreatagain.map;

public class Map2JSONFile {
    public Path path;
    public Map2JSONFile(Path path){
        this.path=path;
    }
    public static String antiEscapeChar(String str){
        return str.replace("\b","\\b")
                .replace("\f","\\f")
                .replace("\n","\\n")
                .replace("\r","\\r")
                .replace("\t","\\t")
                .replace("\\","\\\\")
                //.replace("'", "\\'") 无必要 Not necessary
                .replace("\"","\\\"")
                .replace("\0","\\0");
    }
    public static String getString(){
        StringBuilder builder=new StringBuilder();
        builder.append("{\n");
        map.forEach((key, value) -> {
            builder.append("  \"");
            builder.append(antiEscapeChar(key));
            builder.append("\": \"");
            builder.append(antiEscapeChar(value));
            builder.append("\",\n");
        });
        builder.append("}");
        builder.deleteCharAt(builder.lastIndexOf(","));
        return builder.toString();
    }
    public void save() {
        try{
            path.toFile().mkdir();
            File file= path.resolve("Makei18ngreatagain-"+Util.getFormattedCurrentTime()+".json").toFile();
            file.createNewFile();
            FileOutputStream stream=new FileOutputStream(file);
            stream.write(getString().getBytes(StandardCharsets.UTF_8));
            stream.flush();
            stream.close();
        }catch (Exception e){
            LOGGER.error(e.getMessage());
        }
    }
}
