import java.util.ArrayList;
import java.util.Scanner;

// Kelas Abstrak MenuItem
abstract class MenuItem {
    protected String nama;
    protected double harga;
    protected String kategori;

    public MenuItem(String nama, double harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }

    public String getNama() {
        return nama;
    }

    public abstract void tampilMenu();
}

// Kelas Makanan
class Makanan extends MenuItem {
    private String jenisMakanan;

    public Makanan(String nama, double harga, String jenisMakanan) {
        super(nama, harga, "Makanan");
        this.jenisMakanan = jenisMakanan;
    }

    @Override
    public void tampilMenu() {
        System.out.println("Makanan: " + nama + " - Rp" + harga + " (" + jenisMakanan + ")");
    }
}

// Kelas Minuman
class Minuman extends MenuItem {
    private String jenisMinuman;

    public Minuman(String nama, double harga, String jenisMinuman) {
        super(nama, harga, "Minuman");
        this.jenisMinuman = jenisMinuman;
    }

    @Override
    public void tampilMenu() {
        System.out.println("Minuman: " + nama + " - Rp" + harga + " (" + jenisMinuman + ")");
    }
}

// Kelas Diskon
class Diskon extends MenuItem {
    private double diskon;

    public Diskon(String nama, double harga, double diskon) {
        super(nama, harga, "Diskon");
        this.diskon = diskon;
    }

    @Override
    public void tampilMenu() {
        System.out.println("Diskon: " + nama + " - Diskon sebesar " + diskon + "%");
    }

    public double hitungDiskon() {
        return harga * (1 - diskon / 100);
    }
}

// Kelas Utama
public class ManajemenRestoran {
    private ArrayList<MenuItem> daftarMenu = new ArrayList<>();

    public static void main(String[] args) {
        ManajemenRestoran restoran = new ManajemenRestoran();
        restoran.menuUtama();
    }

    public void menuUtama() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Sistem Manajemen Restoran ===");
            System.out.println("1. Tambah Item Menu");
            System.out.println("2. Tampilkan Menu");
            System.out.println("3. Buat Pesanan");
            System.out.println("4. Keluar");
            System.out.print("Pilih opsi: ");
            int pilihan = scanner.nextInt();
            scanner.nextLine(); // Membersihkan buffer

            switch (pilihan) {
                case 1:
                    tambahMenu(scanner);
                    break;
                case 2:
                    tampilkanMenu();
                    break;
                case 3:
                    buatPesanan(scanner);
                    break;
                case 4:
                    System.out.println("Terima kasih telah menggunakan sistem!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Pilihan tidak valid, coba lagi.");
            }
        }
    }

    public void tambahMenu(Scanner scanner) {
        System.out.println("\nPilih jenis item:");
        System.out.println("1. Makanan");
        System.out.println("2. Minuman");
        System.out.println("3. Diskon");
        System.out.print("Jenis: ");
        int jenis = scanner.nextInt();
        scanner.nextLine(); // Membersihkan buffer

        System.out.print("Masukkan nama: ");
        String nama = scanner.nextLine();
        System.out.print("Masukkan harga: ");
        double harga = scanner.nextDouble();
        scanner.nextLine();

        switch (jenis) {
            case 1:
                System.out.print("Masukkan jenis makanan (contoh: Pedas, Manis): ");
                String jenisMakanan = scanner.nextLine();
                daftarMenu.add(new Makanan(nama, harga, jenisMakanan));
                break;
            case 2:
                System.out.print("Masukkan jenis minuman (contoh: Dingin, Panas): ");
                String jenisMinuman = scanner.nextLine();
                daftarMenu.add(new Minuman(nama, harga, jenisMinuman));
                break;
            case 3:
                System.out.print("Masukkan persentase diskon (contoh: 10): ");
                double diskon = scanner.nextDouble();
                daftarMenu.add(new Diskon(nama, harga, diskon));
                break;
            default:
                System.out.println("Jenis tidak valid!");
        }
        System.out.println("Item menu berhasil ditambahkan.");
    }

    public void tampilkanMenu() {
        if (daftarMenu.isEmpty()) {
            System.out.println("Menu kosong.");
        } else {
            System.out.println("\n=== Daftar Menu ===");
            for (MenuItem item : daftarMenu) {
                item.tampilMenu();
            }
        }
    }

    public void buatPesanan(Scanner scanner) {
        if (daftarMenu.isEmpty()) {
            System.out.println("Menu kosong, tidak ada yang bisa dipesan.");
            return;
        }

        System.out.println("\n=== Buat Pesanan ===");
        ArrayList<MenuItem> pesanan = new ArrayList<>();
        tampilkanMenu();
        double total = 0;

        while (true) {
            System.out.print("Masukkan nama item (atau ketik 'selesai' untuk mengakhiri): ");
            String namaItem = scanner.nextLine();
            if (namaItem.equalsIgnoreCase("selesai")) {
                break;
            }

            MenuItem itemDipilih = null;
            for (MenuItem item : daftarMenu) {
                if (item.getNama().equalsIgnoreCase(namaItem)) {
                    itemDipilih = item;
                    break;
                }
            }

            if (itemDipilih != null) {
                pesanan.add(itemDipilih);
                if (itemDipilih instanceof Diskon) {
                    total += ((Diskon) itemDipilih).hitungDiskon();
                } else {
                    total += itemDipilih.harga;
                }
                System.out.println("Item berhasil ditambahkan ke pesanan.");
            } else {
                System.out.println("Item tidak ditemukan.");
            }
        }

        System.out.println("\n=== Struk Pesanan ===");
        for (MenuItem item : pesanan) {
            item.tampilMenu();
        }
        System.out.println("Total Biaya: Rp" + total);
    }
}
