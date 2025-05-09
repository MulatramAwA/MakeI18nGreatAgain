package io.github.MulatramAwA.makei18ngreatagain.mixin.client;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import static io.github.MulatramAwA.makei18ngreatagain.configs.Makei18ngreatagainConfig.reload;
import static io.github.MulatramAwA.makei18ngreatagain.configs.Makei18ngreatagainConfig.textRenderWhitelist;

@Mixin(TextRenderer.class)
public class TextRendererMixin {
    @ModifyVariable(method = "drawInternal(Ljava/lang/String;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/font/TextRenderer$TextLayerType;IIZ)I", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    String drawInternal1(String s){
        reload();
        if(!textRenderWhitelist.contains(s)) return s;
        return Text.translatable(s).getString();
    }
    @ModifyVariable(method = "drawInternal(Lnet/minecraft/text/OrderedText;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/font/TextRenderer$TextLayerType;II)I", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    OrderedText drawInternal2(OrderedText a){
        reload();
        if(!textRenderWhitelist.contains(orderedText2String(a))) return a;
        return Text.translatable(orderedText2String(a)).asOrderedText();
    }
    @Unique
    private static String orderedText2String(OrderedText s) {
        StringBuilder builder = new StringBuilder();
        s.accept((index, style, codePoint) -> {
            builder.appendCodePoint(codePoint);
            return true;
        });
        return builder.toString();
    }
}
