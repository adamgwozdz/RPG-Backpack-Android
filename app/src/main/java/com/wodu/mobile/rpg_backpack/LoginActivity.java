package com.wodu.mobile.rpg_backpack;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.JsonReader;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private final String ACCESS_TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2MzAwMTMxMjQsImV4cCI6MTYzMDAyNzUyNCwidXNlcklkIjoxLCJzdGF0dXNJZCI6MSwiZW1haWwiOiJhcDFAcWEucWEiLCJuYW1lIjoiYXAxIn0.FrmvZnAElKseIjIrcxjc17xoQgE9kDLCd1W8GSiA7uw";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //TextView test = findViewById(R.id.test_text);

//        RequestQueue queue = Volley.newRequestQueue(this);
//        String url = "https://wodu-rpg-backpack.herokuapp.com/api/app/session/sessions/all-sessions";
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                response -> test.setText(response), error -> test.setText("That didn't work!")) {
//            @Override
//            public Map<String, String> getHeaders() {
//                Map<String, String> params = new HashMap<>();
//                params.put("Content-Type", "application/json; charset=UTF-8");
//                params.put("Authorization", ACCESS_TOKEN);
//                return params;
//            }
//        };
//        queue.add(stringRequest);
    }
}