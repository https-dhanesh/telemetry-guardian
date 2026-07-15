package com.guardian.telemetry;

public class Main {
    public static void main(String[] args) {
        System.out.println("=============================================");
        System.out.println("INITIALIZING TELEMETRY GUARDIAN CORE ENGINE");
        System.out.println("=============================================");

        DataMetrics metrics = new DataMetrics();
        StorageStateController storageController = new StorageStateController();
        DurabilityManager durability = new DurabilityManager(3);

        String payload = "SENSOR_BATCH_DATA_SECURE_01";
        int memoryBlock = 505;

        System.out.println("[INIT] Telemetry core layers online.");
        System.out.println("---------------------------------------------");

        System.out.println("[PHASE 1] Processing ingestion stream...");
        long checksum = metrics.calculateChecksum(payload);
        System.out.println(" -> Integrity Checksum Generated: 0x" + Long.toHexString(checksum).toUpperCase());

        boolean latencyValid = metrics.verifyLatencyThreshold(22, 50);
        System.out.println(" -> Performance Latency Verification: " + (latencyValid ? "PASS" : "FAIL"));

        System.out.println("[PHASE 2] Logging hardware write operations onto Block " + memoryBlock);
        durability.logWriteOperation(memoryBlock);
        System.out.println(" -> Block " + memoryBlock + " total write cycles recorded: " + durability.getWriteCount(memoryBlock));

        System.out.println("[PHASE 3] Staging payload to volatile device cache...");
        storageController.writeToCache(memoryBlock, payload);

        System.out.println("[PHASE 3] Committing cache to persistent non-volatile storage...");
        storageController.commitCacheToStorage();
        System.out.println(" -> Verified Persistent Data: " + storageController.readFromStorage(memoryBlock));

        System.out.println("---------------------------------------------");
        System.out.println("STATUS: Full lifecycle simulation complete. Exiting smoothly.");
        System.out.println("=============================================");
    }
}