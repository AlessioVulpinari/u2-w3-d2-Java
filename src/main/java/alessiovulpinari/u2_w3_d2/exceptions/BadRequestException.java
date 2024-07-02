package alessiovulpinari.u2_w3_d2.exceptions;

import lombok.Getter;
import org.springframework.validation.ObjectError;

import java.util.List;

@Getter
public class BadRequestException extends RuntimeException {

    private List<ObjectError> errorList;

    public BadRequestException(String errorMessage ) {
        super(errorMessage);
    }

    public BadRequestException(List<ObjectError> errorList) {
        super("Errore generico nella validazione del payload");
        this.errorList = errorList;
    }
}
