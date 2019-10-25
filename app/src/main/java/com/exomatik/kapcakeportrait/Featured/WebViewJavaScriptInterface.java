package com.exomatik.kapcakeportrait.Featured;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.exomatik.kapcakeportrait.Activity.AuthPinAct;
import com.exomatik.kapcakeportrait.Activity.MainActivity;
import com.exomatik.kapcakeportrait.Activity.SignInAct;
import com.exomatik.kapcakeportrait.Model.ModelLogout;
import com.exomatik.kapcakeportrait.Model.ModelPesanan;
import com.exomatik.kapcakeportrait.Model.ModelSocket;
import com.exomatik.kapcakeportrait.Model.ModelUser;
import com.exomatik.kapcakeportrait.Model.User;
import com.exomatik.kapcakeportrait.R;
import com.exomatik.kapcakeportrait.RetrofitApi.RetrofitAPI;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by IrfanRZ on 03/08/2019.
 */

public class WebViewJavaScriptInterface {
    public static boolean result = true;
    private Context context;
    private Activity activity;
    private UserSave userSave;
    private ProgressDialog progressDialog;
    private Bluetooth bluetoothClass;
    private BluetoothAdapter bluetoothAdapter;
    private Print printClass;
    private View view;
    private WebView web;

    /*
     * Need a reference to the context in order to sent a post message
     */

    public WebViewJavaScriptInterface(Context context, Activity activity, ProgressDialog progressDialog
            , Bluetooth bluetoothClass, Print printClass, View view, WebView web, BluetoothAdapter bluetoothAdapter) {
        this.view = view;
        this.context = context;
        this.activity = activity;
        this.progressDialog = progressDialog;
        this.bluetoothClass = bluetoothClass;
        this.printClass = printClass;
        this.web = web;
        this.bluetoothAdapter = bluetoothAdapter;

        userSave = new UserSave(context);
    }

    private void customSnackbar(String text, int background) {
        Snackbar snackbar = Snackbar.make(view, "", Snackbar.LENGTH_LONG);

        // Get the Snackbar view
        View view = snackbar.getView();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(ContextCompat.getDrawable(activity, background));
        }
        TextView tv = (TextView) view.findViewById(com.google.android.material.R.id.snackbar_text);

        tv.setTextColor(Color.parseColor("#FFFFFF"));

