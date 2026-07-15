package com.guardian.telemetry;

import java.util.HashMap;
import java.util.Map;

public class StorageStateController {
    private final Map<Integer, String> nonVolatileStorage = new HashMap<>();
    private final Map<Integer, String> volatileCache = new HashMap<>();
    private boolean isPowerStable = true;

    public void writeToCache(int block, String data) {
        if (!isPowerStable) {
            throw new IllegalStateException("HARDWARE_FAILURE: Power drop detected. Volatile cache isolated.");
        }
        volatileCache.put(block, data);
    }

    public void commitCacheToStorage() {
        nonVolatileStorage.putAll(volatileCache);
        volatileCache.clear();
    }

    public void simulatePowerLoss() {
        this.isPowerStable = false;
        volatileCache.clear();
    }

    public String readFromStorage(int block) {
        return nonVolatileStorage.getOrDefault(block, "EMPTY");
    }

    public void restorePower() {
        this.isPowerStable = true;
    }
}