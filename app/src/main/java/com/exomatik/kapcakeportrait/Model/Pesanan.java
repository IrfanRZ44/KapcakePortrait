
package com.exomatik.kapcakeportrait.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Pesanan {

    @SerializedName("menu")
    @Expose
    private List<Menu> menu = null;
    @SerializedName("nama_tipe_penjualan")
    @Expose
    private String namaTipePenjualan;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Pesanan() {
    }

    /**
     * 
     * @param menu
     * @param namaTipePenjualan
     */
    public Pesanan(List<Menu> menu, String namaTipePenjualan) {
        super();
        this.menu = menu;
        this.namaTipePenjualan = namaTipePenjualan;
    }

    public List<Menu> getMenu() {
        return menu;
    }

    public void setMenu(List<Menu> menu) {
        this.menu = menu;
    }

    public String getNamaTipePenjualan() {
        return namaTipePenjualan;
    }

    public void setNamaTipePenjualan(String namaTipePenjualan) {
        this.namaTipePenjualan = namaTipePenjualan;
    }

}
