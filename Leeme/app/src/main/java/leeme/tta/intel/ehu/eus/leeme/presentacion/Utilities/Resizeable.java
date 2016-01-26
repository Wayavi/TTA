package leeme.tta.intel.ehu.eus.leeme.presentacion.Utilities;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.io.IOException;

/**
 * Created by Alexander on 25/01/2016.
 */
public interface Resizeable {

    public final static int PANELSCALE=460;
    public final static int xxxxxxSCALE=0;

    public void imageResize();

    public void imageResize(int[] drawables, int[] ids, int newSize);

    public Bitmap bitmapResize(Bitmap bitmap, int newSize);

    /*public Bitmap imageResize(Bitmap bitmap, int newSize)
    {
        try {
            ImageResize image = new ImageResize(bitmap,newSize);
            return image.getScaled();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }*/

}
