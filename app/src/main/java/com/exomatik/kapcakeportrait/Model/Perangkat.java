
package com.exomatik.kapcakeportrait.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Perangkat {

    @SerializedName("is_aktif")
    @Expose
    private Integer isAktif;
    @SerializedName("id_perangkat")
    @Expose
    private Integer idPerangkat;
    @SerializedName("bisnis_id")
    @Expose
    private Integer bisnisId;
    @SerializedName("teruskan_ke_printer_kasir")
    @Expose
    private Integer teruskanKePrinterKasir;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("mac")
    @Expose
    private Object mac;
    @SerializedName("operating_system")
    @Expose
    private String operatingSystem;
    @SerializedName("perangkat")
    @Expose
    private String perangkat;
    @SerializedName("cetak_salinan_struk")
    @Expose
    private Integer cetakSalinanStruk;
    @SerializedName("email")
    @Expose
    private String email;

    public Integer getIsAktif() {
        return isAktif;
    }

    public void setIsAktif(Integer isAktif) {
        this.isAktif = isAktif;
    }

    public Integer getIdPerangkat() {
        return idPerangkat;
    }

    public void setIdPerangkat(Integer idPerangkat) {
        this.idPerangkat = idPerangkat;
    }

    public Integer getBisnisId() {
        return bisnisId;
    }

    public void setBisnisId(Integer bisnisId) {
        this.bisnisId = bisnisId;
    }

    public Integer getTeruskanKePrinterKasir() {
        return teruskanKePrinterKasir;
    }

    public void setTeruskanKePrinterKasir(Integer teruskanKePrinterKasir) {
        this.teruskanKePrinterKasir = teruskanKePrinterKasir;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Object getMac() {
        return mac;
    }

    public void setMac(Object mac) {
        this.mac = mac;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getPerangkat() {
        return perangkat;
    }

    public void setPerangkat(String perangkat) {
        this.perangkat = perangkat;
    }

    public Integer getCetakSalinanStruk() {
        return cetakSalinanStruk;
    }

    public void setCetakSalinanStruk(Integer cetakSalinanStruk) {
        this.cetakSalinanStruk = cetakSalinanStruk;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
