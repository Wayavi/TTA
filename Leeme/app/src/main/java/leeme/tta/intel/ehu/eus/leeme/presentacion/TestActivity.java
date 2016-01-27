package leeme.tta.intel.ehu.eus.leeme.presentacion;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

import leeme.tta.intel.ehu.eus.leeme.R;
import leeme.tta.intel.ehu.eus.leeme.presentacion.Business.Oraciones;
import leeme.tta.intel.ehu.eus.leeme.presentacion.Business.Vocabulario;
import leeme.tta.intel.ehu.eus.leeme.presentacion.Utilities.Frase;
import leeme.tta.intel.ehu.eus.leeme.presentacion.Utilities.HttpClient;
import leeme.tta.intel.ehu.eus.leeme.presentacion.Utilities.ImageResize;
import leeme.tta.intel.ehu.eus.leeme.presentacion.Utilities.Palabra;
import leeme.tta.intel.ehu.eus.leeme.presentacion.Utilities.Utils;

public class TestActivity extends AppCompatActivity{

    private final String SERVER_URL = "http://51.254.127.111/Leeme";

    private VideoView video;
    private EditText respuesta;
    private Button btnSiguiente, btnCorregir;
    private TextView testHeader, respCorrecta, txtCorrectas, txtIncorrectas;
    private ImageView imgRespuesta, imgCorrectas, imgIncorrectas;
    private LinearLayout layoutTest;

    private int index;
    private int testCont = 10;
    private int numCorrectas = 0;
    private int numIncorrectas = 0;
    private String urlParams = "";
    private String cadenas[];
    private String urls[];

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
        layoutTest = (LinearLayout)findViewById(R.id.test_linearlayout_test);
        video = (VideoView)findViewById(R.id.test_videoview_video);
        MediaController controller = new MediaController(this);
        controller.setAnchorView(video);
        video.setMediaController(controller);
        respuesta = (EditText)findViewById(R.id.test_edittext_respuesta);
        btnSiguiente = (Button)findViewById(R.id.test_button_siguiente);
        btnCorregir = (Button)findViewById(R.id.test_button_corregir);
        testHeader = (TextView)findViewById(R.id.test_textview_numtest);
        respCorrecta = (TextView)findViewById(R.id.test_textview_respcorrecta);
        imgRespuesta = (ImageView)findViewById(R.id.test_imageview_respuesta);
        txtCorrectas = (TextView)findViewById(R.id.test_textview_correctas);
        txtIncorrectas = (TextView)findViewById(R.id.test_textview_incorrectas);
        imgCorrectas = (ImageView)findViewById(R.id.test_imageview_correctas);
        imgIncorrectas = (ImageView)findViewById(R.id.test_imageview_incorrectas);
    }

    public void seleccionarTipo(View view)
    {
        RadioGroup grupo = (RadioGroup)findViewById(R.id.test_radiogroup_gurpo);
        int selectedId = grupo.getCheckedRadioButtonId();
        View buttonView = findViewById(selectedId);
        index = grupo.indexOfChild(buttonView);

        //Cambiamos la visibilidad
        LinearLayout mainLayout = (LinearLayout)findViewById(R.id.test_linearlayout);
        mainLayout.removeView(findViewById(R.id.test_linearlayout_escoger));

        testHeader.setText(testCont + getString(R.string.test_text_numtest));
        txtCorrectas.setText(String.valueOf(numCorrectas));
        txtIncorrectas.setText((String.valueOf(numIncorrectas)));
        //
        //imgCorrectas.setImageBitmap(Utils.bitmapResize());
        //imgIncorrectas;
        layoutTest.setVisibility(View.VISIBLE);

        //LinearLayout layoutPuntuacion = createScore();
        //layoutTest.addView(layoutPuntuacion);
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

        if(resp.equalsIgnoreCase(cadenas[testCont]))
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
            respCorrecta.setText(R.string.test_text_wascorrectanswer + cadenas[testCont]);
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
        testHeader.setText(i + getString(R.string.test_text_numtest));
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

        video.setVideoURI(Uri.parse(SERVER_URL + '/' + urls[testCont]));


        LinearLayout layoutPuntuacion = createScore();
        layoutTest.addView(layoutPuntuacion);
    }

    public void loadContent()
    {
        final String urlPeticion;

        new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    if(index == 0)
                    {
                        Vocabulario business = new Vocabulario(new HttpClient(SERVER_URL));
                        Palabra[] palabras = business.getPalabrasByCategory(urlParams);
                        cadenas = business.getPalabrasStrings(palabras, Utils.getCurrentLenguage());
                        urls = business.getVideoUrls(palabras, Utils.getCurrentLenguage());
                    }
                    else
                    {
                        Oraciones business = new Oraciones(new HttpClient(SERVER_URL));
                        Frase[] frases = business.getFrasesByCategory(urlParams);
                        cadenas = business.getFrasesStrings(frases, Utils.getCurrentLenguage());
                        urls = business.getVideoUrls(frases, Utils.getCurrentLenguage());
                    }

                    video.post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            video.setVideoURI(Uri.parse(SERVER_URL + '/' + urls[testCont]));
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