        snackbar.setText(text);
        snackbar.show();
    }

    /*
     * This method can be called from Android. @JavascriptInterface
     * required after SDK version 17.
     */
    private void alertLogout() {
        AlertDialog.Builder alert = new AlertDialog.Builder(activity, R.style.MyProgressDialogTheme);

        alert.setTitle("Keluar");
        alert.setMessage("Apakah anda yakin ingin keluar dari akun?");
        alert.setPositiveButton("Iya", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                postLogoutUser();
            }
        });
        alert.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();
    }

    private void postLogoutUser() {
        progressShow("Mohon Tunggu", "");
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Retrofit.Builder retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitAPI.URL_LOGOUT)
                .addConverterFactory(GsonConverterFactory.create(gson)
                );
        RetrofitAPI api = retrofit.build().create(RetrofitAPI.class);


        Call<ModelLogout> call = api.signOut("perangkat/logout?perangkat_id=" +
                        userSave.getKEY_USER().getPerangkat().getIdPerangkat().toString(),
                "Bearer " + userSave.getKEY_USER().getToken().toString(),
                "application/json");

        call.enqueue(new Callback<ModelLogout>() {
            @Override
            public void onResponse(Call<ModelLogout> call, Response<ModelLogout> response) {

            }

            @Override
            public void onFailure(Call<ModelLogout> call, Throwable t) {
                progressDismiss();
                customSnackbar(t.getMessage().toString(), R.drawable.snakbar_red);
            }
        });

        customSnackbar(context.getResources().getString(R.string.text_berhasil_logout), R.drawable.snakbar_blue);
        userSave.setKEY_KODE("0");
        userSave.setKEY_TANGGAL("0");
        userSave.setKEY_USER(null);
        progressDismiss();
        Log.e("Saya", "Sign in");
        activity.startActivity(new Intent(context, SignInAct.class));
        activity.finish();
    }

    @JavascriptInterface
    public void progressShow(String title, String message) {
        progressDialog = new ProgressDialog(activity, R.style.MyProgressDialogTheme);
        progressDialog.setMessage(title);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @JavascriptInterface
    public void updatePIN(int pin) {
        userSave.setKEY_USER(new ModelUser(userSave.getKEY_USER().getPerangkat(), userSave.getKEY_USER().getToken(),
                new User(userSave.getKEY_USER().getUser().getNama(), userSave.getKEY_USER().getUser().getBisnisId(),
                        pin, userSave.getKEY_USER().getUser().getTelpon(),
                        userSave.getKEY_USER().getUser().getIsSuperAdmin(), userSave.getKEY_USER().getUser().getId(),
                        userSave.getKEY_USER().getUser().getOutlet(), userSave.getKEY_USER().getUser().getAlamat(),
                        userSave.getKEY_USER().getUser().getEmail(), userSave.getKEY_USER().getUser().getNo_urut())));
    }

    @JavascriptInterface
    public void progressDismiss() {
        progressDialog.dismiss();
    }

    @JavascriptInterface
    public void CustomSnackbar(String message, String kodeBackground) {
        switch (kodeBackground) {
            case "blue":
                customSnackbar(message, R.drawable.snakbar_blue);
                break;
            case "red":
                customSnackbar(message, R.drawable.snakbar_red);
                break;
            case "green":
                customSnackbar(message, R.drawable.snakbar_green);
                break;
        }
    }

    @JavascriptInterface
    public void CustomToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    @JavascriptInterface
    public void setNomorPesanan(String tanggal, String kode) {
        userSave.setKEY_TANGGAL(tanggal);
        userSave.setKEY_KODE(kode);
    }

    @JavascriptInterface
    public String getNomorPesanan() {
        return userSave.getKEY_KODE();
    }

    @JavascriptInterface
    public String getVariabel(String key) {
        return userSave.getVariabel(key);
    }

    @JavascriptInterface
    public void setVariabel(String Key, String Value) {
        userSave.setVariabel(Value, Key);
    }

    @JavascriptInterface
    public String getTanggal() {
        return userSave.getKEY_TANGGAL();
    }

    @JavascriptInterface
    public String getMacAddress() {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();
        String macAddress = wInfo.getMacAddress();

        return macAddress;
    }

    @JavascriptInterface
    public void logoutToPIN() {
        activity.startActivity(new Intent(activity, AuthPinAct.class));
        activity.finish();
    }

    @JavascriptInterface
    public void logout() {
        alertLogout();
    }

    @JavascriptInterface
    public void cekAndRequestBluetooth() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(context, "Bluetooth tidak didukung", Toast.LENGTH_SHORT).show();
        } else {
            if (bluetoothAdapter.isEnabled()) {
                Toast.makeText(context, "Bluetooh Aktif", Toast.LENGTH_SHORT).show();

                bluetoothClass.btnDiscover();
            } else {
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                activity.startActivityForResult(intent, 0);
                Toast.makeText(context, "Menyalakan", Toast.LENGTH_SHORT).show();
                MainActivity.searching = true;
                MainActivity.macAddress = null;
            }
        }

    }

    @JavascriptInterface
    public void connectingToBluetooth(final String macAddress) {
        progressShow("Mohon Tunggu", "");
        new Handler().postDelayed(new Runnable() {
            public void run() {
                timerProgress();
                if (macAddress == null) {
                    progressDismiss();
                    Toast.makeText(context, "Mac Address Kosong", Toast.LENGTH_SHORT).show();
                } else if (macAddress.isEmpty()) {
                    progressDismiss();
                    Toast.makeText(context, "Mac Address Kosong", Toast.LENGTH_SHORT).show();
                } else {
                    bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

                    if (bluetoothAdapter.isEnabled()) {
                        Log.e("Connecting", macAddress);

                        ModelSocket dataSocket = null;
                        for (int a = 0; a < MainActivity.listSocket.size(); a++) {
                            if (MainActivity.listSocket.get(a).getMacAddress().equals(macAddress)) {
                                dataSocket = MainActivity.listSocket.get(a);
                            }
                        }
                        try {
                            printClass = new Print(bluetoothAdapter.getRemoteDevice(macAddress), activity, bluetoothAdapter, progressDialog, web, dataSocket);
                            printClass.hubungkanDevice(macAddress);
                        } catch (Exception e) {
                            progressDismiss();
                            Toast.makeText(context, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.e("Connecting", "to request and connect");
                        requestBluetoothAndConnect(macAddress);
                    }
                }
            }
        }, 1000);
    }

    private void timerProgress() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (result) {
                    printClass = null;
                    bluetoothAdapter = null;

                    Toast.makeText(activity, "Gagal konfigurasi", Toast.LENGTH_SHORT).show();
                }
            }
        }, 10000);
    }

    public void requestBluetoothAndConnect(String macAddress) {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter.isEnabled()) {
            Toast.makeText(context, "Bluetooh Aktif", Toast.LENGTH_SHORT).show();
            progressShow("Mohon Tunggu", "");

            ModelSocket dataSocket = null;
            for (int a = 0; a < MainActivity.listSocket.size(); a++) {
                if (MainActivity.listSocket.get(a).getMacAddress().equals(macAddress)) {
                    dataSocket = MainActivity.listSocket.get(a);
                }
            }
            printClass = new Print(bluetoothAdapter.getRemoteDevice(macAddress), activity, bluetoothAdapter, progressDialog, web, dataSocket);
            printClass.hubungkanDevice(macAddress);
        } else {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(intent, 0);
            Toast.makeText(context, "Menyalakan", Toast.LENGTH_SHORT).show();
            Log.e("bluetooth", " Aktif");
            MainActivity.progressDialog2 = progressDialog;
            MainActivity.searching = false;
            MainActivity.macAddress = macAddress;

        }
    }

    @JavascriptInterface
    public void printPesanan(String message, final String macAddress) {
        final ModelPesanan obj = new Gson().fromJson(message, ModelPesanan.class);

        if (bluetoothAdapter == null) {
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }

        if (bluetoothAdapter.isEnabled()) {
            if (macAddress.isEmpty()) {
                Toast.makeText(context, "Mac Address Printer Tujuan tidak ada", Toast.LENGTH_SHORT).show();
            } else {
                progressShow("Mohon Tunggu", "");
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        ModelSocket dataSocket = null;
                        for (int a = 0; a < MainActivity.listSocket.size(); a++) {
                            if (MainActivity.listSocket.get(a).getMacAddress().equals(macAddress)) {
                                dataSocket = MainActivity.listSocket.get(a);
                            }
                        }
                        if (dataSocket != null) {
                            printClass = new Print(bluetoothAdapter.getRemoteDevice(macAddress), activity, bluetoothAdapter, progressDialog, web, dataSocket);
                            printClass.printData(obj, macAddress, dataSocket);
//                        if (dataSocket != null) {
//                        } else {
//                            printClass.hubungkanDevice(macAddress);
//                        }
                            progressDismiss();
                        } else {
                            progressDismiss();
                            Toast.makeText(context, "Belum ada printer yang terhubung", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, 2000L);
            }
        } else {
            Toast.makeText(context, "Bluetooth tidak aktif", Toast.LENGTH_SHORT).show();
        }
    }

    @JavascriptInterface
    public void tesPrint(String namaOutlet, String hp, String alamat, String tipe, String macAddress, final String urlFoto) {
        Log.e("Mac", macAddress);
        if (namaOutlet.isEmpty()) {
            namaOutlet = "Kapcake";
        }
        if (hp.isEmpty()) {
            hp = "-";
        }
        if (alamat.isEmpty()) {
            alamat = "Villa Samata";
        }
        if (tipe.isEmpty()) {
            tipe = "Bluetooth";
        }
        if (macAddress.isEmpty()) {
            macAddress = "-";
            Toast.makeText(context, "Mac Address printer tujuan tidak ada", Toast.LENGTH_SHORT).show();
        } else {
            if (bluetoothAdapter == null) {
                bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            }
            if (bluetoothAdapter.isEnabled()) {
                progressShow("Mohon Tunggu", "");
                Log.e("Progress", "Show");
                final String finalMacAddress = macAddress;
                final String finalNamaOutlet = namaOutlet;
                final String finalHp = hp;
                final String finalAlamat = alamat;
                final String finalTipe = tipe;
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Log.e("Progress", "Jalan");
                        ModelSocket dataSocket = null;
                        for (int a = 0; a < MainActivity.listSocket.size(); a++) {
                            if (MainActivity.listSocket.get(a).getMacAddress().equals(finalMacAddress)) {
                                dataSocket = MainActivity.listSocket.get(a);
                            }
                        }

                        if (dataSocket == null) {
                            printClass = new Print(bluetoothAdapter.getRemoteDevice(finalMacAddress), activity, bluetoothAdapter, progressDialog, web, dataSocket);
                            printClass.hubungkanDevice(finalMacAddress);
                            ModelSocket socket = null;
                            for (int a = 0; a < MainActivity.listSocket.size(); a++) {
                                if (MainActivity.listSocket.get(a).getMacAddress().equals(finalMacAddress)) {
                                    socket = MainActivity.listSocket.get(a);
                                }
                            }

                            if (socket == null) {
                                Log.e("DSock", "Masih null");
                                progressDialog.dismiss();
                                Toast.makeText(context, "Gagal mendapatkan socket bluetooth", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.e("DSock", "tidak null");
                                printClass.tesPrint(finalNamaOutlet, finalHp, finalAlamat, finalTipe, finalMacAddress, urlFoto);
                            }
                        } else {
                            printClass = new Print(bluetoothAdapter.getRemoteDevice(finalMacAddress), activity, bluetoothAdapter, progressDialog, web, dataSocket);
                            printClass.tesPrint(finalNamaOutlet, finalHp, finalAlamat, finalTipe, finalMacAddress, urlFoto);
                        }
                    }
                }, 2000L);

            } else {
                Toast.makeText(context, "Bluetooth tidak aktif", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @JavascriptInterface
    public void stopConnected(String mac) {
        progressShow("Mohon tunggu", "");

        bluetoothAdapter.disable();
        if (bluetoothClass != null) {
            activity.unregisterReceiver(bluetoothClass.receiverDevice);
        }
        if (printClass != null) {
            printClass = null;
        }
        for (int a = 0; a < MainActivity.listSocket.size(); a++) {
            BluetoothSocket socket = new BluetoothSocket(MainActivity.listSocket.get(a), bluetoothAdapter);
            try {
                socket.closeBT();
            } catch (IOException e) {
                Log.e("Error", e.getMessage().toString());
            }
        }
        progressDismiss();
    }

    @JavascriptInterface
    public void openURL(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        activity.startActivity(i);
    }

}
