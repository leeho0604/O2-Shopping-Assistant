package com.inducesmile.androidtabwithswipe;

import android.app.Application;

/**
 * Created by User on 2016-08-21.
 */
public class MyApplication extends Application {
    private String SERVER_ADDRESS = "http://www.o2shoppingassistant.esy.es";
    private int gValue0;
    private int gValue;
    private int gValue2;
    private String gValue3;

    // 타 class에서 MyApplication class를 통해 해당 variable 값을 참조
    public String getSERVER_ADDRESS() { return SERVER_ADDRESS; }
    public int getGlobalValue0() { return gValue0; }//비콘
    public int getGlobalValue(){ return gValue;  }//룩 자리 구분
    public int getGlobalValue2() { return gValue2; }//룩 선택 후 선택한 구성상품
    public String getGlobalValue3() { return gValue3; }//룩 이름 숫자

    // 타 class에서 변경한 variable을 MyApplication에 저장
    public void setGlobalValue0(int mValue) { this.gValue0 = mValue; }
    public void setGlobalValue(int mValue){
        this.gValue = mValue;
    }
    public void setGlobalValue2(int mValue) { this.gValue2 = mValue; }
    public void setGlobalValue3(String mValue) { this.gValue3 = mValue; }
}
