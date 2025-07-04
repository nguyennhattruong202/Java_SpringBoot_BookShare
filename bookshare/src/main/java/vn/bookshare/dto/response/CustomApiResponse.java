package vn.bookshare.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomApiResponse<T> {

    private boolean status;
    private String code;
    private String message;
    private T data;
    private String timestamp;
    private String path;
}
