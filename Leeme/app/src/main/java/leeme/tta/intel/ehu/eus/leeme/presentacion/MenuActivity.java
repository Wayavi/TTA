package leeme.tta.intel.ehu.eus.leeme.presentacion;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

import leeme.tta.intel.ehu.eus.leeme.R;
import leeme.tta.intel.ehu.eus.leeme.presentacion.Utilities.ImageResize;

public class MenuActivity extends AppCompatActivity {

    private String menu;
    private String subMenu;
    private Display display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        menu=null;
        subMenu=null;
        display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();

        layoutCreate(savedInstanceState);
        imageResize();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "You found the super secret!\nThe super secret is that you are GAY!!!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void imageResize()
    {
        try {
            ImageResize resizeCasa = new ImageResize(getResources(),R.drawable.casa,500);
            ImageResize resizeParque = new ImageResize(getResources(),R.drawable.parque,500);
            ImageResize resizeChuches = new ImageResize(getResources(),R.drawable.chuches,500);
            ImageResize resizeColegio = new ImageResize(getResources(),R.drawable.colegio,500);
            ImageView casa = (ImageView)findViewById(R.id.menu_imagebutton_casa);
            ImageView parque = (ImageView)findViewById(R.id.menu_imagebutton_parque);
            ImageView chuches = (ImageView)findViewById(R.id.menu_imagebutton_chuches);
            ImageView colegio = (ImageView)findViewById(R.id.menu_imagebutton_colegio);
            casa.setImageBitmap(resizeCasa.getScaled());
            parque.setImageBitmap(resizeParque.getScaled());
            chuches.setImageBitmap(resizeChuches.getScaled());
            colegio.setImageBitmap(resizeColegio.getScaled());
            //casa.setBackgroundResource(R.drawable.casa);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void imageLoad()
    {

    }

    private void layoutCreate(Bundle savedInstanceState){
        if(savedInstanceState != null)
        {
            if(savedInstanceState.getString("menu") != null)
            {
                if(savedInstanceState.getString("menu").contains("casa")) {
                    menu=savedInstanceState.getString("menu");
                    if (display.getRotation() == Surface.ROTATION_0)
                        setContentView(R.layout.activity_menucasa);
                    else if (display.getRotation() == Surface.ROTATION_270 || display.getRotation() == Surface.ROTATION_90)
                        setContentView(R.layout.activity_menucasa_horizontal);
                }
                else if(savedInstanceState.getString("menu").contains("colegio")){
                    menu=savedInstanceState.getString("menu");
                    if (display.getRotation() == Surface.ROTATION_0)
                        setContentView(R.layout.activity_menuescuela);
                    else if (display.getRotation() == Surface.ROTATION_270 || display.getRotation() == Surface.ROTATION_90)
                        setContentView(R.layout.activity_menuescuela_horizontal);
                }
            }
            else {
                if (display.getRotation() == Surface.ROTATION_0)
                    setContentView(R.layout.activity_menu);
                else if (display.getRotation() == Surface.ROTATION_270 || display.getRotation() == Surface.ROTATION_90)
                    setContentView(R.layout.activity_menu_horizontal);
            }
        }
        else {
            if (display.getRotation() == Surface.ROTATION_0)
                setContentView(R.layout.activity_menu);
            else if (display.getRotation() == Surface.ROTATION_270 || display.getRotation() == Surface.ROTATION_90)
                setContentView(R.layout.activity_menu_horizontal);
        }
    }

    public void showCasa(View view){
        menu=view.getResources().getResourceName(view.getId());

        if(display.getRotation() == Surface.ROTATION_0)
            setContentView(R.layout.activity_menucasa);
        else if(display.getRotation()== Surface.ROTATION_270 || display.getRotation()== Surface.ROTATION_90)
            setContentView(R.layout.activity_menucasa_horizontal);
    }

    public void showEscuela(View view){
        menu=view.getResources().getResourceName(view.getId());

        if(display.getRotation() == Surface.ROTATION_0)
            setContentView(R.layout.activity_menuescuela);
        else if(display.getRotation()== Surface.ROTATION_270 || display.getRotation()== Surface.ROTATION_90)
            setContentView(R.layout.activity_menuescuela_horizontal);
    }

    public void showMenu2(View view)
    {
        if(menu==null)
            menu=view.getResources().getResourceName(view.getId());
        else
            subMenu=view.getResources().getResourceName(view.getId());
        Toast.makeText(this,menu,Toast.LENGTH_SHORT).show();
        Toast.makeText(this,subMenu,Toast.LENGTH_SHORT).show();
        /*Intent intent = new Intent(this, Menu2Activity.class);
        intent.putExtra("EXTRA_MENU",menu);
        intent.putExtra("EXTRA_SUBMENU", subMenu);
        startActivity(intent);*/
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putString("menu",menu);
        savedInstanceState.putString("subMenu", subMenu);
        super.onSaveInstanceState(savedInstanceState);
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
        if(menu != "" && menu != null)
        {
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
            finish();
        }
        else
        {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

}
