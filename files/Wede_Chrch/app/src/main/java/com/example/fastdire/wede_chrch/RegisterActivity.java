package com.example.fastdire.wede_chrch;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText etName = findViewById(R.id.etName);
        final EditText etlastname = findViewById(R.id.etlastname);
        final EditText etUsername = findViewById(R.id.etUserName);
        final EditText etcity = findViewById(R.id.etcity);
        final EditText etrigion = findViewById(R.id.etrigion);
        final EditText etsex = findViewById(R.id.etsex);
        final EditText etphone = findViewById(R.id.etphone);
        final EditText etemail = findViewById(R.id.etemail);
        final EditText etPassword = findViewById(R.id.etPassword);
        final Button bRegister = findViewById(R.id.bRegister);

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String first_name = etName.getText().toString();
                final String last_name = etlastname.getText().toString();
                final String user_name = etUsername.getText().toString();
                final String city = etcity.getText().toString();
                final String region = etrigion.getText().toString();
                final String sex  = etsex.getText().toString();
                final String phone_number = etphone.getText().toString();
                final String email = etemail.getText().toString();
                final String password = etPassword.getText().toString();


                Response.Listener<String> responselistener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("ServerResponse", response);


                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){

                                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                RegisterActivity.this.startActivity(intent);
                            }
                            else {

                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("REGISTER FAILED")
                                        .setNegativeButton("RETRY", null)
                                        .create()
                                        .show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                };


                RegisterRequest registerRequest = new RegisterRequest(first_name, last_name, user_name, city, region, sex, phone_number, email, password,responselistener);

                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);


            }
        });

    }
}
