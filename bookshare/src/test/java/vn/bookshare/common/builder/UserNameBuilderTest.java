package vn.bookshare.common.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class UserNameBuilderTest {

    private final UserNameBuilder builder = new UserNameBuilder();

    @Test
    void shouldGenerateCorrectSlugFromVietnameseName() {
        String username = builder.buildUsername("Nguyễn Văn An", 123L);
        assertEquals("nguyen-van-an-124", username);
    }

    @Test
    void shouldCleanSymbols() {
        String username = builder.buildUsername("Lê! @Minh# $Đức%", 99L);
        assertEquals("le-minh-duc-100", username);
    }
}
