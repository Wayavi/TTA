package leeme.tta.intel.ehu.eus.leeme.presentacion;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import leeme.tta.intel.ehu.eus.leeme.R;

public class ListActivity extends AppCompatActivity {

    private final String SERVER_URL = "http://51.254.127.111/Leeme/";
    private final String VOCABULARY_QUERY = "vocabularioPorCategoria.php";

    private String listaPalabras[];
    private String hitzZerrenda[];
    private String urlImagenes[];
    private String urlVideos[];
    private String urlBideoak[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Custom code
        new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    String peticion = SERVER_URL + VOCABULARY_QUERY + "?cat=Parque";
                    URL url = new URL(peticion);
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    int code = conn.getResponseCode();
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    JSONObject json = new JSONObject(br.readLine());
                    int numPalabras = json.length();
                    listaPalabras = new String[numPalabras];
                    hitzZerrenda = new String[numPalabras];
                    urlImagenes = new String[numPalabras];
                    urlVideos = new String [numPalabras];
                    urlBideoak = new String[numPalabras];

                    for(int i = 0; i < numPalabras; i++)
                    {
                        int index = i + 1;
                        JSONObject palabra = json.getJSONObject( String.valueOf(index));
                        String cast = palabra.getString("palabra");
                        String eusk = palabra.getString("hitza");
                        String img = palabra.getString("imagen");
                        String vid = palabra.getString("video");
                        String bid = palabra.getString("bideoa");

                        listaPalabras[i] = cast;
                        hitzZerrenda[i] = eusk;
                        urlImagenes[i] = img;
                        urlVideos[i] = vid;
                        urlBideoak[i] = bid;
                    }

                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }).start();

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


    }

}
