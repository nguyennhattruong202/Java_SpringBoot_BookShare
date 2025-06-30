package vn.bookshare.exception;

public class AccountUserAlreadyExistsException extends RuntimeException {

    public AccountUserAlreadyExistsException(String message) {
        super(message);
    }
}
