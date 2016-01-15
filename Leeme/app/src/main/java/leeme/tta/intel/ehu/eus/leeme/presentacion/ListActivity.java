package leeme.tta.intel.ehu.eus.leeme.presentacion;

import android.content.Intent;
import android.os.Bundle;
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

import leeme.tta.intel.ehu.eus.leeme.R;
import leeme.tta.intel.ehu.eus.leeme.presentacion.Utilities.CustomList;

public class ListActivity extends AppCompatActivity {

    public static final String EXTRA_DISPLAY = "eus.ehu.intel.tta.Leeme.Display";
    public static final String EXTRA_VIDEO = "eus.ehu.intel.tta.Leeme.Video";

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
                    if(code == 200)
                    {
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

                            final ListView lista = (ListView)findViewById(R.id.list_listview_lista);
                            final CustomList adaptador = new CustomList(ListActivity.this, listaPalabras, urlImagenes);
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
                                    intent.putExtra(EXTRA_DISPLAY, listaPalabras[position]);
                                    intent.putExtra(EXTRA_VIDEO, urlVideos[position]);
                                    startActivity(intent);
                                }
                            });
                        }
                    }
                    else
                    {
                        Toast.makeText(ListActivity.this, "No se ha podido comunicar con el servidor", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
