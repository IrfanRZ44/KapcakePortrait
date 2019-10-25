package com.exomatik.kapcakeportrait.Featured;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import com.exomatik.kapcakeportrait.Activity.MainActivity;
import com.exomatik.kapcakeportrait.Model.Menu;
import com.exomatik.kapcakeportrait.Model.ModelPesanan;
import com.exomatik.kapcakeportrait.Model.ModelSocket;
import com.exomatik.kapcakeportrait.Model.Pesanan;
import com.exomatik.kapcakeportrait.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

/**
 * Created by IrfanRZ on 20/08/2019.
 */

public class Print {
    private int sizeKertas = 32;
    private BluetoothDevice bluetoothDevice;
    private Activity activity;
    private BluetoothAdapter bluetoothAdapter;
    private ProgressDialog progressDialog;
    private WebView web;
    private ModelSocket socketBluetooth;
    private String macTujuan;

    public Print(BluetoothDevice bluetoothDevice, Activity activity, BluetoothAdapter bluetoothAdapter,
                 ProgressDialog progressDialog, WebView web, ModelSocket socketBluetooth) {
        this.bluetoothDevice = bluetoothDevice;
        this.activity = activity;
        this.bluetoothAdapter = bluetoothAdapter;
        this.progressDialog = progressDialog;
        this.web = web;
        this.socketBluetooth = socketBluetooth;
    }

    public void printData(ModelPesanan listPesanan, String macAddress, ModelSocket socket) {
        if (socket == null) {
            Log.e("SCB", "null");
            for (int a = 0; a < MainActivity.listSocket.size(); a++) {
                if (MainActivity.listSocket.get(a).getMacAddress().equals(macAddress)) {
                    socket = MainActivity.listSocket.get(a);
                }
            }
        }

        if (socket.getMmOutputStream() != null) {
            try {
                byte[] printformat = new byte[]{0x1B, 0x21, 0x03};
                socket.getMmOutputStream().write(printformat);
                tampilanHeaderAtas(listPesanan);
                tampilanInformasiPesanan(listPesanan);
                for (int a = 0; a < listPesanan.getPesanan().size(); a++) {
                    tampilanMenu(listPesanan.getPesanan().get(a));
                }
                tampilanPembayaran(listPesanan);
                tampilanLink(listPesanan);
                tampilNote(listPesanan);

                printCustom("\n \n \n \n", 0, 0);
            } catch (IOException e) {
                Log.e("Error", e.getMessage().toString());
            }
        } else {
            Toast.makeText(activity, "Tidak ada printer yang terhubung", Toast.LENGTH_SHORT).show();
        }

    }

    @TargetApi(Build.VERSION_CODES.N)
    public void tesPrint(String namaOutlet, String hp, String alamat, String tipe, String macAddress, String urlFoto) {
        if (socketBluetooth == null) {
            Log.e("SCB", "null");
            for (int a = 0; a < MainActivity.listSocket.size(); a++) {
                if (MainActivity.listSocket.get(a).getMacAddress().equals(macAddress)) {
                    socketBluetooth = MainActivity.listSocket.get(a);
                }
            }
        }


        if (socketBluetooth.getMmOutputStream() != null) {
            Log.e("Data output socket", "tidak null");
            printPhoto(urlFoto);
            printCustom("\n", 3, 1);
            printCustom(namaOutlet, 3, 1);
            printCustom("\n", 0, 1);
            printCustom(alamat, 0, 1);
            printCustom("\n", 0, 1);
            printCustom(hp, 0, 1);
            printCustom("\n \n", 1, 1);
            printCustom("PRINTER TEST", 1, 1);
            printCustom("\n \n", 1, 1);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String currentDateandTime = sdf.format(new Date());
            printCustom(currentDateandTime, 1, 1);
            printCustom("\n", 1, 1);
            printCustom(garis2(), 1, 1);
            printCustom("\n", 1, 1);
            printCustom(garis(), 1, 1);
            printCustom("\n", 1, 1);
            printCustom("Tipe : " + getDataSpace("Tipe : ".length(), tipe.length(), sizeKertas)
                    + tipe, 0, 0);
            printCustom("\n", 1, 1);
            printCustom("Alamat : " + getDataSpace("Alamat : ".length(), macAddress.length(), sizeKertas)
                    + macAddress, 0, 0);
            printCustom("\n", 1, 1);
            printCustom(garis2(), 1, 1);
            printCustom("\n", 1, 1);
            printCustom(garis(), 1, 1);
            printCustom("\n", 1, 1);


            printCustom("\n \n \n", 1, 1);
            progressDialog.dismiss();
        } else {
            Log.e("Data output socket", "null");
            Toast.makeText(activity, "Tidak ada printer yang terhubung", Toast.LENGTH_SHORT).show();
        }
    }

