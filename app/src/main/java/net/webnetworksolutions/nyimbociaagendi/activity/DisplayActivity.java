package net.webnetworksolutions.nyimbociaagendi.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

import net.webnetworksolutions.nyimbociaagendi.R;

public class DisplayActivity extends AppCompatActivity {

    ImageView imageView;
    TextView txName,txLyrics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        imageView=(ImageView)findViewById(R.id.imgDisplay);
        txName=(TextView)findViewById(R.id.tvDisplayTitle);
        txLyrics=(TextView)findViewById(R.id.tvDisplayLyrics);

        imageView.setImageResource(getIntent().getIntExtra("img_id",00));
        txName.setText(getIntent().getStringExtra("name"));
        txLyrics.setText(getIntent().getStringExtra("lyrics"));
    }
}