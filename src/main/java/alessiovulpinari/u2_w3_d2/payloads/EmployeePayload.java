package alessiovulpinari.u2_w3_d2.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EmployeePayload(
        @NotBlank(message = "Lo username è un dato obbligatorio!")
        @Size(min = 5, max = 15, message = "Inserire uno username compreso tra i 5 e i 15 caratteri")
        String username,
        @NotBlank(message = "Il nome è un dato obbligatorio!")
        @Size(min = 2, max = 20, message = "Inserire un nome compreso tra i 2 e i 20 caratteri" )
        String firstName,
        @NotBlank(message = "Il cognome è un dato obbligatorio!")
        @Size(min = 2, max = 20, message = "Inserire un cognome compreso tra i 2 e i 20 caratteri")
        String LastName,
        @NotBlank(message = "La email è un dato obbligatorio!")
        @Email(message = "Mail inserita non valida!")
        String email,
        @NotBlank
        String password
        )

{
}
