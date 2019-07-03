package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {

    private EditText etTweet;
    public String newTweet;
    public TwitterClient client;
    public TextView chars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        getSupportActionBar().setTitle("New Tweet");

        etTweet  = (EditText) findViewById(R.id.etCompose);
        chars = (TextView) findViewById(R.id.tvCharacters);

        etTweet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String charsLeft = Integer.toString(280 - s.length());
                chars.setText(charsLeft);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        client = TwitterApp.getRestClient(this);
    }


    public void postTweet(String message) {
        client.sendTweet(message, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Tweet tweet = null;
                try {
                    tweet = Tweet.fromJson(response);
                    Intent sendResults = new Intent();
                    sendResults.putExtra("tweet", tweet);
                    setResult(RESULT_OK, sendResults);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("Error Accessing Network", errorResponse.toString());
            }
        });
    }

    public void composeTweet(View view) {
        Log.i("Compose", etTweet.getText().toString());
        String tweet = etTweet.getText().toString();
        if ((tweet.length() <= 280)) {
            postTweet(tweet);
        } else {
            Toast.makeText(this, "Tweet too long", Toast.LENGTH_LONG).show();
        }
    }
}
