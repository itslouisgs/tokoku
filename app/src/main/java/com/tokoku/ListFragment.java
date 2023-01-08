package com.tokoku;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class ListFragment extends Fragment {
    private String filename = "internalStorageFile.txt";
    private ArrayList<String> list;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    TextView tv;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list, container, false);

        FloatingActionButton fab = (FloatingActionButton)root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearList();
                Toast.makeText(getActivity(),"List deleted, please click your list again",Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showSavedList();
    }

    public void showSavedList(){
        String str = readFile(getActivity(), filename);
        String[] x = str.split(";");

        this.list = new ArrayList<>();

        for(String a : x){
            if (x.length-1 >= 0 && a.equals(x[x.length-1])){
                break;
            }
            list.add(a);
        }

        getView().findViewById(R.id.emptyNotice).setVisibility(list.isEmpty() ? View.VISIBLE : View.INVISIBLE);

        recyclerView = getView().findViewById(R.id.myListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new RecyclerViewAdapter(getActivity(), R.layout.list_recycler_view);
        adapter.setData(list);

        recyclerView.setAdapter(adapter);
    }

    public void clearList(){
       File dir = getActivity().getFilesDir();
       File file = new File(dir, filename);
       file.delete();
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