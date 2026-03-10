package bai41;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TinhPhiShipBasisPathTest {

    // Path 1 (D1 True): trongLuong <= 0 -> exception
    @Test(expectedExceptions = IllegalArgumentException.class,
          description = "Path 1: D1 True -> throw IllegalArgumentException")
    public void path1_trongLuong_khong_hop_le() {
        TinhPhiShip.tinhPhiShip(0, "noi_thanh", false);
    }

    // Path 2 (baseline noi_thanh, D3 False, laMember False)
    @Test(description = "Path 2: noi_thanh, trongLuong<=5, không member")
    public void path2_noiThanh_nhe_khongMember() {
        double phi = TinhPhiShip.tinhPhiShip(3, "noi_thanh", false);
        Assert.assertEquals(phi, 15000.0, "3kg noi_thanh, non-member => 15000");
    }

    // Path 3 (noi_thanh, D3 True, laMember False)
    @Test(description = "Path 3: noi_thanh, trongLuong>5, không member")
    public void path3_noiThanh_nang_khongMember() {
        double phi = TinhPhiShip.tinhPhiShip(10, "noi_thanh", false);
        // phi = 15000 + (10-5)*2000 = 15000 + 10000 = 25000
        Assert.assertEquals(phi, 25000.0, "10kg noi_thanh, non-member => 25000");
    }

    // Path 4 (noi_thanh, D3 False, laMember True)
    @Test(description = "Path 4: noi_thanh, trongLuong<=5, member (giảm 10%)")
    public void path4_noiThanh_nhe_member() {
        double phi = TinhPhiShip.tinhPhiShip(4, "noi_thanh", true);
        // base = 15000, member => *0.9 = 13500
        Assert.assertEquals(phi, 13500.0, "4kg noi_thanh, member => 13500");
    }

    // Path 5 (ngoai_thanh, D5 False, laMember False)
    @Test(description = "Path 5: ngoai_thanh, trongLuong<=3, không member")
    public void path5_ngoaiThanh_nhe_khongMember() {
        double phi = TinhPhiShip.tinhPhiShip(2, "ngoai_thanh", false);
        Assert.assertEquals(phi, 25000.0, "2kg ngoai_thanh, non-member => 25000");
    }

    // Path 6 (ngoai_thanh, D5 True, laMember False)
    @Test(description = "Path 6: ngoai_thanh, trongLuong>3, không member")
    public void path6_ngoaiThanh_nang_khongMember() {
        double phi = TinhPhiShip.tinhPhiShip(5, "ngoai_thanh", false);
        // phi = 25000 + (5-3)*3000 = 25000 + 6000 = 31000
        Assert.assertEquals(phi, 31000.0, "5kg ngoai_thanh, non-member => 31000");
    }

    // Path 7 (tinh_khac, D6 False, laMember False)
    @Test(description = "Path 7: tinh_khac, trongLuong<=2, không member")
    public void path7_tinhKhac_nhe_khongMember() {
        double phi = TinhPhiShip.tinhPhiShip(1.5, "tinh_khac", false);
        Assert.assertEquals(phi, 50000.0, "1.5kg tinh_khac, non-member => 50000");
    }

    // Path 8 (tinh_khac, D6 True, laMember True)
    @Test(description = "Path 8: tinh_khac, trongLuong>2, member (giảm 10%)")
    public void path8_tinhKhac_nang_member() {
        double phi = TinhPhiShip.tinhPhiShip(5, "tinh_khac", true);
        // phi gốc = 50000 + (5-2)*5000 = 50000 + 15000 = 65000
        // member => *0.9 = 58500
        Assert.assertEquals(phi, 58500.0, "5kg tinh_khac, member => 58500");
    }
}

