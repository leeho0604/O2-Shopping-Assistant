/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2015 Perples, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.inducesmile.androidtabwithswipe;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.perples.recosdk.RECOBeacon;
import com.perples.recosdk.RECOBeaconManager;
import com.perples.recosdk.RECOBeaconRegion;
import com.perples.recosdk.RECOErrorCode;
import com.perples.recosdk.RECORangingListener;

import java.util.ArrayList;
import java.util.Collection;

/**
 * RECORangingActivity class is to range regions in the foreground.
 *
 * RECORangingActivity 클래스는 foreground 상태에서 ranging을 수행합니다.
 */
public class RecoRangingActivity extends RecoActivity implements RECORangingListener {

    int beacon;
    MyApplication myApp;
    private ArrayList<RECOBeacon> mRangedBeacons;
    private RECOBeaconManager recoManager;
    private ArrayList<RECOBeaconRegion> rangingRegions;

    private RecoRangingListAdapter mRangingListAdapter;
    private ListView mRegionListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reco_ranging);

        myApp = (MyApplication) getApplication();

        //mRecoManager will be created here. (Refer to the RECOActivity.onCreate())
        //mRecoManager 인스턴스는 여기서 생성됩니다. RECOActivity.onCreate() 메소들르 참고하세요.

        //Set RECORangingListener (Required)
        //RECORangingListener 를 설정합니다. (필수)
        mRecoManager.setRangingListener(this);

        /**
         * Bind RECOBeaconManager with RECOServiceConnectListener, which is implemented in RECOActivity
         * You SHOULD call this method to use monitoring/ranging methods successfully.
         * After binding, onServiceConenct() callback method is called.
         * So, please start monitoring/ranging AFTER the CALLBACK is called.
         *
         * RECOServiceConnectListener와 함께 RECOBeaconManager를 bind 합니다. RECOServiceConnectListener는 RECOActivity에 구현되어 있습니다.
         * monitoring 및 ranging 기능을 사용하기 위해서는, 이 메소드가 "반드시" 호출되어야 합니다.
         * bind후에, onServiceConnect() 콜백 메소드가 호출됩니다. 콜백 메소드 호출 이후 monitoring / ranging 작업을 수행하시기 바랍니다.
         */
        mRecoManager.bind(this);

        NotificationManager nm =
                (NotificationManager)getSystemService(NOTIFICATION_SERVICE);


