package com.inducesmile.androidtabwithswipe;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by User on 2016-08-08.
 */
public class PopupStore extends Activity {
    Button mBtn;
    ListView list;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview2);

        mBtn = (Button)findViewById(R.id.agree);
        mBtn.setBackgroundColor(Color.BLACK);

        // 데이터 원본 준비
        ArrayList<String> arStore = new ArrayList<String>();
        arStore.add("    L (KR 77)");
        arStore.add("    M (KR 66)");
        arStore.add("    S (KR 55)");

        // 어댑터 준비
        ArrayAdapter<String> Adapter;
        Adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, arStore);

        // 어댑터 연결
        list = (ListView)findViewById(R.id.list);
        list.setAdapter(Adapter);
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }
}