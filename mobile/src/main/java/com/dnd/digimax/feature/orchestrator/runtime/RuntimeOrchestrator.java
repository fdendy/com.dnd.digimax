package com.dnd.digimax.feature.orchestrator.runtime;

import com.dnd.digimax.core.runtime.state.RuntimePhase;
import com.dnd.digimax.core.runtime.state.RuntimeStateMachine;

public class RuntimeOrchestrator {

    private final RuntimeStateMachine stateMachine;

    public RuntimeOrchestrator(
            RuntimeStateMachine stateMachine) {

        this.stateMachine =
                stateMachine;
    }

    public void initialize() {

        stateMachine.moveTo(
                RuntimePhase.INITIALIZING
        );
    }

    public void ready() {

        stateMachine.moveTo(
                RuntimePhase.READY
        );
    }

    public void running() {

        stateMachine.moveTo(
                RuntimePhase.RUNNING
        );
    }

    public void recovery() {

        stateMachine.moveTo(
                RuntimePhase.RECOVERY
        );
    }

    public void stop() {

        stateMachine.moveTo(
                RuntimePhase.STOPPED
        );
    }
}