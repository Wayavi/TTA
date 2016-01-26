package leeme.tta.intel.ehu.eus.leeme.presentacion.Utilities;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import leeme.tta.intel.ehu.eus.leeme.R;

public class CustomList extends ArrayAdapter<String> implements Resizeable
{
    private final String SERVER_URL = "http://51.254.127.111/Leeme";

    private final Activity context;
    private final String[] cadenas;
    private final String[] urlImagenes;

    public CustomList(Activity context, String[] cadenas, String urls[])
    {
        super(context, R.layout.listitem, cadenas);
        this.context = context;
        this.cadenas = cadenas;
        this.urlImagenes = urls;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent)
    {
        LayoutInflater inflater = context.getLayoutInflater();
        View listRow = inflater.inflate(R.layout.listitem, null);
        final ImageView img = (ImageView)listRow.findViewById(R.id.listitem_img);
        final TextView texto = (TextView)listRow.findViewById(R.id.listitem_txt);
        texto.setText(cadenas[position]);
        img.setImageResource(R.drawable.icon);
        /*new Thread(new Runnable() {
            @Override
            public void run() {
<<<<<<< HEAD
                String path = SERVER_URL + '/' + urlImagenes[position];
                try
                {
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    int code = conn.getResponseCode();
                    if(code == 200)
                    {
                        InputStream is = conn.getInputStream();
                        final Bitmap bitmap = BitmapFactory.decodeStream(is);

                        //TODO: falta aplicar el resize al bitmap para que la app no pete por falta de memoria en la carga de imagen
                        img.setImageBitmap(bitmap);
                    }
                }
                catch(Exception e)
=======
            String path = SERVER_URL + '/' + urlImagenes[1];
            try
            {
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                int code = conn.getResponseCode();
                if(code == 200)
>>>>>>> 9afe3659be9eaf5195703fb12303be2a1e7fb74e
                {
                    InputStream is = conn.getInputStream();
                    //TODO: falta aplicar el resize al bitmap para que la app no pete por falta de memoria en la carga de imagen
                    final Bitmap bitmap = bitmapResize(BitmapFactory.decodeStream(is), 10);
                    img.post(new Runnable() {
                        @Override
                        public void run() {
                            img.setImageBitmap(bitmap);
                        }
                    });
                    //img.setImageDrawable(Drawable.createFromStream(is, "img"));
                    bitmap.recycle();
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            }
        }).start();*/
        //img.setImageURI(Uri.parse(urlImagenes[position]));
        //img.setImageResource(R.drawable.icon);

        return listRow;
    }

    @Override
    public void imageResize() {
        //No need to implement here
    }

    @Override
    public void imageResize(int[] drawables, int[] ids, int newSize) {
        //No need to implement here
    }

    @Override
    public Bitmap bitmapResize(Bitmap bitmap, int newSize)
    {
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
