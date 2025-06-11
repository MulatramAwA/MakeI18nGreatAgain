package io.github.MulatramAwA.makei18ngreatagain.mixin;

import io.github.MulatramAwA.makei18ngreatagain.features.TranslationKeyMap;
import net.minecraft.text.MutableText;
import net.minecraft.text.PlainTextContent;
import net.minecraft.text.TextContent;
import net.minecraft.util.Language;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import static io.github.MulatramAwA.makei18ngreatagain.Makei18ngreatagain.LOGGER;
import static io.github.MulatramAwA.makei18ngreatagain.Makei18ngreatagain.map;

@Mixin(MutableText.class)
public class TextOfMixin {
    @ModifyVariable(method = "<init>",at=@At("HEAD"),ordinal = 0)
    private static TextContent of(TextContent value){
        if(value.getType()!=PlainTextContent.TYPE&&value.getType()!=PlainTextContent.Literal.TYPE) return value;
        String str;
        if(value.getType()==PlainTextContent.TYPE) str=((PlainTextContent) value).string();
        else str=((PlainTextContent.Literal) value).string();
        map.put(TranslationKeyMap.getTranslationKey(str),str);
        LOGGER.info(String.format("Mixin got[\"%s\":\"%s\"]",antiEscapeChar(TranslationKeyMap.getTranslationKey(antiEscapeChar(str))), antiEscapeChar(str)));
        if(map.size()>50) map.remove(map.entrySet().iterator().next().getKey(),map.entrySet().iterator().next().getValue());
        return PlainTextContent.of(Language.getInstance().get(TranslationKeyMap.getTranslationKey(str),str));
    }
    @Unique
    private static String antiEscapeChar(String str) {
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
}
