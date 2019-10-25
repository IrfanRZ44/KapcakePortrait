
package com.exomatik.kapcakeportrait.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelPesanan {

    @SerializedName("instagram")
    @Expose
    private String instagram;
    @SerializedName("url_logo")
    @Expose
    private String url_logo;
    @SerializedName("waktu_proses")
    @Expose
    private String waktuProses;
    @SerializedName("twitter")
    @Expose
    private String twitter;
    @SerializedName("kembalian")
    @Expose
    private String kembalian;
    @SerializedName("pesanan")
    @Expose
    private List<Pesanan> pesanan = null;
    @SerializedName("jumlah_pajak")
    @Expose
    private String jumlahPajak;
    @SerializedName("tanggal_proses")
    @Expose
    private String tanggalProses;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("alamat")
    @Expose
    private String alamat;
    @SerializedName("nama_outlet")
    @Expose
    private String namaOutlet;
    @SerializedName("kode_pemesanan")
    @Expose
    private String kodePemesanan;
    @SerializedName("nama_pajak")
    @Expose
    private String namaPajak;
    @SerializedName("total_pajak")
    @Expose
    private String totalPajak;
    @SerializedName("catatan")
    @Expose
    private String catatan;
    @SerializedName("total_biaya_tambahan")
    @Expose
    private String totalBiayaTambahan;
    @SerializedName("website")
    @Expose
    private String website;
    @SerializedName("nama_diskon")
    @Expose
    private String namaDiskon;
    @SerializedName("jumlah_biaya_tambahan")
    @Expose
    private String jumlahBiayaTambahan;
    @SerializedName("nama_user")
    @Expose
    private String namaUser;
    @SerializedName("facebook")
    @Expose
    private String facebook;
    @SerializedName("telpon")
    @Expose
    private String telpon;
    @SerializedName("no_pemesanan")
    @Expose
    private String noPemesanan;
    @SerializedName("subtotal")
    @Expose
    private String subtotal;
    @SerializedName("nama_pelayan")
    @Expose
    private String namaPelayan;
    @SerializedName("nama_biaya_tambahan")
    @Expose
    private String namaBiayaTambahan;
    @SerializedName("total_diskon")
    @Expose
    private String totalDiskon;
    @SerializedName("tunai")
    @Expose
    private String tunai;
    @SerializedName("jumlah_diskon")
    @Expose
    private String jumlahDiskon;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ModelPesanan() {
    }

    /**
     * 
     * @param total
     * @param namaBiayaTambahan
     * @param instagram
     * @param telpon
     * @param kodePemesanan
     * @param twitter
     * @param namaDiskon
     * @param namaOutlet
     * @param namaPajak
     * @param tunai
     * @param namaUser
     * @param facebook
     * @param totalPajak
     * @param noPemesanan
     * @param totalDiskon
     * @param tanggalProses
     * @param pesanan
     * @param website
     * @param kembalian
     * @param jumlahPajak
     * @param subtotal
     * @param totalBiayaTambahan
     * @param waktuProses
     * @param jumlahDiskon
     * @param catatan
     * @param namaPelayan
     * @param jumlahBiayaTambahan
     * @param alamat
     */
    public ModelPesanan(String instagram, String waktuProses, String twitter, String kembalian, List<Pesanan> pesanan, String jumlahPajak, String tanggalProses, String total, String alamat, String namaOutlet, String kodePemesanan, String namaPajak, String totalPajak, String catatan, String totalBiayaTambahan, String website, String namaDiskon, String jumlahBiayaTambahan, String namaUser, String facebook, String telpon, String noPemesanan, String subtotal, String namaPelayan, String namaBiayaTambahan, String totalDiskon, String tunai, String jumlahDiskon, String url_logo) {
        super();
        this.instagram = instagram;
        this.waktuProses = waktuProses;
        this.twitter = twitter;
        this.kembalian = kembalian;
        this.pesanan = pesanan;
        this.jumlahPajak = jumlahPajak;
        this.tanggalProses = tanggalProses;
        this.total = total;
        this.alamat = alamat;
        this.namaOutlet = namaOutlet;
        this.kodePemesanan = kodePemesanan;
        this.namaPajak = namaPajak;
        this.totalPajak = totalPajak;
        this.catatan = catatan;
        this.totalBiayaTambahan = totalBiayaTambahan;
        this.website = website;
        this.namaDiskon = namaDiskon;
        this.jumlahBiayaTambahan = jumlahBiayaTambahan;
        this.namaUser = namaUser;
        this.facebook = facebook;
        this.telpon = telpon;
        this.noPemesanan = noPemesanan;
        this.subtotal = subtotal;
        this.namaPelayan = namaPelayan;
        this.namaBiayaTambahan = namaBiayaTambahan;
        this.totalDiskon = totalDiskon;
        this.tunai = tunai;
        this.jumlahDiskon = jumlahDiskon;
        this.url_logo= url_logo;
    }

    public String getUrl_logo() {
        return url_logo;
    }

    public void setUrl_logo(String url_logo) {
        this.url_logo = url_logo;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getWaktuProses() {
        return waktuProses;
    }

    public void setWaktuProses(String waktuProses) {
        this.waktuProses = waktuProses;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getKembalian() {
        return kembalian;
    }

    public void setKembalian(String kembalian) {
        this.kembalian = kembalian;
    }

    public List<Pesanan> getPesanan() {
        return pesanan;
    }

    public void setPesanan(List<Pesanan> pesanan) {
        this.pesanan = pesanan;
    }

    public String getJumlahPajak() {
        return jumlahPajak;
    }

    public void setJumlahPajak(String jumlahPajak) {
        this.jumlahPajak = jumlahPajak;
    }

    public String getTanggalProses() {
        return tanggalProses;
    }

    public void setTanggalProses(String tanggalProses) {
        this.tanggalProses = tanggalProses;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNamaOutlet() {
        return namaOutlet;
    }

    public void setNamaOutlet(String namaOutlet) {
        this.namaOutlet = namaOutlet;
    }

    public String getKodePemesanan() {
        return kodePemesanan;
    }

    public void setKodePemesanan(String kodePemesanan) {
        this.kodePemesanan = kodePemesanan;
    }

    public String getNamaPajak() {
        return namaPajak;
    }

    public void setNamaPajak(String namaPajak) {
        this.namaPajak = namaPajak;
    }

    public String getTotalPajak() {
        return totalPajak;
    }

    public void setTotalPajak(String totalPajak) {
        this.totalPajak = totalPajak;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }

    public String getTotalBiayaTambahan() {
        return totalBiayaTambahan;
    }

    public void setTotalBiayaTambahan(String totalBiayaTambahan) {
        this.totalBiayaTambahan = totalBiayaTambahan;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getNamaDiskon() {
        return namaDiskon;
    }

    public void setNamaDiskon(String namaDiskon) {
        this.namaDiskon = namaDiskon;
    }

    public String getJumlahBiayaTambahan() {
        return jumlahBiayaTambahan;
    }

    public void setJumlahBiayaTambahan(String jumlahBiayaTambahan) {
        this.jumlahBiayaTambahan = jumlahBiayaTambahan;
    }

    public String getNamaUser() {
        return namaUser;
    }

    public void setNamaUser(String namaUser) {
        this.namaUser = namaUser;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTelpon() {
        return telpon;
    }

    public void setTelpon(String telpon) {
        this.telpon = telpon;
    }

    public String getNoPemesanan() {
        return noPemesanan;
    }

    public void setNoPemesanan(String noPemesanan) {
        this.noPemesanan = noPemesanan;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getNamaPelayan() {
        return namaPelayan;
    }

    public void setNamaPelayan(String namaPelayan) {
        this.namaPelayan = namaPelayan;
    }

    public String getNamaBiayaTambahan() {
        return namaBiayaTambahan;
    }

    public void setNamaBiayaTambahan(String namaBiayaTambahan) {
        this.namaBiayaTambahan = namaBiayaTambahan;
    }

    public String getTotalDiskon() {
        return totalDiskon;
    }

    public void setTotalDiskon(String totalDiskon) {
        this.totalDiskon = totalDiskon;
    }

    public String getTunai() {
        return tunai;
    }

    public void setTunai(String tunai) {
        this.tunai = tunai;
    }

    public String getJumlahDiskon() {
        return jumlahDiskon;
    }

    public void setJumlahDiskon(String jumlahDiskon) {
        this.jumlahDiskon = jumlahDiskon;
    }

}
