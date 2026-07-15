package com.guardian.telemetry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StorageStateControllerTest {

    private StorageStateController controller;

    @BeforeEach
    public void setUp() {
        controller = new StorageStateController();
    }

    @Test
    public void testCacheCommitToStorage() {
        controller.writeToCache(102, "SENSOR_DATA_OK");

        assertEquals("EMPTY", controller.readFromStorage(102));

        controller.commitCacheToStorage();

        assertEquals("SENSOR_DATA_OK", controller.readFromStorage(102));
    }

    @Test
    public void testPowerLossWipesVolatileCache() {

        controller.writeToCache(205, "CRITICAL_SNAPSHOT");

        controller.simulatePowerLoss();

        controller.restorePower();

        controller.commitCacheToStorage();
        assertEquals("EMPTY", controller.readFromStorage(205), "Volatile cache must be completely wiped on power drop.");
    }

    @Test
    public void testWriteToCacheFailsDuringPowerLoss() {
        controller.simulatePowerLoss();
        assertThrows(IllegalStateException.class, () -> {
            controller.writeToCache(301, "FAIL_DATA");
        });
    }

    @Test
    public void testReadFromEmptyStorage() {
        assertEquals("EMPTY", controller.readFromStorage(999));
    }
}