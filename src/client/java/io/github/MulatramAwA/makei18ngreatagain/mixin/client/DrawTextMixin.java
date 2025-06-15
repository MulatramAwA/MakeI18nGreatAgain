package io.github.MulatramAwA.makei18ngreatagain.mixin.client;

import io.github.MulatramAwA.makei18ngreatagain.Makei18ngreatagainClient;
import io.github.MulatramAwA.makei18ngreatagain.features.TranslationKeyMap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.OrderedText;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static io.github.MulatramAwA.makei18ngreatagain.Makei18ngreatagain.LOGGER;
import static io.github.MulatramAwA.makei18ngreatagain.Makei18ngreatagainClient.hoveringDetectKeyBinding;
import static io.github.MulatramAwA.makei18ngreatagain.Makei18ngreatagainClient.map;
import static io.github.MulatramAwA.makei18ngreatagain.features.Map2JSONFile.antiEscapeChar;

@Mixin(DrawContext.class)
public abstract class DrawTextMixin {
    @Shadow public abstract void drawBorder(int x, int y, int width, int height, int color);

    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(method = "drawText(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/OrderedText;IIIZ)I", at = @At("HEAD"))
    public void drawText(TextRenderer textRenderer, OrderedText text, int x, int y, int color, boolean shadow, CallbackInfoReturnable<Integer> cir) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (InputUtil.isKeyPressed(client.getWindow().getHandle(), ((KeyBindingMixin)hoveringDetectKeyBinding).getBoundKey().getCode())) {
            double mouseX = client.mouse.getX();
            double mouseY = client.mouse.getY();
            StringBuilder content = new StringBuilder();
            text.accept((index, style, codePoint) -> {
                content.appendCodePoint(codePoint);
                return true;
            });

            int textWidth = textRenderer.getWidth(text);
            int textHeight = MinecraftClient.getInstance().textRenderer.fontHeight;
            double scale = client.getWindow().getScaleFactor();
            double scaledX = x * scale;
            double scaledY = y * scale;
            double scaledWidth = textWidth * scale;
            double scaledHeight = textHeight * scale;

            boolean isMouseHover = mouseX >= scaledX && mouseX <= scaledX + scaledWidth &&
                    mouseY >= scaledY && mouseY <= scaledY + scaledHeight;
            if (isMouseHover) {
                this.drawBorder(
                        x, y,
                        textWidth, textHeight,
                        0xFF00FF00
                );
                String translationKey = TranslationKeyMap.getTranslationKey(content.toString());
                map.put(translationKey,content.toString());
                if (LOGGER.isDebugEnabled()) {
                    String unescapedStr = antiEscapeChar(content.toString());
                    String unescapedKey = antiEscapeChar(translationKey);
                    LOGGER.info(String.format("Mixin DrawText got[\"%s\":\"%s\"]", unescapedKey, unescapedStr));
                }
            }

        }

    }

}