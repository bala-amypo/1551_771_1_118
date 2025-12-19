import java.util.List;

public interface SupplierProfileService {
    SupplierProfile createSupplier(SupplierProfile supplier);
    SupplierProfile getSupplierById(Long id) throws SupplierNotFoundException;
    SupplierProfile getBySupplierCode(String supplierCode);
    List<SupplierProfile> getAllSuppliers();
    SupplierProfile updateSupplierStatus(Long id, boolean active);
}
