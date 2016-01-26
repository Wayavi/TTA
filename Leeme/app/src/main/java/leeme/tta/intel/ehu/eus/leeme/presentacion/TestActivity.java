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
import android.view.ViewGroup;
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
import java.util.Locale;

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
    private int testCont = 6;
    private int numCorrectas = 0;
    private int numIncorrectas = 0;
    private String urlParams = "";
    private String listaPalabras[];
    private String hitzZerrenda[];
    private String urlVideos[];
    private String urlBideoak[];
    private String listaUrls[];

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
                Intent intent = new Intent(TestActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Custom code
        Intent intent = getIntent();
        String menu = intent.getStringExtra("EXTRA_MENU");
        String submenu = intent.getStringExtra("EXTRA_SUBMENU");
        if(menu != null)
        {
            urlParams += "?cat=" + menu;
            if(submenu != null)
            {
                urlParams += "&subc=" + submenu;
            }
        }
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
        testHeader.setText(i + R.string.test_text_numtest);
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
        btnCorregir.setText(R.string.test_text_botoncorregir);
        btnCorregir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                corregirTest(v);
            }
        });
        layoutTest.addView(btnCorregir);

        LinearLayout layoutPuntuacion = createScore();
        layoutTest.addView(layoutPuntuacion);
        loadContent();

    }

    public void corregirTest(View view)
    {
        String resp = respuesta.getText().toString();
        layoutTest.removeAllViews();
        imgRespuesta = new ImageView(this);
        btnSiguiente = new Button(this);
        btnSiguiente.setText(R.string.test_text_botonsiguiente);
        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                siguienteTest(v);
            }
        });
        String listaContenido[];
        if(Locale.getDefault().getDisplayLanguage().contains("esp"))
        {
            listaContenido = listaPalabras;
        }
        else
        {
            listaContenido = hitzZerrenda;
        }

        if(resp.equalsIgnoreCase(listaContenido[testCont]))
        {
            //Respuesta correcta
            numCorrectas++;
            Toast.makeText(this, R.string.test_text_correcto, Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this, R.string.test_text_incorrecto, Toast.LENGTH_SHORT).show();
            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.mal_transparente);
            imgRespuesta.setImageBitmap(Utils.bitmapResize(bm, 500));
            bm.recycle();
            respCorrecta = new TextView(this);
<<<<<<< HEAD
            respCorrecta.setText(R.string.test_text_wascorrectanswer + listaContenido[testCont]);
=======
            respCorrecta.setText(R.string.test_text_wascorrectanswer+ listaPalabras[testCont]);
>>>>>>> 5517ab1dc78f6bbeec1126b8865f6e91b89873d0
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
        testHeader.setText(i + R.string.test_text_numtest);
        testHeader.setGravity(Gravity.CENTER_HORIZONTAL);
        layoutTest.addView(testHeader);
        video = new VideoView(this);
        MediaController controller = new MediaController(this);
        video.setMediaController(controller);
        layoutTest.addView(video);
        respuesta = new EditText(this);
        layoutTest.addView(respuesta);
        btnCorregir = new Button(this);
        btnCorregir.setText(R.string.test_text_botoncorregir);
        btnCorregir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                corregirTest(v);
            }
        });
        layoutTest.addView(btnCorregir);

        /*if(index == 0)
        {
            video.setVideoURI(Uri.parse(SERVER_URL + listaUrls[testCont]));
        }
        else
        {
            video.setVideoURI(Uri.parse(SERVER_URL + '/' + listaUrls[testCont]));
        }*/

        video.setVideoURI(Uri.parse(SERVER_URL + '/' + listaUrls[testCont]));


        LinearLayout layoutPuntuacion = createScore();
        layoutTest.addView(layoutPuntuacion);
    }

    public void loadContent()
    {
        final String urlPeticion;
        if(index == 0)
        {
            urlPeticion = SERVER_URL + '/' + QUERY_VOCABULARIO + urlParams;;
        }
        else
        {
            urlPeticion = SERVER_URL + '/' + QUERY_FRASES + urlParams;
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

                    if(Locale.getDefault().getDisplayLanguage().contains("esp"))
                    {
                        listaUrls = urlVideos;
                    }
                    else
                    {
                        listaUrls = urlBideoak;
                    }
                    video.post(new Runnable() {
                        @Override
                        public void run() {
                            /*if(index == 0)
                            {
                                video.setVideoURI(Uri.parse(SERVER_URL + listaUrls[testCont]));
                            }
                            else
                            {
                                video.setVideoURI(Uri.parse(SERVER_URL + '/' + listaUrls[testCont]));
                            }*/
                            video.setVideoURI(Uri.parse(SERVER_URL + '/' + listaUrls[testCont]));
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

    public LinearLayout createScore()
    {
        LinearLayout layoutPuntuacion = new LinearLayout(this);
        layoutPuntuacion.setOrientation(LinearLayout.HORIZONTAL);
        layoutPuntuacion.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ImageView puntuacionCorrecta = new ImageView(this);
        ImageView puntuacionIncorrecta = new ImageView(this);
        Bitmap bmMal = BitmapFactory.decodeResource(getResources(), R.drawable.mal_transparente);
        Bitmap bmBien = BitmapFactory.decodeResource(getResources(), R.drawable.bien_transparente);
        puntuacionCorrecta.setImageBitmap(Utils.bitmapResize(bmBien, 100));
        puntuacionIncorrecta.setImageBitmap(Utils.bitmapResize(bmMal, 100));
        TextView txtCorrecto = new TextView(this);
        txtCorrecto.setText(String.valueOf(numCorrectas));
        TextView txtIncorrecto = new TextView(this);
        txtIncorrecto.setText(String.valueOf(numIncorrectas));
        layoutPuntuacion.addView(puntuacionCorrecta);
        layoutPuntuacion.addView(txtCorrecto);
        layoutPuntuacion.addView(puntuacionIncorrecta);
        layoutPuntuacion.addView(txtIncorrecto);
        return layoutPuntuacion;
    }
}
