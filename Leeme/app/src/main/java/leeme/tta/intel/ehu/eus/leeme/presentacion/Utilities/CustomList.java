package leeme.tta.intel.ehu.eus.leeme.presentacion.Utilities;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import leeme.tta.intel.ehu.eus.leeme.R;

public class CustomList extends ArrayAdapter<String>
{
    private final String SERVER_URL = "http://51.254.127.111/Leeme";

    private final Activity context;
    private final String[] cadenas;
    private final String[] urlImagenes;

    private ImageView img;
    private TextView texto;

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
        texto = (TextView)listRow.findViewById(R.id.listitem_txt);
        texto.setText(cadenas[position]);
        img = (ImageView)listRow.findViewById(R.id.listitem_img);
        //img.setImageResource(R.drawable.icon);
        new Thread(new Runnable() {
            @Override
            public void run() {
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
                {
                    e.printStackTrace();
                }
            }
        }).start();

        return listRow;
    }

}
