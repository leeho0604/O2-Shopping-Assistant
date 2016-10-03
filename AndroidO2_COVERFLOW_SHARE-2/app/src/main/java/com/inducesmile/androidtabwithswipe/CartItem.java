package com.inducesmile.androidtabwithswipe;

import android.graphics.Bitmap;

/**
 * Created by mchoi on 10/3/16.
 */

public class CartItem
{
    public String cartItemTitle;
    public String cartItemPrice;
    public Bitmap cartItemImage;

    public CartItem(String cartItemTitle, String cartItemPrice, Bitmap cartItemImage)
    {
        this.cartItemTitle = cartItemTitle;
        this.cartItemPrice = cartItemPrice;
        this.cartItemImage = cartItemImage;
    }
}