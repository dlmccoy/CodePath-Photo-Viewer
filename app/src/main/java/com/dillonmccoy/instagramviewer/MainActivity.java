package com.dillonmccoy.instagramviewer;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.dillonmccoy.instagramviewer.adapters.PhotoAdapter;
import com.dillonmccoy.instagramviewer.classes.Photo;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    AsyncHttpClient mClient;
    ArrayAdapter<Photo> aPhotos;
    ArrayList<Photo> mPhotos;
    ListView lvPhotos;

    private SwipeRefreshLayout swipeContainer;


    static final String CLIENT_ID = "e05c462ebd86446ea48a5af73769b602";
    static final String API_POPULAR_URL = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchPopularPhotos();
            }
        });

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        // Set up the photos ArrayList and the ArrayAdapter for the ListView.
        mPhotos = new ArrayList<>();
        aPhotos = new PhotoAdapter(this, mPhotos);
        lvPhotos = (ListView) findViewById(R.id.lvPhotos);
        lvPhotos.setAdapter(aPhotos);

        // Send API request
        mClient = new AsyncHttpClient();
        fetchPopularPhotos();

    }

    private void fetchPopularPhotos() {
        mClient.get(API_POPULAR_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i("DEBUG", response.toString());
                aPhotos.clear();
                try {
                    JSONArray photos = response.getJSONArray("data");
                    for (int i = 0; i < photos.length(); i++) {
                        JSONObject photoJson = (JSONObject) photos.get(i);

                        aPhotos.add(new Photo(photoJson));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                aPhotos.notifyDataSetChanged();
                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }
        });
    }
}
