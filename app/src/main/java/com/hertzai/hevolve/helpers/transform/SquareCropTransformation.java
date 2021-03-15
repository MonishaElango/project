package com.hertzai.hevolve.helpers.transform;

import android.content.Context;
import android.graphics.Bitmap;

import java.security.MessageDigest;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;



public class SquareCropTransformation extends BitmapTransformation {

    private int size;

    public SquareCropTransformation(Context context) {
        super();
    }


    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        size = Math.min(toTransform.getWidth(), toTransform.getHeight());

        int mWidth = (toTransform.getWidth() - size) / 2;
        int mHeight = (toTransform.getHeight() - size) / 2;

        Bitmap bitmap = Bitmap.createBitmap(toTransform, mWidth, mHeight, size, size);

        return bitmap;
    }

//    @Override
//    public String getId() {
//        return "RoundedTransformation(size=" + size + ")";
//    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {

    }
}
