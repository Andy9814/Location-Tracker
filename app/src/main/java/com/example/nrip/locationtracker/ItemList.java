package com.example.nrip.locationtracker;

public class ItemList {
    private int mImageResource;
    private String mText1;
    private String mText2;

    public ItemList(int imageResource, String text1, String text2) {
        mImageResource = imageResource;
        mText1 = text1;
        mText2 = text2;
    }

    public int getImageResource() {
        return mImageResource;
    }

    public String getText1() {
        return mText1;
    }

    public String getText2() {
        return mText2;
    }
}
