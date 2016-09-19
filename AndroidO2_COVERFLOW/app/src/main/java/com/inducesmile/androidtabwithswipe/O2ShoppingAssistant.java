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
   // ImageView mAnimTarget;


    private FeatureCoverFlow mCoverFlow;
    private CoverFlowAdapter mAdapter;
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
        setContentView(R.layout.activity_o2_shopping_assistant);
        //setContentView(R.layout.activity_coverflow);

        MyApplication myApp = (MyApplication) getApplication();

        SERVER_ADDRESS = myApp.getSERVER_ADDRESS();



        mTitle = (TextSwitcher) findViewById(R.id.title);
        mTitle.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                LayoutInflater inflater = LayoutInflater.from(O2ShoppingAssistant.this);
                TextView textView = (TextView) inflater.inflate(R.layout.item_title, null);
                return textView;
            }
        });


//
//        mImage = (ImageView)findViewById(R.id.look1);
//        mImage2 = (ImageView)findViewById(R.id.look2);
//        mImage3 = (ImageView)findViewById(R.id.look3);
//        mImage4 = (ImageView)findViewById(R.id.look4);
//        mImage5 = (ImageView)findViewById(R.id.look5);
//        mImage6 = (ImageView)findViewById(R.id.look6);
//



        myApp.setGlobalValue0(2);

        // 아래 주석이 서버에서 이미지 데이터 받아 오는 부분
        // 일단 서버 안붙은 상태에서 테스트 해보려고 내가 주석 처리함
        // 주석 맨 밑에 보면 테스트 하려고 이미지 임의로 내가 넣어서 테스트함
        //


        addrlist = new ArrayList<String>();
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

        path += "/data/com.inducesmile.androidtabwithswipe/files/" + locallist.get(0);
        String path2 = Environment.getDataDirectory().getAbsolutePath();
        path2 += "/data/com.inducesmile.androidtabwithswipe/files/" + locallist.get(1);
        String path3 = Environment.getDataDirectory().getAbsolutePath();
        path3 += "/data/com.inducesmile.androidtabwithswipe/files/" + locallist.get(2);
        String path4 = Environment.getDataDirectory().getAbsolutePath();
        path4 += "/data/com.inducesmile.androidtabwithswipe/files/" + locallist.get(3);
        String path5 = Environment.getDataDirectory().getAbsolutePath();
        path5 += "/data/com.inducesmile.androidtabwithswipe/files/" + locallist.get(4);
        String path6 = Environment.getDataDirectory().getAbsolutePath();
        path6 += "/data/com.inducesmile.androidtabwithswipe/files/" + locallist.get(5);

        if (new File(path).exists()) {
            Toast.makeText(this, "bitmap exists", Toast.LENGTH_SHORT).show();
            bit1 = BitmapFactory.decodeFile(path);
            mData.add(new CoverEntity(bit1,""));

        }
        if (new File(path2).exists()) {
            Toast.makeText(this, "bitmap exists", Toast.LENGTH_SHORT).show();
            bit2 = BitmapFactory.decodeFile(path2);

            mData.add(new CoverEntity(bit2,""));

        }
        if (new File(path3).exists()) {
            Toast.makeText(this, "bitmap exists", Toast.LENGTH_SHORT).show();
            bit3 = BitmapFactory.decodeFile(path3);

            mData.add(new CoverEntity(bit3,""));

        }
        if (new File(path4).exists()) {
            Toast.makeText(this, "bitmap is exist", Toast.LENGTH_SHORT).show();
            
            bit4 = BitmapFactory.decodeFile(path4);
            mData.add(new CoverEntity(bit4,""));

        }
        if (new File(path5).exists()) {
            Toast.makeText(this, "bitmap exists", Toast.LENGTH_SHORT).show();
            
            bit5 = BitmapFactory.decodeFile(path5);
            mData.add(new CoverEntity(bit5,""));

        }
        if (new File(path6).exists()) {
            Toast.makeText(this, "bitmap exists", Toast.LENGTH_SHORT).show();
            bit6 = BitmapFactory.decodeFile(path6);
            mData.add(new CoverEntity(bit6,""));

        } else {
            Toast.makeText(this, "bitmap does not exist", Toast.LENGTH_SHORT).show();
            (new DownThread(addrlist, locallist)).start();

        }


        /*
        mData.add(new CoverEntity(BitmapFactory.decodeResource(getResources(), R.drawable.image_1), ""));
        mData.add(new CoverEntity(BitmapFactory.decodeResource(getResources(), R.drawable.image_2), ""));
        mData.add(new CoverEntity(BitmapFactory.decodeResource(getResources(), R.drawable.image_3), ""));
        mData.add(new CoverEntity(BitmapFactory.decodeResource(getResources(), R.drawable.image_4), ""));
        mData.add(new CoverEntity(BitmapFactory.decodeResource(getResources(), R.drawable.image_1), ""));
        mData.add(new CoverEntity(BitmapFactory.decodeResource(getResources(), R.drawable.image_2), ""));
        */


        mAdapter = new CoverFlowAdapter(this);
        mAdapter.setData(mData);
        mCoverFlow = (FeatureCoverFlow) findViewById(R.id.coverflow);
        mCoverFlow.setAdapter(mAdapter);

        Animation in  = AnimationUtils.loadAnimation(this, R.anim.slide_in_top);
        Animation out = AnimationUtils.loadAnimation(this, R.anim.slide_out_bottom);


        mTitle.setInAnimation(in);
        mTitle.setOutAnimation(out);


       // mProgress.setVisibility(View.INVISIBLE);

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();


        mCoverFlow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*
                Toast.makeText(O2ShoppingAssistant.this,
                         getResources().getString(mData.get(position).titleResId),
                        Toast.LENGTH_SHORT).show();
                */

                //Animation anim = null;
                MyApplication myApp = (MyApplication) getApplication();
                Intent intent = new Intent(view.getContext(), AndroidO2.class);

                /*
                switch (mData.get(position).imageResId) {
                    case bit1:
                        myApp.setGlobalValue("1");
                        break;
                    case bit2:
                        myApp.setGlobalValue("2");
                        break;
                    case bit3:
                        myApp.setGlobalValue("3");
                        break;
                    case bit4:
                        myApp.setGlobalValue("4");
                        break;
                    case bit5:
                        myApp.setGlobalValue("5");
                        break;
                    case bit6:
                        myApp.setGlobalValue("6");
                        break;
                }
                */

                if (mData.get(position).imageResId == bit1)
                    myApp.setGlobalValue("1");
                if (mData.get(position).imageResId == bit2)
                    myApp.setGlobalValue("2");
                if (mData.get(position).imageResId == bit3)
                    myApp.setGlobalValue("3");
                if (mData.get(position).imageResId == bit4)
                    myApp.setGlobalValue("4");
                if (mData.get(position).imageResId == bit5)
                    myApp.setGlobalValue("5");
                if (mData.get(position).imageResId == bit6)
                    myApp.setGlobalValue("6");

                    startActivity(intent);
            }



        });


        mCoverFlow.setOnScrollPositionListener(new FeatureCoverFlow.OnScrollPositionListener() {
            @Override
            public void onScrolledToPosition(int position) {

                /*
                mTitle.setText(getResources().getString(mData.get(position).titleResId));
                */


            }

            @Override
            public void onScrolling() {
                mTitle.setText("");
            }
        });

    }

    /*
    public void mOnClick(View v) {//throws InterruptedException {
        Animation anim = null;
        MyApplication myApp = (MyApplication) getApplication();
        Intent intent = new Intent(this, AndroidO2.class);
        switch (v.getId()) {
            case R.id.look1:
                myApp.setGlobalValue("1");
                break;
            case R.id.look2:
                myApp.setGlobalValue("2");
                break;
            case R.id.look3:
                myApp.setGlobalValue("3");
                break;
            case R.id.look4:
                myApp.setGlobalValue("4");
                break;
            case R.id.look5:
                myApp.setGlobalValue("5");
                break;
            case R.id.look6:
                myApp.setGlobalValue("6");
                break;
        }
        startActivity(intent);
    }
*/

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
                for (int i = 0; i < mAddrs.size(); i++) {
                    imageurl = new URL(mAddrs.get(i));
                    HttpURLConnection conn = (HttpURLConnection) imageurl.openConnection();
                    int len = conn.getContentLength();
                    byte[] raster = new byte[len];
                    InputStream is = conn.getInputStream();
                    FileOutputStream fos = openFileOutput(mFiles.get(i), 0);

                    for (; ; ) {
                        Read = is.read(raster);
                        if (Read <= 0) {
                            break;
                        }
                        fos.write(raster, 0, Read);
                    }

                    is.close();
                    fos.close();
                    conn.disconnect();
                    switch (i) {
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
                        case 5:
                            index = 6;
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
                path += "/data/com.inducesmile.androidtabwithswipe/files/" + (String) msg.obj;
                switch (index) {
                    case 1:
                        mData.add(new CoverEntity(BitmapFactory.decodeFile(path),""));
                        break;

                    case 2:
                        mData.add(new CoverEntity(BitmapFactory.decodeFile(path),""));
                        break;

                    case 3:
                        mData.add(new CoverEntity(BitmapFactory.decodeFile(path),""));
                        break;

                    case 4:
                        mData.add(new CoverEntity(BitmapFactory.decodeFile(path),""));
                        break;

                    case 5:
                        mData.add(new CoverEntity(BitmapFactory.decodeFile(path),""));
                        break;

                    case 6:
                        mData.add(new CoverEntity(BitmapFactory.decodeFile(path),""));
                        break;
                }


            } else {
                Toast.makeText(O2ShoppingAssistant.this, "File not found", Toast.LENGTH_SHORT).show();
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


