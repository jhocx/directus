package com.example.jhocx.directus;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
import android.app.Activity;
import android.widget.Toast;




public class FilterActivity extends AppCompatActivity {

    TextView t;
    CheckBox foodButton, fashionButton, booksButton, entertainmentButton, supermartButton, healthcareButton, serviceButton, othersButton;
    boolean foodIsChecked, fashionIsChecked, booksIsChecked, entertainmentIsChecked, supermartIsChecked, healthcareIsChecked, serviceIsChecked, othersIsChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        foodButton = (CheckBox) findViewById(R.id.food);
        foodIsChecked = getBooleanFromPreferences("foodIsChecked");
        Log.i("startFood",""+foodIsChecked);
        foodButton.setChecked(foodIsChecked);
        foodButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean foodIsChecked) {
                Log.i("foodBoolean",""+foodIsChecked);
                Toast.makeText(FilterActivity.this, "This is my Toast message!", Toast.LENGTH_LONG).show();
                FilterActivity.this.putBooleanInPreferences(foodIsChecked,"foodIsChecked");
            }
        });

        fashionButton = (CheckBox) findViewById(R.id.fashion);
        fashionIsChecked = getBooleanFromPreferences("fashionIsChecked");
        Log.i("startFashion",""+fashionIsChecked);
        fashionButton.setChecked(fashionIsChecked);
        fashionButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean fashionIsChecked) {
                Log.i("fashionBoolean",""+fashionIsChecked);
                FilterActivity.this.putBooleanInPreferences(fashionIsChecked,"fashionIsChecked");
            }
        });

        booksButton = (CheckBox) findViewById(R.id.books);
        booksIsChecked = getBooleanFromPreferences("booksIsChecked");
        Log.i("startBooks",""+booksIsChecked);
        booksButton.setChecked(booksIsChecked);
        booksButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean booksIsChecked) {
                Log.i("booksBoolean",""+ booksIsChecked);
                FilterActivity.this.putBooleanInPreferences(booksIsChecked,"booksIsChecked");
            }
        });

        entertainmentButton = (CheckBox) findViewById(R.id.entertainment);
        entertainmentIsChecked = getBooleanFromPreferences("entertainmentIsChecked");
        Log.i("startEntertainment",""+entertainmentIsChecked);
        entertainmentButton.setChecked(entertainmentIsChecked);
        entertainmentButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean entertainmentIsChecked) {
                Log.i("entertainmentBoolean",""+ entertainmentIsChecked);
                FilterActivity.this.putBooleanInPreferences(entertainmentIsChecked,"entertainmentIsChecked");
            }
        });

        supermartButton = (CheckBox) findViewById(R.id.supermarket);
        supermartIsChecked = getBooleanFromPreferences("supermartIsChecked");
        Log.i("startSupermart",""+supermartIsChecked);
        supermartButton.setChecked(supermartIsChecked);
        supermartButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean supermartIsChecked) {
                Log.i("supermartBoolean",""+ supermartIsChecked);
                FilterActivity.this.putBooleanInPreferences(supermartIsChecked,"supermartIsChecked");
            }
        });

        healthcareButton = (CheckBox) findViewById(R.id.healthcare);
        healthcareIsChecked = getBooleanFromPreferences("healthcareIsChecked");
        Log.i("startHealthcare",""+healthcareIsChecked);
        healthcareButton.setChecked(healthcareIsChecked);
        healthcareButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean healthcareIsChecked) {
                Log.i("healthcareBoolean",""+ healthcareIsChecked);
                FilterActivity.this.putBooleanInPreferences(healthcareIsChecked,"healthcareIsChecked");
            }
        });

        serviceButton = (CheckBox) findViewById(R.id.service);
        serviceIsChecked = getBooleanFromPreferences("serviceIsChecked");
        Log.i("startService",""+ serviceIsChecked);
        serviceButton.setChecked(serviceIsChecked);
        serviceButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean serviceIsChecked) {
                Log.i("serviceBoolean",""+ serviceIsChecked);
                FilterActivity.this.putBooleanInPreferences(serviceIsChecked,"serviceIsChecked");
            }
        });

        othersButton = (CheckBox) findViewById(R.id.others);
        othersIsChecked = getBooleanFromPreferences("othersIsChecked");
        Log.i("startOthers",""+ othersIsChecked);
        othersButton.setChecked(othersIsChecked);
        othersButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean othersIsChecked) {
                Log.i("othersBoolean",""+ othersIsChecked);
                FilterActivity.this.putBooleanInPreferences(othersIsChecked,"othersIsChecked");
            }
        });


    }
    public void putBooleanInPreferences(boolean isChecked,String key){
        SharedPreferences sharedPreferences = this.getPreferences(Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, isChecked);
        editor.commit();
    }
    public boolean getBooleanFromPreferences(String key){
        SharedPreferences sharedPreferences = this.getPreferences(Activity.MODE_PRIVATE);
        Boolean isChecked = sharedPreferences.getBoolean(key, false);
        return isChecked;
    }

    public void submitFilter(View v) {
        startActivity(new Intent(FilterActivity.this, DirectoryActivity.class));
    }

}



