package com.inducesmile.androidtabwithswipe;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;


public class O2ShoppingAssistant extends AppCompatActivity {


    public String mValue_set = "1";
    public int index = 1;

    String SERVER_ADDRESS;
    ArrayList<String> addrlist;
    ArrayList<String> locallist;
    Bitmap bit1;
    Bitmap bit2;
    Bitmap bit3;
    Bitmap bit4;
    Bitmap bit5;
    Bitmap bit6;

    Drawable drawable1;
    Drawable drawable2;
    Drawable drawable3;
    Drawable drawable4;
    Drawable drawable5;
    Drawable drawable6;

    ImageView mImage;
    ImageView mImage2;
    ImageView mImage3;
    ImageView mImage4;
    ImageView mImage5;
    ImageView mImage6;
    ProgressBar mProgress;


    private FeatureCoverFlow       mCoverFlow = null;
    private CoverFlowAdapter       mAdapter   = null;
    private ArrayList<CoverEntity> mData = new ArrayList<>(0);

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private TextSwitcher mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyApplication myApp = (MyApplication) getApplication();
        client              = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        myApp.setGlobalValue0(1);
        SERVER_ADDRESS      = myApp.getSERVER_ADDRESS();

        addrlist  = new ArrayList<String>();
        locallist = new ArrayList<String>();

        switch (myApp.getGlobalValue0()) {
            case 1:
                addrlist.add(SERVER_ADDRESS + "/pic/look_1.bmp");
                addrlist.add(SERVER_ADDRESS + "/pic/look_2.bmp");
                addrlist.add(SERVER_ADDRESS + "/pic/look_3.bmp");
                addrlist.add(SERVER_ADDRESS + "/pic/look_4.bmp");
                addrlist.add(SERVER_ADDRESS + "/pic/look_5.bmp");
                addrlist.add(SERVER_ADDRESS + "/pic/look_6.bmp");
                break;
            case 2:
                addrlist.add(SERVER_ADDRESS + "/pic/look_7.bmp");
                addrlist.add(SERVER_ADDRESS + "/pic/look_8.bmp");
                addrlist.add(SERVER_ADDRESS + "/pic/look_9.bmp");
                addrlist.add(SERVER_ADDRESS + "/pic/look_10.bmp");
                addrlist.add(SERVER_ADDRESS + "/pic/look_11.bmp");
                addrlist.add(SERVER_ADDRESS + "/pic/look_12.bmp");
                break;
            case 3:
                addrlist.add(SERVER_ADDRESS + "/pic/look_13.bmp");
                addrlist.add(SERVER_ADDRESS + "/pic/look_14.bmp");
                addrlist.add(SERVER_ADDRESS + "/pic/look_15.bmp");
                addrlist.add(SERVER_ADDRESS + "/pic/look_16.bmp");
                addrlist.add(SERVER_ADDRESS + "/pic/look_17.bmp");
                addrlist.add(SERVER_ADDRESS + "/pic/look_18.bmp");
                break;
        }

        int idx = addrlist.get(0).lastIndexOf('/');
        locallist.add(addrlist.get(0).substring(idx + 1));
        locallist.add(addrlist.get(1).substring(idx + 1));
        locallist.add(addrlist.get(2).substring(idx + 1));
        locallist.add(addrlist.get(3).substring(idx + 1));
        locallist.add(addrlist.get(4).substring(idx + 1));
        locallist.add(addrlist.get(5).substring(idx + 1));
        String path = Environment.getDataDirectory().getAbsolutePath();
        Log.d("Image Path", path);

        (new LoadBMPThread(addrlist, locallist, OnDownloadBmpHandler)).start();
    }

    public void SetView()
    {
        setContentView(R.layout.activity_o2_shopping_assistant);

        mTitle = (TextSwitcher) findViewById(R.id.title);
        mTitle.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                LayoutInflater inflater = LayoutInflater.from(O2ShoppingAssistant.this);
                TextView textView = (TextView) inflater.inflate(R.layout.item_title, null);
                return textView;
            }
        });

        mAdapter = new CoverFlowAdapter(this);
        mAdapter.setData(mData);
        mCoverFlow = (FeatureCoverFlow) findViewById(R.id.coverflow);
        mCoverFlow.setAdapter(mAdapter);

        Animation in = AnimationUtils.loadAnimation(this, R.anim.slide_in_top);
        Animation out = AnimationUtils.loadAnimation(this, R.anim.slide_out_bottom);

        mTitle.setInAnimation(in);
        mTitle.setOutAnimation(out);

        // mProgress.setVisibility(View.INVISIBLE);

        mCoverFlow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Animation anim = null;
                MyApplication myApp = (MyApplication) getApplication();

                myApp.setGlobalValue(Integer.toString(position+1));

                //if (mData.get(position).imageResId == bit1)
                //    myApp.setGlobalValue("1");
                //if (mData.get(position).imageResId == bit2)
                //    myApp.setGlobalValue("2");
                //if (mData.get(position).imageResId == bit3)
                //    myApp.setGlobalValue("3");
                //if (mData.get(position).imageResId == bit4)
                //    myApp.setGlobalValue("4");
                //if (mData.get(position).imageResId == bit5)
                //    myApp.setGlobalValue("5");
                //if (mData.get(position).imageResId == bit6)
                //    myApp.setGlobalValue("6");

                Intent intent = new Intent(view.getContext(), AndroidO2.class);
                startActivity(intent);
            }
        });

        mCoverFlow.setOnScrollPositionListener(new FeatureCoverFlow.OnScrollPositionListener() {
            @Override
            public void onScrolledToPosition(int position) {
                // mTitle.setText(getResources().getString(mData.get(position).titleResId));
            }

            @Override
            public void onScrolling() {
                mTitle.setText("");
            }
        });
    }

    public void onBackPressed() {
        // TODO Auto-generated method stub
        // super.onBackPressed(); //지워야 실행됨

        AlertDialog.Builder d = new AlertDialog.Builder(this);
        d.setMessage("이 어플리케이션 사용을 중지하시겠습니까?");
        d.setPositiveButton("네", new DialogInterface.OnClickListener() {


            public void onClick(DialogInterface dialog, int which) {
                // process전체 종료
                finish();
            }
        });
        d.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        d.show();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();

        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "O2ShoppingAssistant Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.inducesmile.androidtabwithswipe/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "O2ShoppingAssistant Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.inducesmile.androidtabwithswipe/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    Handler OnDownloadBmpHandler = new Handler()
    {
        public void AddItem(Object obj)
        {
            if (obj != null) {
                // String path = Environment.getDataDirectory().getAbsolutePath();
                // path += "/data/com.inducesmile.androidtabwithswipe/files/" + (String) obj;
                mData.add(new CoverEntity((Bitmap) obj,""));
            } else {
                Toast.makeText(O2ShoppingAssistant.this, "File not found", Toast.LENGTH_SHORT).show();
            }
        }
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);

            switch (msg.what)
            {
                case LoadBMPThread.BMP_LOAD_SUCCESS:
                    AddItem(msg.obj);
                    break;
                case LoadBMPThread.ALL_BMP_LOAD_SUCCESS:
                    SetView();
                    break;
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_coverflow_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


