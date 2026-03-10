package bai72;

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

    public double calculateTotal(List<Item> items,
                                 String couponCode,
                                 String memberLevel,
                                 String paymentMethod) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Gio hang trong");
        }

        double subtotal = items.stream()
                .filter(Objects::nonNull)
                .mapToDouble(Item::getPrice)
                .sum();

        double discount = 0;
        if (couponCode != null && !couponCode.isEmpty()) {
            if (couponCode.equals("SALE10")) {
                discount = subtotal * 0.10;
            } else if (couponCode.equals("SALE20")) {
                discount = subtotal * 0.20;
            } else {
                throw new IllegalArgumentException("Ma giam gia khong hop le");
            }
        }

        double memberDiscount = 0;
        if ("GOLD".equals(memberLevel)) {
            memberDiscount = (subtotal - discount) * 0.05;
        } else if ("PLATINUM".equals(memberLevel)) {
            memberDiscount = (subtotal - discount) * 0.10;
        }

        double total = subtotal - discount - memberDiscount;

        if (total < 500_000) {
            if (!"COD".equals(paymentMethod)) {
                total += 10_000;
            } else {
                total += 20_000;
            }
        }

        return total;
    }
}

