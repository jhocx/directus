package com.example.jhocx.directus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;


public class DirectoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directory);
    }

    public void proceedToFilter(View v){
        startActivity(new Intent(DirectoryActivity.this, FilterActivity.class));
    }
}
