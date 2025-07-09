package vn.bookshare.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import vn.bookshare.dto.request.UserAccountRegistrationRequest;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, UserAccountRegistrationRequest> {

    @Override
    public boolean isValid(UserAccountRegistrationRequest userRegistrationRequest, ConstraintValidatorContext context) {
        return userRegistrationRequest.getPassword() != null
                && userRegistrationRequest.getPassword().equals(userRegistrationRequest.getConfirmPassword());
    }
}
