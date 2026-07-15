package com.guardian.telemetry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DurabilityManagerTest {

    private DurabilityManager durabilityManager;
    private static final int MAX_THRESHOLD = 3;

    @BeforeEach
    public void setUp() {
        durabilityManager = new DurabilityManager(MAX_THRESHOLD);
    }

    @Test
    public void testLogWriteOperation_SuccessWithinThreshold() {
        assertTrue(durabilityManager.logWriteOperation(500));
        assertTrue(durabilityManager.logWriteOperation(500));

        assertEquals(2, durabilityManager.getWriteCount(500));
    }

    @Test
    public void testLogWriteOperation_FailsWhenExceedingThreshold() {
        int targetBlock = 600;

        assertTrue(durabilityManager.logWriteOperation(targetBlock));
        assertTrue(durabilityManager.logWriteOperation(targetBlock));
        assertTrue(durabilityManager.logWriteOperation(targetBlock));

        assertFalse(durabilityManager.logWriteOperation(targetBlock),
                "Block should be flagged as unreliable after exceeding max write threshold.");
    }

    @Test
    public void testGetWriteCountForUnusedBlock() {
        assertEquals(0, durabilityManager.getWriteCount(777));
    }
}