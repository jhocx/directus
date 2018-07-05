package com.example.jhocx.directus;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toolbar;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t = (TextView) findViewById(R.id.nearbyMalls);
        Typeface myCustomFont = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Light.ttf");
        t.setTypeface(myCustomFont);
    }
}
