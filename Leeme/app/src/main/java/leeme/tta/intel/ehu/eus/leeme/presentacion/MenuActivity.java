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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        if(display.getRotation() == Surface.ROTATION_0)
            setContentView(R.layout.activity_menu);
        else if(display.getRotation()== Surface.ROTATION_270 || display.getRotation()== Surface.ROTATION_90)
            setContentView(R.layout.activity_menu_horizontal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        menu=null;
        subMenu=null;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void showMenu2(View view)
    {
        if(menu==null)
            menu=view.getResources().getResourceName(view.getId());
        else
            subMenu=view.getResources().getResourceName(view.getId());
        Toast.makeText(this,menu,Toast.LENGTH_LONG).show();
        /*Intent intent = new Intent(this, Menu2Activity.class);
        intent.putExtra("EXTRA_MENU",1);
        intent.putExtra("EXTRA_SUBMENU", 1);
        startActivity(intent);*/
    }
}
