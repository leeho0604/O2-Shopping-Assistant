package com.inducesmile.androidtabwithswipe;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by User on 2016-08-07.
 */
public class PopupConsist extends Activity {
    private final String SERVER_ADDRESS = "http://10.200.18.87:8888/O2";
    TextView mText;
    TextView mText2;
    TextView mText3;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupconsist);

        MyApplication myApp = (MyApplication) getApplication();

        mText = (TextView)findViewById(R.id.product_id);
        mText2 = (TextView)findViewById(R.id.name);
        mText3 = (TextView)findViewById(R.id.comment);

        StrictMode.enableDefaults();

        try {
            mText.setText("");
            String number = "1";
            switch(myApp.getGlobalValue()) {
                case "2":
                    number = "2";
                    break;
                case "3":
                    number = "3";
                    break;
                case "4":
                    number = "4";
                    break;
                case "5":
                    number = "5";
                    break;
                case "6":
                    number = "6";
                    break;
            }
            URL url = new URL(SERVER_ADDRESS + "/product_search.php?look_id=" + number);
            url.openStream(); //서버의 product_search.php파일을 실행함

            ArrayList<String> product_idlist = getXmlDataList("product_search_result.xml", "product_id");//product_id 태그값을 읽어 namelist 리스트에 저장
            ArrayList<String> namelist = getXmlDataList("product_search_result.xml", "name"); //name 태그값을 읽어 prica 리스트에 저장
            ArrayList<String> commentlist = getXmlDataList("product_search_result.xml", "comment");

            if (product_idlist.isEmpty())
                mText.setText("아무것도 검색되지 않았습니다.");
            else {
                mText.setText(product_idlist.get(myApp.getGlobalValue2()));
                mText2.setText(namelist.get(myApp.getGlobalValue2()));
                mText3.setText(commentlist.get(myApp.getGlobalValue2()));
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
}