package com.example.fastdire.wede_chrch;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.content.Intent;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.HashMap;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;

public class ChurchActivity extends AppCompatActivity {


    static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;

    TextView  Longitude, Latitude;
    EditText ChurchName,Denomination, Description, Location, Email, Phone;
    Button RegisterChurch, ShowChurches, adminb;
    String ChurchNameHolder, DescriptionHolder, DenominationHolder, LocationHolder, LongitudeHolder, LatitudeHolder, EmailHolder, PhoneHolder;
    Boolean CheckEditText;
    ProgressDialog progressDialog;
    String finalResult;
    HashMap<String, String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String HttpURL = "http://192.168.43.169/CRUD/ChurchRegister.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_church);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        getLocation();

        ChurchName =  findViewById(R.id.etchrchname);
        Description = findViewById(R.id.etdescription);
        Denomination = findViewById(R.id.etdenomination);
        Location = findViewById(R.id.etlocationu);
        Longitude = findViewById(R.id.etlongitude);
        Latitude = findViewById(R.id.etlatitude);
        Email = findViewById(R.id.etEmailc);
        Phone = findViewById(R.id.etphonec);

        RegisterChurch = findViewById(R.id.buttonSubmit);
        ShowChurches = findViewById(R.id.buttonShow);
        adminb = findViewById(R.id.adminb);

        adminb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentadmin = new Intent(getApplicationContext(), AdminActivity.class);

                startActivity(intentadmin);
            }
        });


        RegisterChurch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Checking whether EditText is Empty or Not
                CheckEditTextIsEmptyOrNot();

                if (CheckEditText) {

                    // If EditText is not empty and CheckEditText = True then this block will execute.

                    ChurchRegistration(ChurchNameHolder, DescriptionHolder, DenominationHolder, LocationHolder, LongitudeHolder, LatitudeHolder, EmailHolder, PhoneHolder);

                } else {

                    // If EditText is empty then this block will execute .
                    Toast.makeText(ChurchActivity.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();

                }

            }
        });

        ShowChurches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(getApplicationContext(), ShowAllChurchActivity.class);

                startActivity(intent);

            }
        });
    }

    public void ChurchRegistration(final String church_Name, final String description, final String denomination,
                                   final String location, final String longitude, final String latitude, final String email, final String phone_number) {

        class ChurchRegistrationClass extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(ChurchActivity.this, "Loading Data", null, true, true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();


                Log.i("ServerResponse", httpResponseMsg);



                Toast.makeText(ChurchActivity.this, httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {


                hashMap.put("church_name", params[0]);

                hashMap.put("denomination", params[1]);

                hashMap.put("description", params[2]);

                hashMap.put("location", params[3]);

                hashMap.put("longitude", params[4]);

                hashMap.put("latitude", params[5]);

                hashMap.put("email", params[6]);

                hashMap.put("phone_number", params[7]);

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        ChurchRegistrationClass churchRegistrationClass = new ChurchRegistrationClass();

        churchRegistrationClass.execute(church_Name, denomination, description, location, longitude, latitude, email, phone_number);
    }


    public void CheckEditTextIsEmptyOrNot() {

        ChurchNameHolder = ChurchName.getText().toString();
        DescriptionHolder = Description.getText().toString();
        DenominationHolder = Denomination.getText().toString();
        LocationHolder = Location.getText().toString();
        LongitudeHolder = Longitude.getText().toString();
        LatitudeHolder = Latitude.getText().toString();
        EmailHolder = Email.getText().toString();
        PhoneHolder = Phone.getText().toString();

        if (TextUtils.isEmpty(ChurchNameHolder) || TextUtils.isEmpty(DenominationHolder) || TextUtils.isEmpty(DescriptionHolder)
                || TextUtils.isEmpty(LocationHolder) || TextUtils.isEmpty(LongitudeHolder) || TextUtils.isEmpty(LatitudeHolder)
                || TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PhoneHolder)) {

            CheckEditText = false;

        } else {

            CheckEditText = true;
        }
    }






    void getLocation()



    {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);

        }else {

            android.location.Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if(location != null){

                double lon = location.getLongitude();
                double lati = location.getLatitude();

                ((TextView)findViewById(R.id.etlongitude)).setText("longitude: " + lon );
                ((TextView)findViewById(R.id.etlatitude)).setText("latitude: " + lati );

            }else{

                ((TextView)findViewById(R.id.etlongitude)).setText("Can not find the location" );
                ((TextView)findViewById(R.id.etlatitude)).setText("Can not find the location" );




            }
        }
   }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResult ){
        super.onRequestPermissionsResult(requestCode,permissions,grantResult);

        switch (requestCode){

            case REQUEST_LOCATION:
                getLocation();
                break;

        }
    }
}



