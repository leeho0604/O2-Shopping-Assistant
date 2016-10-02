package com.inducesmile.androidtabwithswipe;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.InputStream;
import java.io.FilterInputStream;
import java.io.IOException;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by mchoi on 9/21/16.
 */

public class LoadBMPThread extends Thread
{
    public final static int BMP_LOAD_SUCCESS      = 0;
    public final static int ALL_BMP_LOAD_SUCCESS  = 1;

    private ArrayList<String> mAddrs;
    private ArrayList<String> mFiles;
    private Handler           mHandler;
    int downloadedCount;

    LoadBMPThread(ArrayList<String> addrs,
                  ArrayList<String> filenames,
                  Handler           handler) {
        mAddrs = addrs;
        mFiles = filenames;
        mHandler = handler;
        downloadedCount = 0;
    }

    @Override
    public void run()
    {
        super.run();
        Log.d("Thread","Download Thread Started");

        URL imageurl;
        int Read;
        try
        {
            for (int i = 0; i < mAddrs.size(); i++)
            {
                String filePath = "/data/com.inducesmile.androidtabwithswipe/files/"+mFiles.get(i);
                File file     = new File(filePath);
                Bitmap bitmap   = null;
                if (file.exists())
                {
                    Log.d("LoadBmp", filePath +" File exist");
                    bitmap = BitmapFactory.decodeFile(filePath);
                }
                else
                {
                 //   String addr               = "http://"+mAddrs.get(i); // REAL CODE.
                    String addr               = mAddrs.get(i); // REAL CODE.

                    // String addr                  = "https://upload.wikimedia.org/wikipedia/commons/thumb/3/36/Bmp_format2.svg/260px-Bmp_format2.svg.png"; // FOR TEST

                    Log.d("LoadBmp", filePath +" File does not exist");
                    Log.d("LoadBmp", "Start download bmp file with " + addr);

                    imageurl                     = new URL(addr);
                    HttpURLConnection connection = (HttpURLConnection) imageurl.openConnection();
                    connection.setDoInput(true);
                    connection.connect();

                    switch (connection.getResponseCode())
                    {
                        case HttpURLConnection.HTTP_OK:
                            Log.d("Http", imageurl + " **OK**");
                            break;
                        case HttpURLConnection.HTTP_GATEWAY_TIMEOUT:
                            Log.d("Http", imageurl + " **gateway timeout**");
                            break;// retry
                        case HttpURLConnection.HTTP_UNAVAILABLE:
                            Log.d("Http", imageurl + "**unavailable**");
                            break;// retry, server is unstable
                        default:
                            Log.d("Http", imageurl + " **unknown response code**.");
                            break; // abort
                    }

                    int len                = connection.getContentLength();
                    Log.d("Thread", "1. **unknown response code**. " + len);

                    InputStream input = connection.getInputStream();
                    bitmap = BitmapFactory.decodeStream(new FlushedInputStream(input));
                    input.close();
                    connection.disconnect();
                }

                if (bitmap != null)
                {
                    ++downloadedCount;
                }

                Message message = mHandler.obtainMessage();
                message.obj     = bitmap;
                message.what    = BMP_LOAD_SUCCESS;
                mHandler.sendMessage(message);

                if (downloadedCount >= mAddrs.size())
                {
                    Log.d("DownloadBmp","ALL_BMP_LOAD_SUCCESS");
                    message      = mHandler.obtainMessage();
                    message.what = ALL_BMP_LOAD_SUCCESS;
                    mHandler.sendMessage(message);
                }
            }
        } catch (Exception e)
        {
            Log.d("Thread(Expection)", e.getMessage());
            mFiles = null;
        }
    }

    static class FlushedInputStream extends FilterInputStream {
        public FlushedInputStream(InputStream inputStream) {
            super(inputStream);
        }

        @Override
        public long skip(long n) throws IOException {
            long totalBytesSkipped = 0L;
            while (totalBytesSkipped < n) {
                long bytesSkipped = in.skip(n - totalBytesSkipped);
                if (bytesSkipped == 0L) {
                    int b = read();
                    if (b < 0) {
                        break;  // we reached EOF
                    } else {
                        bytesSkipped = 1; // we read one byte
                    }
                }
                totalBytesSkipped += bytesSkipped;
            }
            return totalBytesSkipped;
        }
    }
}
