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



public class DirectoryActivity extends AppCompatActivity {
    boolean filterActivityOpened; //if FilterActivity is opened
    boolean foodIsChecked, fashionIsChecked, booksIsChecked, entertainmentIsChecked, supermartIsChecked, healthcareIsChecked, serviceIsChecked, othersIsChecked; //if categories are chosen
    boolean searched = false; //if search is being done
    boolean categoriesfiltered = false;
    String categoriesString = "";
    String searchItem;

    private List<Shop> allShops = new ArrayList<Shop>(); //all shops from json, without filtering
    private List<Shop> filteredShops = new ArrayList<>(); //filtered shops from json, after filtering
    private List<Shop> copyFilteredShops = new ArrayList<>(); //copy of filtered shops to revert if original filtered list is changed

    int length;
    //String[] shopNameArray;
    String[] shoppingMalls;

    private View rootView;     //to make search view only focusable on touch
    TextView categoriesSelected, mallsSelected; //Test views
    private ListView list;
    private SearchView searchView;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directory);


        //To check if filterActivity has been opened yet
        filterActivityOpened = FilterActivity.filterActivityIsOpen();

        //To obtain values from other activities
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
            if(filterActivityOpened) {
                shoppingMalls = bundle.getStringArray("MallsFromFilter");
            } else shoppingMalls = bundle.getStringArray("MallsFromMain");
            categoriesfiltered = true;
        }

        //Initialise categories textView
        categoriesSelected = (TextView) findViewById(R.id.categoriesSelected);
        categoriesSelected.setText("No categories yet");

        //Update text view of categories selected, for debugging options
        if (filterActivityOpened) {
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


        rootView = findViewById(R.id.root_layout);
        searchView = findViewById(R.id.search);

        /*For search purposes, if search is submitted, search entries are returned, if any edits (like backspace),
        it is reverted to filtered list or all list.
         */

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                    searched = true;
                    searchItem = query;
                    filteredShops = filter(filteredShops, query);
                    Toast.makeText(DirectoryActivity.this, "filtered shops" +Integer.toString(filteredShops.size()), Toast.LENGTH_LONG).show();
                    populateListView();
                    return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searched = true;
                searchItem = newText;
                filteredShops.clear();
                filteredShops.addAll(copyFilteredShops);
                //filteredShops = copyFilteredShops.clone();
                //Toast.makeText(DirectoryActivity.this, "I am changed", Toast.LENGTH_SHORT).show();
                //Toast.makeText(DirectoryActivity.this, "copyFiltered shops" + Integer.toString(copyFilteredShops.size()), Toast.LENGTH_SHORT).show();
                //Toast.makeText(DirectoryActivity.this, "filtered shops" + Integer.toString(filteredShops.size()), Toast.LENGTH_SHORT).show();
                populateListView();
                return false;
            }
            private List<Shop> filter(List<Shop> shops, String query) {
                query = query.toLowerCase();

                final List<Shop> searchedShopList = new ArrayList<>();
                for (Shop shop : shops) {
                    final String text = shop.getName().toLowerCase();
                    if (text.contains(query)) {
                        searchedShopList.add(shop);
                    }
                }
                return searchedShopList;
            }
        });




        if(!filterActivityOpened && !searched) {
            getJson();
            if(shoppingMalls.length>1){
                //java.util.Collections.sort(filteredShops, Collator.getInstance());
            }
            populateListView();
        }


        if(filterActivityOpened){
            getJson();
            if(shoppingMalls.length>1){
                //java.util.Collections.sort(filteredShops, Collator.getInstance());
            }
            filterByCategories();
            populateListView();
        }



    }

    //To make search view focusable on touch
    protected void onResume() {
        super.onResume();

        searchView.setQuery("", false);
        rootView.requestFocus();
    }


    public void openFilterActivity(View v) {
        Intent intent = new Intent(this, FilterActivity.class);
        Bundle b = new Bundle();
        b.putStringArray("MY_KEY", shoppingMalls);
        intent.putExtras(b);
        startActivity(intent);
    }

    public void backToMain(View v) {
        startActivity(new Intent(DirectoryActivity.this, MainActivity.class));
    }


    //Populate list view with the necessary items: name, address and pictures
    private void populateListView() {
        ArrayAdapter<Shop> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.shopListView);
        list.setAdapter(adapter);
    }

    private class MyListAdapter extends ArrayAdapter<Shop> {
        public MyListAdapter() {
            super(DirectoryActivity.this, R.layout.listview, filteredShops);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Shop currentShop;
            //Make sure view given is done
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.listview, parent, false);
            }
            //Find the shop
                currentShop = filteredShops.get(position);

            ImageView imageView = (ImageView) itemView.findViewById(R.id.shopPhoto);
            if (currentShop.getPhotoURL().isEmpty()) {
                imageView.setImageResource(R.drawable.defaultimg);
            } else {
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
            for (int i = 0; i < shoppingMalls.length; i++) {
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

                filteredShops = allShops.stream().sorted((shop1,shop2) -> shop1.getName().compareTo(shop2.getName())).collect(Collectors.toList());;

                copyFilteredShops.addAll(filteredShops);
                //Collections.copy(copyFilteredShops, filteredShops);



        } catch (IOException e) {
            e.printStackTrace();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void filterByCategories() {
        if (!foodIsChecked) {
            filteredShops = allShops.stream().filter(p -> !("Food".equals(p.getCategory()))).collect(Collectors.toList());
        }
        if (!fashionIsChecked) {
            filteredShops = filteredShops.stream().filter(p -> !("Fashion".equals(p.getCategory()))).collect(Collectors.toList());
        }
        if (!booksIsChecked) {
            filteredShops = filteredShops.stream().filter(p -> !("Books".equals(p.getCategory()))).collect(Collectors.toList());
        }
        if (!entertainmentIsChecked) {
            filteredShops = filteredShops.stream().filter(p -> !("Entertainment".equals(p.getCategory()))).collect(Collectors.toList());
        }
        if (!supermartIsChecked) {
            filteredShops = filteredShops.stream().filter(p -> !("Supermart".equals(p.getCategory()))).collect(Collectors.toList());
        }
        if (!healthcareIsChecked) {
            filteredShops = filteredShops.stream().filter(p -> !("Healthcare".equals(p.getCategory()))).collect(Collectors.toList());
        }
        if (!serviceIsChecked) {
            filteredShops = filteredShops.stream().filter(p -> !("Service".equals(p.getCategory()))).collect(Collectors.toList());
        }
        if (!othersIsChecked) {
            filteredShops = filteredShops.stream().filter(p -> !("Others".equals(p.getCategory()))).collect(Collectors.toList());
        }
        copyFilteredShops.clear();
        copyFilteredShops.addAll(filteredShops);

    }

}


