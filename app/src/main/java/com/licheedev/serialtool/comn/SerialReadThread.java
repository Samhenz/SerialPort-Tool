package com.licheedev.serialtool.comn;

import android.os.SystemClock;
import com.licheedev.hwutils.ByteUtil;
import com.licheedev.myutils.LogPlus;
import com.licheedev.serialtool.comn.message.LogManager;
import com.licheedev.serialtool.comn.message.RecvMessage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Read serial port threads
 */
public class SerialReadThread extends Thread {

    private static final String TAG = "SerialReadThread";

    private BufferedInputStream mInputStream;

    public SerialReadThread(InputStream is) {
        mInputStream = new BufferedInputStream(is);
    }

    @Override
    public void run() {
        byte[] received = new byte[1024];
        int size;

        LogPlus.e("Start reading threads");

        while (true) {

            if (Thread.currentThread().isInterrupted()) {
                break;
            }
            try {

                int available = mInputStream.available();

                if (available > 0) {
                    size = mInputStream.read(received);
                    if (size > 0) {
                        onDataReceive(received, size);
                    }
                } else {
                    // Pause for a little while, so as not to keep looping causing high CPU usage
                    SystemClock.sleep(1);
                }
            } catch (IOException e) {
                LogPlus.e("Failed to read data", e);
            }
            //Thread.yield();
        }

        LogPlus.e("Ending the read process");
    }

    /**
     * Processing the acquired data
     *
     * @param received
     * @param size
     */
    private void onDataReceive(byte[] received, int size) {
        // TODO: 2018/3/22 解决粘包、分包等
        String hexStr = ByteUtil.bytes2HexStr(received, 0, size);
        LogManager.instance().post(new RecvMessage(hexStr));
    }

    /**
     * Stop reading threads
     */
    public void close() {

        try {
            mInputStream.close();
        } catch (IOException e) {
            LogPlus.e("Exceptions", e);
        } finally {
            super.interrupt();
        }
    }
}
