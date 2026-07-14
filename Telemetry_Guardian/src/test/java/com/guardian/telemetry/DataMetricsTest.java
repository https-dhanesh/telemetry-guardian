package com.guardian.telemetry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DataMetricsTest {

    private DataMetrics metrics;

    @BeforeEach
    public void setUp() {
        metrics = new DataMetrics();
    }

    @Test
    public void testCalculateChecksum_Success() {
        String payload = "TELEMETRY_FRAME_01";
        long checksum = metrics.calculateChecksum(payload);
        assertTrue(checksum > 0, "Checksum should be a valid positive long value");
    }

    @Test
    public void testCalculateChecksum_ThrowsExceptionOnNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            metrics.calculateChecksum(null);
        });

        assertTrue(exception.getMessage().contains("CRITICAL_ERR"));
    }

    @Test
    public void testVerifyLatencyThreshold_ValidAndInvalid() {
        assertTrue(metrics.verifyLatencyThreshold(45, 50));

        assertFalse(metrics.verifyLatencyThreshold(60, 50));
    }
}