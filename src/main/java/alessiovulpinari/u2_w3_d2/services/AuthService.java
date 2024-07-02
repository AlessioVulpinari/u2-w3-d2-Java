package alessiovulpinari.u2_w3_d2.services;

import alessiovulpinari.u2_w3_d2.entities.Employee;
import alessiovulpinari.u2_w3_d2.exceptions.UnathorizedException;
import alessiovulpinari.u2_w3_d2.payloads.EmployeeLoginDTO;
import alessiovulpinari.u2_w3_d2.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private JWTTools jwtTools;

    // funzione per la creazione
    public String authenticationAndTokenGeneration(EmployeeLoginDTO body) {

        Employee found = this.employeeService.findByEmail(body.email());

        if (bcrypt.matches(body.password(), found.getPassword())) return jwtTools.createToken(found);
        else throw new UnathorizedException("Credenziali non corette");
    }
}
