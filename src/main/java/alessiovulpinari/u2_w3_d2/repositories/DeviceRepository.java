package alessiovulpinari.u2_w3_d2.repositories;


import alessiovulpinari.u2_w3_d2.entities.Device;
import alessiovulpinari.u2_w3_d2.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeviceRepository extends JpaRepository<Device, UUID> {
    List<Device> findByEmployee(Employee employee);
}
