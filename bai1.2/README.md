# Bài tập 1.2 - Kiểm thử form đăng nhập Saucedemo

## Yêu cầu
- Java 11+, Maven
- Microsoft Edge browser

**Nếu gặp lỗi DNS** (Selenium Manager không tải được EdgeDriver):
1. Tải [Microsoft Edge WebDriver](https://developer.microsoft.com/en-us/microsoft-edge/tools/webdriver/) đúng phiên bản Edge của bạn.
2. Giải nén `msedgedriver.exe` vào một thư mục (ví dụ `C:\edgedriver\`).
3. Chạy test với:  
   `mvn test -Dwebdriver.edge.driver=C:\edgedriver\msedgedriver.exe`

## Chạy test
```bash
cd bai1.2
mvn test
```
Sau mỗi test trình duyệt **dừng 5 giây** để bạn chụp màn hình.

## TestNG HTML report
Sau khi chạy test, tạo report HTML:
```bash
mvn surefire-report:report
```
Mở report: **`target/site/surefire-report.html`** (chụp màn hình file này nộp bài).

Hoặc xem kết quả TestNG trong **`target/surefire-reports/`** (file XML, text).

## Lưu ý đã tuân thủ
1. **@BeforeMethod mở trình duyệt, @AfterMethod đóng** (TestNG).
2. **Dùng Explicit Wait (WebDriverWait)** thay vì `Thread.sleep()` khi đợi element.
3. **Mỗi Assert có thông báo lỗi rõ ràng** (tham số message).
4. **Dừng 5 giây sau mỗi test** trong @AfterMethod để chụp màn hình.
5. Có đủ **5 test case** và **TestNG HTML report** (surefire-report.html).

## Cấu trúc test
| Test | Mô tả | Kỳ vọng |
|------|--------|---------|
| testLoginSuccess | Username + password hợp lệ | Chuyển đến `/inventory.html` |
| testLoginWrongPassword | Sai mật khẩu | Có thông báo lỗi trên trang |
| testLoginEmptyUsername | Để trống username | Thông báo chứa "Username is required" |
| testLoginEmptyPassword | Để trống password | Thông báo chứa "Password is required" |
| testLoginLockedUser | User `locked_out_user` | Thông báo chứa "Sorry, this user has been locked out" |
