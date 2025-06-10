package io.github.MulatramAwA.makei18ngreatagain.mixin;

import io.github.MulatramAwA.makei18ngreatagain.translator.TranslatorWithCache;
import net.minecraft.text.MutableText;
import net.minecraft.text.PlainTextContent;
import net.minecraft.text.Text;
import net.minecraft.text.TextContent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import static io.github.MulatramAwA.makei18ngreatagain.configs.Makei18ngreatagainConfig.*;

@Mixin(MutableText.class)
public class TextOfMixin {
    @ModifyVariable(method = "<init>",at=@At("HEAD"),ordinal = 0)
    private static TextContent of(TextContent value){
        reload();
        if(enableAutoTranslateWhitelist&&textRenderWhitelist.contains(value.toString())) return PlainTextContent.of(new TranslatorWithCache().getTranslateWithCache(value.toString()));
        if(!enableAutoTranslateWhitelist) return PlainTextContent.of(new TranslatorWithCache().getTranslateWithCache(value.toString()));
        return value;
    }
}
