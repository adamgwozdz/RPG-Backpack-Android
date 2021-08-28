package com.wodu.mobile.rpg_backpack.services;

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
import com.android.volley.toolbox.JsonArrayRequest;
import com.wodu.mobile.rpg_backpack.Utilities;
import com.wodu.mobile.rpg_backpack.entities.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SessionService {

    private final String TAG = "RpgBackpackService";
    private static final String QUERY_FOR_CURRENT_USER_SESSIONS = "https://wodu-rpg-backpack.herokuapp.com/api/app/session/sessions/user-sessions";


    private final Context context;

    public SessionService(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener {
        void onResponse(List<Session> response);

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

    private Session createSession(JSONArray response, int index) throws JSONException {
        JSONObject sessionData = response.getJSONObject(index);
        return new Session(
                sessionData.getInt("sessionId"),
                sessionData.getString("name"),
                sessionData.getString("password"),
                sessionData.getInt("maxAttributes"),
                Utilities.convertToTimestamp(sessionData.getString("dateCreated")),
                Utilities.convertToTimestamp(sessionData.getString("dateModified")),
                Utilities.convertToTimestamp(sessionData.getString("dateRemoved")),
                sessionData.getString("image"));
    }

    public void getCurrentUserSessionNames(VolleyResponseListener volleyResponseListener, String accessToken) {
        RequestQueue requestQueue = prepareRequest();

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, QUERY_FOR_CURRENT_USER_SESSIONS, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            List<Session> sessionsList = new LinkedList<>();
                            for (int i = 0; i < response.length(); i++) {
                                sessionsList.add(createSession(response, i));
                            }
                            volleyResponseListener.onResponse(sessionsList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        volleyResponseListener.onError("Error occurred while connecting to server");
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", accessToken);
                return params;
            }
        };
        requestQueue.add(request);
    }
}
