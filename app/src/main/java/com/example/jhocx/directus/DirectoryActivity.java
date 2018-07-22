package com.example.jhocx.directus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.SearchView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.squareup.picasso.Picasso;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;



public class DirectoryActivity extends AppCompatActivity {
    boolean foodIsChecked, fashionIsChecked, booksIsChecked, entertainmentIsChecked, supermartIsChecked, healthcareIsChecked, serviceIsChecked, othersIsChecked;
    boolean categoriesFiltered = false;
    TextView categoriesSelected, mallsSelected;
    String categoriesString = "";

    //Map<String, Integer> mapIndex;
    private List<Shop> allShops = new ArrayList<Shop>();
    private List<Shop> filteredShops = new ArrayList<>();
    private List<Shop> filteredShops2 = new ArrayList<>();

    ListView list;
    int length;
    String[] shopNameArray;
    String[] shoppingMalls = {"Westgate"};

    //To make search view only focusable on touch
    private View rootView;
    private SearchView searchView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directory);
        rootView = findViewById(R.id.root_layout);
;       searchView = (SearchView) findViewById(R.id.search);

        //Initialise categories textView
        categoriesSelected = (TextView) findViewById(R.id.categoriesSelected);
        categoriesSelected.setText("No categories yet");

        Intent intentExtras = getIntent();
        Bundle bundle = intentExtras.getExtras();

        //Update checkbox options in filter and passed into directoryActivity
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
        }

        //Update text view of categories selected, for debugging options
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

        getJson();
        filterByCategories();
        populateListView();
    }

    //To make search view focusable on touch
    protected void onResume() {
        super.onResume();

        searchView.setQuery("", false);
        rootView.requestFocus();
    }




    public void openFilterActivity(View v){
        startActivity(new Intent(DirectoryActivity.this, FilterActivity.class));
    }

    public void backToMain(View v) {
        startActivity(new Intent(DirectoryActivity.this, MainActivity.class));
    }


    //Populate list view with the necessary items: name, address and pictures
    private void populateListView() {
        ArrayAdapter<Shop> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.shopListView);
        list.setAdapter(adapter);

        shopNameArray = new String[length];
        for (int i = 0; i < length; i++) {
            shopNameArray[i] = allShops.get(i).getName();
        }
        // getIndexList(shopNameArray);
        // displayIndex();
    }

    private class MyListAdapter extends ArrayAdapter<Shop> {
        public MyListAdapter() {
            super(DirectoryActivity.this, R.layout.listview, filteredShops);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //Make sure view given is done
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.listview, parent, false);
            }
            //Find the shop
            Shop currentShop = filteredShops.get(position);

            ImageView imageView = (ImageView) itemView.findViewById(R.id.shopPhoto);
            if (currentShop.getPhotoURL().isEmpty()) {
                imageView.setImageResource(R.drawable.defaultimg);
            } else{
                Picasso.get().load(currentShop.getPhotoURL()).into(imageView);
            }


            TextView textView = (TextView) itemView.findViewById(R.id.shopName);
            textView.setText(currentShop.getName());

            TextView textView2 = (TextView) itemView.findViewById(R.id.shopLocation);
            textView2.setText(currentShop.getAddress());

            TextView shoppingMall = (TextView) itemView.findViewById(R.id.shoppingMall);
            shoppingMall.setText(currentShop.getShoppingMall());

            return itemView;
        }
    }

    public void getJson() {
        String json;
        try {

            for(int i=0; i<shoppingMalls.length; i++) {
                InputStream is = getAssets().open(shoppingMalls[i] + ".json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();

                json = new String(buffer, "UTF-8");
                JSONArray jsonArray = new JSONArray(json);
                length = jsonArray.length();


                for (int j = 0; j < jsonArray.length(); j++) {
                    JSONObject obj = jsonArray.getJSONObject(j);
                    allShops.add(new Shop(shoppingMalls[i], obj.getString("Name"), obj.getString("Address"), obj.getString("img_src"), obj.getString("Category")));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void filterByCategories() {

        filteredShops = allShops;

        if (!foodIsChecked) {
            filteredShops = allShops.stream().filter(p -> !("Food".equals(p.getCategory()))).collect(Collectors.toList());
        } if (!fashionIsChecked){
            filteredShops = filteredShops.stream().filter(p -> !("Fashion".equals(p.getCategory()))).collect(Collectors.toList());
        } if (!booksIsChecked){
            filteredShops = filteredShops.stream().filter(p -> !("Books".equals(p.getCategory()))).collect(Collectors.toList());
        } if(!entertainmentIsChecked){
            filteredShops = filteredShops.stream().filter(p -> !("Entertainment".equals(p.getCategory()))).collect(Collectors.toList());
        } if(!supermartIsChecked){
            filteredShops = filteredShops.stream().filter(p -> !("Supermart".equals(p.getCategory()))).collect(Collectors.toList());
        } if(!healthcareIsChecked){
            filteredShops = filteredShops.stream().filter(p -> !("Healthcare".equals(p.getCategory()))).collect(Collectors.toList());
        } if(!serviceIsChecked){
            filteredShops = filteredShops.stream().filter(p -> !("Service".equals(p.getCategory()))).collect(Collectors.toList());
        } if(!othersIsChecked){
            filteredShops = filteredShops.stream().filter(p -> !("Others".equals(p.getCategory()))).collect(Collectors.toList());
        }

    }
}


