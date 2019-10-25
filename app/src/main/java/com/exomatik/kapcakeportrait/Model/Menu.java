
package com.exomatik.kapcakeportrait.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Menu {

    @SerializedName("harga")
    @Expose
    private String harga;
    @SerializedName("jumlah")
    @Expose
    private Integer jumlah;
    @SerializedName("nama_variasi_menu")
    @Expose
    private String namaVariasiMenu;
    @SerializedName("nama_tipe_penjualan")
    @Expose
    private String namaTipePenjualan;
    @SerializedName("nama_menu")
    @Expose
    private String namaMenu;
    @SerializedName("total")
    @Expose
    private String total;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Menu() {
    }

    /**
     * 
     * @param total
     * @param namaMenu
     * @param namaTipePenjualan
     * @param namaVariasiMenu
     * @param jumlah
     * @param harga
     */
    public Menu(String harga, Integer jumlah, String namaVariasiMenu, String namaTipePenjualan, String namaMenu, String total) {
        super();
        this.harga = harga;
        this.jumlah = jumlah;
        this.namaVariasiMenu = namaVariasiMenu;
        this.namaTipePenjualan = namaTipePenjualan;
        this.namaMenu = namaMenu;
        this.total = total;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }

    public String getNamaVariasiMenu() {
        return namaVariasiMenu;
    }

    public void setNamaVariasiMenu(String namaVariasiMenu) {
        this.namaVariasiMenu = namaVariasiMenu;
    }

    public String getNamaTipePenjualan() {
        return namaTipePenjualan;
    }

    public void setNamaTipePenjualan(String namaTipePenjualan) {
        this.namaTipePenjualan = namaTipePenjualan;
    }

    public String getNamaMenu() {
        return namaMenu;
    }

    public void setNamaMenu(String namaMenu) {
        this.namaMenu = namaMenu;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

}
