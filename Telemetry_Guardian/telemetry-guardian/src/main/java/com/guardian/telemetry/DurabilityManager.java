package com.guardian.telemetry;

import java.util.HashMap;
import java.util.Map;

public class DurabilityManager {
    private final Map<Integer, Integer> writeCycles = new HashMap<>();
    private final int maxWriteThreshold;

    public DurabilityManager(int maxWriteThreshold) {
        this.maxWriteThreshold = maxWriteThreshold;
    }

    public boolean logWriteOperation(int block) {
        int currentCycles = writeCycles.getOrDefault(block, 0) + 1;
        writeCycles.put(block, currentCycles);

        return currentCycles <= maxWriteThreshold;
    }

    public int getWriteCount(int block) {
        return writeCycles.getOrDefault(block, 0);
    }
}