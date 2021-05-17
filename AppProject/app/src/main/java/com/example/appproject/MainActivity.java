package com.example.appproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Lake> lakeArrayList = new ArrayList<>();
    private ArrayAdapter<Lake> lakeArrayAdapter;
    private Lake[] lakes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lakeArrayAdapter = new ArrayAdapter<>(this, R.layout.list_line, R.id.list_text, lakeArrayList);//kopplar ihop xml-filen, textview elementet och listan
        ListView myListView = findViewById(R.id.lake_list);
        myListView.setAdapter(lakeArrayAdapter);
        new JsonTask().execute("https://wwwlab.iit.his.se/brom/kurser/mobilprog/dbservice/admin/getdataasjson.php?type=c20emili");
        Button button = findViewById(R.id.uppdater);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JsonTask().execute("https://wwwlab.iit.his.se/brom/kurser/mobilprog/dbservice/admin/getdataasjson.php?type=c20emili");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_item_1) {
            setContentView(R.layout.about);
            return true;
        }
        if (id == R.id.menu_item_2) {
            setContentView(R.layout.activity_main);
            return true;
        }
        if (id == R.id.menu_item_3) {
            return true;
        }
        if (id == R.id.menu_item_4) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @SuppressLint("StaticFieldLeak")
    private class JsonTask extends AsyncTask<String, String, String> {

        private HttpURLConnection connection = null;
        private BufferedReader reader = null;

        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null && !isCancelled()) {
                    builder.append(line).append("\n");
                }
                return builder.toString();
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String json) {
            lakeArrayList.clear();
            try {
                Gson gson = new Gson();
                lakes = gson.fromJson(json, Lake[].class);
                for (int i = 0; i < lakes.length; i++) {
                    Lake temp = lakes[i];
                    lakeArrayList.add(temp);
                    Log.d("arraylist ==>", ""+ lakeArrayList.get(i));
                }
            }
            catch (Exception e){
                Log.d("JsonException ==>", "Error: "+ e);
            }
            lakeArrayAdapter.notifyDataSetChanged();
        }
    }
}