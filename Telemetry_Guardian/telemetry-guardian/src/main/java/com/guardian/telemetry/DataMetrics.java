package com.guardian.telemetry;

import java.nio.charset.StandardCharsets;
import java.util.zip.CRC32;

public class DataMetrics {

    public long calculateChecksum(String payload) {
        if (payload == null) {
            throw new IllegalArgumentException("CRITICAL_ERR: Payload cannot be null for integrity check.");
        }
        CRC32 crc = new CRC32();
        crc.update(payload.getBytes(StandardCharsets.UTF_8));
        return crc.getValue();
    }

    public boolean verifyLatencyThreshold(long actualDurationMs, long thresholdMs) {
        if (actualDurationMs < 0 || thresholdMs <= 0) {
            throw new IllegalArgumentException("INVALID_METRIC: Time durations must be positive values.");
        }
        return actualDurationMs <= thresholdMs;
    }
}