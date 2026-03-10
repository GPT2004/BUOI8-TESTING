# Bài 3.2 - Statement & Branch Coverage thực tế

## Hàm cần kiểm thử
File: `src/main/java/bai32/TienNuoc.java`

Logic (đúng theo đề):
- N1: `soM3 <= 0` -> return 0
- Nếu `loaiKhachHang == "ho_ngheo"` -> `don_gia = 5000`
- Else if `loaiKhachHang == "dan_cu"`:
  - N4: `soM3 <= 10` -> `don_gia = 7500`
  - Else if N5: `soM3 <= 20` -> `don_gia = 9900`
  - Else -> `don_gia = 11400`
- Else (kinh_doanh/khác) -> `don_gia = 22000`
- return `soM3 * don_gia`

## CFG
Bạn có thể vẽ CFG bằng Mermaid (paste vào draw.io):
- `cfg_bai3_2.mmd`

## Đếm Statement và Branch
### Branch (nhánh)
Có 5 điều kiện (N1..N5), mỗi điều kiện có True/False:
- Tổng nhánh = 5 * 2 = **10 nhánh**

### Edge (cạnh) trong CFG
Đánh số cạnh theo CFG ở `cfg_bai3_2.mmd`:
- **E1**: Entry -> N1
- **E2**: N1 True -> return 0
- **E3**: N1 False -> N2
- **E4**: N2 True -> don_gia=5000
- **E5**: don_gia=5000 -> return soM3*don_gia
- **E6**: N2 False -> N3
- **E7**: N3 True -> N4
- **E8**: N3 False -> don_gia=22000
- **E9**: don_gia=22000 -> return soM3*don_gia
- **E10**: N4 True -> don_gia=7500
- **E11**: don_gia=7500 -> return soM3*don_gia
- **E12**: N4 False -> N5
- **E13**: N5 True -> don_gia=9900
- **E14**: don_gia=9900 -> return soM3*don_gia
- **E15**: N5 False -> don_gia=11400
- **E16**: don_gia=11400 -> return soM3*don_gia

Tổng số edge (cạnh) = **16**

### Statement (lệnh)
Đếm theo các câu lệnh thực thi chính trong code:
- S1: `if (soM3 <= 0) return 0;`
- S2: `double don_gia;`
- S3: `don_gia = 5000;`
- S4: `don_gia = 7500;`
- S5: `don_gia = 9900;`
- S6: `don_gia = 11400;`
- S7: `don_gia = 22000;`
- S8: `return soM3 * don_gia;`

Tổng số statement (lệnh) = **8**

## Test suite tối thiểu đạt 100% Branch Coverage
| TC | soM3 | loaiKhachHang | don_gia mong đợi | Kết quả mong đợi |
|---|---:|---|---:|---:|
| TC1 | 0  | ho_ngheo    | -     | 0 |
| TC2 | 5  | ho_ngheo    | 5000  | 25000 |
| TC3 | 10 | dan_cu      | 7500  | 75000 |
| TC4 | 15 | dan_cu      | 9900  | 148500 |
| TC5 | 25 | dan_cu      | 11400 | 285000 |
| TC6 | 5  | kinh_doanh  | 22000 | 110000 |

### Mapping phủ nhánh (True/False)
- **N1-True**:  TC1
- **N1-False**: TC2..TC6
- **N2-True**:  TC2
- **N2-False**: TC3..TC6
- **N3-True**:  TC3, TC4, TC5
- **N3-False**: TC6
- **N4-True**:  TC3
- **N4-False**: TC4, TC5
- **N5-True**:  TC4
- **N5-False**: TC5

## Code TestNG (chạy các TC)
File: `src/test/java/bai32/BranchCoverageTest.java`

## Chạy test + report HTML để chụp
```bash
cd bai3.2
mvn test
mvn surefire-report:report
```

Mở để chụp màn hình:
- `target/surefire-reports/index.html`
- `target/surefire-reports/emailable-report.html`
- `target/site/surefire-report.html`

