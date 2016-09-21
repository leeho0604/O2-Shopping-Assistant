package com.inducesmile.androidtabwithswipe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by User on 2016-08-06.
 */
public class SubActivity extends Activity {
    String SERVER_ADDRESS;
    ImageView mImage;
    PopupWindow popup;
    View popupview;
    LinearLayout linear;
    Vibrator mVib;
    Button mBtn;

    TextView mText;
    TextView mText2;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subactivity);

        MyApplication myApp = (MyApplication) getApplication();
        SERVER_ADDRESS = myApp.getSERVER_ADDRESS();

        mImage = (ImageView) findViewById(R.id.imageView1);

        linear = (LinearLayout) findViewById(R.id.linear);
        //popupview = View.inflate(this, R.layout.listview3, null);
        popup = new PopupWindow(popupview, 800, 600, true);

        mVib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mBtn = (Button) findViewById(R.id.button1);
        mBtn.setBackgroundColor(Color.WHITE);

        mText = (TextView) findViewById(R.id.name);
        mText2 = (TextView) findViewById(R.id.price);

        StrictMode.enableDefaults();

        try {
            mText.setText("");
            URL url = new URL(SERVER_ADDRESS + "/product_search.php?look_id=" + myApp.getGlobalValue3());//getGlobalValue3는 look 18개를 구분하기 위한 것
            url.openStream(); //서버의 product_search.php파일을 실행함

            ArrayList<String> namelist = getXmlDataList("product_search_result.xml", "name");//name 태그값을 읽어 namelist 리스트에 저장
            ArrayList<String> pricelist = getXmlDataList("product_search_result.xml", "price"); //price 태그값을 읽어 prica 리스트에 저장
            ArrayList<String> imagelist = getXmlDataList("product_search_result.xml", "product_image"); //product_image 태그값을 읽어 image 리스트에 저장

            if (namelist.isEmpty())
                mText.setText("아무것도 검색되지 않았습니다.");
            else {
                (new DownThread(SERVER_ADDRESS+imagelist.get(myApp.getGlobalValue2()))).start();
                mText.setText(namelist.get(myApp.getGlobalValue2()));
                mText2.setText(pricelist.get(myApp.getGlobalValue2()) + " 원");
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

    protected void onDestroy() {
        super.onDestroy();
        //mVib.cancel();
    }

    public void mOnClick(View v) {
        mVib.vibrate(100);
        /*popup.setAnimationStyle(-1);
        popup.showAsDropDown(mBtn);*/
        Intent intent = new Intent(getApplicationContext(), Information.class);
        startActivity(intent);
    }

    class DownThread extends Thread {
        String mAddr;

        DownThread(String addr) {
            mAddr = addr;
        }

        public void run() {
            try {
                InputStream is = new URL(mAddr).openStream();
                Bitmap bit = BitmapFactory.decodeStream(is);
                is.close();
                Message message = mAfterDown.obtainMessage();
                message.obj = bit;
                mAfterDown.sendMessage(message);
            } catch (Exception e) {;}
        }
    }

    Handler mAfterDown = new Handler() {
        public void handleMessage(Message msg) {
            Bitmap bit = (Bitmap)msg.obj;
            if (bit == null) {
                Toast.makeText(SubActivity.this, "bitmap is null", Toast.LENGTH_SHORT).show();
            } else {
                mImage.setImageBitmap(bit);
            }
        }
    };
}