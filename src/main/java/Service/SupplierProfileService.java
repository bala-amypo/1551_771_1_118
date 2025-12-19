public class SupplierProfile {
    private Long id;
    private String supplierCode;
    private String name;
    private boolean active;

 
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSupplierCode() { return supplierCode; }
    public void setSupplierCode(String supplierCode) { this.supplierCode = supplierCode; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
