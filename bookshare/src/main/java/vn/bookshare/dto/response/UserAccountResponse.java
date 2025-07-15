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
    private String endpoint;
    private String username;
    private String fullname;
    private LocalDate dateOfBirth;
    private String gender;
    private String phone;
    private String note;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
