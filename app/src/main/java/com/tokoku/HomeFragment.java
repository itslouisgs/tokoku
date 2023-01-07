package com.tokoku;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.util.ArrayList;

public class HomeFragment extends Fragment implements RecyclerViewAdapter.ItemClickListener {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private ArrayList<String> products;

    private String file = "internalStorageFile.txt";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.products = new ArrayList<>();

        new HomeFragment.GetDataTask().execute();

        recyclerView = getView().findViewById(R.id.itemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new RecyclerViewAdapter(getActivity(), R.layout.home_recycler_view);
        adapter.setClickListener(this);

        FirebaseMessaging.getInstance().subscribeToTopic("web_app")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Done";
                        if (!task.isSuccessful()) {
                            msg = "Failed";
                        }
                        Log.d(TAG, msg);
                    }
                });
    }

    @Override
    public void onItemClick(View view, int position) {
        try {
            FileOutputStream fOut = getContext().openFileOutput(file, Context.MODE_APPEND);
            fOut.write((adapter.getItem(position) + ";").getBytes());
            fOut.close();
            Toast.makeText(getActivity(),"Item saved",Toast.LENGTH_SHORT).show();
        }  catch (Exception e) {
            Toast.makeText(getActivity(),"Failed to save item",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private class GetDataTask extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(getActivity(), "", "Loading...", true);
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

            progressDialog.dismiss();

            adapter.setData(products);
            recyclerView.setAdapter(adapter);
        }
    }
}