package io.github.MulatramAwA.makei18ngreatagain.mixin;

import net.minecraft.text.MutableText;
import net.minecraft.text.PlainTextContent;
import net.minecraft.text.TextContent;
import net.minecraft.util.Language;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import static io.github.MulatramAwA.makei18ngreatagain.configs.Makei18ngreatagainConfig.*;

@Mixin(MutableText.class)
public class TextOfMixin {
    @ModifyVariable(method = "<init>",at=@At("HEAD"),ordinal = 0)
    private static TextContent of(TextContent value){
        reload();
        if(value.getType()!=PlainTextContent.TYPE) return value;
        String str=((PlainTextContent) value).string();
        return PlainTextContent.of(Language.getInstance().get(String.format("makei18ngreatagain.\"%s\"",str),str));
    }
}
