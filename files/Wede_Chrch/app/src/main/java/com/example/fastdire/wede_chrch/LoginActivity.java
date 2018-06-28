package com.example.fastdire.wede_chrch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText etPassword = findViewById(R.id.etPassword);
        final EditText etEmail = findViewById(R.id.emailet);
        final Button bLogin = findViewById(R.id.bLogin);
        final TextView registerLink = findViewById(R.id.tvRegisterhere);

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this,RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

     bLogin.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {

             final String email = etEmail.getText().toString();
             final String password = etPassword.getText().toString();


             Response.Listener<String> responselistener = new Response.Listener<String>() {
                 @Override
                 public void onResponse(String response) {

                     Log.i("ServerResponse", response);

                     try {
                         JSONObject jsonResponse = new JSONObject(response);
                         boolean success = jsonResponse.getBoolean("success");

                         if(success){

                             Intent intent = new Intent(LoginActivity.this,ChurchActivity.class);
                             LoginActivity.this.startActivity(intent);

                         }
                         else {

                             AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                             builder.setMessage("Login FAILED")
                                     .setNegativeButton("RETRY", null)
                                     .create()
                                     .show();

                         }
                     } catch (JSONException e) {
                         e.printStackTrace();
                     }


                 }
             };

             LoginRequest loginRequest = new LoginRequest(email, password, responselistener);

             RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
             queue.add(loginRequest);

         }
     });

    }
}