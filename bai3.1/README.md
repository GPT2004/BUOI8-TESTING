# Bài 3.1 - Phân tích CFG và tính Coverage

## Hàm cần kiểm thử
File: `src/main/java/bai31/XepLoai.java`

```java
public static String xepLoai(int diemTB, boolean coThiLai) {
    if (diemTB < 0 || diemTB > 10) return "Diem khong hop le";        // ĐK1
    if (diemTB >= 8.5) return "Gioi";                                 // ĐK2
    else if (diemTB >= 7.0) return "Kha";                              // ĐK3
    else if (diemTB >= 5.5) return "Trung Binh";                       // ĐK4
    else { if (coThiLai) return "Thi lai"; }                            // ĐK5
    return "Yeu - Hoc lai";
}
```

## CFG (mô tả văn bản)
- **Entry**
- **Node 1 (ĐK1)**: `diemTB < 0 || diemTB > 10`
  - True  -> **Exit A**: `"Diem khong hop le"`
  - False -> Node 2
- **Node 2 (ĐK2)**: `diemTB >= 8.5`
  - True  -> **Exit B**: `"Gioi"`
  - False -> Node 3
- **Node 3 (ĐK3)**: `diemTB >= 7.0`
  - True  -> **Exit C**: `"Kha"`
  - False -> Node 4
- **Node 4 (ĐK4)**: `diemTB >= 5.5`
  - True  -> **Exit D**: `"Trung Binh"`
  - False -> Node 5
- **Node 5 (ĐK5)**: `coThiLai`
  - True  -> **Exit E**: `"Thi lai"`
  - False -> **Exit F**: `"Yeu - Hoc lai"`

## Statement Coverage (phủ lệnh)
Mục tiêu: mỗi câu lệnh/nhánh return được thực thi ít nhất 1 lần.

Tối thiểu để đạt **100% Statement Coverage**: 6 test case để đi qua 6 kết quả trả về:
| TC | diemTB | coThiLai | Kết quả mong đợi | Đường đi |
|---|---:|:---:|---|---|
| TC1 | -1 | F | Diem khong hop le | Entry -> ĐK1(T) -> ExitA |
| TC2 | 9 | F | Gioi | Entry -> ĐK1(F) -> ĐK2(T) -> ExitB |
| TC3 | 7 | F | Kha | Entry -> ĐK1(F) -> ĐK2(F) -> ĐK3(T) -> ExitC |
| TC4 | 6 | F | Trung Binh | Entry -> ĐK1(F) -> ĐK2(F) -> ĐK3(F) -> ĐK4(T) -> ExitD |
| TC5 | 5 | T | Thi lai | Entry -> ĐK1(F) -> ĐK2(F) -> ĐK3(F) -> ĐK4(F) -> ĐK5(T) -> ExitE |
| TC6 | 5 | F | Yeu - Hoc lai | Entry -> ĐK1(F) -> ĐK2(F) -> ĐK3(F) -> ĐK4(F) -> ĐK5(F) -> ExitF |

## Branch Coverage (phủ nhánh)
Mục tiêu: mỗi điều kiện phải chạy đủ **True** và **False**.

Các nhánh cần phủ:
- ĐK1 True/False
- ĐK2 True/False
- ĐK3 True/False
- ĐK4 True/False
- ĐK5 True/False

Bộ TC1..TC6 ở trên phủ đủ **100% Branch Coverage**:
- **ĐK1-True**: TC1, **ĐK1-False**: TC2..TC6
- **ĐK2-True**: TC2, **ĐK2-False**: TC3..TC6
- **ĐK3-True**: TC3, **ĐK3-False**: TC4..TC6
- **ĐK4-True**: TC4, **ĐK4-False**: TC5..TC6
- **ĐK5-True**: TC5, **ĐK5-False**: TC6

## Code TestNG cho Branch Coverage
File: `src/test/java/bai31/BranchCoverageTest.java` (đã implement TC1..TC6).

## Chạy test + report HTML để chụp
```bash
cd bai3.1
mvn test
mvn surefire-report:report
```

Mở để chụp màn hình:
- `target/surefire-reports/index.html`
- `target/surefire-reports/emailable-report.html`
- `target/site/surefire-report.html`

