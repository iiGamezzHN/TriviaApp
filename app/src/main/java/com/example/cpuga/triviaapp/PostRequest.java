package com.example.cpuga.triviaapp;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import javax.security.auth.callback.Callback;

public class PostRequest extends StringRequest {
    private int highscore;

    // Constructor
    public PostRequest(int method, String url, Response.Listener<String> listener,
                       Response.ErrorListener errorListener, int score) {
        super(method, url, listener, errorListener);
        highscore = score;
    }

    // Method to supply parameters to the request
    @Override
    protected Map<String, String> getParams() {

        Map<String, String> params = new HashMap<>();
        params.put("name", "David");
        params.put("score", String.valueOf(highscore));
        return params;
    }



}
