package vn.bookshare.exception;

import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import vn.bookshare.response.CustomResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomResponse<Void>> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining(", "));
        CustomResponse<Void> customResponse = new CustomResponse<>();
        customResponse.setStatus(false);
        customResponse.setMessage(message);
        return ResponseEntity.badRequest().body(customResponse);
    }

    @ExceptionHandler(value = PasswordNotMatchException.class)
    public ResponseEntity<CustomResponse<Void>> handlePasswordNotMatchException(PasswordNotMatchException ex) {
        CustomResponse<Void> customResponse = new CustomResponse<>();
        customResponse.setStatus(false);
        customResponse.setMessage(ex.getMessage());
        return ResponseEntity.badRequest().body(customResponse);
    }

    @ExceptionHandler(value = AccountUserAlreadyExistsException.class)
    public ResponseEntity<CustomResponse<Void>> handleUserAlreadyException(AccountUserAlreadyExistsException ex) {
        CustomResponse<Void> customResponse = new CustomResponse<>();
        customResponse.setStatus(false);
        customResponse.setMessage(ex.getMessage());
        return ResponseEntity.badRequest().body(customResponse);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<CustomResponse<Void>> handleException(Exception ex) {
        CustomResponse<Void> customResponse = new CustomResponse<>();
        customResponse.setStatus(false);
        customResponse.setMessage("Internal server error");
        return ResponseEntity.internalServerError().body(customResponse);
    }
}
