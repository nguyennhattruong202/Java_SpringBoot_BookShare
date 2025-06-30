package vn.bookshare.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.bookshare.validator.PasswordMatches;

@Data
@AllArgsConstructor
@NoArgsConstructor
@PasswordMatches
public class AccountUserRegistrationRequest {

    @Email(message = "Invalid email")
    @NotBlank(message = "Email cannot be blank")
    @Size(max = 255, message = "Email has maximum 255 characters")
    private String email;
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
    @NotBlank(message = "Confirm password cannot be blank")
    @Size(min = 6, message = "Confirm password must be at least 6 characters")
    private String confirmPassword;
}
