package com.tokoku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class ShowListActivity extends AppCompatActivity {
    private static final String TAG = ShowListActivity.class.getSimpleName();
    TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);
        Context context = getApplicationContext();
        String filename = "internalStorageFile.txt";
        String str = read_file(context, filename);
        Toast.makeText(getBaseContext(),str,Toast.LENGTH_SHORT).show();

        tv = (TextView)findViewById(R.id.internal_item);

        tv.setText(str);

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
}