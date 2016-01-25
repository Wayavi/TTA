package leeme.tta.intel.ehu.eus.leeme.presentacion;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import leeme.tta.intel.ehu.eus.leeme.R;

public class TestActivity extends AppCompatActivity {

    private final String SERVER_URL = "http://51.254.127.111/Leeme";
    private final String QUERY_VOCABULARIO = "vocabularioPorCategoria.php";
    private final String QUERY_FRASES = "oracionesPorCategoria.php";

    private VideoView video;
    private EditText respuesta;
    private Button btnSiguiente, btnCorregir;
    private TextView respCorrecta;
    private ImageView imgRespuesta;

    private int testCont = 0;
    private String listaPalabras[];
    private String hitzZerrenda[];
    private String urlVideos[];
    private String urlBideoak[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
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


        //Custom code
        Intent intent = getIntent();
        //En la implementación real de aqui se sacaría la categoría/subcategoría, de momento la ponemos a pelo
        video = (VideoView)findViewById(R.id.test_videoview_video);
        btnSiguiente = (Button)findViewById(R.id.test_button_siguiente);
        respCorrecta = (TextView)findViewById(R.id.test_textview_respcorrecta);
        btnCorregir = (Button)findViewById(R.id.test_button_corregir);
        imgRespuesta = (ImageView)findViewById(R.id.test_imageview_imgrespuesta);
        final MediaController controller = new MediaController(TestActivity.this){
            @Override
            public boolean dispatchKeyEvent(KeyEvent event)
            {
                if(event.getKeyCode() == KeyEvent.KEYCODE_BACK)
                    finish();
                return super.dispatchKeyEvent(event);
            }
        };
        controller.setAnchorView(video);
        video.setMediaController(controller);
        respuesta = (EditText)findViewById(R.id.test_edittext_respuesta);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    String peticion = SERVER_URL + '/' + QUERY_VOCABULARIO + "?cat=Parque";
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
                        urlVideos = new String [numPalabras];
                        urlBideoak = new String[numPalabras];

                        for(int i = 0; i < numPalabras; i++)
                        {
                            int index = i + 1;
                            JSONObject palabra = json.getJSONObject( String.valueOf(index));
                            String cast = palabra.getString("palabra");
                            String eusk = palabra.getString("hitza");
                            String vid = palabra.getString("video");
                            String bid = palabra.getString("bideoa");

                            listaPalabras[i] = cast;
                            hitzZerrenda[i] = eusk;
                            urlVideos[i] = vid;
                            urlBideoak[i] = bid;
                        }

                        //TODO: seleccionar euskera o castellano en función del idioma
                        video.post(new Runnable() {
                            @Override
                            public void run() {
                                video.setVideoURI(Uri.parse(SERVER_URL + urlVideos[testCont]));
                            }
                        });

                    }
                    else
                    {
                        Toast.makeText(TestActivity.this, "No se ha podido comunicar con el servidor", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void seleccionarTipo(View view)
    {
        RadioGroup grupo = (RadioGroup)findViewById(R.id.test_radiogroup_gurpo);
        int selectedId = grupo.getCheckedRadioButtonId();
        View buttonView = findViewById(selectedId);
        int index = grupo.indexOfChild(buttonView);
        //RadioButton radio = (RadioButton)grupo.getChildAt(index);
        Toast.makeText(this, index, Toast.LENGTH_SHORT).show();

    }

    public void corregirTest(View view)
    {
        //Se ocultan los elementos
        video.setVisibility(View.INVISIBLE);
        respuesta.setVisibility(View.INVISIBLE);
        btnCorregir.setVisibility(View.INVISIBLE);

        String resp = respuesta.getText().toString();
        //TODO: falta escoger el array correcto de resultados en base al idioma
        if(resp.equalsIgnoreCase(listaPalabras[testCont]))
        {
            //Respuesta correcta
            Toast.makeText(this, "¡Correcto!", Toast.LENGTH_SHORT).show();
            //imgRespuesta.setImageResource(R.drawable.bien_transparente);
            btnSiguiente.setVisibility(View.VISIBLE);
        }
        else
        {
            //Respuesta incorrecta
            Toast.makeText(this, "¡Incorrecto!", Toast.LENGTH_SHORT).show();
            //imgRespuesta.setImageResource(R.drawable.mal_transparente);
            respCorrecta.setText("La respuesta correcta era: " + listaPalabras[testCont]);
            respCorrecta.setVisibility(View.VISIBLE);
            btnSiguiente.setVisibility(View.VISIBLE);
        }
        testCont++;
    }

    public void siguienteTest(View view)
    {
        imgRespuesta.setVisibility(View.INVISIBLE);
        respCorrecta.setVisibility(View.INVISIBLE);
        btnSiguiente.setVisibility(View.INVISIBLE);
        video.setVideoURI(Uri.parse(SERVER_URL + urlVideos[testCont]));
        video.setVisibility(View.VISIBLE);
        respuesta.setText("");
        respuesta.setVisibility(View.VISIBLE);
        btnCorregir.setVisibility(View.VISIBLE);
    }
}
