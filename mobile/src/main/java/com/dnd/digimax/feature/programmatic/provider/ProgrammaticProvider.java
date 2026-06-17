package com.dnd.digimax.feature.programmatic.provider;

public interface ProgrammaticProvider {

    default String getProviderName() {
        return "";
    }
}
