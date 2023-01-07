package com.tokoku;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ShowListActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener {
    private static final String TAG = ShowListActivity.class.getSimpleName();
    TextView tv;
    ArrayList<String> list;
    ListView listSavedFiles;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);

        showSavedList();
    }

    void showSavedList(){
        Context context = getApplicationContext();
        String filename = "internalStorageFile.txt";
        String str = read_file(context, filename);
        String[] x = str.split(";");

        this.list = new ArrayList<>();
        for(String a : x){
            list.add(a);
            Log.d(String.valueOf(list), "showSavedList: ");
        }

        recyclerView = findViewById(R.id.bookList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RecyclerViewAdapter(this);
        adapter.setClickListener(this);

        adapter.setData(list);
        recyclerView.setAdapter(adapter);

    }

    public String read_file(Context context, String filename) {
        try {
            FileInputStream fis = context.openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (FileNotFoundException e) {
            Log.e(TAG, "File Not Found: " + e.getMessage());
            return "";
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "Unsuported: " + e.getMessage());
            return "";
        } catch (IOException e) {
            Log.e(TAG, "IOExeption: " + e.getMessage());
            return "";
        }
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}