package ca.mcgill.ecse321.gamemanager.exception;

import ca.mcgill.ecse321.gamemanager.dto.ErrorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class ResponseExceptionHandler {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorDto> handleResponseStatusException(ResponseStatusException e) {
        return new ResponseEntity<ErrorDto>(new ErrorDto(e.getReason()), e.getStatusCode());
    }
}

