package vn.bookshare.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import vn.bookshare.dto.UserRegistrationRequest;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, UserRegistrationRequest> {

    @Override
    public boolean isValid(UserRegistrationRequest userRegistrationRequest, ConstraintValidatorContext context) {
        return userRegistrationRequest.getPassword() != null
                && userRegistrationRequest.getPassword().equals(userRegistrationRequest.getConfirmPassword());
    }
}
