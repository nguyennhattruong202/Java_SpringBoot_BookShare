package vn.bookshare.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.bookshare.validator.PasswordMatches;

@Data
@AllArgsConstructor
@NoArgsConstructor
@PasswordMatches
public class UserAccountRegistrationRequest {

    @Email(message = "Invalid email")
    @NotBlank(message = "Email cannot be blank")
    @Size(max = 255, message = "Email has maximum 255 characters")
    private String username;
    @NotBlank(message = "Fullname cannot be blank")
    @Size(min = 3, max = 255, message = "Fullname must be at least 3 characters and maximum 255 characters")
    private String fullname;
    @NotNull(message = "Date of birth cannot be null")
    @Past(message = "Date of birth must past")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    @NotBlank(message = "Gender cannot be blank")
    @Size(max = 100, message = "Gender has maximum 100 characters")
    private String gender;
    @NotBlank(message = "Phone cannot be blank")
    @Size(max = 100, message = "Phone has maximum 100 characters")
    private String phone;
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
    @NotBlank(message = "Confirm password cannot be blank")
    @Size(min = 6, message = "Confirm password must be at least 6 characters")
    private String confirmPassword;
}
