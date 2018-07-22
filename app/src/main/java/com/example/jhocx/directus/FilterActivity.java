package com.example.jhocx.directus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
import android.app.Activity;
import android.widget.Toast;




public class FilterActivity extends AppCompatActivity {
    private static boolean filterActivityIsOpen;

    TextView t;
    CheckBox foodButton, fashionButton, booksButton, entertainmentButton, supermartButton, healthcareButton, serviceButton, othersButton;
    boolean foodIsChecked, fashionIsChecked, booksIsChecked, entertainmentIsChecked, supermartIsChecked, healthcareIsChecked, serviceIsChecked, othersIsChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        if(!filterActivityIsOpen) {
            Toast.makeText(FilterActivity.this, "filterActivity first opened " + filterActivityIsOpen, Toast.LENGTH_LONG).show();
            foodButton = (CheckBox) findViewById(R.id.food);
            foodButton.setChecked(false);
            fashionButton = (CheckBox) findViewById(R.id.fashion);
            fashionButton.setChecked(false);
            booksButton = (CheckBox) findViewById(R.id.books);
            booksButton.setChecked(false);
            entertainmentButton = (CheckBox) findViewById(R.id.entertainment);
            entertainmentButton.setChecked(false);
            supermartButton = (CheckBox) findViewById(R.id.supermarket);
            supermartButton.setChecked(false);
            healthcareButton = (CheckBox) findViewById(R.id.healthcare);
            healthcareButton.setChecked(false);
            serviceButton = (CheckBox) findViewById(R.id.service);
            serviceButton.setChecked(false);
            othersButton = (CheckBox) findViewById(R.id.others);
            othersButton.setChecked(false);
        }
       else {

            foodButton = (CheckBox) findViewById(R.id.food);
            foodIsChecked = getBooleanFromPreferences("foodIsChecked");
            Log.i("startFood", "" + foodIsChecked);
            foodButton.setChecked(foodIsChecked);
            foodButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton view, boolean foodIsChecked) {
                    Log.i("foodBoolean", "" + foodIsChecked);
                    FilterActivity.this.putBooleanInPreferences(foodIsChecked, "foodIsChecked");

                }
            });

            fashionButton = (CheckBox) findViewById(R.id.fashion);
            fashionIsChecked = getBooleanFromPreferences("fashionIsChecked");
            Log.i("startFashion", "" + fashionIsChecked);
            fashionButton.setChecked(fashionIsChecked);
            fashionButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton view, boolean fashionIsChecked) {
                    FilterActivity.this.putBooleanInPreferences(fashionIsChecked, "fashionIsChecked");
                }
            });

            booksButton = (CheckBox) findViewById(R.id.books);
            booksIsChecked = getBooleanFromPreferences("booksIsChecked");
            Log.i("startBooks", "" + booksIsChecked);
            booksButton.setChecked(booksIsChecked);
            booksButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton view, boolean booksIsChecked) {
                    Log.i("booksBoolean", "" + booksIsChecked);
                    FilterActivity.this.putBooleanInPreferences(booksIsChecked, "booksIsChecked");
                }
            });

            entertainmentButton = (CheckBox) findViewById(R.id.entertainment);
            entertainmentIsChecked = getBooleanFromPreferences("entertainmentIsChecked");
            Log.i("startEntertainment", "" + entertainmentIsChecked);
            entertainmentButton.setChecked(entertainmentIsChecked);
            entertainmentButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton view, boolean entertainmentIsChecked) {
                    Log.i("entertainmentBoolean", "" + entertainmentIsChecked);
                    FilterActivity.this.putBooleanInPreferences(entertainmentIsChecked, "entertainmentIsChecked");
                }
            });

            supermartButton = (CheckBox) findViewById(R.id.supermarket);
            supermartIsChecked = getBooleanFromPreferences("supermartIsChecked");
            Log.i("startSupermart", "" + supermartIsChecked);
            supermartButton.setChecked(supermartIsChecked);
            supermartButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton view, boolean supermartIsChecked) {
                    Log.i("supermartBoolean", "" + supermartIsChecked);
                    FilterActivity.this.putBooleanInPreferences(supermartIsChecked, "supermartIsChecked");
                }
            });

            healthcareButton = (CheckBox) findViewById(R.id.healthcare);
            healthcareIsChecked = getBooleanFromPreferences("healthcareIsChecked");
            Log.i("startHealthcare", "" + healthcareIsChecked);
            healthcareButton.setChecked(healthcareIsChecked);
            healthcareButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton view, boolean healthcareIsChecked) {
                    Log.i("healthcareBoolean", "" + healthcareIsChecked);
                    FilterActivity.this.putBooleanInPreferences(healthcareIsChecked, "healthcareIsChecked");
                }
            });

            serviceButton = (CheckBox) findViewById(R.id.service);
            serviceIsChecked = getBooleanFromPreferences("serviceIsChecked");
            Log.i("startService", "" + serviceIsChecked);
            serviceButton.setChecked(serviceIsChecked);
            serviceButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton view, boolean serviceIsChecked) {
                    Log.i("serviceBoolean", "" + serviceIsChecked);
                    FilterActivity.this.putBooleanInPreferences(serviceIsChecked, "serviceIsChecked");
                }
            });

            othersButton = (CheckBox) findViewById(R.id.others);
            othersIsChecked = getBooleanFromPreferences("othersIsChecked");
            Log.i("startOthers", "" + othersIsChecked);
            othersButton.setChecked(othersIsChecked);
            othersButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton view, boolean othersIsChecked) {
                    Log.i("othersBoolean", "" + othersIsChecked);
                    FilterActivity.this.putBooleanInPreferences(othersIsChecked, "othersIsChecked");
                }
            });
        }
        filterActivityIsOpen = true;
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
        Intent intentBundle = new Intent(FilterActivity.this, DirectoryActivity.class);
        Bundle bundle  = new Bundle();

        foodIsChecked = foodButton.isChecked();
        fashionIsChecked = fashionButton.isChecked();
        booksIsChecked = booksButton.isChecked();
        entertainmentIsChecked = entertainmentButton.isChecked();
        supermartIsChecked = supermartButton.isChecked();
        healthcareIsChecked = healthcareButton.isChecked();
        serviceIsChecked = serviceButton.isChecked();
        othersIsChecked = othersButton.isChecked();


        bundle.putBoolean("foodIsChecked", foodIsChecked);
        bundle.putBoolean("fashionIsChecked", fashionIsChecked);
        bundle.putBoolean("booksIsChecked", booksIsChecked);
        bundle.putBoolean("entertainmentIsChecked", entertainmentIsChecked);
        bundle.putBoolean("supermartIsChecked", supermartIsChecked);
        bundle.putBoolean("healthcareIsChecked", healthcareIsChecked);
        bundle.putBoolean("serviceIsChecked", serviceIsChecked);
        bundle.putBoolean("othersIsChecked", othersIsChecked);
        intentBundle.putExtras(bundle);
        startActivity(intentBundle);
    }


    protected void onPause() {
        super.onPause();
        filterActivityIsOpen = true;
  }

    protected void onStop() {
        super.onStop();
        filterActivityIsOpen = true;
    }

        public static boolean filterActivityIsOpen() {
        return filterActivityIsOpen;
    }

    /*
    public static void filterActivityIsOpen(boolean filterActivityIsOpen) {
        DayView.filterActivityIsOpen = filterActivityIsOpen;
    }
    */
}



