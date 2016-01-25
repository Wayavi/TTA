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

    public void imageResize(int[] drawables, int[] ids);

    public void imageResize(Bitmap bitmap);

}
