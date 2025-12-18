import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SupplyChainManagementSystem {

    // --- 1. DeliveryRecord Implementation ---
    public static class DeliveryRecord {
        private Long id;                  // Primary Key
        private Long poId;                // Reference to PO
        private LocalDate actualDeliveryDate; //
        private Integer deliveredQuantity;    //
        private String notes;                 //

        public DeliveryRecord(Long id, Long poId, LocalDate actualDeliveryDate, Integer deliveredQuantity, String notes) {
            this.id = id;
            this.poId = poId;
            this.actualDeliveryDate = actualDeliveryDate;
            this.deliveredQuantity = deliveredQuantity;
            this.notes = notes;
        }

        public Long getPoId() { return poId; }
        public Integer getDeliveredQuantity() { return deliveredQuantity; }
    }

    // --- 2. DelayScoreRecord Implementation ---
    public static class DelayScoreRecord {
        private Long id;            // Primary Key
        private Long supplierId;    //
        private Long poId;          //
        private Integer delayDays;  //
        private String delaySeverity; // ON_TIME, MINOR, MODERATE, SEVERE
        private Double score;       //
        private LocalDateTime computedAt; //

        public DelayScoreRecord(Long id, Long supplierId, Long poId, Integer delayDays, 
                                String delaySeverity, Double score, LocalDateTime computedAt) {
            this.id = id;
            this.supplierId = supplierId;
            this.poId = poId;
            this.delayDays = delayDays;
            this.delaySeverity = delaySeverity;
            this.score = score;
            this.computedAt = computedAt;
        }

        public Long getPoId() { return poId; }
    }

    // --- 3. Validation Service ---
    public static class RecordService {
        // Sets for uniqueness/existence checks
        private Set<Long> validPoIds = Set.of(1001L, 1002L, 1003L); // Mock PO Database
        private Map<Long, DelayScoreRecord> delayScoresByPo = new HashMap<>();

        // Validation for DeliveryRecord
        public void saveDeliveryRecord(DeliveryRecord record) {
            // Rule: Must reference a valid PO
            if (!validPoIds.contains(record.getPoId())) {
                throw new IllegalArgumentException("Invalid PO reference.");
            }

            // Rule: deliveredQuantity >= 0
            if (record.getDeliveredQuantity() == null || record.getDeliveredQuantity() < 0) {
                throw new IllegalArgumentException("deliveredQuantity must be >= 0.");
            }

            System.out.println("DeliveryRecord saved for PO: " + record.getPoId());
        }

        // Validation for DelayScoreRecord
        public void saveDelayScore(DelayScoreRecord record) {
            // Rule: One score per PO
            if (delayScoresByPo.containsKey(record.getPoId())) {
                throw new IllegalArgumentException("A score already exists for this PO.");
            }

            // In a real system, the score would be determined based on delay days here
            delayScoresByPo.put(record.getPoId(), record);
            System.out.println("DelayScore saved for PO: " + record.getPoId());
        }
    }

    // --- 4. Main Test Execution ---
    public static void main(String[] args) {
        RecordService service = new RecordService();

        try {
            // Test 1: Valid Delivery
            DeliveryRecord delivery = new DeliveryRecord(1L, 1001L, LocalDate.now(), 50, "Completed");
            service.saveDeliveryRecord(delivery);

            // Test 2: Valid Delay Score
            DelayScoreRecord score = new DelayScoreRecord(1L, 5001L, 1001L, 2, "MINOR", 90.0, LocalDateTime.now());
            service.saveDelayScore(score);

            // Test 3: Constraint Violation (Duplicate Score for same PO)
            DelayScoreRecord duplicateScore = new DelayScoreRecord(2L, 5001L, 1001L, 5, "MODERATE", 70.0, LocalDateTime.now());
            service.saveDelayScore(duplicateScore);

        } catch (IllegalArgumentException e) {
            System.err.println("Validation Error: " + e.getMessage());
        }
    }
}