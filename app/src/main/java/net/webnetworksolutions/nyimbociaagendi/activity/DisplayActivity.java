package net.webnetworksolutions.nyimbociaagendi.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.ImageView;
import android.widget.TextView;

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