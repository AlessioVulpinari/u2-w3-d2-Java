package alessiovulpinari.u2_w3_d2.payloads;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record NewDevicePayload(
        @NotBlank(message = "La tipologia del dispositivo è un dato obbligatorio!")
        String type,
        @NotBlank(message = "Lo stato del dispositivo è un dato obbligatorio")
        String status,
        UUID employeeId) {
}
