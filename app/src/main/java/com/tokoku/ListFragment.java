package com.tokoku;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class ListFragment extends Fragment {
    private String filename = "internalStorageFile.txt";
    TextView tv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        showSavedList();
    }

    void showSavedList(){
        Context context = getContext();
        String filename = "internalStorageFile.txt";
        String str = readFile(context, filename);
        String[] x = str.split(";");

        this.list = new ArrayList<>();
        for(String a : x){
            if (a.equals(x[x.length-1])){
                break;
            }
            list.add(a);
        }

        Log.d("LIST", "| " + list.get(list.size() - 1) + " |");

        recyclerView = findViewById(R.id.myListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RecyclerViewAdapter(this, R.layout.list_recycler_view);
        adapter.setClickListener(this);

        adapter.setData(list);
        recyclerView.setAdapter(adapter);
    }

    public String readFile(Context context, String filename) {
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
            return "";
        } catch (UnsupportedEncodingException e) {
            return "";
        } catch (IOException e) {
            return "";
        }
    }
}