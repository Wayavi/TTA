package leeme.tta.intel.ehu.eus.leeme.presentacion;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;

import leeme.tta.intel.ehu.eus.leeme.R;
import leeme.tta.intel.ehu.eus.leeme.presentacion.Utilities.CustomList;

public class ListActivity extends AppCompatActivity {

    ListView list;
    String[] palabra = {
            "Google Plus",
            "Twitter",
            "Windows"
    } ;
    URL[] imageId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        try
        {
            imageId = new URL[3];
            imageId[0] = new URL("http://51.254.127.111/Leeme/Tarta.jpg");
            imageId[1] = new URL("http://51.254.127.111/Leeme/Tarta.jpg");
            imageId[2] = new URL("http://51.254.127.111/Leeme/Tarta.jpg");

        }
        catch(IOException e){}

        /* Esto sería válido para as frases en las que no hay imagenes, sólo texto
        //Generamos array de strings de prueba para poblr la ListView
        ListView lista = (ListView)findViewById(R.id.list_listview_lista);
        String[] codeLearnChapters = new String[] { "Android Introduction","Android Setup/Installation","Android Hello World",
                                                    "Android Layouts/Viewgroups","Android Activity & Lifecycle","Intents in Android"};
        //Creamos el adaptador para Arrays que hará el binding de el array al ListView
        ArrayAdapter<String> codeLearnArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                                                                                codeLearnChapters);
        //Le asignamos el adaptador a la ListView
        lista.setAdapter(codeLearnArrayAdapter);
        */

        CustomList adapter = new CustomList(this, palabra, imageId);
        list=(ListView)findViewById(R.id.list_listview_lista);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ListActivity.this, "You Clicked at " + palabra[+position], Toast.LENGTH_SHORT).show();
            }
        });

    }

}
