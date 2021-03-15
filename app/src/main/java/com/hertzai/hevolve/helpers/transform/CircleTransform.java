package com.hertzai.hevolve.helpers.transform;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.hertzai.hevolve.R;
import com.hertzai.hevolve.options.GLIDE_TRANSFORM_OPTION;

import java.security.MessageDigest;


public class CircleTransform extends BitmapTransformation {

    private int size;

    private int color;


    public CircleTransform(Context context, @GLIDE_TRANSFORM_OPTION int borderOption) {
        super();

        switch (borderOption) {
            case GLIDE_TRANSFORM_OPTION.CIRCLE_TRANSFORM_NO_BORDER:
                color = -1;
                break;
            case GLIDE_TRANSFORM_OPTION.CIRCLE_TRANSFORM_WHITE_BORDER:
                color = ContextCompat.getColor(context, R.color.white);
                break;
            case GLIDE_TRANSFORM_OPTION.CIRCLE_TRANSFORM_BLACK_BORDER:
                color = ContextCompat.getColor(context, R.color.black);
                break;
            case GLIDE_TRANSFORM_OPTION.CIRCLE_TRANSFORM_GREY_BORDER:
                color = ContextCompat.getColor(context, R.color.light_grey);
                break;
            default:
                color = -1;
                break;
        }
    }

    @Override
    public Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return circleCrop(pool, toTransform);
    }

    private Bitmap circleCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;

        int borderColor = -1;
        int borderRadius = 5;
        if (color != -1)
            borderColor = ColorUtils.setAlphaComponent(color, 0xFF);

        int size = Math.min(source.getWidth(), source.getHeight());
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

        Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

        Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        float r = size / 2f;
        //  canvas.drawCircle(r, r, r, paint);


        // Draw the image smaller than the background so a little border will be seen
        if (color != -1) {
            // Draw the background circle
            // Prepare the background
            Paint paintBg = new Paint();
            paintBg.setColor(borderColor);
            paintBg.setAntiAlias(true);
            canvas.drawCircle(r, r, r, paintBg);
            canvas.drawCircle(r, r, r - borderRadius, paint);
        } else
            canvas.drawCircle(r, r, r, paint);


        return result;
    }


    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

    }
}
