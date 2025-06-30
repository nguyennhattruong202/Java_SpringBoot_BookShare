package vn.bookshare.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import vn.bookshare.dto.AccountUserRegistrationRequest;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, AccountUserRegistrationRequest> {

    @Override
    public boolean isValid(AccountUserRegistrationRequest userRegistrationRequest, ConstraintValidatorContext context) {
        return userRegistrationRequest.getPassword() != null
                && userRegistrationRequest.getPassword().equals(userRegistrationRequest.getConfirmPassword());
    }
}