    private void tampilanHeaderAtas(ModelPesanan listData) {
        //foto
        printPhoto(listData.getUrl_logo());

        //nama warkop
        printCustom("\n" + listData.getNamaOutlet(), 3, 1);

        printCustom("\n", 1, 1);
        if (!listData.getAlamat().isEmpty()) {
            //alamat
            printCustom("\n" + listData.getAlamat() + "\n", 1, 1);
            printCustom(listData.getTelpon(), 1, 1);
            //kop bawah
            printCustom("\n \n", 1, 1);
        }
        printCustom(garis(), 1, 1);
        printCustom("\n", 1, 1);
        printCustom("No. Pesanan : " + listData.getNoPemesanan(), 1, 1);
        printCustom("\n", 1, 1);
        printCustom(garis(), 1, 1);
    }

    private void tampilanInformasiPesanan(ModelPesanan listPesanan) {
        printCustom("\n", 1, 1);
        if (!(listPesanan.getTanggalProses().isEmpty())) {
            printCustom(listPesanan.getTanggalProses() + getDataSpace(listPesanan.getTanggalProses().length()
                    , listPesanan.getWaktuProses().length(), sizeKertas) + listPesanan.getWaktuProses(), 1, 0);
            printCustom("\n", 1, 1);
        }
        printCustom("Receipt" + getDataSpace("Receipt".length()
                , listPesanan.getKodePemesanan().length(), sizeKertas) + listPesanan.getKodePemesanan(), 1, 0);
        if (!(listPesanan.getNamaPelayan().isEmpty())) {
            printCustom("\n", 1, 1);
            printCustom("Pelayan" + getDataSpace("Pelayan".length()
                    , listPesanan.getNamaPelayan().length(), sizeKertas) + listPesanan.getNamaPelayan(), 1, 0);
        }
        printCustom("\n", 1, 1);
        printCustom("Kasir" + getDataSpace("Kasir".length()
                , listPesanan.getNamaUser().length(), sizeKertas) + listPesanan.getNamaUser(), 1, 0);
        printCustom("\n", 1, 1);
        printCustom(garis(), 1, 1);
    }

    private void tampilanMenu(Pesanan modelJenisPemesanan) {
        printCustom("\n", 0, 1);
        printCustom("*" + modelJenisPemesanan.getNamaTipePenjualan() + "*", 1, 1);
        for (int a = 0; a < modelJenisPemesanan.getMenu().size(); a++) {
            printCustom("\n", 0, 1);
            tampilanMakanan(modelJenisPemesanan.getMenu().get(a));
        }
    }

    private void tampilanMakanan(Menu modelMakanan) {
        String jumlah = modelMakanan.getJumlah() + "x   ";
        printCustom(modelMakanan.getNamaMenu(), 1, 0);
        printCustom("\n", 0, 0);
        if (!(modelMakanan.getNamaVariasiMenu().isEmpty())) {
            printCustom(modelMakanan.getNamaVariasiMenu(), 0, 0);
            printCustom("\n", 0, 0);
        }
        printCustom(jumlah + modelMakanan.getHarga() + getDataSpace(jumlah.length() +
                modelMakanan.getHarga().length(), modelMakanan.getTotal().length(), sizeKertas)
                + modelMakanan.getTotal(), 0, 0);
        printCustom("\n", 0, 0);
    }

    private void tampilanPembayaran(ModelPesanan listPesanan) {
        printCustom(garis(), 0, 0);

        printCustom("Subtotal" + getDataSpace("Subtotal".length(), listPesanan.getSubtotal().length(), sizeKertas)
                + listPesanan.getSubtotal(), 0, 0);
        printCustom("\n", 0, 1);

        if (!(listPesanan.getNamaDiskon().isEmpty())) {
            String tip = listPesanan.getNamaDiskon() + "(" + listPesanan.getJumlahDiskon() + ")";
            printCustom(tip + getDataSpace(tip.length(), listPesanan.getTotalDiskon().length(), sizeKertas)
                    + listPesanan.getTotalDiskon(), 0, 0);
            printCustom("\n", 0, 0);
        }

        if (!(listPesanan.getNamaBiayaTambahan().isEmpty())) {
            String tip = listPesanan.getNamaBiayaTambahan() + "(" + listPesanan.getJumlahBiayaTambahan() + ")";
            printCustom(tip + getDataSpace(tip.length(), listPesanan.getTotalBiayaTambahan().length(), sizeKertas)
                    + listPesanan.getTotalBiayaTambahan(), 0, 0);
            printCustom("\n", 0, 0);
        }

        if (!(listPesanan.getNamaPajak().isEmpty())) {
            String pajak = listPesanan.getNamaPajak() + "(" + listPesanan.getJumlahPajak() + ")";
            printCustom(pajak + getDataSpace(pajak.length(), listPesanan.getTotalPajak().length(), sizeKertas)
                    + listPesanan.getTotalPajak(), 0, 0);
            printCustom("\n", 0, 0);
        }
        printCustom(garis(), 0, 1);
        printCustom("\n", 0, 0);

        printCustom("Total" + getDataSpace("Total".length(), listPesanan.getTotal().length(), sizeKertas)
                + listPesanan.getTotal(), 1, 0);
        printCustom("\n", 0, 0);
        printCustom(garis(), 1, 0);

        printCustom("\n", 0, 0);

        printCustom("Cash" + getDataSpace("Cash".length(), listPesanan.getTunai().length(), sizeKertas)
                + listPesanan.getTunai(), 0, 0);

        printCustom("\n", 0, 0);

        printCustom("Change" + getDataSpace("Change".length(), listPesanan.getKembalian().length(), sizeKertas)
                + listPesanan.getKembalian(), 0, 0);
        printCustom("\n", 0, 0);

        printCustom(garis(), 1, 0);
    }

