import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Consolidated Supply Chain Management Entities
 */
public class SupplyChainManagement {

    // -------------------------------------------------------------------------
    // 3. DeliveryRecord
    // -------------------------------------------------------------------------
    public static class DeliveryRecord {
        private Long id; // Primary Key
        private Long poId; //
        private LocalDate actualDeliveryDate; //
        private Integer deliveredQuantity; //
        private String notes; //

        public void validate(Set<Long> validPurchaseOrders) {
            // Rule: Must reference a valid PO.
            if (!validPurchaseOrders.contains(this.poId)) {
                throw new IllegalArgumentException("Invalid PO reference.");
            }
            // Rule: deliveredQuantity >= 0.
            if (this.deliveredQuantity == null || this.deliveredQuantity < 0) {
                throw new IllegalArgumentException("deliveredQuantity must be greater than or equal to 0.");
            }
        }
    }

    // -------------------------------------------------------------------------
    // 4. DelayScoreRecord
    // -------------------------------------------------------------------------
    public static class DelayScoreRecord {
        private Long id; // Primary Key
        private Long supplierId; //
        private Long poId; //
        private Integer delayDays; //
        // ENUM values: ON_TIME / MINOR / MODERATE / SEVERE
        private String delaySeverity; 
        private Double score; //
        private LocalDateTime computedAt; //

        public void validate(Set<Long> existingScorePoIds) {
            // Rule: One score per PO.
            if (existingScorePoIds.contains(this.poId)) {
                throw new IllegalStateException("A score already exists for this PO.");
            }
            // Rule: Score determined based on delay days.
            calculateScore();
        }

        private void calculateScore() {
            // Placeholder logic for: Score determined based on delay days.
            if (this.delayDays <= 0) this.delaySeverity = "ON_TIME";
            else if (this.delayDays < 3) this.delaySeverity = "MINOR";
            else if (this.delayDays < 7) this.delaySeverity = "MODERATE";
            else this.delaySeverity = "SEVERE";
        }
    }

    // -------------------------------------------------------------------------
    // 5. SupplierRiskAlert
    // -------------------------------------------------------------------------
    public static class SupplierRiskAlert {
        private Long id; // Primary Key
        private Long supplierId; //
        // ENUM values: LOW / MEDIUM / HIGH
        private String alertLevel; 
        private String message; //
        private LocalDateTime alertDate; //
        // Rule: resolved defaults to false.
        private Boolean resolved = false; 

        public SupplierRiskAlert() {
            // Ensure rule: resolved defaults to false.
            this.resolved = false;
        }
    }

    // -------------------------------------------------------------------------
    // 6. AppUser
    // -------------------------------------------------------------------------
    public static class AppUser {
        private Long id; // Primary Key
        private String email; // Must be unique
        private String password; //
        private Set<String> roles = new HashSet<>(); //
        private LocalDateTime createdAt; //

        public void validate(Set<String> existingEmails) {
            // Rule: email unique.
            if (existingEmails.contains(this.email)) {
                throw new IllegalArgumentException("Email already in use.");
            }
        }
    }
}