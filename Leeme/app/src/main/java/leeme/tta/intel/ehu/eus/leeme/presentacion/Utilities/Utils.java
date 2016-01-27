package leeme.tta.intel.ehu.eus.leeme.presentacion.Utilities;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import java.io.IOException;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import leeme.tta.intel.ehu.eus.leeme.R;

public class Utils
{
    public final static int PANELSCALE=460;

    public void imageResize() {
        //No need to implement here
    }

    public static void imageResize(int[] drawables, ImageView[] views, int[] newSizes, Resources contexto) {
        try {
            for(int i=0;i<drawables.length;i++) {
                ImageResize image = new ImageResize(contexto,drawables[i],newSizes[i]);
                views[i].setBackground(new BitmapDrawable(contexto,image.getScaled()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void imageResize(int drawables, ImageView views, int newSize, Resources contexto) {
        try {
            ImageResize image = new ImageResize(contexto,drawables,newSize);
            views.setBackground(new BitmapDrawable(contexto,image.getScaled()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void imageResize(int[] drawables, ImageView[] views, int newSize, Resources contexto) {
        try {
            for(int i=0;i<drawables.length;i++) {
                ImageResize image = new ImageResize(contexto,drawables[i],newSize);
                views[i].setBackground(new BitmapDrawable(contexto,image.getScaled()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap bitmapResize(Bitmap bitmap, int newSize) {
        try
        {
            ImageResize image = new ImageResize(bitmap,newSize);
            return image.getScaled();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static String getCurrentLenguage()
    {
        if(Locale.getDefault().getDisplayLanguage().contains("fr"))
        {
            return "Euskera";
        }
        else
        {
            return "Castellano";
        }
    }

}
