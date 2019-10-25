package com.exomatik.kapcakeportrait.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by IrfanRZ on 20/08/2019.
 */

public class ModelBluetooth {
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("mac")
    @Expose
    private String mac;

    /**
     * No args constructor for use in serialization
     *
     */
    public ModelBluetooth() {
    }

    /**
     *
     * @param mac
     * @param nama
     */
    public ModelBluetooth(String nama, String mac) {
        super();
        this.nama = nama;
        this.mac = mac;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getMac() {
        return mac;
    }

    public void setac(String mac) {
        this.mac = mac;
    }
}
