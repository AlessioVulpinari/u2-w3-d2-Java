package alessiovulpinari.u2_w3_d2.services;


import alessiovulpinari.u2_w3_d2.entities.Employee;
import alessiovulpinari.u2_w3_d2.exceptions.BadRequestException;
import alessiovulpinari.u2_w3_d2.exceptions.NotFoundException;
import alessiovulpinari.u2_w3_d2.payloads.EmployeePayload;
import alessiovulpinari.u2_w3_d2.repositories.EmployeeRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class EmployeeService {

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private EmployeeRepository employeeRepository;

    public Page<Employee> getEmployees(int pageNumber, int pageSize) {
        if (pageSize <= 0) pageSize =10;
        if (pageSize >= 50) pageSize = 50;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return employeeRepository.findAll(pageable);
    }

    public Employee saveEmployee(EmployeePayload body) {
        this.employeeRepository.findByEmail(body.email()).ifPresent(employee ->
        {throw new BadRequestException("Esiste già un dipendente con questa email: " + body.email());});

        this.employeeRepository.findByUsername(body.username()).ifPresent(employee -> {
            throw new BadRequestException("Esiste già un impiegato con questo username: " + body.username());});

        Employee newEmployee = new Employee(body.username(), body.firstName(), body.LastName(), body.email(), body.password());
        return employeeRepository.save(newEmployee);
    }

    public Employee findById(UUID employeeId) {
        return this.employeeRepository.findById(employeeId).orElseThrow(() -> new NotFoundException(employeeId));
    }

    public Employee findByEmail(String email) {
        return this.employeeRepository.findByEmail(email).orElseThrow(() -> new NotFoundException(email));
    }

    public Employee findByIdAndUpdate(UUID employeeId, EmployeePayload body)
    {
        Employee found = findById(employeeId);
        found.setEmail(body.email());
        found.setUsername(body.username());
        found.setLastName(body.LastName());
        found.setFirstName(body.firstName());
        found.setPassword(body.password());
        found.setAvatarUrl("https://ui-avatars.com/api/?name=" + body.firstName() + "+" + body.LastName());
        return employeeRepository.save(found);
    }

    public void findByIdAndDelete(UUID employeeId) {
        Employee found = findById(employeeId);
        this.employeeRepository.delete(found);
    }

    public String uploadAvatar(MultipartFile file) throws IOException {
        return (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
    }

    public Employee patchAvatar(UUID employeeId, String avatarUrl) {
        Employee found = findById(employeeId);
        found.setAvatarUrl(avatarUrl);

        return  this.employeeRepository.save(found);
    }
}
