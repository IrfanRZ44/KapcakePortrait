package com.exomatik.kapcakeportrait.Featured;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.exomatik.kapcakeportrait.Model.ModelUser;
import com.google.gson.Gson;

/**
 * Created by IrfanRZ on 02/08/2019.
 */

public class UserSave {
    private SharedPreferences preferences;
    private String KEY_USER = "user";
    private String KEY_KODE = "kode";
    private String KEY_TANGGAL = "tanggal";

    public UserSave(Context paramContext) {
        this.preferences = paramContext.getSharedPreferences("UserPref", 0);
    }

    public void setKEY_USER(ModelUser data) {
        ModelUser myObject = data;
        Editor prefsEditor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(myObject);
        prefsEditor.putString(this.KEY_USER, json);
        prefsEditor.apply();
    }

    public ModelUser getKEY_USER() {
        Gson gson = new Gson();
        String json = preferences.getString(this.KEY_USER, "");
        ModelUser obj = gson.fromJson(json, ModelUser.class);
        return obj;
    }

    public String getKEY_KODE() {
        return this.preferences.getString(this.KEY_KODE, "0");
    }
    public String getKEY_TANGGAL() {
        return this.preferences.getString(this.KEY_TANGGAL, "0");
    }
    public void setKEY_KODE(String paramString) {
        Editor localEditor = this.preferences.edit();
        localEditor.putString(this.KEY_KODE, paramString);
        localEditor.apply();
    }

    public void setKEY_TANGGAL(String paramString) {
        Editor localEditor = this.preferences.edit();
        localEditor.putString(this.KEY_TANGGAL, paramString);
        localEditor.apply();
    }
    public void setVariabel(String paramString, String KEY) {
        Editor localEditor = this.preferences.edit();
        localEditor.putString(KEY, paramString);
        localEditor.apply();
    }

    public String getVariabel(String KEY) {
        return this.preferences.getString(KEY, "0");
    }


}
