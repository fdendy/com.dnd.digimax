package com.dnd.digimax.feature.programmatic.resolver;

import com.dnd.digimax.feature.programmatic.model.ProgrammaticSlot;
import com.dnd.digimax.feature.programmatic.provider.*;

import java.util.List;

public class ProviderResolver {

    private final List<ProgrammaticProvider> providers;

    public ProviderResolver(List<ProgrammaticProvider> providers) {
        this.providers = providers;
    }

    public ProgrammaticProvider resolve(ProgrammaticSlot slot) {

        // SIMPLE STRATEGY:
        // AMG first, fallback internal

        for (ProgrammaticProvider p : providers) {

            if ("AMG".equals(p.getProviderName())) {
                return p;
            }
        }

        return providers.isEmpty() ? null : providers.get(0);
    }
}