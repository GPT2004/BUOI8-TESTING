package bai41;

public class TinhPhiShip {

    /**
     * Hàm tính phí ship hàng hóa theo đề bài 4.1.
     *
     * @param trongLuong trọng lượng (kg)
     * @param vung       "noi_thanh", "ngoai_thanh" hoặc khác (tinh_khac)
     * @param laMember   có phải khách hàng thành viên hay không
     * @return phí ship
     */
    public static double tinhPhiShip(double trongLuong, String vung, boolean laMember) {
        if (trongLuong <= 0) { // D1
            throw new IllegalArgumentException("Trong luong phai > 0");
        }

        double phi = 0;
        if ("noi_thanh".equals(vung)) { // D2
            phi = 15000;
            if (trongLuong > 5) { // D3
                phi += (trongLuong - 5) * 2000;
            }
        } else if ("ngoai_thanh".equals(vung)) { // D4
            phi = 25000;
            if (trongLuong > 3) { // D5
                phi += (trongLuong - 3) * 3000;
            }
        } else { // vùng / tỉnh khác
            phi = 50000;
            if (trongLuong > 2) { // D6
                phi += (trongLuong - 2) * 5000;
            }
        }

        if (laMember) { // D7
            phi = phi * 0.9; // giảm 10%
        }

        return phi;
    }
}

