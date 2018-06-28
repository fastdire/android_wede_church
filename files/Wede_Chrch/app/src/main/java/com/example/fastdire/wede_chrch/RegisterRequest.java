package com.example.fastdire.wede_chrch;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fast dire on 3/21/2018.
 */

public class RegisterRequest extends StringRequest {

    private static  final String Register_url = "http://192.168.43.169/CRUD/Rtest.php";
    private Map<String,String> params;
    public RegisterRequest(String first_name, String last_name, String user_name, String city, String region, String sex, String phone_number, String email, String password, Response.Listener<String> listener){
        super(Method.POST, Register_url, listener, null);
        params = new HashMap<>();
        params.put("first_name", first_name);
        params.put("last_name", last_name);
        params.put("user_name", user_name);
        params.put("city", city);
        params.put("region", region);
        params.put("sex", sex);
        params.put("phone_number", phone_number);
        params.put("email", email);
        params.put("password", password);

    }

    @Override
    public Map<String, String> getParams() {

        return params;
    }
}
