package bai61;

public class PhoneValidator {

    public static boolean isValid(String phone) {
        if (phone == null) {
            return false;
        }

        // Chỉ cho phép số, +, space
        boolean seenPlus = false;
        for (int i = 0; i < phone.length(); i++) {
            char c = phone.charAt(i);
            if (c == '+') {
                if (i != 0 || seenPlus) {
                    return false;
                }
                seenPlus = true;
            } else if (c == ' ') {
                continue;
            } else if (c < '0' || c > '9') {
                return false;
            }
        }

        // Bỏ tất cả space
        String cleaned = phone.replace(" ", "");
        if (cleaned.isEmpty()) {
            return false;
        }

        // Chuẩn hóa +84... -> 0...
        String normalized = cleaned;
        if (normalized.startsWith("+84")) {
            normalized = "0" + normalized.substring(3);
        }

        // Kiểm tra chỉ chứa số sau chuẩn hóa
        for (int i = 0; i < normalized.length(); i++) {
            char c = normalized.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }

        // Phải đúng 10 chữ số, bắt đầu bằng 0
        if (normalized.length() != 10 || normalized.charAt(0) != '0') {
            return false;
        }

        // Kiểm tra prefix: 03x, 05x, 07x, 08x, 09x
        char second = normalized.charAt(1);
        if (second != '3' && second != '5' && second != '7'
                && second != '8' && second != '9') {
            return false;
        }

        return true;
    }
}