// Cancel Notification
        nm.cancel(9999);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mRangingListAdapter = new RecoRangingListAdapter(this);
        mRegionListView = (ListView)findViewById(R.id.list_ranging);
        mRegionListView.setAdapter(mRangingListAdapter);
        mRegionListView.setOnItemClickListener(mItemClickListener);
    }

    AdapterView.OnItemClickListener mItemClickListener =
            new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    String mes;
                    //mes = "Select Item = " + arSrc.get(position).name;
                    //Toast.makeText(PopupBuy.this, mes, Toast.LENGTH_SHORT).show();
                    if ((position == 0) && (myApp.getGlobalValue0() == 2)) {
                        Intent intent0 = new Intent(RecoRangingActivity.this, O2ShoppingAssistant.class);
                        startActivity(intent0);
                    }
                }
            };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.stop(mRegions);
        this.unbind();
    }

    private void unbind() {
        try {
            mRecoManager.unbind();
        } catch (RemoteException e) {
            Log.i("RECORangingActivity", "Remote Exception");
            e.printStackTrace();
        }
    }

    @Override
    public void onServiceConnect() {
        Log.i("RECORangingActivity", "onServiceConnect()");
        mRecoManager.setDiscontinuousScan(MainActivityb.DISCONTINUOUS_SCAN);
        this.start(mRegions);
        //Write the code when RECOBeaconManager is bound to RECOBeaconService
        mRangedBeacons = new ArrayList<RECOBeacon>();
        //리스트 생성해서 비콘 값 저장
        rangingRegions = new ArrayList<RECOBeaconRegion>();
        //비콘의UUID, Major, Minor, 이름
        rangingRegions.add(new RECOBeaconRegion("24DDF411-8CF1-440C-87CD-E368DAF9C93E", 501, 14770, "비콘1"));
        rangingRegions.add(new RECOBeaconRegion("24DDF411-8CF1-440C-87CD-E368DAF9C93E", 501, 14771, "비콘2"));
        rangingRegions.add(new RECOBeaconRegion("24DDF411-8CF1-440C-87CD-E368DAF9C93E", 501, 14772, "비콘3"));

        for (RECOBeaconRegion region : rangingRegions) {
            try {
                recoManager.startRangingBeaconsInRegion(region);
                recoManager.requestStateForRegion(region);
            } catch (RemoteException e) {
                // RemoteException 발생 시 작성 코드

            } catch (NullPointerException e) {
                // NullPointerException 발생 시 작성 코드

            }
        }
    }

    @Override
    public void didRangeBeaconsInRegion(Collection<RECOBeacon> recoBeacons, RECOBeaconRegion recoRegion) {
        Log.i("RECORangingActivity", "didRangeBeaconsInRegion() region: " + recoRegion.getUniqueIdentifier() + ", number of beacons ranged: " + recoBeacons.size());
        mRangingListAdapter.updateAllBeacons(recoBeacons);
        mRangingListAdapter.notifyDataSetChanged();
        //Write the code when the beacons in the region is received

        //Log.i("RECORangingActivity", "didRangeBeaconsInRegion() minor: " + recoBeacons.add);

        /*if (recoRegion.getMinor() == 14770) {
            if (recoBeacons.size() == 0) {
                beacon = 1;
            } else {
                beacon = 1;
            }

        } else if (recoRegion.getMinor() == 14771) {
            if (recoBeacons.size() == 0) {
                beacon = 2;
            } else {
                beacon = 2;
            }
        } else if (recoRegion.getMinor() == 14772) {
            if (recoBeacons.size() == 0) {
                beacon = 3;
            } else {
                beacon = 3;
            }
        }*/
    }

    @Override
    protected void start(ArrayList<RECOBeaconRegion> regions) {

        /**
         * There is a known android bug that some android devices scan BLE devices only once. (link: http://code.google.com/p/android/issues/detail?id=65863)
         * To resolve the bug in our SDK, you can use setDiscontinuousScan() method of the RECOBeaconManager.
         * This method is to set whether the device scans BLE devices continuously or discontinuously.
         * The default is set as FALSE. Please set TRUE only for specific devices.
         *
         * mRecoManager.setDiscontinuousScan(true);
         */

        for(RECOBeaconRegion region : regions) {
            try {
                mRecoManager.startRangingBeaconsInRegion(region);
            } catch (RemoteException e) {
                Log.i("RECORangingActivity", "Remote Exception");
                e.printStackTrace();
            } catch (NullPointerException e) {
                Log.i("RECORangingActivity", "Null Pointer Exception");
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void stop(ArrayList<RECOBeaconRegion> regions) {
        for(RECOBeaconRegion region : regions) {
            try {
                mRecoManager.stopRangingBeaconsInRegion(region);
            } catch (RemoteException e) {
                Log.i("RECORangingActivity", "Remote Exception");
                e.printStackTrace();
            } catch (NullPointerException e) {
                Log.i("RECORangingActivity", "Null Pointer Exception");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onServiceFail(RECOErrorCode errorCode) {
        //Write the code when the RECOBeaconService is failed.
        //See the RECOErrorCode in the documents.
        return;
    }

    @Override
    public void rangingBeaconsDidFailForRegion(RECOBeaconRegion region, RECOErrorCode errorCode) {
        //Write the code when the RECOBeaconService is failed to range beacons in the region.
        //See the RECOErrorCode in the documents.
        return;
    }
}
