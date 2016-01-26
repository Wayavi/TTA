package leeme.tta.intel.ehu.eus.leeme.presentacion.Utilities;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
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

    public ImageResize(Resources resources, int resId, int newHeight)
            throws IOException {
        Size size = getRoughSize(resources, resId, newHeight);
        roughScaleImage(resources, resId, size);
        scaleImage(newHeight);
    }

    public ImageResize(Bitmap bitmap, int newHeight)
            throws IOException {
        Size size = getRoughSize(bitmap, newHeight);
        roughScaleImage(bitmap, size);
        scaleImage(newHeight);
    }

    public Bitmap getScaled() {
        return scaled;
    }

    public BitmapDrawable getScaledBackground() {
        return new BitmapDrawable(scaled);
    }

    private void scaleImage(int newHeight) {
        int width = scaled.getWidth();
        int height = scaled.getHeight();

        float scaleHeight = ((float) newHeight) / height;
        float ratio = ((float) scaled.getHeight()) / newHeight;
        int newWidth = (int) (width / ratio);
        float scaleWidth = ((float) newWidth) / width;

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

    private Size getRoughSize(Resources resources, int resId, int newHeight) {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, resId, o);

        Size size = getRoughSize(o.outWidth, o.outHeight, newHeight);
        return size;
    }

    private Size getRoughSize(int outWidth, int outHeight, int newHeight) {
        Size size = new Size();
        size.scale = outHeight / newHeight;
        size.sample = 1;

        int width = outWidth;
        int height = outHeight;

        int newWidth = (int) (outWidth / size.scale);

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

    private Size getRoughSize(Bitmap bitmap, int newHeight) {
        //BitmapFactory.Options o = new BitmapFactory.Options();
        //o.inJustDecodeBounds = true;

        Size size = getRoughSize(bitmap.getWidth(), bitmap.getHeight(), newHeight);
        return size;
    }

    private void roughScaleImage(Bitmap bitmap, Size size) {
        Matrix matrix = new Matrix();
        matrix.postScale(size.scale, size.scale);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        byte[] byteArray = stream.toByteArray();

        BitmapFactory.Options scaledOpts = new BitmapFactory.Options();
        scaledOpts.inSampleSize = size.sample;
        scaled = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length,scaledOpts);

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