    private void tampilanLink(ModelPesanan listPesanan) {
        if (!(listPesanan.getWebsite().isEmpty())) {
            printCustom("\n", 0, 0);
            printCustom("WS : " + listPesanan.getWebsite(), 0, 0);
        }
        if (!(listPesanan.getFacebook().isEmpty())) {
            printCustom("\n", 0, 0);
            printCustom("FB : " + listPesanan.getFacebook(), 0, 0);
        }
        if (!(listPesanan.getTwitter().isEmpty())) {
            printCustom("\n", 0, 0);
            printCustom("TW : " + listPesanan.getTwitter(), 0, 0);
        }
        if (!(listPesanan.getInstagram().isEmpty())) {
            printCustom("\n", 0, 0);
            printCustom("IG : " + listPesanan.getInstagram(), 0, 0);
        }
    }

    private void tampilNote(ModelPesanan listPesanan) {
        if (!(listPesanan.getWebsite().isEmpty())) {
            printCustom("\n", 0, 0);
            printCustom(garis(), 1, 1);
        }

        if (!listPesanan.getCatatan().isEmpty()) {
            printCustom("\n", 1, 1);
            printCustom(listPesanan.getCatatan(), 1, 1);
        }

        //alamat
        printCustom("", 1, 1);
    }

    private String getDataSpace(int lengthKiri, int lengthKanan, int size) {
        String hasil = "";
        int jumlah = 0, hasil_jumlah;

        jumlah = lengthKanan + lengthKiri;
        hasil_jumlah = size - jumlah;

        for (int a = 0; a < hasil_jumlah; a++) {
            hasil = hasil + " ";
        }

        return hasil;
    }

    private String garis() {
        String garis1 = "-";
        for (int a = 0; a < 31; a++) {
            garis1 = garis1 + "-";
        }
        return garis1;
    }

    private String garis2() {
        String garis1 = "_";
        for (int a = 0; a < 31; a++) {
            garis1 = garis1 + "_";
        }
        return garis1;
    }

