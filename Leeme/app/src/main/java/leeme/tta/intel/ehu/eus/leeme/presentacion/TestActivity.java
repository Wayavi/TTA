package leeme.tta.intel.ehu.eus.leeme.presentacion;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
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
import leeme.tta.intel.ehu.eus.leeme.presentacion.Utilities.Utils;

public class TestActivity extends AppCompatActivity{

    private final String SERVER_URL = "http://51.254.127.111/Leeme";
    private final String QUERY_VOCABULARIO = "vocabularioPorCategoria.php";
    private final String QUERY_FRASES = "oracionesPorCategoria.php";

    private VideoView video;
    private EditText respuesta;
    private Button btnSiguiente, btnCorregir;
    private TextView testHeader, respCorrecta;
    private ImageView imgRespuesta;
    private LinearLayout layoutTest;

    private int index;
    private int testCont = 0;
    private int numCorrectas = 0;
    private int numIncorrectas = 0;
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
        //Se deberia coger la categoria y subcategoria
        layoutTest = (LinearLayout)findViewById(R.id.test_linearlayout_test);
    }

    public void seleccionarTipo(View view)
    {
        RadioGroup grupo = (RadioGroup)findViewById(R.id.test_radiogroup_gurpo);
        int selectedId = grupo.getCheckedRadioButtonId();
        View buttonView = findViewById(selectedId);
        index = grupo.indexOfChild(buttonView);

        //Creamos el contenido
        testHeader = new TextView(this);
        int i = testCont + 1;
        testHeader.setText(i + "º Test");
        testHeader.setTextSize(16);
        testHeader.setGravity(Gravity.CENTER_HORIZONTAL);
        layoutTest.addView(testHeader);
        video = new VideoView(this);
        MediaController controller = new MediaController(this);
        video.setMediaController(controller);
        layoutTest.addView(video);
        respuesta = new EditText(this);
        layoutTest.addView(respuesta);
        btnCorregir = new Button(this);
        btnCorregir.setText("Corregir");
        btnCorregir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                corregirTest(v);
            }
        });
        layoutTest.addView(btnCorregir);
        loadContent();

    }

    public void corregirTest(View view)
    {
        String resp = respuesta.getText().toString();
        layoutTest.removeAllViews();
        imgRespuesta = new ImageView(this);
        btnSiguiente = new Button(this);
        btnSiguiente.setText("Siguiente");
        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                siguienteTest(v);
            }
        });
        //TODO: falta escoger el array correcto de resultados en base al idioma
        if(resp.equalsIgnoreCase(listaPalabras[testCont]))
        {
            //Respuesta correcta
            numCorrectas++;
            Toast.makeText(this, "¡Correcto!", Toast.LENGTH_SHORT).show();
            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.bien_transparente);
            imgRespuesta.setImageBitmap(Utils.bitmapResize(bm, 500));
            layoutTest.addView(imgRespuesta);
            layoutTest.addView(btnSiguiente);
            bm.recycle();
        }
        else
        {
            //Respuesta incorrecta
            numIncorrectas++;
            Toast.makeText(this, "¡Incorrecto!", Toast.LENGTH_SHORT).show();
            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.mal_transparente);
            imgRespuesta.setImageBitmap(Utils.bitmapResize(bm, 500));
            bm.recycle();
            respCorrecta = new TextView(this);
            respCorrecta.setText("La respuesta correcta era: " + listaPalabras[testCont]);
            layoutTest.addView(imgRespuesta);
            layoutTest.addView(respCorrecta);
            layoutTest.addView(btnSiguiente);
        }
        testCont++;
    }

    public void siguienteTest(View view)
    {
        layoutTest.removeAllViews();
        testHeader = new TextView(this);
        int i = testCont + 1;
        testHeader.setText(i + "º Test");
        video = new VideoView(this);
        MediaController controller = new MediaController(this);
        video.setMediaController(controller);
        layoutTest.addView(video);
        respuesta = new EditText(this);
        layoutTest.addView(respuesta);
        btnCorregir = new Button(this);
        btnCorregir.setText("Corregir");
        btnCorregir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                corregirTest(v);
            }
        });
        layoutTest.addView(btnCorregir);

        if(index == 0)
        {
            video.setVideoURI(Uri.parse(SERVER_URL + urlVideos[testCont]));
        }
        else
        {
            video.setVideoURI(Uri.parse(SERVER_URL + '/' + urlVideos[testCont]));
        }
        video.setVisibility(View.VISIBLE);
        respuesta.setText("");
        respuesta.setVisibility(View.VISIBLE);
        btnCorregir.setVisibility(View.VISIBLE);
    }

    public void loadContent()
    {
        final String urlPeticion;
        if(index == 0)
        {
            urlPeticion = SERVER_URL + '/' + QUERY_VOCABULARIO + "?cat=Parque";;
        }
        else
        {
            urlPeticion = SERVER_URL + '/' + QUERY_FRASES + "?cat=Parque";
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
            try
            {
                URL url = new URL(urlPeticion);
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
                        int indice = i + 1;
                        JSONObject palabra = json.getJSONObject( String.valueOf(indice));
                        String cast, eusk;
                        if(index == 0)
                        {
                            cast = palabra.getString("palabra");
                            eusk = palabra.getString("hitza");
                        }
                        else
                        {
                            cast = palabra.getString("oracion");
                            eusk = palabra.getString("esaldia");
                        }

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
                            if(index == 0)
                            {
                                video.setVideoURI(Uri.parse(SERVER_URL + urlVideos[testCont]));
                            }
                            else
                            {
                                video.setVideoURI(Uri.parse(SERVER_URL + '/' + urlVideos[testCont]));
                            }
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
        LinearLayout linearMain = (LinearLayout)findViewById(R.id.test_linearlayout);
        LinearLayout linearEscoger = (LinearLayout)findViewById(R.id.test_linearlayout_escoger);
        LinearLayout linearTest = (LinearLayout)findViewById(R.id.test_linearlayout_test);
        linearMain.removeView(linearEscoger);
        linearTest.setVisibility(View.VISIBLE);
    }
}
