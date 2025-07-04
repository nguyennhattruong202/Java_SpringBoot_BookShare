package vn.bookshare.exception;

public class CustomExpiredJwtException extends RuntimeException {

    public CustomExpiredJwtException(String message) {
        super(message);
    }
}
