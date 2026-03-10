package bai52;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class VayVonMCDCTest - viết test TestNG từ MC/DC (bài 5.1).
 * Mỗi test tương ứng 1 dòng trong tập test MC/DC tối thiểu:
 * Rows: 2, 3, 4, 6, 9.
 */
public class VayVonMCDCTest {

    // Mapping giá trị:
    // A: tuoi >= 22       -> T: 25,  F: 20
    // B: thuNhap >= 10tr  -> T: 12_000_000, F: 8_000_000
    // C: coTaiSanBaoLanh  -> T/F
    // D: diemTinDung>=700 -> T: 720, F: 650

    @Test(description = "MC/DC - Điều kiện A độc lập (Row2 vs Row9)")
    public void testMCDC_A_DocLap_Tuoi() {
        // Row 2: A=T, B=T, C=T, D=F => expected true
        boolean r2 = DieuKienVay.duDieuKienVay(
                25,          // A=T
                12_000_000,  // B=T
                true,        // C=T
                650          // D=F
        );
        // Row 9: A=F, B=T, C=T, D=F => expected false
        boolean r9 = DieuKienVay.duDieuKienVay(
                20,          // A=F
                12_000_000,  // B=T
                true,        // C=T
                650          // D=F
        );

        Assert.assertTrue(r2, "Row 2 phải đủ điều kiện vay (A=T,B=T,C=T,D=F).");
        Assert.assertFalse(r9, "Row 9 phải không đủ điều kiện vay (A=F,B=T,C=T,D=F).");
    }

    @Test(description = "MC/DC - Điều kiện B độc lập (Row2 vs Row6)")
    public void testMCDC_B_DocLap_ThuNhap() {
        // Row 2: A=T, B=T, C=T, D=F => true
        boolean r2 = DieuKienVay.duDieuKienVay(
                25,          // A=T
                12_000_000,  // B=T
                true,        // C=T
                650          // D=F
        );
        // Row 6: A=T, B=F, C=T, D=F => false
        boolean r6 = DieuKienVay.duDieuKienVay(
                25,          // A=T
                8_000_000,   // B=F
                true,        // C=T
                650          // D=F
        );

        Assert.assertTrue(r2, "Row 2 phải đủ điều kiện vay.");
        Assert.assertFalse(r6, "Row 6 phải không đủ điều kiện vay (thuNhap không đạt).");
    }

    @Test(description = "MC/DC - Điều kiện C độc lập (Row2 vs Row4)")
    public void testMCDC_C_DocLap_TaiSanBaoLanh() {
        // Row 2: A=T, B=T, C=T, D=F => true
        boolean r2 = DieuKienVay.duDieuKienVay(
                25,          // A=T
                12_000_000,  // B=T
                true,        // C=T
                650          // D=F
        );
        // Row 4: A=T, B=T, C=F, D=F => false
        boolean r4 = DieuKienVay.duDieuKienVay(
                25,          // A=T
                12_000_000,  // B=T
                false,       // C=F
                650          // D=F
        );

        Assert.assertTrue(r2, "Row 2 phải đủ điều kiện vay.");
        Assert.assertFalse(r4, "Row 4 phải không đủ điều kiện vay (không TS, điểm thấp).");
    }

    @Test(description = "MC/DC - Điều kiện D độc lập (Row3 vs Row4)")
    public void testMCDC_D_DocLap_DiemTinDung() {
        // Row 3: A=T,B=T,C=F,D=T => true
        boolean r3 = DieuKienVay.duDieuKienVay(
                25,          // A=T
                12_000_000,  // B=T
                false,       // C=F
                720          // D=T
        );
        // Row 4: A=T,B=T,C=F,D=F => false
        boolean r4 = DieuKienVay.duDieuKienVay(
                25,          // A=T
                12_000_000,  // B=T
                false,       // C=F
                650          // D=F
        );

        Assert.assertTrue(r3, "Row 3 phải đủ điều kiện vay (điểm tín dụng cao).");
        Assert.assertFalse(r4, "Row 4 phải không đủ điều kiện vay (điểm tín dụng thấp).");
    }

    @Test(description = "MC/DC - Kiểm tra cả tập MC/DC tối thiểu đều đúng")
    public void testMCDC_TongHop() {
        // Row 2: T,T,T,F -> true
        Assert.assertTrue(DieuKienVay.duDieuKienVay(25, 12_000_000, true, 650),
                "Row2 phải true.");
        // Row 3: T,T,F,T -> true
        Assert.assertTrue(DieuKienVay.duDieuKienVay(25, 12_000_000, false, 720),
                "Row3 phải true.");
        // Row 4: T,T,F,F -> false
        Assert.assertFalse(DieuKienVay.duDieuKienVay(25, 12_000_000, false, 650),
                "Row4 phải false.");
        // Row 6: T,F,T,F -> false
        Assert.assertFalse(DieuKienVay.duDieuKienVay(25, 8_000_000, true, 650),
                "Row6 phải false.");
        // Row 9: F,T,T,F -> false
        Assert.assertFalse(DieuKienVay.duDieuKienVay(20, 12_000_000, true, 650),
                "Row9 phải false.");
    }
}

