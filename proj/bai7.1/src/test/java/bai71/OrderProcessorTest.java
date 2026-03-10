package bai71;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Epic("E-commerce Order Processing")
@Feature("Order total calculation")
@Listeners({io.qameta.allure.testng.AllureTestNg.class})
public class OrderProcessorTest {

    private final OrderProcessor processor = new OrderProcessor();

    private List<OrderProcessor.Item> items(double... prices) {
        return Arrays.stream(prices)
                .mapToObj(OrderProcessor.Item::new)
                .toList();
    }

    // ========= Basis Path tests =========

    @Test(description = "BP1 - Gio hang null/empty -> throw exception")
    @Story("Validate cart input")
    @Severity(SeverityLevel.CRITICAL)
    @Description("D1 True: items == null || empty => throw IllegalArgumentException('Gio hang trong').")
    public void testEmptyCart_ThrowsException() {
        Allure.step("Goi calculateTotal voi items = null");
        Assert.expectThrows(IllegalArgumentException.class, () ->
                processor.calculateTotal(null, null, "NONE", "COD"));

        Allure.step("Goi calculateTotal voi items empty");
        Assert.expectThrows(IllegalArgumentException.class, () ->
                processor.calculateTotal(Collections.emptyList(), null, "NONE", "COD"));
    }

    @Test(description = "BP2 - Khong coupon, khong member, tong >= 500k, khong phi ship")
    @Story("Normal order without discounts")
    @Severity(SeverityLevel.NORMAL)
    public void testNoCoupon_NoMember_TotalOverThreshold_NoShippingFee() {
        List<OrderProcessor.Item> items = items(600_000);
        double total = processor.calculateTotal(items, null, "NONE", "COD");
        Allure.step("Tong >= 500k, khong coupon, khong member, khong phi ship");
        Assert.assertEquals(total, 600_000.0, 0.01, "Tong tien phai giu nguyen, khong phi ship");
    }

    @Test(description = "BP3 - Coupon SALE10, khong member, ship COD (<500k)")
    @Story("Coupon SALE10 with COD shipping")
    @Severity(SeverityLevel.CRITICAL)
    public void testSale10_NoMember_ShipCOD() {
        List<OrderProcessor.Item> items = items(400_000);
        double subtotal = 400_000;
        double discount = subtotal * 0.10;
        double expected = subtotal - discount + 20_000;

        double actual = processor.calculateTotal(items, "SALE10", "NONE", "COD");
        Allure.step("Ap dung SALE10, khong member, COD, them 20k phi ship");
        Assert.assertEquals(actual, expected, 0.01, "Tinh tong voi SALE10 + phi ship COD sai");
    }

    @Test(description = "BP4 - Coupon SALE20, khong member, ship online (<500k)")
    @Story("Coupon SALE20 with online shipping")
    @Severity(SeverityLevel.CRITICAL)
    public void testSale20_NoMember_ShipOnline() {
        List<OrderProcessor.Item> items = items(400_000);
        double subtotal = 400_000;
        double discount = subtotal * 0.20;
        double expected = subtotal - discount + 10_000;

        double actual = processor.calculateTotal(items, "SALE20", "NONE", "ONLINE");
        Allure.step("Ap dung SALE20, khong member, ONLINE, them 10k phi ship");
        Assert.assertEquals(actual, expected, 0.01, "Tinh tong voi SALE20 + phi ship online sai");
    }

    @Test(description = "BP5 - Coupon khong hop le -> throw exception")
    @Story("Invalid coupon handling")
    @Severity(SeverityLevel.CRITICAL)
    public void testInvalidCoupon_ThrowsException() {
        List<OrderProcessor.Item> items = items(300_000);
        Allure.step("Nhap coupon INVALID, mong doi IllegalArgumentException");
        IllegalArgumentException ex = Assert.expectThrows(IllegalArgumentException.class, () ->
                processor.calculateTotal(items, "ABC", "NONE", "COD"));
        Assert.assertTrue(ex.getMessage().contains("Ma giam gia khong hop le"),
                "Thong bao loi phai noi ro ma giam gia khong hop le");
    }

    @Test(description = "BP6 - Member GOLD, khong coupon, ship COD (<500k)")
    @Story("Member GOLD discount")
    @Severity(SeverityLevel.NORMAL)
    public void testMemberGold_DiscountAndShipCOD() {
        List<OrderProcessor.Item> items = items(400_000);
        double subtotal = 400_000;
        double memberDiscount = subtotal * 0.05;
        double expected = subtotal - memberDiscount + 20_000;

        double actual = processor.calculateTotal(items, null, "GOLD", "COD");
        Allure.step("Member GOLD giam 5%, ship COD 20k");
        Assert.assertEquals(actual, expected, 0.01, "Tinh tong cho GOLD + COD sai");
    }

