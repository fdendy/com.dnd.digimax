package com.dnd.digimax.feature.playback.pop.provider;

import java.util.HashMap;
import java.util.Map;

public class PopProviderRegistry {

    private final Map<String, PopProvider>
            providers =
            new HashMap<>();

    public PopProviderRegistry() {

        register(
                new InternalCmsPopProvider());

        register(
                new AmgPopProvider());

        register(
                new VistarPopProvider());

        register(
                new BroadsignPopProvider());
    }

    public void register(
            PopProvider provider) {

        providers.put(
                provider.getProviderName(),
                provider);
    }

    public PopProvider get(
            String provider) {

        return providers.get(
                provider);
    }
}