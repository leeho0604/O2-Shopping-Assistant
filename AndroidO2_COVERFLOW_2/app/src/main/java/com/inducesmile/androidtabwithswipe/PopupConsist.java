package com.inducesmile.androidtabwithswipe;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by User on 2016-08-07.
 */
public class PopupConsist extends Activity {

    public int index = 1;

    String SERVER_ADDRESS;
    ArrayList<String> addrlist;
    ArrayList<String> locallist;
    String[] array;
    TextView mText;
    TextView mText2;
    TextView mText3;
    TextView mText4;
    TextView mText5;
    TextView mText6;
    TextView mText7;
    TextView mText8;
    TextView mText9;
    TextView mText10;
    TextView mText11;
    ImageView mImage;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupconsist);

        MyApplication myApp = (MyApplication) getApplication();
        SERVER_ADDRESS = myApp.getSERVER_ADDRESS();

        mText = (TextView)findViewById(R.id.product_id);
        mText2 = (TextView)findViewById(R.id.name);
        mText3 = (TextView)findViewById(R.id.comment);
        mText4 = (TextView)findViewById(R.id.material);
        mText5 = (TextView)findViewById(R.id.material1);
        mText6 = (TextView)findViewById(R.id.material2);
        mText7 = (TextView)findViewById(R.id.material3);
        mText8 = (TextView)findViewById(R.id.material4);
        mText9 = (TextView)findViewById(R.id.material5);
        mText10 = (TextView)findViewById(R.id.material6);
        mText11 = (TextView)findViewById(R.id.material7);
        mImage = (ImageView)findViewById(R.id.laundry);

        StrictMode.enableDefaults();

        try {
            mText.setText("");

            URL url = new URL(SERVER_ADDRESS + "/product_search.php?look_id=" + myApp.getGlobalValue());
            url.openStream(); //서버의 product_search.php파일을 실행함

            ArrayList<String> product_idlist = getXmlDataList("product_search_result.xml", "product_id");//product_id 태그값을 읽어 namelist 리스트에 저장
            ArrayList<String> namelist = getXmlDataList("product_search_result.xml", "name"); //name 태그값을 읽어 prica 리스트에 저장
            ArrayList<String> commentlist = getXmlDataList("product_search_result.xml", "comment");
            ArrayList<String> materiallist = getXmlDataList("product_search_result.xml", "material");
            ArrayList<String> laundrylist = getXmlDataList("product_search_result.xml", "laundry");

            if (product_idlist.isEmpty())
                mText.setText("아무것도 검색되지 않았습니다.");
            else {
                addrlist = new ArrayList<String>();
                locallist = new ArrayList<String>();
                addrlist.add(SERVER_ADDRESS+laundrylist.get(0));
                int idx = addrlist.get(0).lastIndexOf('/');
                locallist.add(addrlist.get(0).substring(idx + 1));
                String path = Environment.getDataDirectory().getAbsolutePath();
                path += "/data/com.inducesmile.androidtabwithswipe/files/" + locallist.get(0);
                if (new File(path).exists()) {
                    mImage.setImageBitmap(BitmapFactory.decodeFile(path));
                } else {
                    (new DownThread(addrlist, locallist)).start();
                }
                mText.setText(product_idlist.get(myApp.getGlobalValue2()));
                mText2.setText(namelist.get(myApp.getGlobalValue2()));
                mText3.setText(commentlist.get(myApp.getGlobalValue2()));
                int count=0;
                for(int i=0;i<materiallist.get(myApp.getGlobalValue2()).length();i++) {
                    if(materiallist.get(myApp.getGlobalValue2()).charAt(i)==':')count++;
                }
                array = materiallist.get(myApp.getGlobalValue2()).split(":");
                if(count == 7) {
                    mText4.setText(array[0]);
                    mText5.setText(array[1]);
                    mText6.setText(array[2]);
                    mText7.setText(array[3]);
                    mText8.setText(array[4]);
                    mText9.setText(array[5]);
                    mText10.setText(array[6]);
                    mText11.setText(array[7]);
                }
                if(count == 6) {
                    mText4.setText(array[0]);
                    mText5.setText(array[1]);
                    mText6.setText(array[2]);
                    mText7.setText(array[3]);
                    mText8.setText(array[4]);
                    mText9.setText(array[5]);
                    mText10.setText(array[6]);
                    mText11.setVisibility(View.GONE);
                }
                if(count == 5) {
                    mText4.setText(array[0]);
                    mText5.setText(array[1]);
                    mText6.setText(array[2]);
                    mText7.setText(array[3]);
                    mText8.setText(array[4]);
                    mText9.setText(array[5]);
                    mText10.setVisibility(View.GONE);
                    mText11.setVisibility(View.GONE);
                }
                if(count == 4) {
                    mText4.setText(array[0]);
                    mText5.setText(array[1]);
                    mText6.setText(array[2]);
                    mText7.setText(array[3]);
                    mText8.setText(array[4]);
                    mText9.setVisibility(View.GONE);
                    mText10.setVisibility(View.GONE);
                    mText11.setVisibility(View.GONE);
                }
                if(count == 3) {
                    mText4.setText(array[0]);
                    mText5.setText(array[1]);
                    mText6.setText(array[2]);
                    mText7.setText(array[3]);
                    mText8.setVisibility(View.GONE);
                    mText9.setVisibility(View.GONE);
                    mText10.setVisibility(View.GONE);
                    mText11.setVisibility(View.GONE);
                }
                if(count == 2) {
                    mText4.setText(array[0]);
                    mText5.setText(array[1]);
                    mText6.setText(array[2]);
                    mText7.setVisibility(View.GONE);
                    mText8.setVisibility(View.GONE);
                    mText9.setVisibility(View.GONE);
                    mText10.setVisibility(View.GONE);
                    mText11.setVisibility(View.GONE);
                }
                if(count == 1) {
                    mText4.setText(array[0]);
                    mText5.setText(array[1]);
                    mText6.setVisibility(View.GONE);
                    mText7.setVisibility(View.GONE);
                    mText8.setVisibility(View.GONE);
                    mText9.setVisibility(View.GONE);
                    mText10.setVisibility(View.GONE);
                    mText11.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        } finally {
        }
    }

    private ArrayList<String> getXmlDataList(String filename, String str) { //태그값 여러개를 받아오기위한 ArrayList<String>형 변수
        String rss = SERVER_ADDRESS + "/";
        ArrayList<String> ret = new ArrayList<String>();

        try { //XML 파싱을 위한 과정
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            URL server = new URL(rss + filename);
            InputStream is = server.openStream();
            xpp.setInput(is, "UTF-8");

            int eventType = xpp.getEventType();

            while(eventType != XmlPullParser.END_DOCUMENT) {
                if(eventType == XmlPullParser.START_TAG) {
                    if(xpp.getName().equals(str)) { //태그 이름이 str 인자값과 같은 경우
                        ret.add(xpp.nextText());
                    }
                }
                eventType = xpp.next();
            }
        } catch(Exception e) {
            //Log.e("Error", e.getMessage());
        }

        return ret;
    }

    class DownThread extends Thread {
        ArrayList<String> mAddrs;
        ArrayList<String> mFiles;

        DownThread(ArrayList<String> addrs, ArrayList<String> filenames) {
            mAddrs = addrs;
            mFiles = filenames;
        }

        public void run() {
            URL imageurl;
            int Read;
            try {
                for(int i = 0; i < mAddrs.size(); i++) {
                    imageurl = new URL(mAddrs.get(i));
                    HttpURLConnection conn= (HttpURLConnection)imageurl.openConnection();
                    int len = conn.getContentLength();
                    byte[] raster = new byte[len];
                    InputStream is = conn.getInputStream();
                    FileOutputStream fos = openFileOutput(mFiles.get(i), 0);

                    for (;;) {
                        Read = is.read(raster);
                        if (Read <= 0) {
                            break;
                        }
                        fos.write(raster,0, Read);
                    }

                    is.close();
                    fos.close();
                    conn.disconnect();
                    switch(i) {
                        case 0:
                            index = 1;
                            break;
                    }
                    Message message = mAfterDown.obtainMessage();
                    message.obj = mFiles.get(i);
                    mAfterDown.sendMessage(message);
                }
            } catch (Exception e) {
                mFiles = null;
            }
        }
    }

    Handler mAfterDown = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.obj != null) {
                String path = Environment.getDataDirectory().getAbsolutePath();
                path += "/data/com.inducesmile.androidtabwithswipe/files/" + (String)msg.obj;
                File file = new File(path);
                Uri uri = Uri.fromFile(file);
                Bitmap bm = null;
                try {
                    bm = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                switch(index) {
                    case 1:
                        mImage.setImageBitmap(bm);
                        //mImage.setImageBitmap(BitmapFactory.decodeFile(path));
                        break;
                }
            } else {
                Toast.makeText(PopupConsist.this, "File not found", Toast.LENGTH_SHORT).show();
            }
        }
    };
}