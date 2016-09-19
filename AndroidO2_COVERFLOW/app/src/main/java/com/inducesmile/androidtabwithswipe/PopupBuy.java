package com.inducesmile.androidtabwithswipe;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by User on 2016-08-08.
 */
public class PopupBuy extends AppCompatActivity {
    Button mBtn;
    ArrayList<MyItem> arItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);

        mBtn = (Button) findViewById(R.id.x);
        mBtn.setBackgroundColor(Color.WHITE);

        arItem = new ArrayList<MyItem>();
        MyItem mi;
        mi = new MyItem("  일반 정보");arItem.add(mi);
        mi = new MyItem("  지불 수단");arItem.add(mi);
        mi = new MyItem("  발송");arItem.add(mi);
        mi = new MyItem("  환불");arItem.add(mi);
        mi = new MyItem("  변경");arItem.add(mi);

        MyListAdapter MyAdapter = new MyListAdapter(this, R.layout.custom, arItem);

        ListView MyList;
        MyList=(ListView)findViewById(R.id.list);
        MyList.setAdapter(MyAdapter);
        MyList.setOnItemClickListener(mItemClickListener);
    }

    AdapterView.OnItemClickListener mItemClickListener =
            new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    String mes;
                    //mes = "Select Item = " + arSrc.get(position).name;
                    //Toast.makeText(PopupBuy.this, mes, Toast.LENGTH_SHORT).show();
                    /*if (position == 0) {
                        Intent intent0 = new Intent(PopupBuy.this, PurchaseGuidance.class);
                        startActivity(intent0);
                    }*/
                }
            };

    public void mOnClick(View v) {
        switch (v.getId()) {
            case R.id.x:
                finish();
                break;
        }
    }
}

//리스트 뷰에 출력할 항목
class MyItem {
    MyItem(String aName) {
        name = aName;
    }
    String name;
}

//어댑터 클래스
class MyListAdapter extends BaseAdapter {
    Context maincon;
    LayoutInflater Inflater;
    ArrayList<MyItem> arSrc;
    int layout;

    public MyListAdapter(Context context, int alayout, ArrayList<MyItem> aarSrc) {
        maincon = context;
        Inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        arSrc = aarSrc;
        layout = alayout;
    }

    public int getCount() {
        return arSrc.size();
    }

    public String getItem(int position) {
        return arSrc.get(position).name;
    }

    public long getItemId(int position) {
        return position;
    }

    // 각 항목의 뷰 생성
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        if (convertView == null) {
            convertView = Inflater.inflate(layout, parent, false);
        }
        TextView txt = (TextView) convertView.findViewById(R.id.text);
        txt.setText(arSrc.get(position).name);

        return convertView;
    }
}