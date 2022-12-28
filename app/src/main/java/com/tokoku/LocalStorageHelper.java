package com.tokoku;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class LocalStorageHelper {
    private SharedPreferences listStorage;
    private Gson gson;

    public LocalStorageHelper(Context context) {
        listStorage = context.getSharedPreferences("listStorage",context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void saveLists(ArrayList<String> lists){
        SharedPreferences.Editor editor = listStorage.edit();
        editor.putString("lists",gson.toJson(lists));
        editor.apply();
    }

    public ArrayList<String> getLists(){
        String listString = listStorage.getString("lists",null);
        Type listType = new TypeToken<ArrayList<String>>(){}.getType();
        ArrayList<String> lists = gson.fromJson(listString, listType);
        if(lists != null) return  lists;
        else return new ArrayList<>();
    }

}
