package alessiovulpinari.u2_w3_d2.repositories;


import alessiovulpinari.u2_w3_d2.entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DeviceRepository extends JpaRepository<Device, UUID> {

}
