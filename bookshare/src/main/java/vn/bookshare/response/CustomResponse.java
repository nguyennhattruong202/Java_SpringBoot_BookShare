package vn.bookshare.response;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CustomResponse<T> {

    private boolean status;
    private String message;
    private T data;
    private LocalDateTime timestamp = LocalDateTime.now();
}
