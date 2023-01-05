package com.tokoku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private ArrayList<String> products;
    private String file = "internalStorageFile.txt";

    private static final String TAG = HTTPHandler.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.products = new ArrayList<>();

        new GetDataTask().execute();

        recyclerView = findViewById(R.id.bookList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RecyclerViewAdapter(this);
        adapter.setClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.about){
            startActivity(new Intent(MainActivity.this, AboutActivity.class));
        } else if (item.getItemId() == R.id.settings){
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
        } else if (item.getItemId() == R.id.showList){
            startActivity(new Intent(MainActivity.this,ShowListActivity.class));
        }

        return true;
    }

    @Override
    public void onItemClick(View view, int position) {
        try {
            FileOutputStream fOut = openFileOutput(file,MODE_APPEND);
            fOut.write((adapter.getItem(position) + "\n").getBytes());
            fOut.close();
            Toast.makeText(getBaseContext(),"Item saved",Toast.LENGTH_SHORT).show();
        }  catch (Exception e) {
            Toast.makeText(getBaseContext(),"Failed to save item",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private class GetDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this, "Fetching data...", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HTTPHandler handler = new HTTPHandler();
            String json = handler.makeServiceCall("https://fakestoreapi.com/products");

            if (json != null) {
                try {
                    JSONArray jsonArray = new JSONArray(json);

                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject product =  jsonArray.getJSONObject(i);
                        products.add(product.getString("title"));
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "JSONException: " + e.getMessage());
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            adapter.setData(products);

            recyclerView.setAdapter(adapter);
        }
    }
}