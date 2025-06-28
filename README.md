# 📚 BookShare — Nền tảng chia sẻ & mua bán sách cũ

**BookShare** là một nền tảng mạng xã hội được xây dựng bằng Java Spring Boot, giúp người dùng **chia sẻ, tặng hoặc rao bán sách cũ**. Dự án hướng đến việc kết nối cộng đồng yêu sách, tối ưu quy trình cho tặng sách và góp phần lan tỏa văn hóa đọc.

> ✅ Được phát triển với kiến trúc RESTful  
> ✅ Tách lớp rõ ràng: Controller, Service, Repository  
> ✅ Bảo mật, mở rộng tốt và dễ test

---

## 🚀 Các tính năng chính

- 📝 Đăng ký & đăng nhập người dùng
- 📚 Đăng bài chia sẻ hoặc rao bán sách
- 💬 Bình luận, yêu thích và tương tác
- 🔍 Tìm kiếm sách theo từ khóa, thể loại
- 🔐 Mã hóa mật khẩu với BCrypt
- ✅ Validation dữ liệu đầu vào theo chuẩn Hibernate Validator
- 📦 JSON Response chuẩn hóa + Exception handler

---

## 🧱 Kiến trúc hệ thống

- Java 21
- Spring Boot 3.5.3
- Spring Data JPA (MySQL)
- Spring Security (sẵn sàng tích hợp JWT)
- Hibernate Validator
- JUnit 5, Mockito, MockMvc (test)

---

## ✨ Ví dụ: Chức năng đăng ký người dùng

Một trong các chức năng cơ bản, được phát triển với:
- Validation đầu vào (`@Email`, `@NotBlank`, custom `@PasswordMatches`)
- Exception toàn cục (`@ControllerAdvice`)
- Unit & Integration Test

### 🔐 API: `POST /api/auth/register`

```json
{
  "username": "user@example.com",
  "password": "securepassword",
  "confirmPassword": "securepassword"
}
