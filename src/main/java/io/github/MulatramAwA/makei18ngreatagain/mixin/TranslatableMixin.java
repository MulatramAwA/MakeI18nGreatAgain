package io.github.MulatramAwA.makei18ngreatagain.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.MulatramAwA.makei18ngreatagain.translator.TranslatorWithCache;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.Language;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@Mixin(TranslatableTextContent.class)
public class TranslatableMixin {
    @Shadow private @Nullable Language languageCache;

    @Shadow @Final private String key;

    @WrapOperation(method = "updateTranslations", at = @At(target="Lnet/minecraft/text/TranslatableTextContent;forEachPart(Ljava/lang/String;Ljava/util/function/Consumer;)V",value = "INVOKE"))
    private void updateTranslations(TranslatableTextContent instance, String translation, Consumer<StringVisitable> partsConsumer, Operation<Void> original){
        if (languageCache != null && !languageCache.hasTranslation(key)){
            original.call(instance, new TranslatorWithCache().getTranslateWithCache(translation).toString(), partsConsumer);
        }
    }
}