    //print photo
    public void printPhoto(String img) {
        try {
            if (img == null){
                Bitmap bmp = BitmapFactory.decodeResource(activity.getResources(), R.drawable.logo_kue);
                if (bmp != null) {
                    byte[] command = Utils.decodeBitmap(bmp);
                    socketBluetooth.getMmOutputStream().write(PrinterCommands.ESC_ALIGN_CENTER);
                    socketBluetooth.getMmOutputStream().write(command);
                } else {
                    Log.e("Print Photo error", "the file isn't exists");
                }
            }
            else if (img.isEmpty()){
                Bitmap bmp = BitmapFactory.decodeResource(activity.getResources(), R.drawable.logo_kue);
                if (bmp != null) {
                    byte[] command = Utils.decodeBitmap(bmp);
                    socketBluetooth.getMmOutputStream().write(PrinterCommands.ESC_ALIGN_CENTER);
                    socketBluetooth.getMmOutputStream().write(command);
                } else {
                    Log.e("Print Photo error", "the file isn't exists");
                }
            }
            else if (img == ""){
                Bitmap bmp = BitmapFactory.decodeResource(activity.getResources(), R.drawable.logo_kue);
                if (bmp != null) {
                    byte[] command = Utils.decodeBitmap(bmp);
                    socketBluetooth.getMmOutputStream().write(PrinterCommands.ESC_ALIGN_CENTER);
                    socketBluetooth.getMmOutputStream().write(command);
                } else {
                    Log.e("Print Photo error", "the file isn't exists");
                }
            }
            else {
                Bitmap bmp = getBitmapFromURL(img);
                if (bmp != null) {
                    byte[] command = Utils.decodeBitmap(bmp);
                    socketBluetooth.getMmOutputStream().write(PrinterCommands.ESC_ALIGN_CENTER);
                    socketBluetooth.getMmOutputStream().write(command);
                } else {
                    Log.e("Print Photo error", "the file isn't exists");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("PrintTools", e.getMessage().toString());
        }
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            myBitmap = myBitmap.createScaledBitmap(myBitmap, 100, 100, true);
            Log.e("Height", Integer.toString(myBitmap.getHeight()));
            Log.e("Width", Integer.toString(myBitmap.getWidth()));
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

    //print custom
    public void printCustom(String msg, int size, int align) {
        //Print config "mode"
        byte[] cc = new byte[]{0x1B, 0x21, 0x03};  // 0- normal size text
        //byte[] cc1 = new byte[]{0x1B,0x21,0x00};  // 0- normal size text
        byte[] bb = new byte[]{0x1B, 0x21, 0x08};  // 1- only bold text
        byte[] bb2 = new byte[]{0x1B, 0x21, 0x20}; // 2- bold with medium text
        byte[] bb3 = new byte[]{0x1B, 0x21, 0x10}; // 3- bold with large text
        try {
            switch (size) {
                case 0:
                    socketBluetooth.getMmOutputStream().write(cc);
                    break;
                case 1:
                    socketBluetooth.getMmOutputStream().write(bb);
                    break;
                case 2:
                    socketBluetooth.getMmOutputStream().write(bb2);
                    break;
                case 3:
                    socketBluetooth.getMmOutputStream().write(bb3);
                    break;
                case 4:
                    socketBluetooth.getMmOutputStream().write(PrinterCommands.ESC_ITALIC);
                    break;
                case 5:
                    socketBluetooth.getMmOutputStream().write(PrinterCommands.SELECT_FONT_A);
                    break;
            }
            switch (align) {
                case 0:
                    //left align
                    socketBluetooth.getMmOutputStream().write(PrinterCommands.ESC_ALIGN_LEFT);
                    break;
                case 1:
                    //center align
                    socketBluetooth.getMmOutputStream().write(PrinterCommands.ESC_ALIGN_CENTER);
                    break;
                case 2:
                    //right align
                    socketBluetooth.getMmOutputStream().write(PrinterCommands.ESC_ALIGN_RIGHT);
                    break;
            }
            socketBluetooth.getMmOutputStream().write(msg.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void hubungkanDevice(String macAddress) {

        if (bluetoothAdapter != null) {
            bluetoothAdapter.cancelDiscovery();
        }

        if (bluetoothAdapter.isEnabled()) {
            Log.e("Bt", "Aktif");

            macTujuan = macAddress;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                try {
                    bluetoothDevice.createBond();
                    if (bluetoothDevice.getBondState() == BluetoothDevice.BOND_BONDED){
                        waitAndStopProgress();
                        Log.e("Konfig", "show");
                        Toast.makeText(activity, "Konfigurasi Bluetooth", Toast.LENGTH_LONG).show();
                        web.post(new Runnable() {
                            @Override
                            public void run() {
                                web.loadUrl("javascript:konfirmasiBluetoothTerhubung()");
                            }
                        });
                        BluetoothSocket btSoc = new BluetoothSocket(new ModelSocket(null, null
                                , false, null, null, 0
                                , null, macTujuan), bluetoothAdapter);
                        btSoc.openSocketBluetooth();
                        WebViewJavaScriptInterface.result = false;
                    }
                    else if (bluetoothDevice.getBondState() == BluetoothDevice.BOND_BONDING){
                        waitAndStopProgress();
                        Toast.makeText(activity, "Sedang menghubungkan", Toast.LENGTH_SHORT).show();
                        WebViewJavaScriptInterface.result = false;
                    }
                    else if (bluetoothDevice.getBondState() == BluetoothDevice.BOND_NONE){
                        waitAndStopProgress();
                        Toast.makeText(activity, "Gagal menghubungkan", Toast.LENGTH_SHORT).show();
                        WebViewJavaScriptInterface.result = false;
                    }
                    else {
                        waitAndStopProgress();
                        Toast.makeText(activity, "Terjadi kegagalan yang tidak diketahui", Toast.LENGTH_SHORT).show();
                        WebViewJavaScriptInterface.result = false;
                    }
                } catch (Exception e) {
                    waitAndStopProgress();
                    Toast.makeText(activity, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    WebViewJavaScriptInterface.result = false;
                }
            }

        } else {
            waitAndStopProgress();
            Toast.makeText(activity, "Bluetooth tidak aktif", Toast.LENGTH_SHORT).show();
            WebViewJavaScriptInterface.result = false;
        }
    }

    private void waitAndStopProgress() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                progressDialog.dismiss();
            }
        }, 3000L);
    }
}
