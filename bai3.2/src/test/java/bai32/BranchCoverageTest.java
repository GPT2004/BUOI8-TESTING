package bai32;

import org.testng.Assert;
import org.testng.annotations.Test;

public class BranchCoverageTest {

    @Test(description = "TC1: N1 True (soM3<=0) -> 0")
    public void tc1_soM3_le_0() {
        Assert.assertEquals(TienNuoc.tinhTienNuoc(0, "ho_ngheo"), 0.0,
                "soM3<=0 phải trả về 0");
    }

    @Test(description = "TC2: N2 True (ho_ngheo) -> don_gia=5000")
    public void tc2_ho_ngheo() {
        Assert.assertEquals(TienNuoc.tinhTienNuoc(5, "ho_ngheo"), 25000.0,
                "5m3 * 5000 = 25000");
    }

    @Test(description = "TC3: N3 True (dan_cu), N4 True (<=10) -> 7500")
    public void tc3_dan_cu_le_10() {
        Assert.assertEquals(TienNuoc.tinhTienNuoc(10, "dan_cu"), 75000.0,
                "10m3 * 7500 = 75000");
    }

    @Test(description = "TC4: N3 True (dan_cu), N4 False, N5 True (<=20) -> 9900")
    public void tc4_dan_cu_11_20() {
        Assert.assertEquals(TienNuoc.tinhTienNuoc(15, "dan_cu"), 148500.0,
                "15m3 * 9900 = 148500");
    }

    @Test(description = "TC5: N3 True (dan_cu), N4 False, N5 False (>20) -> 11400")
    public void tc5_dan_cu_gt_20() {
        Assert.assertEquals(TienNuoc.tinhTienNuoc(25, "dan_cu"), 285000.0,
                "25m3 * 11400 = 285000");
    }

    @Test(description = "TC6: N2 False, N3 False -> else (kinh_doanh/khac) don_gia=22000")
    public void tc6_kinh_doanh() {
        Assert.assertEquals(TienNuoc.tinhTienNuoc(5, "kinh_doanh"), 110000.0,
                "5m3 * 22000 = 110000");
    }
}

