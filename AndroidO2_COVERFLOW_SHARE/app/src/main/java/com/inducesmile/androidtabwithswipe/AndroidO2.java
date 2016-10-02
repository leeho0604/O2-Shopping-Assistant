package com.inducesmile.androidtabwithswipe;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
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

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;



/**
 * Created by User on 2016-08-18.
 */
public class AndroidO2 extends Activity {

    public int index = 1;


    private int downloadedCount = 0;

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


        MyApplication myApp = (MyApplication) getApplication();

        Log.d("AndroidO2","Hello");
        setContentView(R.layout.activity_android_o2);

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

        Log.d("AndroidO2","1.Hello");


            Log.d("AndroidO2","1-1.Hello");

        switch(myApp.getGlobalValue0()) {
            case 1:
                myApp.setGlobalValue3(Integer.toString(myApp.getGlobalValue()));
                break;
            case 2:
                myApp.setGlobalValue3(Integer.toString(myApp.getGlobalValue() + 6));
                break;
            case 3:
                myApp.setGlobalValue3(Integer.toString(myApp.getGlobalValue() + 12));
                break;
        }

            Log.d("AndroidO2","2.Hello");


        try {
                URL url = new URL(SERVER_ADDRESS + "/product_search.php?look_id=" + myApp.getGlobalValue3());
                url.openStream(); //서버의 product_search.php파일을 실행함
            } catch (Exception e)
            {

            }

            ArrayList<String> namelist  = getXmlDataList("/product_search_result.xml", "name");//name 태그값을 읽어 namelist 리스트에 저장
            ArrayList<String> pricelist = getXmlDataList("/product_search_result.xml", "price"); //price 태그값을 읽어 prica 리스트에 저장
            ArrayList<String> imagelist = getXmlDataList("/product_search_result.xml", "product_image"); //product_image 태그값을 읽어 image 리스트에 저장

            Log.d("AndroidO2","3.Hello");


            if(namelist.isEmpty())
                mText.setText("아무것도 검색되지 않았습니다.");
            else {
                addrlist = new ArrayList<String>();
                locallist = new ArrayList<String>();
                if(imagelist.size() == 3) {
                    addrlist.add(SERVER_ADDRESS+"/pic/look_" + myApp.getGlobalValue3() + ".jpg");
                    addrlist.add(SERVER_ADDRESS+imagelist.get(0));
                    addrlist.add(SERVER_ADDRESS+imagelist.get(1));
                    addrlist.add(SERVER_ADDRESS+imagelist.get(2));

                    int idx = addrlist.get(0).lastIndexOf('/');
                    //String localimage = addrlist.get(0).substring(idx + 1);
                    locallist.add(addrlist.get(0).substring(idx + 1));
                    locallist.add(addrlist.get(1).substring(idx + 1));
                    locallist.add(addrlist.get(2).substring(idx + 1));
                    locallist.add(addrlist.get(3).substring(idx + 1));

                    downloadedCount = 0;
                    (new LoadBMPThread(addrlist, locallist, OnDownloadBmpHandler)).start();

                    mText.setText(namelist.get(0));
                    mText2.setText(pricelist.get(0) + " 원");
                    mText3.setText(namelist.get(1));
                    mText4.setText(pricelist.get(1) + " 원");
                    mText5.setText(namelist.get(2));
                    mText6.setText(pricelist.get(2) + " 원");
                }
                if(imagelist.size() == 4) {
                    addrlist.add(SERVER_ADDRESS+"/pic/look_" + myApp.getGlobalValue3() + ".jpg");
                    addrlist.add(SERVER_ADDRESS+imagelist.get(0));
                    addrlist.add(SERVER_ADDRESS+imagelist.get(1));
                    addrlist.add(SERVER_ADDRESS+imagelist.get(2));
                    addrlist.add(SERVER_ADDRESS+imagelist.get(3));
                    int idx = addrlist.get(0).lastIndexOf('/');

                    locallist.add(addrlist.get(0).substring(idx + 1));
                    locallist.add(addrlist.get(1).substring(idx + 1));
                    locallist.add(addrlist.get(2).substring(idx + 1));
                    locallist.add(addrlist.get(3).substring(idx + 1));
                    locallist.add(addrlist.get(4).substring(idx + 1));

                    downloadedCount = 0;
                    (new LoadBMPThread(addrlist, locallist, OnDownloadBmpHandler)).start();

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

    }

    private ArrayList<String> getXmlDataList(String filename, String str) { //태그값 여러개를 받아오기위한 ArrayList<String>형 변수
        String rss = SERVER_ADDRESS+"/";
        ArrayList<String> ret = new ArrayList<String>();

        try
        { //XML 파싱을 위한 과정
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            Log.d("XML",rss + filename);
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
        } catch(Exception e)
        {
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



    Handler OnDownloadBmpHandler = new Handler()
    {
        public void AddItem(Object obj)
        {

            Log.d("1.Handler", downloadedCount + " ");

            if (obj != null)
            {
                ++downloadedCount;
                Log.d("2.Handler", downloadedCount + " ");
switch(downloadedCount) {
                    case 1:
                        mImage.setImageBitmap((Bitmap)obj);
                        break;
                    case 2:
                        mImage2.setImageBitmap((Bitmap)obj);
                        break;
                    case 3:
                        mImage3.setImageBitmap((Bitmap)obj);
                        break;
                    case 4:
                        mImage4.setImageBitmap((Bitmap)obj);
                        break;
                    case 5:
                        mImage5.setImageBitmap((Bitmap)obj);
                        break;
                }

            } else {
                Toast.makeText(AndroidO2.this, "File not found", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);


            Log.d("0.Handler", downloadedCount + " ");

            switch (msg.what)
            {
                case LoadBMPThread.BMP_LOAD_SUCCESS:
                    AddItem(msg.obj);
                    break;
                case LoadBMPThread.ALL_BMP_LOAD_SUCCESS:
                    break;
            }
        }
    };

    Handler mAfterDown = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.obj != null) {
                String path = Environment.getDataDirectory().getAbsolutePath();
                path += "/data/com.inducesmile.androidtabwithswipe/files/" + (String)msg.obj;
                switch(index) {
                    case 0:
                        mImage.setImageBitmap(BitmapFactory.decodeFile(path));
                        break;
                    case 1:
                        mImage2.setImageBitmap(BitmapFactory.decodeFile(path));
                        break;
                    case 2:
                        mImage3.setImageBitmap(BitmapFactory.decodeFile(path));
                        break;
                    case 3:
                        mImage4.setImageBitmap(BitmapFactory.decodeFile(path));
                        break;
                    case 4:
                        mImage5.setImageBitmap(BitmapFactory.decodeFile(path));
                        break;
                }
            } else {
                Toast.makeText(AndroidO2.this, "File not found", Toast.LENGTH_SHORT).show();
            }
        }
    };
}
