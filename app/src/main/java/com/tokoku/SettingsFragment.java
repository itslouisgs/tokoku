package com.tokoku;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.Preference;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        Preference themePref = (Preference)findPreference("theme_pref");
        ((SwitchPreference) themePref).setChecked(getResources().getString(R.string.theme).equals("Dark"));

        themePref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {
                Boolean isChecked = ((SwitchPreference) preference).isChecked();

                if (isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                return true;
            }
        });

        Preference versionPref = (Preference) findPreference("ver_pref");
        versionPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {
                Toast.makeText(getActivity(), "Version 1.0.0-alpha01", Toast.LENGTH_SHORT).show();
                return true;
            }
        });


        Preference aboutPref = (Preference)findPreference("about_pref");
        aboutPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {
                startActivity(new Intent(getContext(), AboutActivity.class));
                return true;
            }
        });

    }

}
