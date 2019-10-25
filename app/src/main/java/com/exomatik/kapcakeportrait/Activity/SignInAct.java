package com.exomatik.kapcakeportrait.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;
import com.exomatik.kapcakeportrait.Featured.UserSave;
import com.exomatik.kapcakeportrait.Model.ModelUser;
import com.exomatik.kapcakeportrait.R;
import com.exomatik.kapcakeportrait.RetrofitApi.RetrofitAPI;
import com.google.android.material.snackbar.Snackbar;

public class SignInAct extends AppCompatActivity {
    private EditText etUser, etPass;
    private ImageButton btnTogle;
    private RelativeLayout btnMasuk;
    private boolean show = false;
    private View view;
    private ProgressDialog progressDialog;
    private UserSave userSave;
    private TextView textForgot, textDaftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_sign_in);

        init();
        onClick();
    }

    private void init(){
        etUser = findViewById(R.id.etUser);
        etPass = findViewById(R.id.etPass);
        btnTogle = findViewById(R.id.btnTogle);
        btnMasuk = findViewById(R.id.btnMasuk);
        textForgot = findViewById(R.id.textForgot);
        textDaftar = findViewById(R.id.textDaftar);
        view = findViewById(android.R.id.content);

        userSave = new UserSave(this);
    }

    private void onClick() {
        btnTogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (show){
                    show = false;
                    etPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    btnTogle.setImageDrawable(getResources().getDrawable(R.drawable.ic_uneye_gray));
                }
                else {
                    show = true;
                    etPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    btnTogle.setImageDrawable(getResources().getDrawable(R.drawable.ic_eye_gray));
                }
            }
        });

        textDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://backoffice.kapcake.com/daftar";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        textForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://backoffice.kapcake.com/lupa-password";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etUser.getText().toString();
                String pass = etPass.getText().toString();

                if (email.isEmpty() || pass.isEmpty() || pass.length() < 6){
                    if (email.isEmpty()){
                        customSnackbar("Email " + getResources().getString(R.string.error_data_kosong), R.drawable.snakbar_red);
                    }
                    else if (pass.isEmpty()){
                        customSnackbar("Password " + getResources().getString(R.string.error_data_kosong), R.drawable.snakbar_red);
                    }
                    else if (pass.length() < 6){
                        customSnackbar("Password " + getResources().getString(R.string.error_password_kurang), R.drawable.snakbar_red);
                    }
                }
                else {
                    progressDialog = new ProgressDialog(SignInAct.this, R.style.MyProgressDialogTheme);
                    progressDialog.setMessage(getResources().getString(R.string.progress_title1));
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    postLoginUser(email, pass);
                }
            }
        });
    }
    private void customSnackbar(String text, int background) {
        Snackbar snackbar = Snackbar.make(view, "", Snackbar.LENGTH_LONG);

        // Get the Snackbar view
        View view = snackbar.getView();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(ContextCompat.getDrawable(this, background));
        }
        TextView tv = (TextView) view.findViewById(com.google.android.material.R.id.snackbar_text);

        tv.setTextColor(Color.parseColor("#FFFFFF"));

        snackbar.setText(text);
        snackbar.show();
    }

    private void postLoginUser(String email, String pass){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI api = retrofit.create(RetrofitAPI.class);

        String androidId = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);

        Call<ModelUser> call = api.signIn("kasir/login?email=" + email +
                "&operating_system=android" +"&password=" + pass + "&mac=" + androidId);

        call.enqueue(new Callback<ModelUser>() {
            @Override
            public void onResponse(Call<ModelUser> call, Response<ModelUser> response) {
                ModelUser dataUser = response.body();

                if (dataUser == null){
                    customSnackbar(getResources().getString(R.string.error_email_not_found), R.drawable.snakbar_red);
                }
                else {
                    if (dataUser.getUser().getPin() != null){

                        userSave.setKEY_USER(dataUser);
                        Intent intent = new Intent(getApplicationContext(), AuthPinAct.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        customSnackbar("Mohon maaf, data user anda tidak valid", R.drawable.snakbar_red);
                    }
                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ModelUser> call, Throwable t) {
                Log.e("Message", t.getMessage().toString());
                progressDialog.dismiss();
                if (t.getMessage().toString().contains("Unable to resolve host")){
                    customSnackbar("Mohon periksa koneksi Internet Anda", R.drawable.snakbar_red);
                }
                else {
                    customSnackbar(t.getMessage().toString(), R.drawable.snakbar_red);
                }
            }
        });
    }
}
