package ru.sergeykamyshov.splashscreen.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import ru.sergeykamyshov.splashscreen.R;

public class SplashActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "ru.sergeykamyshov.splashscreen.SplashActivity";
    private static final String FIRST_LAUNCH_KEY = "FIRST_LAUNCH_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        if (prefs.getBoolean(FIRST_LAUNCH_KEY, true)) {
            setContentView(R.layout.activity_splash);
        } else {
            startMainActivity();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(FIRST_LAUNCH_KEY, false);
        editor.apply();
    }

    public void skip(View view) {
        startMainActivity();
    }

    private void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
