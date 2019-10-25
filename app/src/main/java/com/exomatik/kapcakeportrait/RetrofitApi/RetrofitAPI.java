package com.exomatik.kapcakeportrait.RetrofitApi;

import com.exomatik.kapcakeportrait.Model.ModelLogout;
import com.exomatik.kapcakeportrait.Model.ModelUser;
import com.exomatik.kapcakeportrait.Model.ModelVersion;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by IrfanRZ on 02/08/2019.
 */

public interface RetrofitAPI {
    String BASE_URL = "https://api.telkomreg7.id/api/";
    String BASE_URL_VERSION = BASE_URL + "version/";
//    String BASE_URL = "http://192.168.43.196/kapcake/api/public/api/";
    String URL_LOGOUT = BASE_URL + "kasir/";

    @POST
    Call<ModelUser> signIn(@Url String url);

    @Headers({"Accept: application/json"})
    @POST
    Call<ModelLogout> signOut(@Url String url, @Header("Authorization") String authToken, @Header("Content-Type") String contentType);

    @POST
    Call<ModelVersion> cekVersion(@Url String url, @Header("Content-Type") String contentType);
}
