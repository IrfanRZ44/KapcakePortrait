package com.exomatik.kapcakeportrait.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.exomatik.kapcakeportrait.Featured.Bluetooth;
import com.exomatik.kapcakeportrait.Featured.BluetoothSocket;
import com.exomatik.kapcakeportrait.Featured.CustomWebChromeClient;
import com.exomatik.kapcakeportrait.Featured.Print;
import com.exomatik.kapcakeportrait.Featured.UserSave;
import com.exomatik.kapcakeportrait.Featured.WebViewJavaScriptInterface;
import com.exomatik.kapcakeportrait.Model.ModelBluetooth;
import com.exomatik.kapcakeportrait.Model.ModelSocket;
import com.exomatik.kapcakeportrait.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private WebView web;
    private boolean jalan = true;
    private UserSave userSave;
    public ProgressDialog progressDialog;
    private boolean tampil = true;
    private Bluetooth bluetoothClass;
    private Print printClass;
    private View view;
    private ArrayList<ModelBluetooth> listDevice = new ArrayList<ModelBluetooth>();
    public static ArrayList<ModelSocket> listSocket = new ArrayList<ModelSocket>();
    private BluetoothAdapter bluetoothAdapter;
    public static boolean searching = true;
    public static String macAddress = null;
    public static ProgressDialog progressDialog2;
    private boolean paused = false;
    private boolean exit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hideStatusBar();
        setContentView(R.layout.act_main);

        init();
        progressShow("Mohon tunggu");
        runnabelCekKoneksi();
        setWebView();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    private void setWebView() {

        web.setWebChromeClient(new CustomWebChromeClient(this));
        web.setWebViewClient(new WebViewClient());
        web.getSettings().setLoadsImagesAutomatically(true);
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        web.getSettings().setDomStorageEnabled(true);

        web.getSettings().setSupportZoom(false);
        web.getSettings().setBuiltInZoomControls(false);
        web.getSettings().setDisplayZoomControls(false);

        web.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

//        web.loadUrl("file:///android_asset/index.html");
        web.loadUrl("192.168.196.43/kapcake/kasir-potrait/index.html");
//        web.loadUrl("http://192.168.43.196/kapcake/kasir/index.html");
        web.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String weburl) {
                if (jalan) {
                    web.loadUrl("javascript:loginUser(" + new Gson().toJson(userSave.getKEY_USER()) + ", "
                            + userSave.getKEY_KODE() + ")");
                    jalan = false;
                }
                if (progressDialog != null){
                    progressDialog.dismiss();
                }
            }
        });
        WebViewJavaScriptInterface webViewJS = new WebViewJavaScriptInterface(this, MainActivity.this
                , progressDialog, bluetoothClass, printClass, view, web, bluetoothAdapter);
        web.addJavascriptInterface(webViewJS, "android");
    }

    private void progressShow(String title) {
        progressDialog = new ProgressDialog(MainActivity.this, R.style.MyProgressDialogTheme);
        progressDialog.setMessage(title);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (progressDialog != null){
            progressDialog.dismiss();
        }
    }

    private void runnabelCekKoneksi() {
        new CountDownTimer(300000, 1000) {
            public void onTick(long millisUntilFinished) {
                cekKoneksi();
            }

            public void onFinish() {
            }

        }.start();
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

    private void cekKoneksi() {
        ConnectivityManager connectivityManager = (ConnectivityManager) MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                tampil = true;
            } else {
                if (tampil) {
                    customSnackbar("Mohon periksa koneksi internet anda", R.drawable.snakbar_red);
                    tampil = false;
                }
            }
        }
    }

    private void init() {
        web = (WebView) findViewById(R.id.web);
        view = (View) findViewById(android.R.id.content);

        userSave = new UserSave(this);
        bluetoothClass = new Bluetooth(this, web, listDevice);
    }

    @Override
    public void onBackPressed() {
        web.loadUrl("javascript:btnBack()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("Stop", "Yes");
    }

    public void minimizeApp() {
        moveTaskToBack(true);
    }

    private void hideStatusBar() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        paused = false;
        switch (requestCode) {
            case 0:
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(this, "Bluetooth aktif", Toast.LENGTH_SHORT).show();

                    if (searching) {
                        progressDialog.dismiss();
                        bluetoothClass.btnDiscover();
                    } else {
                        progressShow("Mohon tunggu...");
                        ModelSocket dataSocket = null;
                        for (int a = 0; a < listSocket.size(); a++) {
                            if (listSocket.get(a).getMacAddress().equals(macAddress)) {
                                dataSocket = listSocket.get(a);
                            }
                        }

                        progressDialog.dismiss();
                        printClass = new Print(bluetoothAdapter.getRemoteDevice(macAddress), MainActivity.this, bluetoothAdapter, progressDialog2, web, dataSocket);
                        printClass.hubungkanDevice(macAddress);
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(this, "Bluetooth tidak aktif", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("Destroy", "Yes");

        searching = true;
        macAddress = null;
        if (listDevice.size() != 0) {
            listDevice.removeAll(listDevice);
        }
        if (listSocket.size() != 0){
            listSocket.removeAll(listSocket);
        }
        if (bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.disable();
            if (bluetoothClass != null) {
                if (bluetoothClass.receiverDevice.isOrderedBroadcast()) {
                    unregisterReceiver(bluetoothClass.receiverDevice);
                }
            }
            if (bluetoothAdapter != null) {
                if (printClass != null) {
                    for (int a = 0; a < listSocket.size(); a++) {
                        BluetoothSocket socket = new BluetoothSocket(listSocket.get(a), bluetoothAdapter);

                        try {
                            socket.closeBT();
                        } catch (IOException e) {
                            Log.e("Error", e.getMessage().toString());
                        }
                    }
                    printClass = null;
                }
            }
        }
    }
}
