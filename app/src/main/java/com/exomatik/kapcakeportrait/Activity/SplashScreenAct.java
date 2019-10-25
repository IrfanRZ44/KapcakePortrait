package com.exomatik.kapcakeportrait.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.exomatik.kapcakeportrait.BuildConfig;
import com.exomatik.kapcakeportrait.Featured.UserSave;
import com.exomatik.kapcakeportrait.Model.ModelVersion;
import com.exomatik.kapcakeportrait.R;
import com.exomatik.kapcakeportrait.RetrofitApi.RetrofitAPI;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SplashScreenAct extends AppCompatActivity {
    private UserSave userSave;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_splash_screen);

        init();
        cekVersion();
    }

    private void cekVersion(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Retrofit.Builder retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitAPI.BASE_URL_VERSION)
                .addConverterFactory(GsonConverterFactory.create(gson)
                );
        RetrofitAPI api = retrofit.build().create(RetrofitAPI.class);

        Call<ModelVersion> call = api.cekVersion("portrait",
                "application/json");

        Log.e("Base Url", retrofit.toString());

        call.enqueue(new Callback<ModelVersion>() {
            @Override
            public void onResponse(Call<ModelVersion> call, Response<ModelVersion> response) {
                String version = response.body().getVersion().toString();

                Log.e("Version", version);
                if (version.equals(BuildConfig.VERSION_NAME)){
                    masukApps();
                }
                else {
                    alert();
                }
            }

            @Override
            public void onFailure(Call<ModelVersion> call, Throwable t) {
                if ((t.getMessage().toString().contains("Unable to resolve host")) || (t.getMessage().toString().contains("timed out"))
                        || (t.getMessage().toString().contains("stream was reset"))){
                    customSnackbar("Periksa koneksi internet Anda", R.drawable.snakbar_red);
                }
                else {
                    customSnackbar(t.getMessage().toString(), R.drawable.snakbar_red);
                }
            }
        });
    }

    private void alert() {
        AlertDialog.Builder alert = new AlertDialog.Builder(SplashScreenAct.this, R.style.MyProgressDialogTheme);

        alert.setMessage("Perbarui Aplikasi?");
        alert.setCancelable(false);
        alert.setPositiveButton("Iya", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
                finish();
            }
        });
        alert.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });

        alert.show();
    }

    private void init() {
        userSave = new UserSave(this);
        view = findViewById(android.R.id.content);
    }

    private void customSnackbar(String text, int background) {
        Snackbar snackbar = Snackbar.make(view, "", Snackbar.LENGTH_INDEFINITE);

        // Get the Snackbar view
        View view = snackbar.getView();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(ContextCompat.getDrawable(this, background));
        }
        TextView tv = (TextView) view.findViewById(com.google.android.material.R.id.snackbar_text);

        tv.setTextColor(Color.parseColor("#FFFFFF"));

        snackbar.setText(text);
        snackbar.setAction("Coba lagi", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekVersion();
            }
        });
        snackbar.setActionTextColor(getResources().getColor(R.color.putih));
        snackbar.show();
    }

    private void masukApps(){
        new Handler().postDelayed(new Runnable()
        {
            public void run()
            {
                if (userSave.getKEY_USER() == null){
                    Intent intent = new Intent(SplashScreenAct.this, SignInAct.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(SplashScreenAct.this, AuthPinAct.class);
                    startActivity(intent);
                }
                finish();
            }
        }, 2000L);
    }
}
