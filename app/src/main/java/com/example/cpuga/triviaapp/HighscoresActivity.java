package com.example.cpuga.triviaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class HighscoresActivity extends AppCompatActivity implements HighscoreRequest.Callback {
    private int highscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);

        HighscoreRequest highscoreRequest = new HighscoreRequest(this);
        highscoreRequest.getHighscore(this);
    }

    public void gotHighscore(ArrayList arrayList) {
        // Simple adapter to show highscores

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                android.R.id.text1, arrayList);
        ListView listView = findViewById(R.id.highscore_list);
        listView.setAdapter(adapter);
    }
}
