package com.dnd.digimax.core.runtime.state;

import java.util.EnumSet;

public class RuntimeTransitionManager {

    public boolean canTransition(
            RuntimeState from,
            RuntimeState to) {

        switch (from) {

            case OFFLINE:
                return EnumSet.of(
                                RuntimeState.BOOTING)
                        .contains(to);

            case BOOTING:
                return EnumSet.of(
                                RuntimeState.READY,
                                RuntimeState.FAILED)
                        .contains(to);

            case READY:
                return EnumSet.of(
                                RuntimeState.PLAYING,
                                RuntimeState.SHUTTING_DOWN,
                                RuntimeState.FAILED)
                        .contains(to);

            case PLAYING:
                return EnumSet.of(
                                RuntimeState.DEGRADED,
                                RuntimeState.FAILED,
                                RuntimeState.READY)
                        .contains(to);

            case DEGRADED:
                return EnumSet.of(
                                RuntimeState.RECOVERING,
                                RuntimeState.FAILED)
                        .contains(to);

            case RECOVERING:
                return EnumSet.of(
                                RuntimeState.READY,
                                RuntimeState.PLAYING,
                                RuntimeState.FAILED)
                        .contains(to);

            case FAILED:
                return EnumSet.of(
                                RuntimeState.RECOVERING,
                                RuntimeState.SHUTTING_DOWN)
                        .contains(to);

            default:
                return false;
        }
    }
}