    @Test(description = "BP7 - Member PLATINUM, co coupon SALE10, tong >= 500k, khong phi ship")
    @Story("PLATINUM + SALE10, free shipping")
    @Severity(SeverityLevel.NORMAL)
    public void testPlatinum_WithSale10_NoShipping() {
        List<OrderProcessor.Item> items = items(600_000);
        double subtotal = 600_000;
        double discount = subtotal * 0.10;
        double memberDiscount = (subtotal - discount) * 0.10;
        // total sau giam = 486_000 < 500_000 nen van bi tinh phi ship ONLINE 10k
        double expected = subtotal - discount - memberDiscount + 10_000;

        double actual = processor.calculateTotal(items, "SALE10", "PLATINUM", "ONLINE");
        Allure.step("PLATINUM + SALE10, tong sau giam < 500k nen van tinh phi ship ONLINE 10k");
        Assert.assertEquals(actual, expected, 0.01, "Tong sau giam cho PLATINUM + SALE10 sai");
    }

    @Test(description = "BP8 - Khong coupon, khong member, ship ONLINE (<500k)")
    @Story("No discount, online shipping")
    @Severity(SeverityLevel.MINOR)
    public void testNoDiscount_OnlineShipping() {
        List<OrderProcessor.Item> items = items(300_000);
        double subtotal = 300_000;
        double expected = subtotal + 10_000;

        double actual = processor.calculateTotal(items, null, "NONE", "ONLINE");
        Allure.step("Khong giam gia, ONLINE, them 10k");
        Assert.assertEquals(actual, expected, 0.01, "Tinh phi ship ONLINE sai");
    }

    @Test(description = "BP9 - Khong coupon, khong member, ship COD (<500k)")
    @Story("No discount, COD shipping")
    @Severity(SeverityLevel.MINOR)
    public void testNoDiscount_CodShipping() {
        List<OrderProcessor.Item> items = items(300_000);
        double subtotal = 300_000;
        double expected = subtotal + 20_000;

        double actual = processor.calculateTotal(items, null, "NONE", "COD");
        Allure.step("Khong giam gia, COD, them 20k");
        Assert.assertEquals(actual, expected, 0.01, "Tinh phi ship COD sai");
    }

    // ========= MC/DC cho dieu kien coupon (D2 & D3) =========
    //
    // A: couponCode != null
    // B: !couponCode.isEmpty()
    // C: couponCode.equals("SALE10")
    // Muc tieu: kiem tra viec ap dung giam gia 10% chi khi A,B,C deu True.

    @Test(description = "MC/DC - A=F, B=T, C=T (coupon=null) -> khong ap dung SALE10")
    @Story("MC/DC for coupon conditions")
    @Severity(SeverityLevel.CRITICAL)
    public void testMcdc_AFalse_BTrue_CTrue() {
        List<OrderProcessor.Item> items = items(400_000);
        double subtotal = 400_000;
        double expected = subtotal + 20_000; // khong giam gia, ship COD

        double actual = processor.calculateTotal(items, null, "NONE", "COD");
        Allure.step("A=F (coupon null) => khong ap dung SALE10");
        Assert.assertEquals(actual, expected, 0.01);
    }

    @Test(description = "MC/DC - A=T, B=F, C=T (coupon=\"\") -> khong ap dung SALE10")
    @Story("MC/DC for coupon conditions")
    @Severity(SeverityLevel.CRITICAL)
    public void testMcdc_ATrue_BFalse_CTrue() {
        List<OrderProcessor.Item> items = items(400_000);
        double subtotal = 400_000;
        double expected = subtotal + 20_000; // khong giam gia, ship COD

        double actual = processor.calculateTotal(items, "", "NONE", "COD");
        Allure.step("B=F (coupon empty) => khong ap dung SALE10");
        Assert.assertEquals(actual, expected, 0.01);
    }

    @Test(description = "MC/DC - A=T, B=T, C=F (coupon=SALE20) -> khong ap dung SALE10, ap dung giam 20%")
    @Story("MC/DC for coupon conditions")
    @Severity(SeverityLevel.CRITICAL)
    public void testMcdc_ATrue_BTrue_CFalse() {
        List<OrderProcessor.Item> items = items(400_000);
        double subtotal = 400_000;
        double discount = subtotal * 0.20;
        double expected = subtotal - discount + 20_000;

        double actual = processor.calculateTotal(items, "SALE20", "NONE", "COD");
        Allure.step("C=F (khong phai SALE10) -> khong ap dung 10%, ma la 20%");
        Assert.assertEquals(actual, expected, 0.01);
    }

    @Test(description = "MC/DC - A=T, B=T, C=T (coupon=SALE10) -> ap dung giam 10%")
    @Story("MC/DC for coupon conditions")
    @Severity(SeverityLevel.CRITICAL)
    public void testMcdc_ATrue_BTrue_CTrue() {
        List<OrderProcessor.Item> items = items(400_000);
        double subtotal = 400_000;
        double discount = subtotal * 0.10;
        double expected = subtotal - discount + 20_000;

        double actual = processor.calculateTotal(items, "SALE10", "NONE", "COD");
        Allure.step("A,B,C deu True -> ap dung giam 10%");
        Assert.assertEquals(actual, expected, 0.01);
    }
}

