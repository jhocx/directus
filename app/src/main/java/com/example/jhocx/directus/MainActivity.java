package com.example.jhocx.directus;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.text.DecimalFormat;


public class MainActivity extends AppCompatActivity {

    TextView tvHeader;
    Button btnSelectMall;
    TextView tvMallsSelected;
    Button btnToDirectory;
    Button btnGetLoc;
    TextView tvLocation;
    static DecimalFormat df3 = new DecimalFormat(".###");

    // listMalls is a string array hardcoded in strings.xml. It contains the names of all malls available in our app.
    String[] listMalls;
    // checkedMalls is the boolean array that determines which mall(s) the user has selected.
    boolean[] checkedMalls;
    // mUserMalls is the current ArrayList of malls that the user has selected.
    ArrayList<Integer> mUserMalls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // This section changes the font of tvHeader to Roboto-Light.
        tvHeader = (TextView) findViewById(R.id.tvHeader);
        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
        tvHeader.setTypeface(myCustomFont);

        // This section implements the Mall Selection MultiChoice menu, and the visual text display tvMallsSelected.
        // Learnt from https://www.youtube.com/watch?v=wfADRuyul04
        btnSelectMall = (Button) findViewById(R.id.btnSelectMall);
        tvMallsSelected = (TextView) findViewById(R.id.tvMallsSelected);

        listMalls = getResources().getStringArray(R.array.malls);
        checkedMalls = new boolean[listMalls.length];
        tvMallsSelected.setText(getString(R.string.MA_tvMallsSelectedDefault));

        btnSelectMall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                mBuilder.setTitle("Please select malls you wish to be included.");
                mBuilder.setMultiChoiceItems(listMalls, checkedMalls, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        // if checkbox is checked:
                        if (isChecked) {
                            // and if the item is not already in mUserMalls.
                            // this is necessary to avoid duplicates
                            if (!mUserMalls.contains(position)) {
                                mUserMalls.add(position);
                            }
                        }
                        // if checkbox is unchecked and if the mall is currently in mUserMalls:
                        else if (mUserMalls.contains(position)) {
                            mUserMalls.remove(position);
                        }
                    }
                });

                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String item = "";
                        for (int i = 0; i < mUserMalls.size(); i++) {
                            item = item + listMalls[mUserMalls.get(i)];
                            // append a comma at the end if item is not the last item.
                            if (i != mUserMalls.size() - 1) {
                                item = item + "\n";
                            }
                        }
                        tvMallsSelected.setText(item);
                    }
                });
                mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                mBuilder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < checkedMalls.length; i++) {
                            checkedMalls[i] = false;
                            mUserMalls.clear();
                            tvMallsSelected.setText(getString(R.string.MA_tvMallsSelectedDefault));
                        }
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });

        // This section implements the bottommost  button that sends us to the DirectoryActivity.
        // The function openActivity2 is specified outside of the onCreate function.
        btnToDirectory = (Button) findViewById(R.id.btnToDirectory);
        btnToDirectory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDirectoryActivity();
            }
        });

        // This section deals with obtainin user's GPS location.
        btnGetLoc = (Button) findViewById(R.id.btnGetLoc);
        tvLocation = (TextView) findViewById(R.id.tvLocation);
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);
        GPSTracker g = new GPSTracker(getApplicationContext());
        Location l = g.getLocation();
        if (l!= null) {
            double lat = l.getLatitude();
            double lon = l.getLongitude();
            tvLocation.setText("Current GPS Location: LAT = " + df3.format(lat) + ", LON = " + df3.format(lon));
        }
        btnGetLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GPSTracker g = new GPSTracker(getApplicationContext());
                Location l = g.getLocation();
                if (l!= null) {
                    double lat = l.getLatitude();
                    double lon = l.getLongitude();
                    tvLocation.setText("Current GPS Location: LAT = " + df3.format(lat) + ", LON = " + df3.format(lon));
                }
            }
        });
    }

    public void openDirectoryActivity() {
        Intent intent = new Intent(this, DirectoryActivity.class);
        startActivity(intent);
    }

}

