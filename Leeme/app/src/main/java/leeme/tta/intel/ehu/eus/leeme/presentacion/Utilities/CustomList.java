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

public class CustomList extends ArrayAdapter<String>
{
    private final String SERVER_URL = "http://51.254.127.111/Leeme";

    private final Activity context;
    private final String[] cadenas;

    public CustomList(Activity context, String[] cadenas)
    {
        super(context, R.layout.listitem, cadenas);
        this.context = context;
        this.cadenas = cadenas;
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

        return listRow;
    }

}
