package com.inducesmile.androidtabwithswipe;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by mchoi on 10/3/16.
 */

public class CartItemContainer
{
    private static CartItemContainer singleton = new CartItemContainer( );

    /* A private Constructor prevents any other
     * class from instantiating.
     */
    private CartItemContainer() { }

    /* Static 'instance' method */
    public static CartItemContainer getInstance( ) {
        return singleton;
    }

    public void SetCurrentImage(Bitmap bitmap)  { this.bitmap = bitmap; }
    public void SetCurrentName(String name)     { this.name   = name;   }
    public void SetCurrentPrice(String price)   { this.price  = price;  }



    public ArrayList<CartItem> cartItems = new ArrayList<CartItem>();

    public Bitmap bitmap;
    public String name;
    public String price;

    public int getCount()
    {
        return cartItems.size();
    }

    public String getName(int index)
    {
        return cartItems.get(index).cartItemTitle;
    }

    public String getPrice(int index)
    {
        return cartItems.get(index).cartItemPrice;
    }

    public Bitmap getBitmap(int index)
    {
        return cartItems.get(index).cartItemImage;
    }

    public void AddToCart()
    {
        this.AddToCart(name, price, bitmap);
    }

    public void AddToCart(String itemName, String cartItemPrice, Bitmap cartItemImage)
    {
        cartItems.add(new CartItem(itemName, cartItemPrice, cartItemImage));
    }

    public void AddToCart(CartItem cartItem)
    {
        cartItems.add(cartItem);
    }

    public void ClearCarts()
    {
        cartItems.clear();
    }

    public void RemoveAt(int index)
    {
        cartItems.remove(index);
    }


}
