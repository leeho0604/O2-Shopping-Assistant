package com.inducesmile.androidtabwithswipe;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by User on 2016-08-18.
 */
public class AndroidO2 extends Activity {

    public int index = 1;

    String SERVER_ADDRESS;
    ArrayList<String> addrlist;
    ArrayList<String> locallist;
    ImageView mImage;
    ImageView mImage2;
    ImageView mImage3;
    ImageView mImage4;
    ImageView mImage5;

    TextView mText;
    TextView mText2;
    TextView mText3;
    TextView mText4;
    TextView mText5;
    TextView mText6;
    TextView mText7;
    TextView mText8;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_o2);

        MyApplication myApp = (MyApplication) getApplication();
        SERVER_ADDRESS = myApp.getSERVER_ADDRESS();

        mImage = (ImageView)findViewById(R.id.look2);
        mImage2 = (ImageView)findViewById(R.id.btn);
        mImage3 = (ImageView)findViewById(R.id.btn2);
        mImage4 = (ImageView)findViewById(R.id.btn3);
        mImage5 = (ImageView)findViewById(R.id.btn4);
        mText = (TextView)findViewById(R.id.name1);
        mText2 = (TextView)findViewById(R.id.price1);
        mText3 = (TextView)findViewById(R.id.name2);
        mText4 = (TextView)findViewById(R.id.price2);
        mText5 = (TextView)findViewById(R.id.name3);
        mText6 = (TextView)findViewById(R.id.price3);
        mText7 = (TextView)findViewById(R.id.name4);
        mText8 = (TextView)findViewById(R.id.price4);
        StrictMode.enableDefaults();

        try {
            switch(myApp.getGlobalValue0()) {
                case 1:
                    myApp.setGlobalValue3(myApp.getGlobalValue());
                    break;
                case 2:
                    switch(myApp.getGlobalValue()) {
                        case "1":
                            myApp.setGlobalValue3("7");
                            break;
                        case "2":
                            myApp.setGlobalValue3("8");
                            break;
                        case "3":
                            myApp.setGlobalValue3("9");
                            break;
                        case "4":
                            myApp.setGlobalValue3("10");
                            break;
                        case "5":
                            myApp.setGlobalValue3("11");
                            break;
                        case "6":
                            myApp.setGlobalValue3("12");
                            break;
                    }
                    break;
                case 3:
                    switch(myApp.getGlobalValue()) {
                        case "1":
                            myApp.setGlobalValue3("13");
                            break;
                        case "2":
                            myApp.setGlobalValue3("14");
                            break;
                        case "3":
                            myApp.setGlobalValue3("15");
                            break;
                        case "4":
                            myApp.setGlobalValue3("16");
                            break;
                        case "5":
                            myApp.setGlobalValue3("17");
                            break;
                        case "6":
                            myApp.setGlobalValue3("18");
                            break;
                    }
                    break;
            }

            URL url = new URL(SERVER_ADDRESS + "/product_search.php?look_id=" + myApp.getGlobalValue3());
            url.openStream(); //서버의 product_search.php파일을 실행함

            ArrayList<String> namelist = getXmlDataList("/src/product_search_result.xml", "name");//name 태그값을 읽어 namelist 리스트에 저장
            ArrayList<String> pricelist = getXmlDataList("/src/product_search_result.xml", "price"); //price 태그값을 읽어 prica 리스트에 저장
            ArrayList<String> imagelist = getXmlDataList("/src/product_search_result.xml", "product_image"); //product_image 태그값을 읽어 image 리스트에 저장

            if(namelist.isEmpty())
                mText.setText("아무것도 검색되지 않았습니다.");
            else {
                /*String imageurl = SERVER_ADDRESS + "/pic/look_" + number + ".bmp";
                int idx = imageurl.lastIndexOf('/');
                String localimage = imageurl.substring(idx + 1);
                String path = Environment.getDataDirectory().getAbsolutePath();
                path += "/data/com.example.beacon/files/" + localimage;

                if (new File(path).exists()) {
                    Toast.makeText(this, "bitmap is exist", Toast.LENGTH_SHORT).show();
                    mImage.setImageBitmap(BitmapFactory.decodeFile(path));
                } else {
                    Toast.makeText(this, "bitmap is not exist", Toast.LENGTH_SHORT).show();
                    (new DownThread(imageurl, localimage)).start();
                }*/

                addrlist = new ArrayList<String>();
                locallist = new ArrayList<String>();
                if(imagelist.size() == 3) {
                    addrlist.add(SERVER_ADDRESS+"/pic/look_" + myApp.getGlobalValue3() + ".bmp");
                    addrlist.add(SERVER_ADDRESS+imagelist.get(0));
                    addrlist.add(SERVER_ADDRESS+imagelist.get(1));
                    addrlist.add(SERVER_ADDRESS+imagelist.get(2));
                    int idx = addrlist.get(0).lastIndexOf('/');
                    //String localimage = addrlist.get(0).substring(idx + 1);
                    locallist.add(addrlist.get(0).substring(idx + 1));
                    locallist.add(addrlist.get(1).substring(idx + 1));
                    locallist.add(addrlist.get(2).substring(idx + 1));
                    locallist.add(addrlist.get(3).substring(idx + 1));
                    String path = Environment.getDataDirectory().getAbsolutePath();
                    path += "/data/com.inducesmile.androidtabwithswipe/files/" + locallist.get(0);
                    String path2 = Environment.getDataDirectory().getAbsolutePath();
                    path2 += "/data/com.inducesmile.androidtabwithswipe/files/" + locallist.get(1);
                    String path3 = Environment.getDataDirectory().getAbsolutePath();
                    path3 += "/data/com.inducesmile.androidtabwithswipe/files/" + locallist.get(2);
                    String path4 = Environment.getDataDirectory().getAbsolutePath();
                    path4 += "/data/com.inducesmile.androidtabwithswipe/files/" + locallist.get(3);
                    if (new File(path).exists()) {
                        Toast.makeText(this, "bitmap is exist", Toast.LENGTH_SHORT).show();
                        mImage.setImageBitmap(BitmapFactory.decodeFile(path));
                    }
                    if (new File(path2).exists()) {
                        Toast.makeText(this, "bitmap is exist", Toast.LENGTH_SHORT).show();
                        mImage2.setImageBitmap(BitmapFactory.decodeFile(path2));
                    }
                    if (new File(path3).exists()) {
                        Toast.makeText(this, "bitmap is exist", Toast.LENGTH_SHORT).show();
                        mImage3.setImageBitmap(BitmapFactory.decodeFile(path3));
                    }
                    if (new File(path4).exists()) {
                        Toast.makeText(this, "bitmap is exist", Toast.LENGTH_SHORT).show();
                        mImage4.setImageBitmap(BitmapFactory.decodeFile(path4));
                    } else {
                        Toast.makeText(this, "bitmap is not exist", Toast.LENGTH_SHORT).show();
                        (new DownThread(addrlist, locallist)).start();
                    }
                    mText.setText(namelist.get(0));
                    mText2.setText(pricelist.get(0) + " 원");
                    mText3.setText(namelist.get(1));
                    mText4.setText(pricelist.get(1) + " 원");
                    mText5.setText(namelist.get(2));
                    mText6.setText(pricelist.get(2) + " 원");
                }
                if(imagelist.size() == 4) {
                    addrlist.add(SERVER_ADDRESS+"/pic/look_" + myApp.getGlobalValue3() + ".bmp");
                /*for(int i = 0; i < imagelist.size(); i++) {
                    addrlist.add(SERVER_ADDRESS+imagelist.get(i));
                }*/
                    addrlist.add(SERVER_ADDRESS+imagelist.get(0));
                    addrlist.add(SERVER_ADDRESS+imagelist.get(1));
                    addrlist.add(SERVER_ADDRESS+imagelist.get(2));
                    addrlist.add(SERVER_ADDRESS+imagelist.get(3));
                    int idx = addrlist.get(0).lastIndexOf('/');
                    //String localimage = addrlist.get(0).substring(idx + 1);
                /*for(int i = 0; i <= imagelist.size(); i++) {
                    locallist.add(addrlist.get(i).substring(idx + 1));
                }*/
                    locallist.add(addrlist.get(0).substring(idx + 1));
                    locallist.add(addrlist.get(1).substring(idx + 1));
                    locallist.add(addrlist.get(2).substring(idx + 1));
                    locallist.add(addrlist.get(3).substring(idx + 1));
                    locallist.add(addrlist.get(4).substring(idx + 1));
                    String path = Environment.getDataDirectory().getAbsolutePath() + "/data/com.inducesmile.androidtabwithswipe/files/";
                    String path1 = path + locallist.get(0);
                    String path2 = path + locallist.get(1);
                    String path3 = path + locallist.get(2);
                    String path4 = path + locallist.get(3);
                //String path5 = "";
                //if(imagelist.size() == 4) {
                    String path5 = path + locallist.get(4);
                //}
                    if (new File(path1).exists()) {
                        //Toast.makeText(this, "bitmap is exist", Toast.LENGTH_SHORT).show();
                        mImage.setImageBitmap(BitmapFactory.decodeFile(path1));
                    } /*else {
                        Toast.makeText(this, "bitmap is not exist", Toast.LENGTH_SHORT).show();
                        (new DownThread(addrlist, locallist.get(0))).start();
                    }*/
                    if (new File(path2).exists()) {
                        //Toast.makeText(this, "bitmap is exist", Toast.LENGTH_SHORT).show();
                        mImage2.setImageBitmap(BitmapFactory.decodeFile(path2));
                    } /*else {
                        Toast.makeText(this, "bitmap is not exist", Toast.LENGTH_SHORT).show();
                        (new DownThread(addrlist, locallist.get(1))).start();
                    }*/
                    if (new File(path3).exists()) {
                        //Toast.makeText(this, "bitmap is exist", Toast.LENGTH_SHORT).show();
                        mImage3.setImageBitmap(BitmapFactory.decodeFile(path3));
                    } /*else {
                        Toast.makeText(this, "bitmap is not exist", Toast.LENGTH_SHORT).show();
                        (new DownThread(addrlist, locallist.get(2))).start();
                    }*/
                    if (new File(path4).exists()) {
                        //Toast.makeText(this, "bitmap is exist", Toast.LENGTH_SHORT).show();
                        mImage4.setImageBitmap(BitmapFactory.decodeFile(path4));
                    } /*else {
                        Toast.makeText(this, "bitmap is not exist", Toast.LENGTH_SHORT).show();
                        (new DownThread(addrlist, locallist.get(3))).start();
                    }*/
                //if(imagelist.size() == 4) {
                    if (new File(path5).exists()) {
                        //Toast.makeText(this, "bitmap is exist", Toast.LENGTH_SHORT).show();
                        mImage5.setImageBitmap(BitmapFactory.decodeFile(path5));
                    } else {
                        //Toast.makeText(this, "bitmap is not exist", Toast.LENGTH_SHORT).show();
                        (new DownThread(addrlist, locallist)).start();
                    }
                    //(new DownThread(addrlist)).start();
                    mText.setText(namelist.get(0));
                    mText2.setText(pricelist.get(0) + " 원");
                    mText3.setText(namelist.get(1));
                    mText4.setText(pricelist.get(1) + " 원");
                    mText5.setText(namelist.get(2));
                    mText6.setText(pricelist.get(2) + " 원");
                //if(imagelist.size() == 4) {
                    mText7.setText(namelist.get(3));
                    mText8.setText(pricelist.get(3) + " 원");
                //}
                }
            }
        } catch(Exception e) {
            //Log.e("Error", e.getMessage());
        } finally{
        }
    }

    private ArrayList<String> getXmlDataList(String filename, String str) { //태그값 여러개를 받아오기위한 ArrayList<String>형 변수
        String rss = SERVER_ADDRESS + "/";//?????????이게 맞는건가...
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
            Log.e("Error", e.getMessage());
        }

        return ret;
    }

    public void mOnClick(View v) {
        MyApplication myApp = (MyApplication) getApplication();
        Intent intent = new Intent(this, SubActivity.class);
        switch (v.getId()) {
            case R.id.btn:
                myApp.setGlobalValue2(0);
                break;
            case R.id.btn2:
                myApp.setGlobalValue2(1);
                break;
            case R.id.btn3:
                myApp.setGlobalValue2(2);
                break;
            case R.id.btn4:
                myApp.setGlobalValue2(3);
                break;
        }
        startActivity(intent);
    }

    /*public static int getBitmapOfWidth( String fileName ){

        try {

            BitmapFactory.Options options = new BitmapFactory.Options();

            options.inJustDecodeBounds = true;

            BitmapFactory.decodeFile(fileName, options);

            return options.outWidth;

        } catch(Exception e) {

            return 0;

        }

    }



    public static int getBitmapOfHeight( String fileName ){

        try {

            BitmapFactory.Options options = new BitmapFactory.Options();

            options.inJustDecodeBounds = true;

            BitmapFactory.decodeFile(fileName, options);

            return options.outHeight;

        } catch(Exception e) {

            return 0;

        }

    }

    private Bitmap decodeFile( int minImageSize, InputStream is ){
        Bitmap b = null;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream( is, null, options );
        int scale = 1;
        if( options.outHeight > minImageSize || options.outWidth > minImageSize ) {
            scale = (int)Math.pow(  2,  (int)Math.round( Math.log( minImageSize / (double)Math.max( options.outHeight, options.outWidth ) ) / Math.log( 0.5 ) ) );
        }
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        b = BitmapFactory.decodeStream( is, null, o2 );

        return b;
    }*/

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
                        case 1:
                            index = 2;
                            break;
                        case 2:
                            index = 3;
                            break;
                        case 3:
                            index = 4;
                            break;
                        case 4:
                            index = 5;
                            break;
                    }
                    Message message = mAfterDown.obtainMessage();
                    message.obj = mFiles.get(i);
                    mAfterDown.sendMessage(message);
                }
                /*imageurl = new URL(mAddr);
                HttpURLConnection conn= (HttpURLConnection)imageurl.openConnection();
                int len = conn.getContentLength();
                byte[] raster = new byte[len];
                InputStream is = conn.getInputStream();
                FileOutputStream fos = openFileOutput(mFile, 0);

                for (;;) {
                    Read = is.read(raster);
                    if (Read <= 0) {
                        break;
                    }
                    fos.write(raster,0, Read);
                }

                is.close();
                fos.close();
                conn.disconnect();*/
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
                switch(index) {
                    case 1:
                        mImage.setImageBitmap(BitmapFactory.decodeFile(path));
                        break;
                    case 2:
                        mImage2.setImageBitmap(BitmapFactory.decodeFile(path));
                        break;
                    case 3:
                        mImage3.setImageBitmap(BitmapFactory.decodeFile(path));
                        break;
                    case 4:
                        mImage4.setImageBitmap(BitmapFactory.decodeFile(path));
                        break;
                    case 5:
                        mImage5.setImageBitmap(BitmapFactory.decodeFile(path));
                        break;
                }
            } else {
                Toast.makeText(AndroidO2.this, "File not found", Toast.LENGTH_SHORT).show();
            }
        }
    };
}
