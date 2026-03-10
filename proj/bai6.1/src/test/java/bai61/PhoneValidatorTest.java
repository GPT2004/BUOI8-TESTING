package bai61;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Bước 2 (RED) + Bước 3 (GREEN) của TDD:
 * Viết test trước cho hàm PhoneValidator.isValid(String).
 *
 * Luật số điện thoại Việt Nam hợp lệ (sau chuẩn hóa):
 * - Bắt đầu bằng 0 (hoặc đầu vào bắt đầu bằng +84, được chuẩn hóa về 0).
 * - Sau chuẩn hóa: dạng 0xxxxxxxxx (10 chữ số).
 * - Prefix hợp lệ: 03x, 05x, 07x, 08x, 09x.
 * - Trước chuẩn hóa chỉ cho phép: số 0-9, dấu +, và khoảng trắng.
 */
public class PhoneValidatorTest {

    @DataProvider(name = "validPhones")
    public Object[][] validPhones() {
        return new Object[][]{
                // 1. Số chuẩn đã đúng dạng 0xxxxxxxxx, prefix 09x
                {"0912345678", "Số 09x chuẩn 10 chữ số", true},
                // 2. Số 03x hợp lệ
                {"0351234567", "Số 03x chuẩn 10 chữ số", true},
                // 3. Số có +84 và space, sau chuẩn hóa về 09x
                {"+84 912345678", "+84 và space, chuẩn hóa về 0912345678", true},
                // 4. Số có space giữa nhưng hợp lệ sau bỏ space
                {"0 8 1 2 3 4 5 6 7 8", "Có space nhưng chỉ chứa số và space", true}
        };
    }

    @DataProvider(name = "invalidPhones")
    public Object[][] invalidPhones() {
        return new Object[][]{
                {null, "Null không hợp lệ", false},
                {"", "Chuỗi rỗng không hợp lệ", false},
                {"   ", "Chỉ space không hợp lệ", false},
                {"09123", "Độ dài < 10 sau chuẩn hóa", false},
                {"00123456789", "Độ dài > 10 sau chuẩn hóa", false},
                {"+849912345678", "Sau chuẩn hóa dài hơn 10 chữ số", false},
                {"0112345678", "Prefix 01x không hợp lệ", false},
                {"0212345678", "Prefix 02x không hợp lệ", false},
                {"a912345678", "Ký tự chữ cái không hợp lệ", false},
                {"09-12345678", "Ký tự '-' không hợp lệ", false},
                {"+849x234567", "Chứa chữ cái sau +84", false}
        };
    }

    @Test(dataProvider = "validPhones",
            description = "Các trường hợp số điện thoại hợp lệ theo luật VN")
    public void testValidPhones(String input, String message, boolean expected) {
        boolean actual = PhoneValidator.isValid(input);
        Assert.assertTrue(actual,
                "Expected VALID nhưng isValid trả về false. Case: " + message + ", input=" + input);
    }

    @Test(dataProvider = "invalidPhones",
            description = "Các trường hợp số điện thoại KHÔNG hợp lệ")
    public void testInvalidPhones(String input, String message, boolean expected) {
        boolean actual = PhoneValidator.isValid(input);
        Assert.assertFalse(actual,
                "Expected INVALID nhưng isValid trả về true. Case: " + message + ", input=" + input);
    }
}

