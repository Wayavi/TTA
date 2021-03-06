package leeme.tta.intel.ehu.eus.leeme.presentacion;

import android.content.Intent;
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
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

import leeme.tta.intel.ehu.eus.leeme.R;
import leeme.tta.intel.ehu.eus.leeme.presentacion.Utilities.ImageResize;
import leeme.tta.intel.ehu.eus.leeme.presentacion.Utilities.Resizeable;
import leeme.tta.intel.ehu.eus.leeme.presentacion.Utilities.Utils;

public class MenuActivity extends AppCompatActivity {

    private String menu;
    private String subMenu;
    private Display display;

    private ImageView[] panels;
    private int[] drawables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        menu=null;
        subMenu=null;
        display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();

        layoutCreate(savedInstanceState);
        Utils.imageResize(drawables, panels, Utils.PANELSCALE,getResources());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
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

                    getPanels("casa");
                }
                else if(savedInstanceState.getString("menu").contains("colegio")){
                    menu=savedInstanceState.getString("menu");
                    if (display.getRotation() == Surface.ROTATION_0)
                        setContentView(R.layout.activity_menuescuela);
                    else if (display.getRotation() == Surface.ROTATION_270 || display.getRotation() == Surface.ROTATION_90)
                        setContentView(R.layout.activity_menuescuela_horizontal);

                    getPanels("colegio");
                }
            }
            else {
                if (display.getRotation() == Surface.ROTATION_0)
                    setContentView(R.layout.activity_menu);
                else if (display.getRotation() == Surface.ROTATION_270 || display.getRotation() == Surface.ROTATION_90)
                    setContentView(R.layout.activity_menu_horizontal);

                getPanels("menu");
            }
        }
        else {
            if (display.getRotation() == Surface.ROTATION_0)
                setContentView(R.layout.activity_menu);
            else if (display.getRotation() == Surface.ROTATION_270 || display.getRotation() == Surface.ROTATION_90)
                setContentView(R.layout.activity_menu_horizontal);

            getPanels("menu");
        }
    }

    public void showCasa(View view){
        menu=view.getResources().getResourceName(view.getId());

        if(display.getRotation() == Surface.ROTATION_0)
            setContentView(R.layout.activity_menucasa);
        else if(display.getRotation()== Surface.ROTATION_270 || display.getRotation()== Surface.ROTATION_90)
            setContentView(R.layout.activity_menucasa_horizontal);

        getPanels("casa");
        Utils.imageResize(drawables, panels, Utils.PANELSCALE, getResources());
    }

    public void showEscuela(View view){
        menu=view.getResources().getResourceName(view.getId());

        if(display.getRotation() == Surface.ROTATION_0)
            setContentView(R.layout.activity_menuescuela);
        else if(display.getRotation()== Surface.ROTATION_270 || display.getRotation()== Surface.ROTATION_90)
            setContentView(R.layout.activity_menuescuela_horizontal);

        getPanels("colegio");
        Utils.imageResize(drawables, panels, Utils.PANELSCALE, getResources());
    }

    public void showMenu2(View view)
    {
        if(menu==null)
            menu=view.getResources().getResourceName(view.getId());
        else
            subMenu=view.getResources().getResourceName(view.getId());

        Intent intent = new Intent(this, Menu2Activity.class);
        intent.putExtra("EXTRA_MENU",menu);
        intent.putExtra("EXTRA_SUBMENU", subMenu);
        startActivity(intent);
    }

    public void getPanels(String menu)
    {
        drawables=null;
        panels=null;
        if(menu.equals("menu")) {
            int[] drawables = {R.drawable.casa, R.drawable.parque, R.drawable.colegio, R.drawable.chuches};
            this.drawables = drawables;
            ImageView[] panels = {(ImageView) findViewById(R.id.menu_imagebutton_casa), (ImageView) findViewById(R.id.menu_imagebutton_parque), (ImageView) findViewById(R.id.menu_imagebutton_colegio), (ImageView) findViewById(R.id.menu_imagebutton_chuches)};
            this.panels = panels;
        }
        if(menu.equals("casa"))
        {
            int[] drawables = {R.drawable.dormitorio, R.drawable.cocina, R.drawable.salon, R.drawable.baino};
            this.drawables = drawables;
            ImageView[] panels = {(ImageView)findViewById(R.id.menu_imagebutton_dormitorio), (ImageView)findViewById(R.id.menu_imagebutton_cocina), (ImageView)findViewById(R.id.menu_imagebutton_salon), (ImageView)findViewById(R.id.menu_imagebutton_baino)};
            this.panels=panels;
        }
        if(menu.equals("colegio"))
        {
            int[] drawables2 = {R.drawable.clase, R.drawable.biblioteca, R.drawable.gimnasio};
            drawables = drawables2;
            ImageView[] panels2 = {(ImageView)findViewById(R.id.menu_imagebutton_clase), (ImageView)findViewById(R.id.menu_imagebutton_biblioteca), (ImageView)findViewById(R.id.menu_imagebutton_gimnasio)};
            panels=panels2;
        }
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

    /*@Override
    public void imageResize(int[] drawables, int[] ids, int newSize) {
        try {
            for(int i=0;i<ids.length;i++) {
                ImageResize image = new ImageResize(getResources(),drawables[i],newSize);
                ImageView view = (ImageView)findViewById(ids[i]);
                view.setBackground(new BitmapDrawable(getResources(),image.getScaled()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Bitmap bitmapResize(Bitmap bitmap, int newSize) {
        return null;
    }

    @Override
    public void imageResize() {

    }*/
}
