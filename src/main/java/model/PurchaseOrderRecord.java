import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class PurchaseOrderSystem {

    // --- 1. The Data Record (Entity) ---
    public static class PurchaseOrderRecord {
        private Long id;
        private String poNumber;
        private Long supplierId;
        private String itemDescription;
        private Integer quantity;
        private LocalDate promisedDeliveryDate;
        private LocalDate issuedDate;

        // Constructor
        public PurchaseOrderRecord(Long id, String poNumber, Long supplierId, String itemDescription, 
                                   Integer quantity, LocalDate promisedDeliveryDate, LocalDate issuedDate) {
            this.id = id;
            this.poNumber = poNumber;
            this.supplierId = supplierId;
            this.itemDescription = itemDescription;
            this.quantity = quantity;
            this.promisedDeliveryDate = promisedDeliveryDate;
            this.issuedDate = issuedDate;
        }

        // Getters
        public String getPoNumber() { return poNumber; }
        public Integer getQuantity() { return quantity; }
        public Long getSupplierId() { return supplierId; }

        @Override
        public String toString() {
            return "PO #" + poNumber + " [Item: " + itemDescription + ", Qty: " + quantity + "]";
        }
    }

    // --- 2. The Logic Handler (Service) ---
    public static class PurchaseOrderService {
        // Set to track unique PO Numbers in memory
        private Set<String> existingPoNumbers = new HashSet<>();
        
        // Mock list of "Valid" Supplier IDs (101, 102, 103)
        private Set<Long> validSuppliers = Set.of(101L, 102L, 103L);

        public void savePurchaseOrder(PurchaseOrderRecord record) {
            // Rule 1: quantity > 0
            if (record.getQuantity() == null || record.getQuantity() <= 0) {
                throw new IllegalArgumentException("Quantity must be greater than 0.");
            }

            // Rule 2: poNumber must be unique
            if (existingPoNumbers.contains(record.getPoNumber())) {
                throw new IllegalArgumentException("poNumber unique constraint violated: " + record.getPoNumber());
            }

            // Rule 3: Must reference valid supplier
            if (!validSuppliers.contains(record.getSupplierId())) {
                throw new IllegalArgumentException("Invalid supplierId");
            }

            // If all rules pass, "save" the record
            existingPoNumbers.add(record.getPoNumber());
            System.out.println("Successfully recorded: " + record);
        }
    }

    // --- 3. Main Method to Test the Rules ---
    public static void main(String[] args) {
        PurchaseOrderService service = new PurchaseOrderService();

        try {
            // Test Case 1: Valid Record
            PurchaseOrderRecord po1 = new PurchaseOrderRecord(1L, "PO-001", 101L, "Laptops", 10, 
                                            LocalDate.now().plusDays(7), LocalDate.now());
            service.savePurchaseOrder(po1);

            // Test Case 2: Duplicate PO Number (Should fail)
            PurchaseOrderRecord po2 = new PurchaseOrderRecord(2L, "PO-001", 102L, "Monitors", 5, 
                                            LocalDate.now().plusDays(7), LocalDate.now());
            // service.savePurchaseOrder(po2); // Uncomment to see uniqueness error

            // Test Case 3: Invalid Supplier ID (Should fail)
            PurchaseOrderRecord po3 = new PurchaseOrderRecord(3L, "PO-002", 999L, "Chairs", 20, 
                                            LocalDate.now().plusDays(7), LocalDate.now());
            service.savePurchaseOrder(po3);

        } catch (IllegalArgumentException e) {
            System.err.println("Validation Error: " + e.getMessage());
        }
    }
}