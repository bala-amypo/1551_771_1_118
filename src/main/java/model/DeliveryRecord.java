import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;

public class DeliveryManagementSystem {

    // --- 1. The Data Record (Entity) ---
    public static class DeliveryRecord {
        private Long id;                // Primary Key
        private Long poId;              // Reference to PO
        private LocalDate actualDeliveryDate; //
        private Integer deliveredQuantity;    //
        private String notes;           //

        public DeliveryRecord(Long id, Long poId, LocalDate actualDeliveryDate, Integer deliveredQuantity, String notes) {
            this.id = id;
            this.poId = poId;
            this.actualDeliveryDate = actualDeliveryDate;
            this.deliveredQuantity = deliveredQuantity;
            this.notes = notes;
        }

        // Getters
        public Long getPoId() { return poId; }
        public Integer getDeliveredQuantity() { return deliveredQuantity; }
    }

    // --- 2. The Logic Handler (Service) ---
    public static class DeliveryService {
        // Mock set of valid Purchase Order IDs (e.g., 501, 502)
        private Set<Long> validPoIds = new HashSet<>(Arrays.asList(501L, 502L));

        public void processDelivery(DeliveryRecord record) {
            // Rule: deliveredQuantity >= 0
            if (record.getDeliveredQuantity() == null || record.getDeliveredQuantity() < 0) {
                throw new IllegalArgumentException("deliveredQuantity must be greater than or equal to 0.");
            }

            // Rule: Must reference a valid PO
            if (!validPoIds.contains(record.getPoId())) {
                throw new IllegalArgumentException("Invalid PO reference: PO ID not found.");
            }

            System.out.println("Delivery processed successfully for PO ID: " + record.getPoId());
        }
    }

    // --- 3. Main Method for Testing ---
    public static void main(String[] args) {
        DeliveryService service = new DeliveryService();

        try {
            // Valid Delivery
            DeliveryRecord valid = new DeliveryRecord(1L, 501L, LocalDate.now(), 10, "On time");
            service.processDelivery(valid);

            // This will fail the quantity rule (quantity < 0)
            DeliveryRecord invalidQty = new DeliveryRecord(2L, 501L, LocalDate.now(), -5, "Damaged");
            service.processDelivery(invalidQty);

        } catch (IllegalArgumentException e) {
            System.err.println("Validation Error: " + e.getMessage());
        }
    }
}