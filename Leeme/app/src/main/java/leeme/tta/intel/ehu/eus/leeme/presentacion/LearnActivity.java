package leeme.tta.intel.ehu.eus.leeme.presentacion;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import leeme.tta.intel.ehu.eus.leeme.R;

public class LearnActivity extends AppCompatActivity {

    private final String SERVER_URL = "http://51.254.127.111/Leeme/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LearnActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Custom code
        Intent intent = getIntent();
        String cadena = intent.getStringExtra(ListActivity.EXTRA_DISPLAY);
        String url = intent.getStringExtra(ListActivity.EXTRA_VIDEO);

        TextView titulo = (TextView)findViewById(R.id.learn_textview_titulo);
        titulo.setText(cadena);
        final VideoView video = (VideoView)findViewById(R.id.learn_videoview_video);
        final MediaController controller = new MediaController(this){
            @Override
            public void hide()
            {
                //Queremos que el MediaController siempre este visible
                this.show();
            }

            @Override
            public boolean dispatchKeyEvent(KeyEvent event)
            {
                if(event.getKeyCode() == KeyEvent.KEYCODE_BACK)
                    finish();
                return super.dispatchKeyEvent(event);
            }
        };
        controller.setAnchorView(video);
        video.setMediaController(controller);
        video.setVideoURI(Uri.parse(SERVER_URL + url));
        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                video.start();
            }
        });
    }
}
