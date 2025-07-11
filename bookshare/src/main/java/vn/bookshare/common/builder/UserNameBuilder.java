package vn.bookshare.common.builder;

import java.text.Normalizer;
import org.springframework.stereotype.Component;

@Component
public class UserNameBuilder {

    public String buildUsername(String fullname, Long userId) {
        String slug = toSlug(fullname);
        return slug + "-" + (userId + 1); // 4. Nối với userId
    }

    private String toSlug(String string) {
        string = string.replace("Đ", "D").replace("đ", "d");
        String noAccent = Normalizer.normalize(string, Normalizer.Form.NFD); // 1. Loại bỏ dấu tiếng Việt
        String asciiOnly = noAccent.replaceAll("\\p{M}", ""); // 1. Loại bỏ dấu tiếng Việt
        String cleaned = asciiOnly.replaceAll("[^a-zA-Z0-9\\s]", ""); // 2. Loại bỏ các ký tự không phải chữ/số hoặc khoảng trắng
        return cleaned.trim().replaceAll("\\s+", "-").toLowerCase();// 3. Chuyển lowercase và thay thế khoảng trắng thành dấu gạch ngang
    }
}
