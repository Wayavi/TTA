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
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


import leeme.tta.intel.ehu.eus.leeme.R;

public class CustomList extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] palabra;
    private final URL[] imageUrl;
    private TextView txtTitle;
    private ImageView imageView;
    private int pos;
    private Bitmap myBitmap;

    public CustomList(Activity context, String[] pal, URL[] imgurl)
    {
        super(context, R.layout.listitem, pal);
        this.context = context;
        this.palabra = pal;
        this.imageUrl = imgurl;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent)
    {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.listitem, null, true);
        txtTitle = (TextView) rowView.findViewById(R.id.txt);
        imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(palabra[position]);

        pos = position;
        //imageView.setImageResource(imageId[position]);
        new Thread(new Runnable() {
            @Override
            public void run()
            {
                imageView.setImageBitmap(getBitmapFromURL(imageUrl[position]));
            }
        }).start();
        return rowView;
    }

    public static Bitmap getBitmapFromURL(URL url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
