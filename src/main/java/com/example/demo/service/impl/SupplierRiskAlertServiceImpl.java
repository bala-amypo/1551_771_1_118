@Service
public class SupplierRiskAlertServiceImpl implements SupplierRiskAlertService {

    private final SupplierRiskAlertRepository repository;

    public SupplierRiskAlertServiceImpl(SupplierRiskAlertRepository repository) {
        this.repository = repository;
    }

    @Override
    public SupplierRiskAlert createAlert(SupplierRiskAlert alert) {
        alert.setResolved(false);
        return repository.save(alert);
    }

    @Override
    public List<SupplierRiskAlert> getAlertsBySupplier(Long supplierId) {
        return repository.findBySupplierId(supplierId);
    }

    @Override
    public SupplierRiskAlert resolveAlert(Long alertId) {
        SupplierRiskAlert alert = repository.findById(alertId)
                .orElseThrow(() -> new ResourceNotFoundException("Alert not found"));
        alert.setResolved(true);
        return repository.save(alert);
    }

    @Override
    public List<SupplierRiskAlert> getAllAlerts() {
        return repository.findAll();
    }
}
