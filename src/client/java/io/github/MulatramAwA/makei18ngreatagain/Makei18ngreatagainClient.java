package io.github.MulatramAwA.makei18ngreatagain;

import io.github.MulatramAwA.makei18ngreatagain.features.Map2JSONFile;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import java.nio.file.Path;

public class Makei18ngreatagainClient implements ClientModInitializer {
    private static KeyBinding keyBinding;
    @Override
    public void onInitializeClient() {
        Map2JSONFile convertor=new Map2JSONFile(FabricLoader.getInstance().getGameDir().resolve("makei18ngreatagain"));
        keyBinding=KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.makei18ngreatagain.mapExport",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_M,
                "category.makei18ngreatagain"
        ));
        final boolean[] oldState = {false};
        ClientTickEvents.END_CLIENT_TICK.register(client->{
            if(!oldState[0] &&keyBinding.isPressed()){
               convertor.save();
            }
            oldState[0] =keyBinding.isPressed();
        });
    }
}
