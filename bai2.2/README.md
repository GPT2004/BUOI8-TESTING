# Bài tập 2.2 - Parallel Execution với ThreadLocal (Edge)

## Đã làm theo lý thuyết / chú ý
- `DriverFactory` dùng **ThreadLocal<WebDriver>** để mỗi thread có driver riêng.
- `quitDriver()` có `tlDriver.remove()` để tránh memory leak khi chạy song song.
- `testng.xml` cấu hình `parallel="classes"` và `thread-count="2"`.
- Chạy **Edge** bằng driver local (parameter `driverPath`).
- Sau mỗi test **dừng 5 giây** để bạn chụp màn hình 2 cửa sổ Edge chạy song song.

## Chạy test
```bash
cd bai2.2
mvn test
```

## Chụp màn hình chạy song song
Khi `mvn test` chạy, bạn sẽ thấy **2 cửa sổ Edge** mở cùng lúc (2 class chạy parallel).  
Trong mỗi test, cửa sổ sẽ giữ **5 giây** trước khi đóng -> bạn chụp màn hình.

## Report HTML để chụp
```bash
mvn surefire-report:report
```

Mở và chụp:
- `target/surefire-reports/index.html`
- `target/surefire-reports/emailable-report.html`
- `target/site/surefire-report.html`

