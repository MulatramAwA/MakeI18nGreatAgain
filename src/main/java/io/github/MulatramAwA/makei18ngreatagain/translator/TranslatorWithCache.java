package io.github.MulatramAwA.makei18ngreatagain.translator;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import static io.github.MulatramAwA.makei18ngreatagain.Makei18ngreatagain.TranslatorCache;

public class TranslatorWithCache extends BingTranslator {
    public MutableText getTranslateWithCache(String text) {
        if(!TranslatorCache.containsKey(text)){
            TranslatorCache.put(text,getTranslate(text));
        }
        if(TranslatorCache.get(text)==null) return (MutableText) Text.of(text);
        return (MutableText) Text.of(TranslatorCache.get(text));
    }
}
