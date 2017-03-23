package com.example.rachael.musicplayer;

import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import java.io.IOException;
import java.util.List;


/**
 * Created by racha on 2/9/2017.
 */

public class YouTubeTask extends AsyncTask<Object, Void, List<SearchResult>>{

    private static final String TAG = YouTubeTask.class.getSimpleName();
    private static final String API_KEY = "AIzaSyAx7i55jzSdLL9btXqarEC8dTF0l3T67Zs";
    private static final long NUMBER_OF_VIDEOS_RETURNED = 25;

    private YouTube youtube;
    private JsonFactory jsonFactory;
    private HttpTransport transport;

    private String searchQuery;

    YouTubeTask(String query) {
        searchQuery = query;
    }

    @Override
    protected List<SearchResult> doInBackground (Object... params) {

        try {
            youtube = new YouTube.Builder(transport, jsonFactory, new HttpRequestInitializer() {
                @Override
                public void initialize(HttpRequest request) throws IOException {
                }
            }).setApplicationName("youtube-cmdline-geolocationsearch-sample").build();

            YouTube.Search.List search = youtube.search().list("id,snippet");

            search.setKey(API_KEY);
            search.setQ(searchQuery);

            // Note: You don't need to use this part, but without it you might be inundated with a
            // ton of results and (possibly) crash the app.
            search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);

            SearchListResponse searchResponse = search.execute();
            List<SearchResult> searchResultList = searchResponse.getItems();

            return searchResultList;

        } catch (IOException ex) {
            Log.e(TAG, "Network Error: ", ex);
        }

        return null;
    }

}
