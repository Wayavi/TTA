package leeme.tta.intel.ehu.eus.leeme.presentacion;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

import leeme.tta.intel.ehu.eus.leeme.R;
import leeme.tta.intel.ehu.eus.leeme.presentacion.Business.Oraciones;
import leeme.tta.intel.ehu.eus.leeme.presentacion.Business.Vocabulario;
import leeme.tta.intel.ehu.eus.leeme.presentacion.Utilities.CustomList;
import leeme.tta.intel.ehu.eus.leeme.presentacion.Utilities.Frase;
import leeme.tta.intel.ehu.eus.leeme.presentacion.Utilities.HttpClient;
import leeme.tta.intel.ehu.eus.leeme.presentacion.Utilities.Palabra;
import leeme.tta.intel.ehu.eus.leeme.presentacion.Utilities.Utils;

public class ListActivity extends AppCompatActivity {

    public static final String EXTRA_DISPLAY = "eus.ehu.intel.tta.Leeme.Display";
    public static final String EXTRA_VIDEO = "eus.ehu.intel.tta.Leeme.Video";

    private final String SERVER_URL = "http://51.254.127.111/Leeme/";
    private String urlParams;
    private int type;

    private Frase[] frases;
    private Palabra[] palabras;
    private String[] cadenas, urls;

    private ListView lista;

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
                Intent intent = new Intent(ListActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Custom code
        Intent intent = getIntent();
        String menu = intent.getStringExtra("EXTRA_MENU");
        String submenu = intent.getStringExtra("EXTRA_SUBMENU");
        String tipo = intent.getStringExtra("EXTRA_TIPO");

        lista = (ListView)findViewById(R.id.list_listview_lista);

        urlParams = "";
        if(tipo.equalsIgnoreCase("vocabulario"))
        {
            type = 1;
        }
        else
        {
            type = 2;
        }

        if(menu != null)
        {
            urlParams += "?cat=" + menu;
            if(submenu != null && submenu != "")
            {
                urlParams += "&subc=" + submenu;
            }
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
            try
            {
                if(type == 1)
                {
                    Vocabulario business = new Vocabulario(new HttpClient(SERVER_URL));
                    palabras = business.getPalabrasByCategory(urlParams);
                    cadenas = business.getPalabrasStrings(palabras, Utils.getCurrentLenguage());
                    urls = business.getVideoUrls(palabras, Utils.getCurrentLenguage());
                }
                else
                {
                    Oraciones business = new Oraciones(new HttpClient(SERVER_URL));
                    frases = business.getFrasesByCategory(urlParams);
                    cadenas = business.getFrasesStrings(frases, Utils.getCurrentLenguage());
                    urls = business.getVideoUrls(frases, Utils.getCurrentLenguage());
                }

                final CustomList adaptador = new CustomList(ListActivity.this, cadenas);
                lista.post(new Runnable() {
                    @Override
                    public void run() {
                        lista.setAdapter(adaptador);
                    }
                });
                lista.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(ListActivity.this, LearnActivity.class);
                        intent.putExtra(EXTRA_DISPLAY, cadenas[position]);
                        intent.putExtra(EXTRA_VIDEO, urls[position]);
                        startActivity(intent);
                    }
                });
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            }
        }).start();
    }
}
