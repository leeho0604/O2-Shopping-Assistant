package com.inducesmile.androidtabwithswipe;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.perples.recosdk.RECOBeacon;
import com.perples.recosdk.RECOBeaconManager;
import com.perples.recosdk.RECOBeaconRegion;
import com.perples.recosdk.RECOErrorCode;
import com.perples.recosdk.RECORangingListener;
import com.perples.recosdk.RECOServiceConnectListener;

import java.util.ArrayList;
import java.util.Collection;

public class MainActivity extends AppCompatActivity implements RECOServiceConnectListener, RECORangingListener {

    private ArrayList<RECOBeacon> mRangedBeacons;
    private TextView tv;
    private RECOBeaconManager recoManager;
    private ArrayList<RECOBeaconRegion> rangingRegions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.textView);
        tv.setMaxLines(60);
        tv.setVerticalScrollBarEnabled(true);
        tv.setMovementMethod(new ScrollingMovementMethod());
        tv.setText("Beacon시작");

        //RECOBeaconManager의 인스턴스를 생성합니다
        //RECOBeaconManager.getInstance(Context, boolean, boolean)의 경우,
        //Context, RECO 비콘만을 대상으로 동작 여부를 설정하는 값, 그리고 백그라운드 monitoring 중 ranging 시 timeout을 설정하는 값을 매개변수로 받습니다.
        recoManager = RECOBeaconManager.getInstance(this, true, true);
        recoManager.bind(this);
        recoManager.setRangingListener(this);

        NotificationManager nm =
                (NotificationManager)getSystemService(NOTIFICATION_SERVICE);


// Cancel Notification
        nm.cancel(9999);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/


    //연결이 되었을때 제공되는 서비스 부분
    @Override
    public void onServiceConnect() {
        tv.setText(tv.getText() + "\n연결되었습니다");

        mRangedBeacons = new ArrayList<RECOBeacon>();
        //리스트 생성해서 비콘 값 저장
        rangingRegions = new ArrayList<RECOBeaconRegion>();
        //비콘의UUID, Major, 이름
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

    public void updateBeacon(RECOBeacon beacon) {
        synchronized (mRangedBeacons) {
            if(mRangedBeacons.contains(beacon)) {
                mRangedBeacons.remove(beacon);
            }
            mRangedBeacons.add(beacon);
        }
    }

    public void updateAllBeacons(Collection<RECOBeacon> beacons) {
        synchronized (beacons) {
            mRangedBeacons = new ArrayList<RECOBeacon>(beacons);
        }
    }

    public void clear() {
        mRangedBeacons.clear();
    }

    public int getCount() {
        return mRangedBeacons.size();
    }

    public Object getItem(int position) {
        return mRangedBeacons.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onServiceFail(RECOErrorCode arg0) {
        // TODO Auto-generated method stub

    }


    //1초마다 비콘변화 감지
    @Override
    public void didRangeBeaconsInRegion(Collection<RECOBeacon> arg0, RECOBeaconRegion arg1) {

        MyApplication myApp = (MyApplication) getApplication();
        Intent intent = new Intent(getApplicationContext(), O2ShoppingAssistant.class);
        if (arg0.size() != 0) {
            switch (arg1.getMinor()) {
                case 14770:
                    myApp.setGlobalValue0(1); //비콘1
                    break;
                case 14771:
                    myApp.setGlobalValue0(2); //비콘2
                    break;
                case 14772:
                    myApp.setGlobalValue0(3); //비콘3
                    break;
            }
            startActivity(intent);
            for(RECOBeaconRegion region : rangingRegions) {
                try {
                    recoManager.stopRangingBeaconsInRegion(region);
                } catch (RemoteException e) {
                    //RemoteException 발생 시 작성 코드
                } catch (NullPointerException e) {
                    //NullPointerException 발생 시 작성 코드
                }
            }
        }
    }
    // TODO Auto-generated method stub

        /*if (arg0.size() == 0) {
            tv.setText(tv.getText() + "\n" + arg1.getUniqueIdentifier() + " 없음");
        } else {
            tv.setText(tv.getText() + "\n" + arg1.getUniqueIdentifier() + " 있음");
            Intent intent = new Intent(getApplicationContext(), Android_BookActivity.class);
            startActivity(intent);
            for(RECOBeaconRegion region : rangingRegions) {
                try {
                    recoManager.stopRangingBeaconsInRegion(region);
                } catch (RemoteException e) {
                    //RemoteException 발생 시 작성 코드
                } catch (NullPointerException e) {
                    //NullPointerException 발생 시 작성 코드
                }
            }
        }*/
		/*if (arg1.getMinor() == 14770) {
			if (arg0.size() == 0) {
			} else {
                myApp.setGlobalValue0(1);
                startActivity(intent);
                for(RECOBeaconRegion region : rangingRegions) {
                    try {
                        recoManager.stopRangingBeaconsInRegion(region);
                    } catch (RemoteException e) {
                        //RemoteException 발생 시 작성 코드
                    } catch (NullPointerException e) {
                        //NullPointerException 발생 시 작성 코드
                    }
                }
			}

		} else if (arg1.getMinor() == 14771) {
			if (arg0.size() == 0) {
			} else {
                myApp.setGlobalValue0(2);
                startActivity(intent);
                for(RECOBeaconRegion region : rangingRegions) {
                    try {
                        recoManager.stopRangingBeaconsInRegion(region);
                    } catch (RemoteException e) {
                        //RemoteException 발생 시 작성 코드
                    } catch (NullPointerException e) {
                        //NullPointerException 발생 시 작성 코드
                    }
                }
			}
		} else if (arg1.getMinor() == 14772) {
			if (arg0.size() == 0) {
			} else {
                myApp.setGlobalValue0(3);
                startActivity(intent);
                for (RECOBeaconRegion region : rangingRegions) {
                    try {
                        recoManager.stopRangingBeaconsInRegion(region);
                    } catch (RemoteException e) {
                        //RemoteException 발생 시 작성 코드
                    } catch (NullPointerException e) {
                        //NullPointerException 발생 시 작성 코드
                    }
                }
            }
		}*/

    @Override
    public void rangingBeaconsDidFailForRegion(RECOBeaconRegion arg0, RECOErrorCode arg1) {
        // TODO Auto-generated method stub
        tv.setText(tv.getText() + "\n실패하였습니다.");
    }
}
