package leeme.tta.intel.ehu.eus.leeme.presentacion;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.IOException;

import leeme.tta.intel.ehu.eus.leeme.R;
import leeme.tta.intel.ehu.eus.leeme.presentacion.Utilities.ImageResize;
import leeme.tta.intel.ehu.eus.leeme.presentacion.Utilities.Resizeable;

public class Menu2Activity extends AppCompatActivity implements Resizeable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu2);

        imageResize();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu2Activity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void goVocabulario(View view)
    {
        Intent intent = createLearnIntent(getIntent(), "vocabulario");
        startActivity(intent);
    }

    public void goOraciones(View view)
    {
        Intent intent = createLearnIntent(getIntent(), "oraciones");
        startActivity(intent);
    }

    public void goEjercicios(View view)
    {
        Intent intent = getIntent();
        String menu = intent.getStringExtra("EXTRA_MENU");
        String submenu = intent.getStringExtra("EXTRA_SUBMENU");
        String tipo = intent.getStringExtra("EXTRA_TIPO");
        String intentMenu = "";
        String intentSubmenu = "";
        if(menu.contains("casa"))
        {
            intentMenu = "Casa";
            if(submenu.contains("baino"))
            {
                intentSubmenu = "Baino";
            }
            else if(submenu.contains("salon"))
            {
                intentSubmenu = "Salon";
            }
            else if(submenu.contains("cocina"))
            {
                intentSubmenu = "Cocina";
            }
            else if(submenu.contains("dormitorio"))
            {
                intentSubmenu = "Dormitorio";
            }
        }
        else if(menu.contains("colegio"))
        {
            intentMenu = "Colegio";
            if(submenu.contains("clase"))
            {
                intentSubmenu = "Clase";
            }
            else if(submenu.contains("biblioteca"))
            {
                intentSubmenu = "Biblioteca";
            }
            else if(submenu.contains("gimnasio"))
            {
                intentSubmenu = "Gimnasio";
            }
        }
        else if(menu.contains("parque"))
        {
            intentMenu = "Parque";
            intentSubmenu = "";
        }
        else if(menu.contains("chuches"))
        {
            intentMenu = "Chuches";
            intentSubmenu = "";
        }
        Intent intentNew = new Intent(this, TestActivity.class);
        intentNew.putExtra("EXTRA_MENU", intentMenu);
        intentNew.putExtra("EXTRA_SUBMENU", intentSubmenu);
        startActivity(intentNew);
    }

    public Intent createLearnIntent(Intent receivedIntent, String tipo)
    {
        String menu = receivedIntent.getStringExtra("EXTRA_MENU");
        String submenu = receivedIntent.getStringExtra("EXTRA_SUBMENU");
        String intentMenu = "";
        String intentSubmenu = "";
        if(menu.contains("casa"))
        {
            intentMenu = "Casa";
            if(submenu.contains("baino"))
            {
                intentSubmenu = "Baino";
            }
            else if(submenu.contains("salon"))
            {
                intentSubmenu = "Salon";
            }
            else if(submenu.contains("cocina"))
            {
                intentSubmenu = "Cocina";
            }
            else if(submenu.contains("dormitorio"))
            {
                intentSubmenu = "Dormitorio";
            }
        }
        else if(menu.contains("colegio"))
        {
            intentMenu = "Colegio";
            if(submenu.contains("clase"))
            {
                intentSubmenu = "Clase";
            }
            else if(submenu.contains("biblioteca"))
            {
                intentSubmenu = "Biblioteca";
            }
            else if(submenu.contains("gimnasio"))
            {
                intentSubmenu = "Gimnasio";
            }
        }
        else if(menu.contains("parque"))
        {
            intentMenu = "Parque";
            intentSubmenu = "";
        }
        else if(menu.contains("chuches"))
        {
            intentMenu = "Chuches";
            intentSubmenu = "";
        }
        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra("EXTRA_MENU", intentMenu);
        intent.putExtra("EXTRA_SUBMENU", intentSubmenu);
        intent.putExtra("EXTRA_TIPO", tipo);
        return intent;
    }

    @Override
    public void imageResize() {
        try {
            ImageResize resizeVocabulario= new ImageResize(getResources(),R.drawable.vocabulario,460);
            ImageResize resizeOraciones = new ImageResize(getResources(),R.drawable.oraciones,460);
            ImageResize resizeEjercicios = new ImageResize(getResources(),R.drawable.ejercicios,460);
            ImageView vocabulario = (ImageView)findViewById(R.id.menu2_imagebutton_vocabulario);
            ImageView oraciones = (ImageView)findViewById(R.id.menu2_imagebutton_oraciones);
            ImageView ejercicios = (ImageView)findViewById(R.id.menu2_imagebutton_ejercicios);
            vocabulario.setImageBitmap(resizeVocabulario.getScaled());
            oraciones.setImageBitmap(resizeOraciones.getScaled());
            ejercicios.setImageBitmap(resizeEjercicios.getScaled());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void imageResize(int[] drawables, int[] ids, int newSize) {

    }

    @Override
    public Bitmap bitmapResize(Bitmap bitmap, int newSize) {
        return null;
    }
}
