package com.dnd.digimax.core.runtime.health;

import com.dnd.digimax.core.runtime.inspector.RuntimeInspector;

public class RuntimeRiskAnalyzer {
    private final RuntimeInspector inspector;
    public RuntimeRiskAnalyzer(RuntimeInspector inspector) {
        this.inspector = inspector;
    }

    public int calculateRiskScore() {
        if (inspector == null) {
            return 0;
        }
        return inspector.getRiskScore();
    }
}