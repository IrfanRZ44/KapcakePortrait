package com.exomatik.kapcakeportrait.Featured;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.util.Log;

import com.exomatik.kapcakeportrait.Activity.MainActivity;
import com.exomatik.kapcakeportrait.Model.ModelSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class BluetoothSocket {
    ModelSocket socket;
    private OutputStream mmOutputStream;
    private InputStream mmInputStream;
    private volatile boolean stopWorker;
    private android.bluetooth.BluetoothSocket mmSocket;
    private byte[] readBuffer;
    private int readBufferPosition;
    private Thread workerThread;
    private BluetoothAdapter bluetoothAdapter;

    public BluetoothSocket(ModelSocket socket, BluetoothAdapter bluetoothAdapter) {
        this.socket = socket;
        this.bluetoothAdapter = bluetoothAdapter;
    }

    public void openSocketBluetooth(){
        try {
            Log.e("Connecting to BT", socket.getMacAddress());
            openBT(socket.getMacAddress());
        } catch (IOException e) {
            Log.e("Error", e.getMessage().toString());
        }
    }

    public void addSocket(){
        if (mmOutputStream != null){
            MainActivity.listSocket.add(new ModelSocket(mmOutputStream, mmInputStream, stopWorker,
                    mmSocket, readBuffer, readBufferPosition, workerThread, socket.getMacAddress()));
            Log.e("Data Socket", socket.getMacAddress());
        }
        else {
            Log.e("Data Socket", "Null");
        }
    }

    public void closeBT() throws IOException {
        try {
            stopWorker = true;
            mmOutputStream.close();
            mmInputStream.close();
            mmSocket.close();

            mmInputStream = null;
            mmOutputStream = null;
            mmSocket = null;
            Log.e("Stopped", "Connected");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openBT(String macPrinter) throws IOException {
        try {
            // Standard SerialPortService ID
            UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
            BluetoothDevice device = bluetoothAdapter.getRemoteDevice(macPrinter);
            mmSocket = device.createRfcommSocketToServiceRecord(uuid);
            mmSocket.connect();
            mmOutputStream = mmSocket.getOutputStream();
            mmInputStream = mmSocket.getInputStream();

            beginListenForData();
            addSocket();
            Log.e("Bluetooth", "Opened");

        } catch (Exception e) {
            Log.e("Error while opening bt", e.getMessage().toString());
        }
    }

    private void beginListenForData() {
        try {
            final Handler handler = new Handler();

            // this is the ASCII code for a newline character
            final byte delimiter = 10;

            stopWorker = false;
            readBufferPosition = 0;
            readBuffer = new byte[1024];

            workerThread = new Thread(new Runnable() {
                public void run() {

                    while (!Thread.currentThread().isInterrupted() && !stopWorker) {

                        try {

                            int bytesAvailable = mmInputStream.available();

                            if (bytesAvailable > 0) {

                                byte[] packetBytes = new byte[bytesAvailable];
                                mmInputStream.read(packetBytes);

                                for (int i = 0; i < bytesAvailable; i++) {

                                    byte b = packetBytes[i];
                                    if (b == delimiter) {

                                        byte[] encodedBytes = new byte[readBufferPosition];
                                        System.arraycopy(
                                                readBuffer, 0,
                                                encodedBytes, 0,
                                                encodedBytes.length
                                        );

                                        // specify US-ASCII encoding
                                        final String data = new String(encodedBytes, "US-ASCII");
                                        readBufferPosition = 0;

                                    } else {
                                        readBuffer[readBufferPosition++] = b;
                                    }
                                }
                            }

                        } catch (IOException ex) {
                            stopWorker = true;
                        }

                    }
                }
            });

            workerThread.start();

        } catch (Exception e) {
            Log.e("Error begin listen data", e.getMessage().toString());
        }
    }
}
