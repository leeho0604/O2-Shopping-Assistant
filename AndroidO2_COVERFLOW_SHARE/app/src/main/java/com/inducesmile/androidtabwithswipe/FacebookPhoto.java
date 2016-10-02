package com.inducesmile.androidtabwithswipe;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.ShareApi;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.MessageDialog;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by elainachoe on 30/09/16.
 */
public class FacebookPhoto extends FragmentActivity {

    String SERVER_ADDRESS;
    Bitmap image;
    ImageView mImage;

    CallbackManager callbackManager;
    ShareDialog shareDialog;
    private LoginManager loginManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_facebookphoto);

        mImage = (ImageView) findViewById(R.id.mImage);


        callbackManager = CallbackManager.Factory.create();

        List<String> permissionNeeds = Arrays.asList("publish_actions");

        loginManager = LoginManager.getInstance();

        loginManager.logInWithPublishPermissions(this, permissionNeeds);

        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                sharePhotoToFacebook();
            }

            @Override
            public void onCancel() {
                System.out.println("onCancel");
            }

            @Override
            public void onError(FacebookException exception) {
                System.out.println("onError");
            }
        });


    }


    private void sharePhotoToFacebook() {

        StrictMode.enableDefaults();
        MyApplication myApp = (MyApplication) getApplication();
        SERVER_ADDRESS = myApp.getSERVER_ADDRESS();

        InputStream is = null;
        try {
            URL url = new URL(SERVER_ADDRESS + "/product_search.php?look_id=" + myApp.getGlobalValue3());//getGlobalValue3는 look 18개를 구분하기 위한 것
            is = url.openStream(); //서버의 product_search.php파일을 실행함

            ArrayList<String> imagelist = getXmlDataList("product_search_result.xml", "product_image"); //product_image 태그값을 읽어 image 리스트에 저장

            if (imagelist.isEmpty()) {

                Toast.makeText(FacebookPhoto.this, "Error downloading image", Toast.LENGTH_SHORT).show();
                Log.d("Error downloading image", "");

            }
            else {
                (new DownThread(SERVER_ADDRESS + imagelist.get(myApp.getGlobalValue2()))).start();
            }
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                // Swallow
            }
        }

        //Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.background);

        // image = thread.getFbPhoto();
//        SharePhoto photo = new SharePhoto.Builder()
//                .setBitmap(image)
//                .setCaption("I love this new outfit from O2!!")
//                .build();
//
//        SharePhotoContent content = new SharePhotoContent.Builder()
//                .addPhoto(photo)
//                .build();
//
//        ShareApi.share(content, null);

    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent data) {
        super.onActivityResult(requestCode, responseCode, data);
        callbackManager.onActivityResult(requestCode, responseCode, data);
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

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals(str)) { //태그 이름이 str 인자값과 같은 경우
                        ret.add(xpp.nextText());
                    }
                }
                eventType = xpp.next();
            }
        } catch (Exception e) {
            //Log.e("Error", e.getMessage());
        }

        return ret;
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
                Toast.makeText(FacebookPhoto.this, "bitmap is null", Toast.LENGTH_SHORT).show();
            }else{
                mImage.setImageBitmap(bit);
                image = bit;
                Toast.makeText(FacebookPhoto.this, "해당 상품이 타임라인에 공유되었습니다", Toast.LENGTH_SHORT).show();

                SharePhoto photo = new SharePhoto.Builder()
                        .setBitmap(image)
                        .setCaption("I love this new item from Zara <3  Download 'O2' from Google Play!")
                        .build();

                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .build();

                ShareApi.share(content, null);
            }
        }
    };

}




