import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

class Diem2D {
    private double x;
    private double y;

    public Diem2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double khoangCach(Diem2D diemKhac) {
        return Math.hypot(x - diemKhac.x, y - diemKhac.y);
    }

    public Diem2D doiXungQuaGoc() {
        return new Diem2D(-x, -y);
    }

    public void tinhTien(double dx, double dy) {
        x += dx;
        y += dy;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "(%.2f, %.2f)", x, y);
    }
}

class Diem3D extends Diem2D {
    private double z;

    public Diem3D(double x, double y, double z) {
        super(x, y);
        this.z = z;
    }

    public double getZ() {
        return z;
    }

    public double khoangCach(Diem3D diemKhac) {
        double dx = getX() - diemKhac.getX();
        double dy = getY() - diemKhac.getY();
        double dz = z - diemKhac.z;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    @Override
    public Diem3D doiXungQuaGoc() {
        return new Diem3D(-getX(), -getY(), -z);
    }

    public void tinhTien(double dx, double dy, double dz) {
        super.tinhTien(dx, dy);
        z += dz;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "(%.2f, %.2f, %.2f)", getX(), getY(), z);
    }
}

public class Bai2_Diem {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            List<Diem2D> danhSach = new ArrayList<>();
            List<Diem2D> danhSach2D = new ArrayList<>();
            List<Diem3D> danhSach3D = new ArrayList<>();

            int soDiem2D = docSoLuong(scanner, "Nhap so diem 2D (n > 0): ");
            for (int i = 0; i < soDiem2D; i++) {
                System.out.printf("Nhap diem 2D thu %d:%n", i + 1);
                Diem2D diem = new Diem2D(
                        docSoThuc(scanner, "Nhap x: "),
                        docSoThuc(scanner, "Nhap y: "));
                danhSach2D.add(diem);
                danhSach.add(diem);
            }

            int soDiem3D = docSoLuong(scanner, "Nhap so diem 3D (n > 0): ");
            for (int i = 0; i < soDiem3D; i++) {
                System.out.printf("Nhap diem 3D thu %d:%n", i + 1);
                Diem3D diem = new Diem3D(
                        docSoThuc(scanner, "Nhap x: "),
                        docSoThuc(scanner, "Nhap y: "),
                        docSoThuc(scanner, "Nhap z: "));
                danhSach3D.add(diem);
                danhSach.add(diem);
            }

            double tong2D = tongKhoangCach2D(danhSach2D);
            double tong3D = tongKhoangCach3D(danhSach3D);
            System.out.printf(Locale.US,
                    "Tong khoang cach giua moi cap diem 2D: %.2f%n", tong2D);
            System.out.printf(Locale.US,
                    "Tong khoang cach giua moi cap diem 3D: %.2f%n", tong3D);

            int soLuongBanDau = danhSach.size();
            for (int i = 0; i < soLuongBanDau; i++) {
                // Tinh da hinh goi phien ban doi xung phu hop voi tung loai diem.
                danhSach.add(danhSach.get(i).doiXungQuaGoc());
            }

            System.out.println("\nDanh sach diem sau khi them cac diem doi xung:");
            for (Diem2D diem : danhSach) {
                String loai = diem instanceof Diem3D ? "Diem3D" : "Diem2D";
                System.out.println(loai + " " + diem);
            }
        }
    }

    private static double tongKhoangCach2D(List<Diem2D> danhSach) {
        double tong = 0.0;
        for (int i = 0; i < danhSach.size(); i++) {
            for (int j = i + 1; j < danhSach.size(); j++) {
                tong += danhSach.get(i).khoangCach(danhSach.get(j));
            }
        }
        return tong;
    }

    private static double tongKhoangCach3D(List<Diem3D> danhSach) {
        double tong = 0.0;
        for (int i = 0; i < danhSach.size(); i++) {
            for (int j = i + 1; j < danhSach.size(); j++) {
                tong += danhSach.get(i).khoangCach(danhSach.get(j));
            }
        }
        return tong;
    }

    private static int docSoLuong(Scanner scanner, String loiNhac) {
        int soLuong;
        do {
            soLuong = docSoNguyen(scanner, loiNhac);
            if (soLuong <= 0) {
                System.out.println("So luong phai lon hon 0.");
            }
        } while (soLuong <= 0);
        return soLuong;
    }

    private static int docSoNguyen(Scanner scanner, String loiNhac) {
        while (true) {
            System.out.print(loiNhac);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException ex) {
                System.out.println("Vui long nhap so nguyen hop le.");
            }
        }
    }

    private static double docSoThuc(Scanner scanner, String loiNhac) {
        while (true) {
            System.out.print(loiNhac);
            try {
                double giaTri = Double.parseDouble(scanner.nextLine().trim().replace(',', '.'));
                if (!Double.isInfinite(giaTri) && !Double.isNaN(giaTri)) {
                    return giaTri;
                }
                System.out.println("Vui long nhap so huu han.");
            } catch (NumberFormatException ex) {
                System.out.println("Vui long nhap so thuc hop le.");
            }
        }
    }
}