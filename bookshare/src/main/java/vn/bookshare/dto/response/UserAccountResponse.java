package vn.bookshare.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserAccountResponse {

    private Long userId;
    private String url;
    private String fullname;
    private String username;
    private LocalDate dateOfBirth;
    private String gender;
    private String nationality;
    private String phone;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String note;
    private String role;
}
