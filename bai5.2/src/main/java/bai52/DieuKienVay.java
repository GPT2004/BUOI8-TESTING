package bai52;

public class DieuKienVay {

    /**
     * Hàm kiểm tra đủ điều kiện vay vốn theo bài 5.1.
     *
     * dieuKienCoBan = (tuoi >= 22) && (thuNhap >= 10_000_000)
     * dieuKienBaoDam = coTaiSanBaoLanh || (diemTinDung >= 700)
     * Kết quả = dieuKienCoBan && dieuKienBaoDam
     */
    public static boolean duDieuKienVay(int tuoi,
                                        double thuNhap,
                                        boolean coTaiSanBaoLanh,
                                        int diemTinDung) {
        boolean dieuKienCoBan = (tuoi >= 22) && (thuNhap >= 10_000_000);
        boolean dieuKienBaoDam = coTaiSanBaoLanh || (diemTinDung >= 700);
        return dieuKienCoBan && dieuKienBaoDam;
    }
}

