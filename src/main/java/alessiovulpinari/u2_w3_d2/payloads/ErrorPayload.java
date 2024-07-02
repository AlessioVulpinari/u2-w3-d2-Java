package alessiovulpinari.u2_w3_d2.payloads;

import java.time.LocalDateTime;

public record ErrorPayload(String errorMessage, LocalDateTime dateTime) {
}
