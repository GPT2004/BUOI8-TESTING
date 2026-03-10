package bai31;

import org.testng.Assert;
import org.testng.annotations.Test;

public class BranchCoverageTest {

    @Test(description = "TC1: Điều kiện 1 (invalid) = True -> Diem khong hop le")
    public void tc1_invalidScore() {
        String actual = XepLoai.xepLoai(-1, false);
        Assert.assertEquals(actual, "Diem khong hop le", "Invalid score phải trả về 'Diem khong hop le'");
    }

    @Test(description = "TC2: Điều kiện 2 = True -> Gioi")
    public void tc2_gioi() {
        String actual = XepLoai.xepLoai(9, false);
        Assert.assertEquals(actual, "Gioi", ">=8.5 phải là 'Gioi'");
    }

    @Test(description = "TC3: Điều kiện 3 = True (sau khi ĐK2 False) -> Kha")
    public void tc3_kha() {
        String actual = XepLoai.xepLoai(7, false);
        Assert.assertEquals(actual, "Kha", ">=7.0 và <8.5 phải là 'Kha'");
    }

    @Test(description = "TC4: Điều kiện 4 = True (sau khi ĐK2,3 False) -> Trung Binh")
    public void tc4_trungBinh() {
        String actual = XepLoai.xepLoai(6, false);
        Assert.assertEquals(actual, "Trung Binh", ">=5.5 và <7.0 phải là 'Trung Binh'");
    }

    @Test(description = "TC5: Điều kiện 5 = True (diemTB <5.5, coThiLai=true) -> Thi lai")
    public void tc5_thiLai() {
        String actual = XepLoai.xepLoai(5, true);
        Assert.assertEquals(actual, "Thi lai", "diemTB<5.5 và coThiLai=true phải là 'Thi lai'");
    }

    @Test(description = "TC6: Điều kiện 5 = False (diemTB <5.5, coThiLai=false) -> Yeu - Hoc lai")
    public void tc6_yeuHocLai() {
        String actual = XepLoai.xepLoai(5, false);
        Assert.assertEquals(actual, "Yeu - Hoc lai", "diemTB<5.5 và coThiLai=false phải là 'Yeu - Hoc lai'");
    }
}

