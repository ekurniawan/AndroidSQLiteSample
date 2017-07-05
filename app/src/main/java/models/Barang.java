package models;

/**
 * Created by erick on 30/06/2017.
 */

public class Barang {
    private String BarangID;
    private int KategoriID;
    private String NamaBarang;
    private String Deskripsi;
    private int Stok;
    private double HargaBeli;
    private double HargaJual;
    private String Gambar;
    private int isSync;

    public String getBarangID() {
        return BarangID;
    }

    public void setBarangID(String barangID) {
        BarangID = barangID;
    }

    public int getKategoriID() {
        return KategoriID;
    }

    public void setKategoriID(int kategoriID) {
        KategoriID = kategoriID;
    }

    public String getNamaBarang() {
        return NamaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        NamaBarang = namaBarang;
    }

    public String getDeskripsi() {
        return Deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        Deskripsi = deskripsi;
    }

    public int getStok() {
        return Stok;
    }

    public void setStok(int stok) {
        Stok = stok;
    }

    public double getHargaBeli() {
        return HargaBeli;
    }

    public void setHargaBeli(double hargaBeli) {
        HargaBeli = hargaBeli;
    }

    public double getHargaJual() {
        return HargaJual;
    }

    public void setHargaJual(double hargaJual) {
        HargaJual = hargaJual;
    }

    public String getGambar() {
        return Gambar;
    }

    public void setGambar(String gambar) {
        Gambar = gambar;
    }

    public int isSync() {
        return isSync;
    }

    public void setSync(int sync) {
        isSync = sync;
    }
}
