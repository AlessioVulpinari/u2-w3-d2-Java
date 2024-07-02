package alessiovulpinari.u2_w3_d2.services;


import alessiovulpinari.u2_w3_d2.entities.Device;
import alessiovulpinari.u2_w3_d2.enums.DeviceStatus;
import alessiovulpinari.u2_w3_d2.enums.DeviceType;
import alessiovulpinari.u2_w3_d2.exceptions.BadRequestException;
import alessiovulpinari.u2_w3_d2.exceptions.NotFoundException;
import alessiovulpinari.u2_w3_d2.payloads.DeviceEmployeePayload;
import alessiovulpinari.u2_w3_d2.payloads.NewDevicePayload;
import alessiovulpinari.u2_w3_d2.repositories.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private EmployeeService employeeService;

    public Page<Device> getDevices(int pageNumber, int pageSize) {
        if (pageSize <= 0) pageSize =10;
        if (pageSize >= 50) pageSize =50;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return deviceRepository.findAll(pageable);
    }

    public Device saveDevice(NewDevicePayload body) {

        Device newDevice = new Device(convertStringToDeviceStatus(body.status()), convertStringToDeviceType(body.type()));
        return deviceRepository.save(newDevice);
    }

    public Device findById(UUID deviceId) {
        return this.deviceRepository.findById(deviceId).orElseThrow(() -> new NotFoundException(deviceId));
    }

    public Device findByIdAndUpdate(UUID deviceId, NewDevicePayload modifiedDevice) {
        Device found = findById(deviceId);

        found.setStatus(convertStringToDeviceStatus(modifiedDevice.status()));
        found.setType(convertStringToDeviceType(modifiedDevice.type()));

        if (modifiedDevice.employeeId() != null) found.setEmployee(employeeService.findById(modifiedDevice.employeeId()));
        return deviceRepository.save(found);
    }

    public void findByIdAndDelete(UUID deviceId) {
        Device found = findById(deviceId);
        this.deviceRepository.delete(found);
    }

    public Device findByIdAndUpdateEmployee(UUID deviceId, DeviceEmployeePayload body) {
        Device found = findById(deviceId);
        found.setEmployee(this.employeeService.findById(body.employeeId()));
        return this.deviceRepository.save(found);
    }

    private static DeviceType convertStringToDeviceType(String type) {
        try {
            return DeviceType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Tipo dispositivo inserito non coretto! Inserire SMARTPHONE, " +
                    "TABLET, LAPTOP o COMPUTER.");
        }
    }

    private static DeviceStatus convertStringToDeviceStatus(String status) {
        try {
            return DeviceStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Stato dispositivo inserito non coretto! Inserire AVAILABLE," +
                    "ASSIGNED, UNDER_MAINTENANCE, DISMISSED");
        }
    }
}
