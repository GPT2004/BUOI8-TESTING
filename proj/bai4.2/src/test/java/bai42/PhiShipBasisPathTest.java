package bai42;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PhiShipBasisPathTest {

    @Test(description = "Path 1: Trong lượng không hợp lệ (trongLuong <= 0)")
    public void testPath1_InvalidWeight() {
        Assert.expectThrows(IllegalArgumentException.class,
                () -> TinhPhiShip.tinhPhiShip(0, "noi_thanh", false));
    }

    @Test(description = "Path 2: Nội thành, <= 5kg, không member")
    public void testPath2_NoiThanhNheKhongMember() {
        double expected = 15000.0;
        double actual = TinhPhiShip.tinhPhiShip(3, "noi_thanh", false);
        Assert.assertEquals(actual, expected, 0.01,
                "3kg noi_thanh, non-member phải ~15000");
    }

    @Test(description = "Path 3: Nội thành, > 5kg, không member")
    public void testPath3_NoiThanhNangKhongMember() {
        double expected = 15000 + (10 - 5) * 2000; // 25000
        double actual = TinhPhiShip.tinhPhiShip(10, "noi_thanh", false);
        Assert.assertEquals(actual, expected, 0.01,
                "10kg noi_thanh, non-member phải ~25000");
    }

    @Test(description = "Path 4: Nội thành, <= 5kg, member (giảm 10%)")
    public void testPath4_NoiThanhNheMember() {
        double base = 15000;
        double expected = base * 0.9; // 13500
        double actual = TinhPhiShip.tinhPhiShip(4, "noi_thanh", true);
        Assert.assertEquals(actual, expected, 0.01,
                "4kg noi_thanh, member phải ~13500");
    }

    @Test(description = "Path 5: Ngoại thành, <= 3kg, không member")
    public void testPath5_NgoaiThanhNheKhongMember() {
        double expected = 25000.0;
        double actual = TinhPhiShip.tinhPhiShip(2, "ngoai_thanh", false);
        Assert.assertEquals(actual, expected, 0.01,
                "2kg ngoai_thanh, non-member phải ~25000");
    }

    @Test(description = "Path 6: Ngoại thành, > 3kg, không member")
    public void testPath6_NgoaiThanhNangKhongMember() {
        double expected = 25000 + (5 - 3) * 3000; // 31000
        double actual = TinhPhiShip.tinhPhiShip(5, "ngoai_thanh", false);
        Assert.assertEquals(actual, expected, 0.01,
                "5kg ngoai_thanh, non-member phải ~31000");
    }

    @Test(description = "Path 7: Tỉnh khác, <= 2kg, không member")
    public void testPath7_TinhKhacNheKhongMember() {
        double expected = 50000.0;
        double actual = TinhPhiShip.tinhPhiShip(1.5, "tinh_khac", false);
        Assert.assertEquals(actual, expected, 0.01,
                "1.5kg tinh_khac, non-member phải ~50000");
    }

    @Test(description = "Path 8: Tỉnh khác, > 2kg, member (giảm 10%)")
    public void testPath8_TinhKhacNangMember() {
        double base = 50000 + (5 - 2) * 5000; // 65000
        double expected = base * 0.9; // 58500
        double actual = TinhPhiShip.tinhPhiShip(5, "tinh_khac", true);
        Assert.assertEquals(actual, expected, 0.01,
                "5kg tinh_khac, member phải ~58500");
    }
}

