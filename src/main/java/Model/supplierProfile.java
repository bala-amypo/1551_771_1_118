import jakarta.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.annotation.CreatedDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "supplier_profile")
class SupplierProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String supplierCode; 

    private String supplierName;
    private String email;
    private String phone;
    
    private Boolean active = true; 

    @CreatedDate
    private LocalDateTime createdAt = LocalDateTime.now();

  
    public Long getId() { return id; }
    public String getSupplierCode() { return supplierCode; }
    public void setSupplierCode(String supplierCode) { this.supplierCode = supplierCode; }
    public String getSupplierName() { return supplierName; }
    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}


interface SupplierRepository extends JpaRepository<SupplierProfile, Long> {
    List<SupplierProfile> findByActiveTrue();
    boolean existsBySupplierCode(String supplierCode);
}


@Service
class SupplierService {
    
    private final SupplierRepository repository;

    public SupplierService(SupplierRepository repository) {
        this.repository = repository;
    }

    public List<SupplierProfile> getSuppliersForAnalytics() {
        return repository.findByActiveTrue();
    }

    public void seedDatabase() {
        SupplierProfile s1 = new SupplierProfile();
        s1.setSupplierCode("SUP-001");
        s1.setSupplierName("Global Tech");
        s1.setActive(true);

        SupplierProfile s2 = new SupplierProfile();
        s2.setSupplierCode("SUP-002");
        s2.setSupplierName("Legacy Parts");
        s2.setActive(false); 

        repository.saveAll(List.of(s1, s2));
    }
}