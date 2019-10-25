
package com.exomatik.kapcakeportrait.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelUser {

    @SerializedName("perangkat")
    @Expose
    private Perangkat perangkat;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("user")
    @Expose
    private User user;

    public ModelUser() {
    }

    public ModelUser(Perangkat perangkat, String token, User user) {
        this.perangkat = perangkat;
        this.token = token;
        this.user = user;
    }

    public Perangkat getPerangkat() {
        return perangkat;
    }

    public void setPerangkat(Perangkat perangkat) {
        this.perangkat = perangkat;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
