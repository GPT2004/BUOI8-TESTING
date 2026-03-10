# Bài 6.1 – PhoneValidator: CC và Basis Path

## 1. Hàm kiểm tra số điện thoại Việt Nam

File: `src/main/java/bai61/PhoneValidator.java`

Khái quát logic (ở mức CFG cao – bỏ bớt chi tiết vòng lặp):

1. `N0`: `phone == null` → `return false`
2. `N1`: kiểm tra toàn chuỗi chỉ chứa số, `'+'`, hoặc space → nếu vi phạm → `return false`
3. Bỏ space → `cleaned`
   - `N2`: `cleaned.isEmpty()` → `return false`
4. `N3`: nếu `cleaned` bắt đầu bằng `+84` thì chuẩn hoá thành `normalized = "0" + cleaned.substring(3)`; ngược lại giữ nguyên
5. `N4`: kiểm tra `normalized` chỉ chứa số → nếu có ký tự khác → `return false`
6. `N5`: `normalized.length() == 10 && normalized.charAt(0) == '0'` → nếu sai → `return false`
7. `N6`: kiểm tra prefix – chữ số thứ 2 ∈ {3,5,7,8,9} → nếu không → `return false`, ngược lại → `return true`

## 2. Cyclomatic Complexity (CC)

### 2.1 Đếm theo số decision (cách nhanh)

Các decision độc lập ở mức CFG:

- D1: `phone == null` (N0)
- D2: ký tự hợp lệ trong vòng lặp (N1 – vi phạm thì return)
- D3: `cleaned.isEmpty()` (N2)
- D4: `normalized.startsWith("+84")` (N3)
- D5: chỉ chứa số sau chuẩn hoá (N4)
- D6: `normalized.length() != 10 || normalized.charAt(0) != '0'` (N5)
- D7: prefix `second ∈ {3,5,7,8,9}` (N6)

Số điều kiện/decision \(D = 7\).

\[
\text{CC} = D + 1 = 7 + 1 = 8
\]

⇒ Cần tối thiểu **8 basis path test case** nếu thiết kế theo Basis Path Testing.

### 2.2 Tham chiếu CFG

File Mermaid dùng để vẽ CFG: `cfg_phone_validator.mmd`  
Trong đó các node quyết định tương ứng N0..N6 như liệt kê ở trên.

## 3. Basis Path (mô tả đường cơ sở – ở mức khái niệm)

Ví dụ một số đường cơ sở tiêu biểu (không liệt kê toàn bộ 8 path chi tiết):

1. **Path 1 – Null**  
   Entry → N0(True) → `return false`
2. **Path 2 – Ký tự không hợp lệ**  
   Entry → N0(False) → N1(vi phạm ký tự) → `return false`
3. **Path 3 – Chỉ space**  
   Entry → N0(False) → N1(OK) → N2(True) → `return false`
4. **Path 4 – +84 nhưng sai ký tự sau đó**  
   Entry → N0(False) → N1(OK) → N2(False) → N3(True) → N4(False) → `return false`
5. **Path 5 – Độ dài != 10 sau chuẩn hoá**  
   Entry → N0(False) → N1(OK) → N2(False) → N3(±) → N4(True) → N5(False) → `return false`
6. **Path 6 – Prefix không hợp lệ (01x,02x,...)**  
   Entry → N0(False) → N1(OK) → N2(False) → N3(±) → N4(True) → N5(True) → N6(False) → `return false`
7. **Path 7 – Số hợp lệ dạng 0xxxxxxxxx**  
   Entry → N0(False) → N1(OK) → N2(False) → N3(False) → N4(True) → N5(True) → N6(True) → `return true`
8. **Path 8 – Số hợp lệ có +84 và space**  
   Entry → N0(False) → N1(OK) → N2(False) → N3(True) → N4(True) → N5(True) → N6(True) → `return true`

Các test case trong:

- `src/test/java/bai61/PhoneValidatorTest.java`
- `phone_validator_testcases.xls`

được thiết kế để bao phủ các đường đi quan trọng ở trên (bao gồm boundary và lỗi phổ biến), thỏa **CC=8** ở mức khái niệm và cho coverage cao khi đo bằng JaCoCo.

## 4. Chạy test + xem coverage (JaCoCo)

```bash
cd bai6.1
mvn clean test
```

JaCoCo report:

- `target/site/jacoco/index.html` – mở trong trình duyệt để xem % coverage.

