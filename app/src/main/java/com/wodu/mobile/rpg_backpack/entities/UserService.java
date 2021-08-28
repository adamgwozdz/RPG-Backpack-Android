package com.wodu.mobile.rpg_backpack.entities;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

public class UserService {

    private final String TAG = "UserService";
    private static final String REGISTER_USER = "https://wodu-rpg-backpack.herokuapp.com/api/users/register";

    private final Context context;

    public UserService(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener {
        void onResponse(String response);

        void onError(String message);
    }

    private RequestQueue prepareRequest() {
        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        return requestQueue;
    }

    public void register(VolleyResponseListener volleyResponseListener, HashMap data) {
        RequestQueue requestQueue = prepareRequest();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, REGISTER_USER, new JSONObject(data),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            volleyResponseListener.onResponse(response.get("token").toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String responseBody = null;
                        try {
                            responseBody = new String(error.networkResponse.data, "utf-8");
                            JSONObject data = new JSONObject(responseBody);
                            String errorMessage = data.getString("error");
                            volleyResponseListener.onError(errorMessage);
                        } catch (UnsupportedEncodingException | JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ) {
            //here I want to post data to sever
        };
        requestQueue.add(request);

    }
}
