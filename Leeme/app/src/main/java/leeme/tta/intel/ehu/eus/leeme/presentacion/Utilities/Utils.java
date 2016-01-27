package leeme.tta.intel.ehu.eus.leeme.presentacion.Utilities;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import java.io.IOException;

public class Utils
{
    public final static int PANELSCALE=460;

    public void imageResize() {
        //No need to implement here
    }

    public void imageResize(int[] drawables, int[] ids, int newSize) {

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
}
