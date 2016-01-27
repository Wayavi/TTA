package leeme.tta.intel.ehu.eus.leeme.presentacion;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.IOException;
import java.util.Locale;

import leeme.tta.intel.ehu.eus.leeme.R;
import leeme.tta.intel.ehu.eus.leeme.presentacion.Utilities.ImageResize;
import leeme.tta.intel.ehu.eus.leeme.presentacion.Utilities.Resizeable;

public class MainActivity extends AppCompatActivity implements Resizeable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        if(display.getRotation() == Surface.ROTATION_0)
            setContentView(R.layout.activity_main);
        else if(display.getRotation()== Surface.ROTATION_270 || display.getRotation()== Surface.ROTATION_90)
            setContentView(R.layout.activity_main_horizontal);

        imageResize();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, R.string.ayuda, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    /* Función encargada de recoger un click en el botón de comenzar, y arrancar la actividad MenuActivity */
    public void showMenu(View view)
    {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    public void selectLanguage(View view)
    {
        Locale locale=null;
        if(view.getResources().getResourceName(view.getId()).contains("euskera"))
            locale = new Locale("fr");
        if(view.getResources().getResourceName(view.getId()).contains("españo"))
            locale = new Locale("es");

        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        this.getApplicationContext().getResources().updateConfiguration(config, null);

        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onBackPressed()
    {
        finish();
    }

    @Override
    public void imageResize(int[] drawables, int[] ids, int newSize) {

    }

    @Override
    public Bitmap bitmapResize(Bitmap bitmap, int newSize) {
        return null;
    }

    @Override
    public void imageResize() {
        try {
            ImageResize resizeLogo= new ImageResize(getResources(),R.drawable.icon,300);
            ImageResize resizeLeeme = new ImageResize(getResources(),R.drawable.leeme_sin,300);
            ImageResize resizeSpain = new ImageResize(getResources(),R.drawable.spain,150);
            ImageResize resizeEuskera = new ImageResize(getResources(),R.drawable.ikurrina,165);
            ImageView logo = (ImageView)findViewById(R.id.main_image_logo);
            ImageView leeme = (ImageView)findViewById(R.id.main_image_leeme);
            ImageView spain = (ImageView)findViewById(R.id.main_image_español);
            ImageView euskera = (ImageView)findViewById(R.id.main_image_euskera);
            logo.setImageBitmap(resizeLogo.getScaled());
            leeme.setImageBitmap(resizeLeeme.getScaled());
            spain.setImageBitmap(resizeSpain.getScaled());
            euskera.setImageBitmap(resizeEuskera.getScaled());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/


}
