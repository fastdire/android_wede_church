package com.example.fastdire.wede_chrch;

import android.preference.PreferenceManager;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fast dire on 3/23/2018.
 */

public class LoginRequest extends StringRequest {



    private static  final String Login_url = "http://192.168.43.169/CRUD/Login.php";
    private Map<String,String> params;
    public LoginRequest(String email, String password, Response.Listener<String> listener){
        super(Method.POST, Login_url, listener, null);
        params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
