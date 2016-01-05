package leeme.tta.intel.ehu.eus.leeme.presentacion.Utilities;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import leeme.tta.intel.ehu.eus.leeme.R;

public class CustomList extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] palabra;
    private final Uri[] imageUrl;

    public CustomList(Activity context, String[] pal, Uri[] imgurl)
    {
        super(context, R.layout.listitem, pal);
        this.context = context;
        this.palabra = pal;
        this.imageUrl = imgurl;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.listitem, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(palabra[position]);

        //imageView.setImageResource(imageId[position]);
        imageView.setImageURI(imageUrl[position]);
        return rowView;
    }
}
