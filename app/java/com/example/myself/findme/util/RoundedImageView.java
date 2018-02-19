package com.example.myself.findme.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class RoundedImageView extends ImageView {
    public RoundedImageView(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
    }

    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (drawable != null && getWidth() != 0 && getHeight() != 0) {
            try {
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap().copy(Config.ARGB_8888, true);
                int w = getWidth();
                int h = getHeight();
                canvas.drawBitmap(getRoundedCroppedBitmap(bitmap, w), 0.0f, 0.0f, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Bitmap getRoundedCroppedBitmap(Bitmap bitmap, int radius) {
        Bitmap finalBitmap;
        if (bitmap.getWidth() == radius && bitmap.getHeight() == radius) {
            finalBitmap = bitmap;
        } else {
            finalBitmap = Bitmap.createScaledBitmap(bitmap, radius, radius, false);
        }
        Bitmap output = Bitmap.createBitmap(finalBitmap.getWidth(), finalBitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, finalBitmap.getWidth(), finalBitmap.getHeight());
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(false);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.parseColor("#BAB399"));
        canvas.drawCircle(((float) (finalBitmap.getWidth() / 2)) + 0.7f, ((float) (finalBitmap.getHeight() / 2)) + 0.7f, ((float) (finalBitmap.getWidth() / 2)) + 0.1f, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(finalBitmap, rect, rect, paint);
        return output;
    }
}
