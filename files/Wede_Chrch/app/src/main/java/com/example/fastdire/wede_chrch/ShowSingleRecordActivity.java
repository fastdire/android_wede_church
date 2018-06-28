package com.example.fastdire.wede_chrch;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;

public class ShowSingleRecordActivity extends AppCompatActivity {

    HttpParse httpParse = new HttpParse();
    ProgressDialog pDialog;

    // Http Url For Filter Church Data from Id Sent from previous activity.
    String HttpURL = "http://192.168.43.169/CRUD/FilterChurchData.php";

    // Http URL for delete Already Open Church Record.
    String HttpUrlDeleteRecord = "http://192.168.43.169/CRUD/DeleteChurch.php";

    String finalResult ;
    HashMap<String,String> hashMap = new HashMap<>();
    String ParseResult ;
    HashMap<String,String> ResultHash = new HashMap<>();
    String FinalJSonObject ;
    TextView Chrch_name, Denomination, Description,Location,Longitude,Latitude,Email,Phone;
    String ChurchNameHolder, DescriptionHolder, DenominationHolder, LocationHolder, LongitudeHolder, LatitudeHolder, EmailHolder, PhoneHolder;
    Button UpdateButton, DeleteButton;
    String TempItem;
    ProgressDialog progressDialog2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_single_record);

        Chrch_name = findViewById(R.id.textName);
        Denomination = findViewById(R.id.textDenominatiion);
        Description = findViewById(R.id.textDescription);
        Location = findViewById(R.id.textLocation);
        Longitude = findViewById(R.id.textLongitude);
        Latitude = findViewById(R.id.textLatitude);
        Email = findViewById(R.id.textEmail);
        Phone = findViewById(R.id.textPhone);

        UpdateButton = findViewById(R.id.buttonUpdate);
        DeleteButton = findViewById(R.id.buttonDelete);

        //Receiving the ListView Clicked item value send by previous activity.
        TempItem = getIntent().getStringExtra("ListViewValue");

        //Calling method to filter Church Record and open selected record.
        HttpWebCall(TempItem);


        UpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ShowSingleRecordActivity.this,UpdateActivity.class);

                // Sending Church Id, Name, Number and Class to next UpdateActivity.
                intent.putExtra("id", TempItem);
                intent.putExtra("church_name", ChurchNameHolder);
                intent.putExtra("denomination", DenominationHolder);
                intent.putExtra("description", DescriptionHolder);
                intent.putExtra("location", LocationHolder);
                intent.putExtra("longitude", LongitudeHolder);
                intent.putExtra("latitude", LatitudeHolder);
                intent.putExtra("email", EmailHolder);
                intent.putExtra("phone_number", PhoneHolder);


                startActivity(intent);

                // Finishing current activity after opening next activity.
                finish();

            }
        });

        // Add Click listener on Delete button.
        DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Calling Church delete method to delete current record using Church ID.
                ChurchDelete(TempItem);

            }
        });

    }

    // Method to Delete Church Record
    public void ChurchDelete(final String id) {

        class ChurchDeleteClass extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog2 = ProgressDialog.show(ShowSingleRecordActivity.this, "Loading Data", null, true, true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog2.dismiss();

                Toast.makeText(ShowSingleRecordActivity.this, httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

                finish();

            }

            @Override
            protected String doInBackground(String... params) {

                // Sending Church id.
                hashMap.put("id", params[0]);

                finalResult = httpParse.postRequest(hashMap, HttpUrlDeleteRecord);

                return finalResult;
            }
        }

        ChurchDeleteClass churchDeleteClass = new ChurchDeleteClass();

        churchDeleteClass.execute(id);
    }


    //Method to show current record Current Selected Record
    public void HttpWebCall(final String PreviousListViewClickedItem){

        class HttpWebCallFunction extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                pDialog = ProgressDialog.show(ShowSingleRecordActivity.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                pDialog.dismiss();

                //Storing Complete JSon Object into String Variable.
                FinalJSonObject = httpResponseMsg ;

                //Parsing the Stored JSOn String to GetHttpResponse Method.
                new GetHttpResponse(ShowSingleRecordActivity.this).execute();

            }

            @Override
            protected String doInBackground(String... params) {

                ResultHash.put("id",params[0]);

                ParseResult = httpParse.postRequest(ResultHash, HttpURL);

                return ParseResult;
            }
        }

        HttpWebCallFunction httpWebCallFunction = new HttpWebCallFunction();

        httpWebCallFunction.execute(PreviousListViewClickedItem);
    }


    // Parsing Complete JSON Object.
    private class GetHttpResponse extends AsyncTask<Void, Void, Void>
    {
        public Context context;

        public GetHttpResponse(Context context)
        {
            this.context = context;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0)
        {
            try
            {
                if(FinalJSonObject != null)
                {
                    JSONArray jsonArray = null;

                    try {
                        jsonArray = new JSONArray(FinalJSonObject);

                        JSONObject jsonObject;

                        for(int i=0; i<jsonArray.length(); i++)
                        {
                            jsonObject = jsonArray.getJSONObject(i);

                            // Storing Church Name, Denomination, description.... into Variables.
                            ChurchNameHolder = jsonObject.getString("church_name").toString() ;
                            DenominationHolder = jsonObject.getString("denomination").toString() ;
                            DescriptionHolder = jsonObject.getString("description").toString() ;
                            LocationHolder = jsonObject.getString("location").toString() ;
                            LongitudeHolder = jsonObject.getString("longitude").toString() ;
                            LatitudeHolder = jsonObject.getString("latitude").toString() ;
                            EmailHolder = jsonObject.getString("email").toString() ;
                            PhoneHolder = jsonObject.getString("phone_number").toString() ;

                        }
                    }
                    catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {

            // Setting  Name, Denomination, Description... into TextView after done all process .
            Chrch_name.setText(ChurchNameHolder);
            Denomination.setText(DenominationHolder);
            Description.setText(DescriptionHolder);
            Location.setText(LocationHolder);
            Longitude.setText(LongitudeHolder);
            Latitude.setText(LatitudeHolder);
            Email.setText(EmailHolder);
            Phone.setText(PhoneHolder);

        }
    }

}