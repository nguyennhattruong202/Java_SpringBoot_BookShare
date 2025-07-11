package vn.bookshare.exception;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import vn.bookshare.common.builder.CustomApiResponseBuilder;
import vn.bookshare.common.enums.ResponseCode;
import vn.bookshare.dto.response.CustomApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomApiResponse<Map<String, List<String>>>> handleValidation(
            MethodArgumentNotValidException exception, HttpServletRequest request) {
        Map<String, List<String>> errors = new HashMap<>();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            errors.computeIfAbsent(fieldError.getField(),
                    key -> new ArrayList<>()).add(fieldError.getDefaultMessage());
        }
        CustomApiResponse<Map<String, List<String>>> customApiResponse = CustomApiResponseBuilder
                .buildCustomApiResponse(
                        false,
                        ResponseCode.VALIDATION_ERROR.name(),
                        "Validation error",
                        errors,
                        request
                );
        return ResponseEntity.status(400).body(customApiResponse);
    }

    @ExceptionHandler(value = PasswordNotMatchException.class)
    public ResponseEntity<CustomApiResponse<Void>> handlePasswordNotMatchException(
            PasswordNotMatchException exception,
            HttpServletRequest request) {
        CustomApiResponse<Void> customApiResponse = CustomApiResponseBuilder
                .buildCustomApiResponse(
                        false,
                        ResponseCode.PASSWORD_NOT_MATCH_ERROR.name(),
                        exception.getMessage(),
                        null,
                        request
                );
        return ResponseEntity.status(400).body(customApiResponse);
    }

    @ExceptionHandler(value = AccountUserAlreadyExistsException.class)
    public ResponseEntity<CustomApiResponse<Void>> handleUserAlreadyException(
            AccountUserAlreadyExistsException exception,
            HttpServletRequest request) {
        CustomApiResponse<Void> customApiResponse = CustomApiResponseBuilder.
                buildCustomApiResponse(
                        false,
                        ResponseCode.USER_ALREADY_EXISTS_ERROR.name(),
                        exception.getMessage(),
                        null,
                        request
                );
        return ResponseEntity.status(400).body(customApiResponse);
    }

    @ExceptionHandler(value = CustomExpiredJwtException.class)
    public ResponseEntity<CustomApiResponse<Void>> handleCustomExpiredJwtException(
            CustomExpiredJwtException exception,
            HttpServletRequest request) {
        CustomApiResponse<Void> customApiResponse = CustomApiResponseBuilder.
                buildCustomApiResponse(
                        false,
                        ResponseCode.EXPIRED_JWT_ERROR.name(),
                        exception.getMessage(),
                        null,
                        request
                );
        return ResponseEntity.status(400).body(customApiResponse);
    }

    @ExceptionHandler(value = MalformedJwtException.class)
    public ResponseEntity<CustomApiResponse<Void>> handleMalformedJwtException(
            MalformedJwtException exception,
            HttpServletRequest request) {
        CustomApiResponse<Void> customApiResponse = CustomApiResponseBuilder
                .buildCustomApiResponse(
                        false,
                        ResponseCode.JWT_INCORRECT_FORMAT.name(),
                        exception.getMessage(),
                        null,
                        request
                );
        return ResponseEntity.status(400).body(customApiResponse);
    }

    @ExceptionHandler(value = JwtException.class)
    public ResponseEntity<CustomApiResponse<Void>> handleJwtException(JwtException exception,
            HttpServletRequest request) {
        CustomApiResponse<Void> customApiResponse = CustomApiResponseBuilder
                .buildCustomApiResponse(
                        false,
                        ResponseCode.JWT_ERROR.name(),
                        exception.getMessage(),
                        null,
                        request
                );
        return ResponseEntity.status(400).body(customApiResponse);
    }

    @ExceptionHandler(value = UnsupportedJwtException.class)
    public ResponseEntity<CustomApiResponse<Void>> handleUnsupportedJwtException(UnsupportedJwtException exception,
            HttpServletRequest request) {
        CustomApiResponse<Void> customApiResponse = CustomApiResponseBuilder
                .buildCustomApiResponse(
                        false,
                        ResponseCode.JWT_UNSUPPORT_ERROR.name(),
                        exception.getMessage(),
                        null,
                        request
                );
        return ResponseEntity.status(400).body(customApiResponse);
    }

    @ExceptionHandler(value = SecurityException.class)
    public ResponseEntity<CustomApiResponse<Void>> handleSecurityException(SecurityException exception,
            HttpServletRequest request) {
        CustomApiResponse<Void> customApiResponse = CustomApiResponseBuilder
                .buildCustomApiResponse(
                        false,
                        ResponseCode.USER_AUTHENTICATION_ERROR.name(),
                        exception.getMessage(),
                        null,
                        request
                );
        return ResponseEntity.status(400).body(customApiResponse);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<CustomApiResponse<String>> handleException(Exception exception,
            HttpServletRequest request) {
        CustomApiResponse<String> customApiResponse = CustomApiResponseBuilder
                .buildCustomApiResponse(
                        false,
                        ResponseCode.INTERNAL_SERVER_ERROR.name(),
                        "Internal server error",
                        exception.getMessage(),
                        request
                );
        return ResponseEntity.internalServerError().body(customApiResponse);
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity<CustomApiResponse<String>> handleAuthenticationException(BadCredentialsException exception,
            HttpServletRequest request) {
        CustomApiResponse<String> customApiResponse = CustomApiResponseBuilder
                .buildCustomApiResponse(
                        false,
                        ResponseCode.BAD_CREDENTIALS.name(),
                        "Bad credentials",
                        exception.getMessage(),
                        request
                );
        return ResponseEntity.status(400).body(customApiResponse);
    }

    @ExceptionHandler(value = UserInfoNotFoundException.class)
    public ResponseEntity<CustomApiResponse<String>> handleUserNotFoundException(UserInfoNotFoundException exception,
            HttpServletRequest request) {
        CustomApiResponse<String> customApiResponse = CustomApiResponseBuilder
                .buildCustomApiResponse(
                        false,
                        ResponseCode.USER_NOT_FOUND.name(),
                        "User not found",
                        exception.getMessage(),
                        request
                );
        return ResponseEntity.status(400).body(customApiResponse);
    }

}
