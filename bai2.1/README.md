# Bài tập 2.1 - Xây dựng TestNG Suite XML

## Nội dung đã làm
- Có 3 class kiểm thử: `LoginTest`, `CartTest`, `CheckoutTest`
- Mỗi class có **ít nhất 2 test**
- Phân nhóm:
  - **smoke**: 1 test/class
  - **regression**: tất cả test
- Suite XML:
  - `testng-smoke.xml`: chỉ chạy nhóm **smoke**
  - `testng-regression.xml`: chỉ chạy nhóm **regression**
- Chạy song song `parallel="classes"` và dùng `ThreadLocal<WebDriver>` (không dùng static WebDriver).

## Chạy test
Mặc định `pom.xml` đang trỏ tới `testng-smoke.xml`:

```bash
cd bai2.1
mvn test
```

Chạy regression (đổi suite xml khi chạy):

```bash
mvn test -DsuiteXmlFile=testng-regression.xml
```

## Report HTML để chụp màn hình
Sau khi chạy test:

```bash
mvn surefire-report:report
```

Mở và chụp:
- `target/surefire-reports/index.html` (TestNG report)
- `target/surefire-reports/emailable-report.html` (TestNG report)
- `target/site/surefire-report.html` (Surefire HTML report)

## Lưu ý driver Edge local
Trong `testng-*.xml` có parameter:
- `driverPath` = `D:\Download\edgedriver_win64\msedgedriver.exe`

Nếu máy bạn khác đường dẫn, sửa `driverPath` trong XML hoặc chạy:

```bash
mvn test -Dwebdriver.edge.driver=D:\duongdan\msedgedriver.exe
```

