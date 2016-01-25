package leeme.tta.intel.ehu.eus.leeme.presentacion;

import android.content.Intent;
import android.content.res.Configuration;
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

import leeme.tta.intel.ehu.eus.leeme.R;
import leeme.tta.intel.ehu.eus.leeme.presentacion.Utilities.ImageResize;

public class MainActivity extends AppCompatActivity {

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
                Snackbar.make(view, "Here comes the help", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void imageResize()
    {
        try {
            ImageResize resizeLogo= new ImageResize(getResources(),R.drawable.icon,400);
            ImageResize resizeLeeme = new ImageResize(getResources(),R.drawable.leeme_sin,400);
            //ImageResize resizeChuches = new ImageResize(getResources(),R.drawable.chuches,400);
            ImageView logo = (ImageView)findViewById(R.id.main_image_logo);
            ImageView leeme = (ImageView)findViewById(R.id.main_image_leeme);
            //ImageView chuches = (ImageView)findViewById(R.id.menu_imagebutton_chuches);
            logo.setImageBitmap(resizeLogo.getScaled());
            leeme.setImageBitmap(resizeLeeme.getScaled());
            // chuches.setImageBitmap(resizeChuches.getScaled());
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
    }

    /* Función encargada de recoger un click en el botón de comenzar, y arrancar la actividad MenuActivity */
    public void showMenu(View view)
    {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    //Función para ir a la lista de palabras (sólo para testeo)
    public void goList(View view)
    {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    public void goTest(View view)
    {
        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Toast.makeText(this, "FUCK", Toast.LENGTH_SHORT).show();
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

}
