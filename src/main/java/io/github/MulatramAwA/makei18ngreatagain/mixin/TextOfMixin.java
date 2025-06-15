package io.github.MulatramAwA.makei18ngreatagain.mixin;

import io.github.MulatramAwA.makei18ngreatagain.features.TranslationKeyMap;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.MutableText;
import net.minecraft.text.TextContent;
import net.minecraft.util.Language;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import static io.github.MulatramAwA.makei18ngreatagain.Makei18ngreatagain.LOGGER;

@Mixin(MutableText.class)
public class TextOfMixin {
    @ModifyVariable(method = "<init>",at=@At("HEAD"),ordinal = 0)

    private static TextContent of(TextContent value) {
        String str;
        if (value instanceof LiteralTextContent literal) {
            str = literal.string();
        } else {
            return value;
        }
        String translationKey = TranslationKeyMap.getTranslationKey(str);

        if (LOGGER.isDebugEnabled()) {
            String unescapedStr = antiEscapeChar(str);
            String unescapedKey = antiEscapeChar(translationKey);
            LOGGER.debug(String.format("Mixin got[\"%s\":\"%s\"]", unescapedKey, unescapedStr));
        }
        return new LiteralTextContent(Language.getInstance().get(translationKey, str));
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
