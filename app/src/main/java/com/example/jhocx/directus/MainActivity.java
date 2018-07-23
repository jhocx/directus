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
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.view.View;
import android.content.Intent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.text.DecimalFormat;
import java.lang.Math;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    SeekBar seekBar;
    TextView tvHeader, textView, textView2;
    TextView tvMallsSelected, tvLocation;
    Button btnSelectMall, btnToDirectory;
    double userLat, userLon;
    static DecimalFormat df3 = new DecimalFormat(".###");
    int progressValue = 500;

    // listNearby is a string array of malls near the user.
    String[] listNearby;
    String[] listNearbyTrimmed;
    // checkedMalls is the boolean array that determines which mall(s) the user has selected.
    boolean[] checkedMalls;
    // mUserMalls is the current ArrayList of malls that the user has selected.
    ArrayList<String> mUserMalls = new ArrayList<>();
    String[] mUserMallsString;
    int noOfMallsSelected = 0;
    boolean firstTime = true;
    boolean distanceChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Changes the font of tvHeader to Roboto-Light.
        tvHeader = (TextView) findViewById(R.id.tvHeader);
        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
        tvHeader.setTypeface(myCustomFont);
        textView.setTypeface(myCustomFont);
        textView2.setTypeface(myCustomFont);

        // Obtains user's preferred distance from their input in the seekbar.
        configureSeekBar();

        // Implements the Mall Selection MultiChoice menu, and the visual text display tvMallsSelected.
        btnSelectMall = (Button) findViewById(R.id.btnSelectMall);
        tvMallsSelected = (TextView) findViewById(R.id.tvMallsSelected);
        tvMallsSelected.setText(getString(R.string.MA_tvMallsSelectedDefault));
        tvLocation = (TextView) findViewById(R.id.tvLocation);
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);

        btnSelectMall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GPSTracker g = new GPSTracker(getApplicationContext());
                Location l = g.getLocation();
                if (l!= null) {
                    double lat = l.getLatitude();
                    double lon = l.getLongitude();
                    tvLocation.setText("Current GPS Location: LAT = " + df3.format(lat) + ", LON = " + df3.format(lon));
                    userLat = lat;
                    userLon = lon;
                }

                if (firstTime) {
                    firstTime = false;
                    // Algorithm for determining distance between 2 coordinate points.
                    // A LAT LON distance calculator: https://www.movable-type.co.uk/scripts/latlong.html
                    // Algorithm retrieved from https://stackoverflow.com/questions/3694380/calculating-distance-between-two-points-using-latitude-longitude-what-am-i-doi
                    // LAT LON coordinates finder: https://www.latlong.net/
                    try {
                        InputStream is = getAssets().open("Mall Coordinates.json");
                        int size = is.available();
                        byte[] buffer = new byte[size];
                        is.read(buffer);
                        is.close();

                        String json = new String(buffer, "UTF-8");
                        JSONArray jsonArray = new JSONArray(json);
                        listNearby = new String[jsonArray.length()];

                        TextView testMall = (TextView) findViewById(R.id.testMall);

                        final int earthRadius = 6371000;
                        int arrayCounter = 0;
                        for (int i=0; i< jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            double lat2 = Double.parseDouble(obj.getString("LAT"));
                            double lon2 = Double.parseDouble(obj.getString("LON"));

                            double circle1 = Math.toRadians(userLat);
                            double circle2 = Math.toRadians(lat2);

                            double deltaCircle = Math.toRadians(lat2 - userLat);
                            double deltaRen = Math.toRadians(lon2 - userLon);
                            double a = Math.sin(deltaCircle/2) * Math.sin(deltaCircle/2)
                                    + Math.cos(circle1) * Math.cos(circle2)
                                    * Math.sin(deltaRen/2) * Math.sin(deltaRen/2);
                            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
                            double distance = earthRadius * c;      // distance in metres

                            if (distance < progressValue) {
                                listNearby[arrayCounter] = obj.getString("Mall");
                                arrayCounter++;
                            }
                        }
                        listNearbyTrimmed = new String[arrayCounter];
                        for(int i=0; i<arrayCounter; i++) {
                            listNearbyTrimmed[i] = listNearby[i];
                        }
                        checkedMalls = new boolean[arrayCounter];
                        testMall.setText("The malls nearby are: " + Arrays.toString(listNearbyTrimmed));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    if (distanceChanged) {
                        distanceChanged = false;
                        // Algorithm for determining distance between 2 coordinate points.
                        // A LAT LON distance calculator: https://www.movable-type.co.uk/scripts/latlong.html
                        // Algorithm retrieved from https://stackoverflow.com/questions/3694380/calculating-distance-between-two-points-using-latitude-longitude-what-am-i-doi
                        // LAT LON coordinates finder: https://www.latlong.net/
                        try {
                            InputStream is = getAssets().open("Mall Coordinates.json");
                            int size = is.available();
                            byte[] buffer = new byte[size];
                            is.read(buffer);
                            is.close();

                            String json = new String(buffer, "UTF-8");
                            JSONArray jsonArray = new JSONArray(json);
                            listNearby = new String[jsonArray.length()];

                            TextView testMall = (TextView) findViewById(R.id.testMall);

                            final int earthRadius = 6371000;
                            int arrayCounter = 0;
                            for (int i=0; i< jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                double lat2 = Double.parseDouble(obj.getString("LAT"));
                                double lon2 = Double.parseDouble(obj.getString("LON"));

                                double circle1 = Math.toRadians(userLat);
                                double circle2 = Math.toRadians(lat2);

                                double deltaCircle = Math.toRadians(lat2 - userLat);
                                double deltaRen = Math.toRadians(lon2 - userLon);
                                double a = Math.sin(deltaCircle/2) * Math.sin(deltaCircle/2)
                                        + Math.cos(circle1) * Math.cos(circle2)
                                        * Math.sin(deltaRen/2) * Math.sin(deltaRen/2);
                                double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
                                double distance = earthRadius * c;      // distance in metres

                                if (distance < progressValue) {
                                    listNearby[arrayCounter] = obj.getString("Mall");
                                    arrayCounter++;
                                }
                            }
                            listNearbyTrimmed = new String[arrayCounter];
                            for(int i=0; i<arrayCounter; i++) {
                                listNearbyTrimmed[i] = listNearby[i];
                            }
                            checkedMalls = new boolean[arrayCounter];
                            testMall.setText("The malls nearby are: " + Arrays.toString(listNearbyTrimmed));
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                // Learnt from https://www.youtube.com/watch?v=wfADRuyul04
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                mBuilder.setTitle("Please select malls you wish to be included.");
                mBuilder.setMultiChoiceItems(listNearbyTrimmed, checkedMalls, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        // if checkbox is checked:
                        if (isChecked) {
                            // and if the item is not already in mUserMalls.
                            // this is necessary to avoid duplicates
                            if (!mUserMalls.contains(listNearbyTrimmed[position])) {
                                mUserMalls.add(listNearbyTrimmed[position]);
                            }
                        }
                        // if checkbox is unchecked and if the mall is currently in mUserMalls:
                        else if (mUserMalls.contains(listNearbyTrimmed[position])) {
                            mUserMalls.remove(listNearbyTrimmed[position]);
                        }
                    }
                });

                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String item = "";
                        for (int i = 0; i < mUserMalls.size(); i++) {
                            if (mUserMalls.get(i) != null) {
                                item = item + mUserMalls.get(i);
                            }
                            // append a comma at the end if item is not the last item.
                            if (i != mUserMalls.size() - 1) {
                                item = item + "\n";
                            }
                        }
                        if (item == "") {
                            tvMallsSelected.setText(getString(R.string.MA_tvMallsSelectedDefault));
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
    }

    public void configureSeekBar () {
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        textView2 = (TextView) findViewById(R.id.textView2);

        seekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progressValue = (progress * 15) + 500;
                        textView2.setText("Your desired distance is: " + progressValue + "m.");
                        distanceChanged = true;
                        firstTime = false;
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        textView2.setText("Your desired distance is: " + progressValue + "m.");
                    }
                }
        );
    }

    public void openDirectoryActivity(View v) {
        Intent intent = new Intent(this, DirectoryActivity.class);
        Bundle b = new Bundle();
        for (int i=0; i<mUserMalls.size(); i++) {
            if (mUserMalls.get(i) != null) {
                noOfMallsSelected++;
            }
        }
        mUserMallsString = new String[noOfMallsSelected];
        int j=0;
        for (int i = 0; i < mUserMalls.size(); i++) {
            if (mUserMalls.get(i) != null) {
                mUserMallsString[j] = mUserMalls.get(i);
                j++;
            }
        }
        b.putStringArray("MallsFromMain", mUserMallsString);
        intent.putExtras(b);
        startActivity(intent);
    }
}