package leeme.tta.intel.ehu.eus.leeme.presentacion;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import leeme.tta.intel.ehu.eus.leeme.R;

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

        if(savedInstanceState.getString("menu").contains("casa")) {
            menu=savedInstanceState.getString("menu");
            if (display.getRotation() == Surface.ROTATION_0)
                setContentView(R.layout.activity_menucasa);
            else if (display.getRotation() == Surface.ROTATION_270 || display.getRotation() == Surface.ROTATION_90)
                setContentView(R.layout.activity_menucasa_horizontal);
        }

        else {
            if (display.getRotation() == Surface.ROTATION_0)
                setContentView(R.layout.activity_menu);
            else if (display.getRotation() == Surface.ROTATION_270 || display.getRotation() == Surface.ROTATION_90)
                setContentView(R.layout.activity_menu_horizontal);
        }

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

    public void showCasa(View view){
        menu=view.getResources().getResourceName(view.getId());

        if(display.getRotation() == Surface.ROTATION_0)
            setContentView(R.layout.activity_menucasa);
        else if(display.getRotation()== Surface.ROTATION_270 || display.getRotation()== Surface.ROTATION_90)
            setContentView(R.layout.activity_menucasa_horizontal);
    }

    public void showEscuela(View view){
        menu=view.getResources().getResourceName(view.getId());
        setContentView(R.layout.activity_menu);
    }

    public void showMenu2(View view)
    {
        if(menu==null)
            menu=view.getResources().getResourceName(view.getId());
        else
            subMenu=view.getResources().getResourceName(view.getId());
        Toast.makeText(this,menu,Toast.LENGTH_LONG).show();
        Toast.makeText(this,subMenu,Toast.LENGTH_LONG).show();
        /*Intent intent = new Intent(this, Menu2Activity.class);
        intent.putExtra("EXTRA_MENU",menu);
        intent.putExtra("EXTRA_SUBMENU", subMenu);
        startActivity(intent);*/
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putString("menu",menu);
        savedInstanceState.putString("subMenu",subMenu);
        super.onSaveInstanceState(savedInstanceState);

    }
}
