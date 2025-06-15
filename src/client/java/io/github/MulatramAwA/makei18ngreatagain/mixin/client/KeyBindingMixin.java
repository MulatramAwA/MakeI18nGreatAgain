package io.github.MulatramAwA.makei18ngreatagain.mixin.client;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(KeyBinding.class)
public interface KeyBindingMixin {
    @Accessor InputUtil.Key getBoundKey();
}
