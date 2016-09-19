package com.inducesmile.androidtabwithswipe;

import android.app.Application;

/**
 * Created by User on 2016-08-21.
 */
public class MyApplication extends Application {
    private String SERVER_ADDRESS = "o2shoppingassistant.esy.es";
    private int gValue0;
    private String gValue;
    private int gValue2;
    private String gValue3;

    // 타 class에서 MyApplication class를 통해 해당 variable 값을 참조
    public String getSERVER_ADDRESS() { return SERVER_ADDRESS; }
    public int getGlobalValue0() { return gValue0; }
    public String getGlobalValue(){
        return gValue;
    }
    public int getGlobalValue2() { return gValue2; }
    public String getGlobalValue3() { return gValue3; }

    // 타 class에서 변경한 variable을 MyApplication에 저장
    public void setGlobalValue0(int mValue) { this.gValue0 = mValue; }
    public void setGlobalValue(String mValue){
        this.gValue = mValue;
    }
    public void setGlobalValue2(int mValue) { this.gValue2 = mValue; }
    public void setGlobalValue3(String mValue) { this.gValue3 = mValue; }
}
