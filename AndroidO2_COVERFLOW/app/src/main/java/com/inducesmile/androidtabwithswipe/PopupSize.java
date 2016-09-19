package com.inducesmile.androidtabwithswipe;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

/**
 * Created by User on 2016-08-08.
 */
public class PopupSize extends Activity {
    WebView mWeb;
    // 1.변수선언
    /*ImageView m_imageView;
    PhotoViewAttacher mAttacher;*/

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupsize);

        mWeb = (WebView)findViewById(R.id.web);
        mWeb.setWebViewClient(new MyWebClient());
        WebSettings set = mWeb.getSettings();
        set.setJavaScriptEnabled(true);
        set.setBuiltInZoomControls(true);
        mWeb.loadUrl("http://www.zara.com/kr/ko/product/3612490/size-guide");
        // 2.ImageView를 인자로 하여 PhotoViewAttacher객체를 생성
        /*m_imageView = (ImageView) findViewById(R.id.imageView);
        mAttacher = new PhotoViewAttacher(m_imageView);
        // 3.화면에 꽉차는 옵션 (선택사항)
        mAttacher.setScaleType(ImageView.ScaleType.FIT_XY);*/
    }

    class MyWebClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}