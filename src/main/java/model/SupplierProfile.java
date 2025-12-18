import jakarta.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDateTime;
import java.util.List;
@Entity
@Table(name = "supplier_profiles")
class SupplierProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "supplier_code", nullable = false, unique = true)
    private String supplierCode;
    @Column(name = "supplier_name", nullable = false)
    private String supplierName;
    private String email;
    private String phone;
    @Column(nullable = false)
    private Boolean active = true;
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getSupplierCode() { return supplierCode; }
    public void setSupplierCode(String supplierCode) { this.supplierCode = supplierCode; }
    public String getSupplierName() { return supplierName; }
    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
interface SupplierProfileRepository extends JpaRepository<SupplierProfile, Long> {
    List<SupplierProfile> findByActiveTrue();
    boolean existsBySupplierCode(String supplierCode);
}
@Service
class SupplierService {
    @Autowired
    private SupplierProfileRepository repository;
    public SupplierProfile saveSupplier(SupplierProfile supplier) {
        if (repository.existsBySupplierCode(supplier.getSupplierCode())) {
            throw new RuntimeException("Error: Supplier Code must be unique.");
        }
        return repository.save(supplier);
    }
    public List<SupplierProfile> getSuppliersForAnalytics() {
        return repository.findByActiveTrue();
    }
}