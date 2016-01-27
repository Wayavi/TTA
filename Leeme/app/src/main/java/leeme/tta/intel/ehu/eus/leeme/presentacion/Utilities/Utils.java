package leeme.tta.intel.ehu.eus.leeme.presentacion.Utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import java.io.IOException;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Utils
{
    public void imageResize() {
        //No need to implement here
    }

    public void imageResize(int[] drawables, int[] ids, int newSize) {

    }

    public void imageResize(int[] drawables, ImageView[] views, int newSize, Context contexto) {
        try {
            for(int i=0;i<drawables.length;i++) {
                ImageResize image = new ImageResize(contexto.getResources(),drawables[i],newSize);
                views[i].setBackground(new BitmapDrawable(contexto.getResources(),image.getScaled()));
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
        if(Locale.getDefault().getDisplayLanguage().contains("esp"))
        {
            return "Castellano";
        }
        else
        {
            return "Euskera";
        }
    }

}
