package com.inducesmile.androidtabwithswipe;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class BookMarkActivity extends AppCompatActivity
{
    private LinearLayout linearLayout;

    private ArrayList<Button> buttons = new ArrayList<Button>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater     = LayoutInflater.from(this);
        CoordinatorLayout rootView  = (CoordinatorLayout)inflater.inflate(R.layout.activity_book_mark, null, false);
        NestedScrollView scrollView = (NestedScrollView)rootView.getChildAt(2);
        linearLayout   = (LinearLayout)scrollView.getChildAt(0);
        for (int count = 0; count < CartItemContainer.getInstance().getCount(); ++count)
        {
            LinearLayout layout  = (LinearLayout) inflater.inflate(R.layout.book_mark_item, null, false);

            TextView  bookMarkTitle       = (TextView)layout.findViewById(R.id.bookmarkTitle);
            TextView  bookMarkDescription = (TextView)layout.findViewById(R.id.bookmarkDescription);
            ImageView cartItemImage       = (ImageView)layout.findViewById(R.id.bookMarkImage);
            Button    button = (Button)layout.findViewById(R.id.cartItemDelete);

            bookMarkTitle.setText(CartItemContainer.getInstance().getName(count));
            bookMarkDescription.setText(CartItemContainer.getInstance().getPrice(count));
            cartItemImage.setImageBitmap(CartItemContainer.getInstance().getBitmap(count));

            button.setOnClickListener(mCartItemDelete);

            buttons.add(button);
            linearLayout.addView(layout);
        }

        setContentView(rootView);
    }

    public void Refresh()
    {
        if(linearLayout.getChildCount() > 0)
        {
            linearLayout.removeAllViews();
        }
    }

    public int Remove(int id)
    {
        for(int count = 0; count < buttons.size(); ++count)
        {
            if(buttons.get(count).getId() == id)
            {
                CartItemContainer.getInstance().RemoveAt(count);
                return count;
            }
        }
        return -1;
    }


    // Create an anonymous implementation of OnClickListener
    private View.OnClickListener mCartItemDelete = new View.OnClickListener() {
        public void onClick(View v) {
            Log.d("OnClickListener", "mCartItemDelete" + " " + v.getId());
            // do something when the button is clicked
            int index = Remove(v.getId());

            Log.d("OnClickListener", " " + index);

            if (index >= 0) {
                linearLayout.removeViewAt(index);
                linearLayout.invalidate();
            }
        }
    };


}
