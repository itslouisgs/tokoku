package com.tokoku;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView mBottomNavigation;

    private static final String TAG = HTTPHandler.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        addListener();

        setFragment(new HomeFragment());
        subscribeToNotifications();

    }

    private void initComponents() {
        mBottomNavigation = findViewById(R.id.buttom_navigation);
    }

    private void addListener() {
        mBottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setFragment(new HomeFragment());
                    break;
                case R.id.navigation_list:
                    setFragment(new ListFragment());
                    break;
            }
            return true;
        });
    }

    private void setFragment(Fragment f){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.homeFragmentPlaceholder, f);
        ft.commit();
    }

    private void subscribeToNotifications() {
        FirebaseMessaging.getInstance().subscribeToTopic("web_app")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Done";
                        if (!task.isSuccessful()) {
                            msg = "Failed";
                        }
                    }
                });
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
        }

        return true;
    }
}