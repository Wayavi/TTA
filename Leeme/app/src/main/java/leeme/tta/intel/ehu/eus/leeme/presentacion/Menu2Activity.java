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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void goVocabulario(View view)
    {
        Toast.makeText(this, getIntent().getStringExtra("EXTRA_MENU"), Toast.LENGTH_SHORT).show();
        Toast.makeText(this,getIntent().getStringExtra("EXTRA_SUBMENU"),Toast.LENGTH_SHORT).show();

    }

    public void goOraciones(View view)
    {

    }

    public void goEjercicios(View view)
    {

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
