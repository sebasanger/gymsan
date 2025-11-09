package com.sanger.gymsan.errors;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.sanger.gymsan.exceptions.EntitiesNotFoundException;
import com.sanger.gymsan.exceptions.FindEntityByIdNotFoundException;
import com.sanger.gymsan.exceptions.NewUserWithDifferentPasswordsException;
import com.sanger.gymsan.exceptions.PasswordNotMismatch;
import com.sanger.gymsan.exceptions.SearchEntityNoResultException;
import com.sanger.gymsan.exceptions.UserNotFoundException;

import jakarta.mail.AuthenticationFailedException;
import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({EntityNotFoundException.class, SearchEntityNoResultException.class, UserNotFoundException.class,
        FindEntityByIdNotFoundException.class, EntitiesNotFoundException.class})
    public ResponseEntity<ApiError> handleNotFound(Exception ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler({NewUserWithDifferentPasswordsException.class, PasswordNotMismatch.class,})
    public ResponseEntity<ApiError> handleBadRequest(Exception ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ApiError> handleContrainValidationError(ConstraintViolationException ex) {

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Error");

        String error = ex.getCause().getMessage();

        // Case duplicate element
        Pattern pattern = Pattern.compile("^Duplicate\\s\\w+\\s\\'\\w+\\'", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(error);
        boolean matchFound = matcher.find();
        if (matchFound) {
            String messageDulicate = matcher.group(0);
            String messageDulicateTrim = messageDulicate.replace("'", "");
            apiError.setMessage(messageDulicateTrim);
        }

        // Case delete element have relations
        if (error.startsWith("Cannot delete")) {
            apiError.setMessage("No se puede eliminar, tiene registros asociados");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);

    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<ApiError> handleDataIntegrityViolation(DataIntegrityViolationException ex) {

        ApiError apiError = new ApiError(HttpStatus.CONFLICT,
                ex.getMostSpecificCause().getMessage() + " constrain validation exception");

        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);

    }

    @ExceptionHandler({AuthenticationFailedException.class})
    public ResponseEntity<ApiError> handleDataIntegrityViolation(AuthenticationFailedException ex) {

        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                "Error al enviar el mail");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);

    }

    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, LocalDateTime.now(), "Validation error", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        ApiError apiError = new ApiError(status, ex.getMessage());
        return ResponseEntity.status(status).headers(headers).body(apiError);
    }

}
