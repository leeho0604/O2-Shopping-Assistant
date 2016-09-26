package com.inducesmile.androidtabwithswipe;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * Created by marco.granatiero on 03/02/2015.
 */
public class CoverEntity {
    public Bitmap imageResId;
    public String titleResId;

    public CoverEntity(Bitmap imageResId, String titleResId){
        this.imageResId = imageResId;
        this.titleResId = titleResId;
    }
}
