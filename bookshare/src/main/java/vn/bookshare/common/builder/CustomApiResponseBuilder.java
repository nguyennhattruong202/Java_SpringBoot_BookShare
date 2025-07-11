package vn.bookshare.common.builder;

import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import vn.bookshare.dto.response.CustomApiResponse;

public class CustomApiResponseBuilder {

    private CustomApiResponseBuilder() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static <T> CustomApiResponse<T> buildCustomApiResponse(boolean status,
            String code, String message, T data, HttpServletRequest request) {
        CustomApiResponse<T> response = new CustomApiResponse<>();
        response.setStatus(status);
        response.setCode(code);
        response.setMessage(message);
        response.setData(data);
        response.setTimestamp(Instant.now().toString());
        response.setPath(request.getRequestURI());
        return response;
    }
}
