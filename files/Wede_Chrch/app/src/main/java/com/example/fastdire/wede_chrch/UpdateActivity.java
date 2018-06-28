package com.example.fastdire.wede_chrch;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class UpdateActivity extends AppCompatActivity {

    String HttpURL = "http://192.168.43.169/CRUD/UpdateChurch.php";
    ProgressDialog progressDialog;
    String finalResult ;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    EditText ChurchName, Denomination, Description, Location, Longitude, Latitude, Email, Phone;
    Button UpdateStudent;
    String IdHolder,ChurchNameHolder, DescriptionHolder, DenominationHolder, LocationHolder, LongitudeHolder, LatitudeHolder, EmailHolder, PhoneHolder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        ChurchName = findViewById(R.id.editName);
        Denomination = findViewById(R.id.editDenomination);
        Description = findViewById(R.id.editDescription);
        Location = findViewById(R.id.editLocation);
        Longitude = findViewById(R.id.editLongitude);
        Latitude = findViewById(R.id.editLatitude);
        Email = findViewById(R.id.editEmail);
        Phone = findViewById(R.id.editPhone);

        UpdateStudent = findViewById(R.id.UpdateButton);

        // Receive Church ID, Name.... Send by previous ShowSingleRecordActivity.
        IdHolder = getIntent().getStringExtra("id");
        ChurchNameHolder = getIntent().getStringExtra("church_name");
        DenominationHolder = getIntent().getStringExtra("denomination");
        DescriptionHolder = getIntent().getStringExtra("description");
        LocationHolder = getIntent().getStringExtra("location");
        LongitudeHolder = getIntent().getStringExtra("longitude");
        LatitudeHolder = getIntent().getStringExtra("latitude");
        EmailHolder = getIntent().getStringExtra("email");
        PhoneHolder = getIntent().getStringExtra("phone_number");

        // Setting Received Church Name, Denomination..... into EditText.
        ChurchName.setText(ChurchNameHolder);
        Denomination.setText(DenominationHolder);
        Description.setText(DescriptionHolder);
        Location.setText(LocationHolder);
        Longitude.setText(LongitudeHolder);
        Latitude.setText(LatitudeHolder);
        Email.setText(EmailHolder);
        Phone.setText(PhoneHolder);

        // Adding click listener to update button .
        UpdateStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Getting data from EditText after button click.
                GetDataFromEditText();

                // Sending Church Name,Denomination.... to method to update on server.
                ChurchRecordUpdate(IdHolder,ChurchNameHolder,DenominationHolder, DescriptionHolder,
                              LocationHolder, LongitudeHolder, LatitudeHolder, EmailHolder, PhoneHolder);

            }
        });


    }

    // Method to get existing data from EditText.
    public void GetDataFromEditText(){

        ChurchNameHolder = ChurchName.getText().toString();
        DenominationHolder = Denomination.getText().toString();
        DescriptionHolder = Description.getText().toString();
        LocationHolder = Location.getText().toString();
        LongitudeHolder = Longitude.getText().toString();
        LatitudeHolder = Latitude.getText().toString();
        EmailHolder = Email.getText().toString();
        PhoneHolder = Phone.getText().toString();

    }

    // Method to Update Church Record.
    public void ChurchRecordUpdate(final String id,final String church_Name, final String description,
                                   final String denomination, final String location, final String longitude,
                                   final String latitude, final String email, final String phone){

        class ChurchRecordUpdateClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(UpdateActivity.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                Toast.makeText(UpdateActivity.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("id",params[0]);

                hashMap.put("church_name",params[1]);

                hashMap.put("denomination",params[2]);

                hashMap.put("description",params[3]);

                hashMap.put("location",params[4]);

                hashMap.put("longitude",params[5]);

                hashMap.put("latitude",params[6]);

                hashMap.put("email",params[7]);

                hashMap.put("phone_number",params[8]);

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        ChurchRecordUpdateClass churchRecordUpdateClass = new ChurchRecordUpdateClass();

        churchRecordUpdateClass.execute(id,church_Name,denomination,description,location,longitude,latitude,email,phone);
    }
}