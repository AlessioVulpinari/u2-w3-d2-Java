package alessiovulpinari.u2_w3_d2.exceptions;

import alessiovulpinari.u2_w3_d2.payloads.ErrorPayload;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorPayload handleBadRequest(BadRequestException err) {
        if (err.getErrorList() != null) {
            String errorMessage = err.getErrorList().stream().map(objectError ->
                    objectError.getDefaultMessage()).collect(Collectors.joining("\n"));
            return new ErrorPayload(errorMessage, LocalDateTime.now());
        } else {
            return new ErrorPayload(err.getMessage(), LocalDateTime.now());
        }
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorPayload handleNotFound (NotFoundException err) {
        return new ErrorPayload(err.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorPayload handleGenericError(Exception err) {
        err.printStackTrace();
        return new ErrorPayload("Problema lato server! Aggiusteremo il prima possibile", LocalDateTime.now());
    }
}
