package leeme.tta.intel.ehu.eus.leeme.presentacion.Utilities;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

import leeme.tta.intel.ehu.eus.leeme.presentacion.MenuActivity;

/**
 * Created by Alexander on 15/01/2016.
 */
public class ImageResize
{
    private static class Size {
        int sample;
        float scale;
    }

    private Bitmap scaled;

    public ImageResize(Resources resources, int resId, int newWidth)
            throws IOException {
        Size size = getRoughSize(resources, resId, newWidth);
        roughScaleImage(resources, resId, size);
        scaleImage(newWidth);
    }

    public Bitmap getScaled() {
        return scaled;
    }

    private void scaleImage(int newWidth) {
        int width = scaled.getWidth();
        int height = scaled.getHeight();

        float scaleWidth = ((float) newWidth) / width;
        float ratio = ((float) scaled.getWidth()) / newWidth;
        int newHeight = (int) (height / ratio);
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        scaled = Bitmap.createBitmap(scaled, 0, 0, width, height, matrix, true);
    }

    private void roughScaleImage(Resources resources, int resId, Size size) {
        Matrix matrix = new Matrix();
        matrix.postScale(size.scale, size.scale);

        BitmapFactory.Options scaledOpts = new BitmapFactory.Options();
        scaledOpts.inSampleSize = size.sample;
        scaled = BitmapFactory.decodeResource(resources, resId, scaledOpts);
    }

    private Size getRoughSize(Resources resources, int resId, int newWidth) {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, resId, o);

        Size size = getRoughSize(o.outWidth, o.outHeight, newWidth);
        return size;
    }

    private Size getRoughSize(int outWidth, int outHeight, int newWidth) {
        Size size = new Size();
        size.scale = outWidth / newWidth;
        size.sample = 1;

        int width = outWidth;
        int height = outHeight;

        int newHeight = (int) (outHeight / size.scale);

        while (true) {
            if (width / 2 < newWidth || height / 2 < newHeight) {
                break;
            }
            width /= 2;
            height /= 2;
            size.sample *= 2;
        }
        return size;
    }


   /* public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

    final int height = options.outHeight;
    final int width = options.outWidth;
    int inSampleSize = 1;

    if (height > reqHeight || width > reqWidth) {

        final int halfHeight = height / 2;
        final int halfWidth = width / 2;

        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
        // height and width larger than the requested height and width.
        while ((halfHeight / inSampleSize) > reqHeight
                && (halfWidth / inSampleSize) > reqWidth) {
            inSampleSize *= 2;
        }
    }

    return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight)
    {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }*/
}
