package com.exomatik.kapcakeportrait.Model;

import android.bluetooth.BluetoothSocket;

import java.io.InputStream;
import java.io.OutputStream;

public class ModelSocket {
    private OutputStream mmOutputStream;
    private InputStream mmInputStream;
    private volatile boolean stopWorker;
    private BluetoothSocket mmSocket;
    private byte[] readBuffer;
    private int readBufferPosition;
    private Thread workerThread;
    private String macAddress;

    public ModelSocket() {
    }

    public ModelSocket(OutputStream mmOutputStream, InputStream mmInputStream
            , boolean stopWorker, BluetoothSocket mmSocket, byte[] readBuffer
            , int readBufferPosition, Thread workerThread, String macAddress) {
        this.mmOutputStream = mmOutputStream;
        this.mmInputStream = mmInputStream;
        this.stopWorker = stopWorker;
        this.mmSocket = mmSocket;
        this.readBuffer = readBuffer;
        this.readBufferPosition = readBufferPosition;
        this.workerThread = workerThread;
        this.macAddress = macAddress;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public OutputStream getMmOutputStream() {
        return mmOutputStream;
    }

    public void setMmOutputStream(OutputStream mmOutputStream) {
        this.mmOutputStream = mmOutputStream;
    }

    public InputStream getMmInputStream() {
        return mmInputStream;
    }

    public void setMmInputStream(InputStream mmInputStream) {
        this.mmInputStream = mmInputStream;
    }

    public boolean isStopWorker() {
        return stopWorker;
    }

    public void setStopWorker(boolean stopWorker) {
        this.stopWorker = stopWorker;
    }

    public BluetoothSocket getMmSocket() {
        return mmSocket;
    }

    public void setMmSocket(BluetoothSocket mmSocket) {
        this.mmSocket = mmSocket;
    }

    public byte[] getReadBuffer() {
        return readBuffer;
    }

    public void setReadBuffer(byte[] readBuffer) {
        this.readBuffer = readBuffer;
    }

    public int getReadBufferPosition() {
        return readBufferPosition;
    }

    public void setReadBufferPosition(int readBufferPosition) {
        this.readBufferPosition = readBufferPosition;
    }

    public Thread getWorkerThread() {
        return workerThread;
    }

    public void setWorkerThread(Thread workerThread) {
        this.workerThread = workerThread;
    }
}
