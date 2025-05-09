package io.github.MulatramAwA.makei18ngreatagain;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class Makei18ngreatagain implements ModInitializer {
    public static final String MOD_ID = "makei18ngreatagain";
    public static final Logger LOGGER=LoggerFactory.getLogger(MOD_ID);
    public static Map<String,String> TranslatorCache=new HashMap<>();

    @Override
    public void onInitialize() {
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
            @Override
            public Identifier getFabricId() {
                return Identifier.of("makei18ngreatagain");
            }

            @Override
            public void reload(ResourceManager manager) {
                TranslatorCache.clear();
            }
        });
    }
}
