package com.example.jhocx.directus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;


public class DirectoryActivity extends AppCompatActivity {
    boolean foodIsChecked, fashionIsChecked, booksIsChecked, entertainmentIsChecked, supermartIsChecked, healthcareIsChecked, serviceIsChecked, othersIsChecked;
    boolean categoriesFiltered = false;
    TextView categoriesSelected, mallsSelected;
    String categoriesString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directory);

        categoriesSelected = (TextView) findViewById(R.id.categoriesSelected);
        categoriesSelected.setText("No categories yet");

        Intent intentExtras = getIntent();
        Bundle bundle = intentExtras.getExtras();

        if (bundle != null) {
            foodIsChecked = bundle.getBoolean("foodIsChecked", false);
            fashionIsChecked = bundle.getBoolean("fashionIsChecked", false);
            booksIsChecked = bundle.getBoolean("booksIsChecked", false);
            entertainmentIsChecked = bundle.getBoolean("entertainmentIsChecked", false);
            supermartIsChecked = bundle.getBoolean("supermartIsChecked", false);
            healthcareIsChecked = bundle.getBoolean("healthcareIsChecked", false);
            serviceIsChecked = bundle.getBoolean("serviceIsChecked", false);
            othersIsChecked = bundle.getBoolean("othersIsChecked", false);
            categoriesFiltered = true;
            Toast.makeText(DirectoryActivity.this, Boolean.toString(foodIsChecked) + Boolean.toString(fashionIsChecked), Toast.LENGTH_LONG).show();

        }


        if (categoriesFiltered == true) {
            if (foodIsChecked == true) {
                categoriesString += "Food, ";
            }
            if (fashionIsChecked == true) {
                categoriesString += "Fashion, ";
            }
            if (booksIsChecked == true) {
                categoriesString += "Books, ";
            }
            if (entertainmentIsChecked == true) {
                categoriesString += "Entertainment, ";
            }
            if (supermartIsChecked == true) {
                categoriesString += "Supermarket, ";
            }
            if (healthcareIsChecked == true) {
                categoriesString += "Healthcare, ";
            }
            if (serviceIsChecked == true) {
                categoriesString += "Service, ";
            }
            if (othersIsChecked == true) {
                categoriesString += "Others.";
            }
            categoriesSelected.setText(categoriesString);
        }

    }

    public void openFilterActivity(View v){
        startActivity(new Intent(DirectoryActivity.this, FilterActivity.class));
    }

    public void backToMain(View v) {
        startActivity(new Intent(DirectoryActivity.this, MainActivity.class));
    }
}
