
package com.exomatik.kapcakeportrait.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("bisnis_id")
    @Expose
    private Integer bisnisId;
    @SerializedName("pin")
    @Expose
    private Integer pin;
    @SerializedName("telpon")
    @Expose
    private String telpon;
    @SerializedName("is_super_admin")
    @Expose
    private Integer isSuperAdmin;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("outlet")
    @Expose
    private List<Outlet> outlet = null;
    @SerializedName("alamat")
    @Expose
    private String alamat;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("no_urut")
    @Expose
    private Integer no_urut;

    public User() {
    }

    public User(String nama, Integer bisnisId, Integer pin, String telpon, Integer isSuperAdmin, Integer id, List<Outlet> outlet, String alamat, String email, Integer no_urut) {
        this.nama = nama;
        this.bisnisId = bisnisId;
        this.pin = pin;
        this.telpon = telpon;
        this.isSuperAdmin = isSuperAdmin;
        this.id = id;
        this.outlet = outlet;
        this.alamat = alamat;
        this.email = email;
        this.no_urut = no_urut;
    }

    public Integer getNo_urut() {
        return no_urut;
    }

    public void setNo_urut(Integer no_urut) {
        this.no_urut = no_urut;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Integer getBisnisId() {
        return bisnisId;
    }

    public void setBisnisId(Integer bisnisId) {
        this.bisnisId = bisnisId;
    }

    public Integer getPin() {
        return pin;
    }

    public void setPin(Integer pin) {
        this.pin = pin;
    }

    public String getTelpon() {
        return telpon;
    }

    public void setTelpon(String telpon) {
        this.telpon = telpon;
    }

    public Integer getIsSuperAdmin() {
        return isSuperAdmin;
    }

    public void setIsSuperAdmin(Integer isSuperAdmin) {
        this.isSuperAdmin = isSuperAdmin;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Outlet> getOutlet() {
        return outlet;
    }

    public void setOutlet(List<Outlet> outlet) {
        this.outlet = outlet;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
