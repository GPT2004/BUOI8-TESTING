# Bài 6.2 – White-box + Selenium: Form Text Box (demoqa)

## 1. CFG luồng xử lý form

File Mermaid để vẽ CFG: `cfg_textbox_form.mmd`

Tóm tắt CFG (ở mức logic đơn giản – dựa trên HTML/JS của trang `https://demoqa.com/text-box`):

- Entry → người dùng nhập dữ liệu, bấm **Submit**:
  - N0: Khi click Submit, luôn chạy **validate email** phía client.
  - N1: `Email hợp lệ?`
    - False → **không hiển thị output** (form báo lỗi email).
    - True → thu thập các field (name, current address, permanent address) → hiển thị vùng **output** bên dưới.

Name / address không có validate phức tạp (chủ yếu là “collect & show”), nên focus chính vào các trường biên liên quan tới email và chuỗi nhập.

## 2. Boundary Value & Trường hợp biên từ CFG

Dựa trên luồng trên và HTML, ta xác định các nhóm biên sau:

### 2.1 Trường name (userName)

- **Empty string**: `""`  
  - Form vẫn submit, output có phần `Name:` nhưng giá trị rỗng.
- **Chuỗi bình thường**: `"Nguyen Van A"`  
  - Đường đi chuẩn (happy path).
- (Có thể thêm) **Ký tự đặc biệt / số**: `"User 123"` – vẫn chấp nhận, vì không có validate đặc biệt.

### 2.2 Trường address (currentAddress)

- **Empty string**: `""`  
  - Form vẫn submit, output hiển thị các field còn lại; address có thể rỗng.
- **Chuỗi dài / nhiều ký tự đặc biệt**: `"12/5 Đường A, P.B, Q.C"`  
  - Kiểm tra ứng dụng không cắt/bỏ ký tự và hiển thị đúng trong output.

### 2.3 Trường email (userEmail) – trọng tâm CFG

Theo HTML/JS của demoqa, email phải đúng định dạng `something@domain`:

- **Email hợp lệ (baseline)**:
  - `"test@example.com"` – ký tự thường + `@` + domain.
  - Đường đi: N1 = True → hiển thị output.
- **Biên “thiếu @”**:
  - `"testexample.com"` – không có `@` → N1 = False.
- **Biên “thiếu phần local hoặc domain”**:
  - `"@example.com"` – thiếu phần trước `@`.
  - `"test@"` – thiếu domain.
- **Ký tự không hợp lệ**:
  - `"test@@example.com"` – nhiều `@`.
  - `"test example@com"` – có space.
- **Empty / chỉ space**:
  - `""` hoặc `"   "` – email trống → N1 = False.

Khi email sai (N1 False), mong đợi:

- Không render vùng `#output`.
- Trường email được highlight lỗi (class `field-error` hoặc thuộc tính `aria-invalid="true"`).

### 2.4 Chuỗi nhập tổng thể (các ký tự đặc biệt)

- **Space ở đầu/cuối**: `"  test@example.com  "`  
  - Tùy vào validate: có thể vẫn hợp lệ (trim) hoặc không – đây là **biên cần test thêm** nếu muốn chi tiết.
- **Unicode / dấu tiếng Việt trong address**: `"Đường số 1, Q.1"`  
  - Tối thiểu nên có 1 test để đảm bảo hệ thống không lỗi khi nhập Unicode.

## 3. Mapping với các test hiện tại

Trong `TextBoxWhiteBoxTest`:

- `testValidInput_ShowsOutput`:
  - Bao phủ **happy path**: name/email/address hợp lệ → N1 True → output hiển thị đầy đủ.
- `testEmptyName_StillAccept`:
  - Biên **name empty** (nhưng email + address hợp lệ) → vẫn đi nhánh N1 True → output hiển thị.
- `testInvalidEmail_ShowsClientValidation`:
  - Biên **email sai định dạng** (không có `@`/domain hợp lệ) → N1 False → không có output, email highlight lỗi.
- `testEmptyAddress_StillOutput`:
  - Biên **address empty** với email hợp lệ → N1 True → output vẫn hiển thị (địa chỉ có thể rỗng).
- `testAddressWithSpecialChars`:
  - Biên **địa chỉ có ký tự đặc biệt/Unicode** → N1 True → kiểm tra output giữ nguyên chuỗi.

Nếu cần mở rộng thêm theo đề:

- Thêm test cho **email có space** hoặc **nhiều @** (gộp vào `testInvalidEmail...` hoặc tạo test riêng).
- Thêm test cho **name có ký tự đặc biệt / số** (thường vẫn chấp nhận, dùng như trường hợp biên bổ sung cho white-box).

