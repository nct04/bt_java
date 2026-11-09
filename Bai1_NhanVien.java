import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

class Nguoi {
    private String hoTen;
    private int tuoi;

    public void nhap(Scanner scanner) {
        hoTen = Bai1_NhanVien.docChuoi(scanner, "Ho ten: ");
        tuoi = Bai1_NhanVien.docSoNguyenKhongAm(scanner, "Tuoi: ");
    }

    public void xuat() {
        System.out.printf("Ho ten: %s | Tuoi: %d", hoTen, tuoi);
    }

    public String getHoTen() {
        return hoTen;
    }

    public int getTuoi() {
        return tuoi;
    }
}

class NhanVien extends Nguoi {
    private static final double LUONG_CO_BAN = 1_500_000.0;
    private static final double PHU_CAP_MOI_NAM = 200_000.0;

    private int snct;
    private double hsl;

    @Override
    public void nhap(Scanner scanner) {
        super.nhap(scanner);
        snct = Bai1_NhanVien.docSoNguyenKhongAm(scanner, "So nam cong tac: ");
        hsl = Bai1_NhanVien.docSoThucKhongAm(scanner, "He so luong: ");
    }

    public double tinhTienLuong() {
        return LUONG_CO_BAN * hsl + PHU_CAP_MOI_NAM * snct;
    }

    @Override
    public void xuat() {
        super.xuat();
        NumberFormat dinhDangTien = NumberFormat.getIntegerInstance(new Locale("vi", "VN"));
        System.out.printf(Locale.US,
                " | So nam cong tac: %d | He so luong: %.2f | Tien luong: %s dong%n",
                snct, hsl, dinhDangTien.format(tinhTienLuong()));
    }

    public int getSnct() {
        return snct;
    }

    public double getHsl() {
        return hsl;
    }
}

public class Bai1_NhanVien {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = docSoNguyenDuong(scanner, "Nhap so nhan vien (n > 0): ");
            List<NhanVien> danhSach = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                System.out.printf("\nNhap nhan vien thu %d:%n", i + 1);
                NhanVien nhanVien = new NhanVien();
                nhanVien.nhap(scanner);
                danhSach.add(nhanVien);
            }

            int snctMax = danhSach.get(0).getSnct();
            for (NhanVien nhanVien : danhSach) {
                if (nhanVien.getSnct() > snctMax) {
                    snctMax = nhanVien.getSnct();
                }
            }

            System.out.println("\nNhan vien co so nam cong tac nhieu nhat:");
            for (NhanVien nhanVien : danhSach) {
                if (nhanVien.getSnct() == snctMax) {
                    nhanVien.xuat();
                }
            }

            danhSach.sort(Comparator.comparingDouble(NhanVien::tinhTienLuong).reversed());
            System.out.println("\nDanh sach nhan vien theo luong tu cao den thap:");
            for (int i = 0; i < danhSach.size(); i++) {
                System.out.printf("%d. ", i + 1);
                danhSach.get(i).xuat();
            }
        }
    }

    static String docChuoi(Scanner scanner, String loiNhac) {
        while (true) {
            System.out.print(loiNhac);
            String giaTri = scanner.nextLine().trim();
            if (!giaTri.isEmpty()) {
                return giaTri;
            }
            System.out.println("Gia tri khong duoc de trong.");
        }
    }

    static int docSoNguyen(Scanner scanner, String loiNhac) {
        while (true) {
            System.out.print(loiNhac);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException ex) {
                System.out.println("Vui long nhap so nguyen hop le.");
            }
        }
    }

    static int docSoNguyenKhongAm(Scanner scanner, String loiNhac) {
        int giaTri;
        do {
            giaTri = docSoNguyen(scanner, loiNhac);
            if (giaTri < 0) {
                System.out.println("Gia tri khong duoc am.");
            }
        } while (giaTri < 0);
        return giaTri;
    }

    static int docSoNguyenDuong(Scanner scanner, String loiNhac) {
        int giaTri;
        do {
            giaTri = docSoNguyen(scanner, loiNhac);
            if (giaTri <= 0) {
                System.out.println("So luong phai lon hon 0.");
            }
        } while (giaTri <= 0);
        return giaTri;
    }

    static double docSoThucKhongAm(Scanner scanner, String loiNhac) {
        while (true) {
            System.out.print(loiNhac);
            try {
                double giaTri = Double.parseDouble(scanner.nextLine().trim().replace(',', '.'));
                if (giaTri >= 0 && !Double.isInfinite(giaTri)) {
                    return giaTri;
                }
                System.out.println("Gia tri phai khong am va huu han.");
            } catch (NumberFormatException ex) {
                System.out.println("Vui long nhap so thuc hop le.");
            }
        }
    }
}