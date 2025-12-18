import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SupplyChainManagementSystem {

    // --- 1. DeliveryRecord ---
    public static class DeliveryRecord {
        private Long id; // Primary Key
        private Long poId;
        private LocalDate actualDeliveryDate;
        private Integer deliveredQuantity;
        private String notes;

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

    // --- 2. DelayScoreRecord ---
    public static class DelayScoreRecord {
        private Long id; // Primary Key
        private Long supplierId;
        private Long poId;
        private Integer delayDays;
        private String delaySeverity; // ON_TIME, MINOR, MODERATE, SEVERE
        private Double score;
        private LocalDateTime computedAt;

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

    // --- 3. SupplierRiskAlert ---
    public static class SupplierRiskAlert {
        private Long id; // Primary Key
        private Long supplierId;
        private String alertLevel; // LOW, MEDIUM, HIGH
        private String message;
        private LocalDateTime alertDate;
        private Boolean resolved = false; // Defaulted to false per rule

        public SupplierRiskAlert(Long id, Long supplierId, String alertLevel, String message, LocalDateTime alertDate) {
            this.id = id;
            this.supplierId = supplierId;
            this.alertLevel = alertLevel;
            this.message = message;
            this.alertDate = alertDate;
            // 'resolved' remains false by default
        }

        public void setResolved(Boolean resolved) { this.resolved = resolved; }
    }

    // --- 4. Logic/Service Layer to enforce Rules ---
    public static class SupplyChainService {
        private Set<Long> validPoIds = Set.of(101L, 102L, 103L); // Mock valid POs
        private Map<Long, DelayScoreRecord> delayScoreStore = new HashMap<>();

        // Validation for DeliveryRecord
        public void processDelivery(DeliveryRecord record) {
            // Rule: Must reference a valid PO
            if (!validPoIds.contains(record.getPoId())) {
                throw new IllegalArgumentException("Invalid PO reference.");
            }
            // Rule: deliveredQuantity >= 0
            if (record.getDeliveredQuantity() == null || record.getDeliveredQuantity() < 0) {
                throw new IllegalArgumentException("deliveredQuantity must be >= 0.");
            }
            System.out.println("DeliveryRecord processed for PO: " + record.getPoId());
        }

        // Validation for DelayScoreRecord
        public void processDelayScore(DelayScoreRecord record) {
            // Rule: One score per PO
            if (delayScoreStore.containsKey(record.getPoId())) {
                throw new IllegalStateException("A score already exists for this PO.");
            }
            delayScoreStore.put(record.getPoId(), record);
            System.out.println("DelayScore recorded for PO: " + record.getPoId());
        }
    }

    // --- 5. Main Method to Demonstrate ---
    public static void main(String[] args) {
        SupplyChainService service = new SupplyChainService();

        try {
            // Example 1: Valid Delivery
            DeliveryRecord delivery = new DeliveryRecord(1L, 101L, LocalDate.now(), 50, "Delivered early");
            service.processDelivery(delivery);

            // Example 2: Valid Delay Score
            DelayScoreRecord score = new DelayScoreRecord(1L, 500L, 101L, 2, "MINOR", 90.0, LocalDateTime.now());
            service.processDelayScore(score);

            // Example 3: Defaulting SupplierRiskAlert
            SupplierRiskAlert alert = new SupplierRiskAlert(1L, 500L, "HIGH", "Late shipment", LocalDateTime.now());
            System.out.println("Alert resolved status (Default): " + alert.resolved); // Should be false

        } catch (Exception e) {
            System.err.println("Rule Violation: " + e.getMessage());
        }
    }
}