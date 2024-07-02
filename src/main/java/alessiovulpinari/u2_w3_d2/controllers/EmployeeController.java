package alessiovulpinari.u2_w3_d2.controllers;


import alessiovulpinari.u2_w3_d2.entities.Employee;
import alessiovulpinari.u2_w3_d2.exceptions.BadRequestException;
import alessiovulpinari.u2_w3_d2.payloads.EmployeePayload;
import alessiovulpinari.u2_w3_d2.payloads.EmployeePayloadResponse;
import alessiovulpinari.u2_w3_d2.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public Page<Employee> getAllEmployees(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size)
    {
        return this.employeeService.getEmployees(page, size);
    }


    @GetMapping("/{employeeId}")
    public Employee findById(@PathVariable UUID employeeId)
    {
        return this.employeeService.findById(employeeId);
    }

    @PutMapping("/{employeeId}")
    public Employee findByIdAndUpdate(@PathVariable UUID employeeId, @RequestBody EmployeePayload body) {
        return this.employeeService.findByIdAndUpdate(employeeId, body);
    }

    @DeleteMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID employeeId) {
        this.employeeService.findByIdAndDelete(employeeId);
    }

    @PatchMapping("/{employeeId}/avatar")
    public Employee uploadAvatar(@PathVariable UUID employeeId, @RequestParam("avatar")MultipartFile image) throws IOException {
        String url = employeeService.uploadAvatar(image);
        return this.employeeService.patchAvatar(employeeId, url);
    }
}
