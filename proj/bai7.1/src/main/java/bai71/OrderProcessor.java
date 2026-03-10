package bai71;

import java.util.List;
import java.util.Objects;

public class OrderProcessor {

    public static class Item {
        private final double price;

        public Item(double price) {
            this.price = price;
        }

        public double getPrice() {
            return price;
        }
    }

    /**
     * Tính tổng tiền đơn hàng theo mô tả đề bài 7.1.
     *
     * @param items         danh sách item, không null/empty
     * @param couponCode    mã giảm giá: null/"" hoặc SALE10/SALE20 hoặc mã khác
     * @param memberLevel   cấp thành viên: GOLD, PLATINUM hoặc giá trị khác
     * @param paymentMethod phương thức thanh toán: COD hoặc phương thức khác (online)
     * @return tổng tiền sau giảm giá và cộng phí ship (nếu có)
     */
    public double calculateTotal(List<Item> items,
                                 String couponCode,
                                 String memberLevel,
                                 String paymentMethod) {
        if (items == null || items.isEmpty()) { // D1
            throw new IllegalArgumentException("Gio hang trong");
        }

        double subtotal = items.stream()
                .filter(Objects::nonNull)
                .mapToDouble(Item::getPrice)
                .sum();

        // Giam gia theo coupon
        double discount = 0;
        if (couponCode != null && !couponCode.isEmpty()) { // D2
            if (couponCode.equals("SALE10")) { // D3
                discount = subtotal * 0.10;
            } else if (couponCode.equals("SALE20")) { // D4
                discount = subtotal * 0.20;
            } else {
                throw new IllegalArgumentException("Ma giam gia khong hop le");
            }
        }

        // Giam gia theo thanh vien
        double memberDiscount = 0;
        if ("GOLD".equals(memberLevel)) { // D5
            memberDiscount = (subtotal - discount) * 0.05;
        } else if ("PLATINUM".equals(memberLevel)) { // D6
            memberDiscount = (subtotal - discount) * 0.10;
        }

        double total = subtotal - discount - memberDiscount;

        // Phi ship
        if (total < 500_000) { // D7
            if (!"COD".equals(paymentMethod)) { // D8
                total += 10_000; // phi ship online
            } else {
                total += 20_000; // phi ship COD
            }
        }

        return total;
    }
}

