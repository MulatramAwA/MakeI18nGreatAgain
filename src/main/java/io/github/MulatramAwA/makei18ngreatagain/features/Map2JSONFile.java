package io.github.MulatramAwA.makei18ngreatagain.features;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import static io.github.MulatramAwA.makei18ngreatagain.Makei18ngreatagain.LOGGER;
import static io.github.MulatramAwA.makei18ngreatagain.Makei18ngreatagain.map;

public class Map2JSONFile {
    public Path path;
    public Map2JSONFile(Path path){
        this.path=path;
    }
    public String getString(){
        StringBuilder builder=new StringBuilder();
        builder.append("{\n");
        map.forEach((key, value) -> {
            builder.append("  \"");
            builder.append(key.replace("\"","\\\""));
            builder.append("\":\"");
            builder.append(value.replace("\"","\\\""));
            builder.append("\"\n");
        });
        return builder.toString();
    }
    public void save() {
        try{
            path.toFile().createNewFile();
            FileOutputStream stream=new FileOutputStream(path.toFile());
            stream.write(getString().getBytes(StandardCharsets.UTF_8));
            stream.flush();
            stream.close();
        }catch (Exception e){
            LOGGER.error(e.getMessage());
        }
    }
}
