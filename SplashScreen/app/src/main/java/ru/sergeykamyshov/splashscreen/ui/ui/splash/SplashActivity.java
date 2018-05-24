package ru.sergeykamyshov.splashscreen.ui.ui.splash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import ru.sergeykamyshov.splashscreen.R;
import ru.sergeykamyshov.splashscreen.ui.ui.main.MainActivity;
import ru.sergeykamyshov.splashscreen.ui.adapters.SplashAdapter;

public class SplashActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "ru.sergeykamyshov.splashscreen.SplashActivity";
    private static final String FIRST_LAUNCH_KEY = "FIRST_LAUNCH_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        if (!prefs.getBoolean(FIRST_LAUNCH_KEY, true)) {
            startMainActivity();
            finish();
        }

        setContentView(R.layout.activity_splash);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(new SplashAdapter(getSupportFragmentManager()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        // TODO: удалить true после тестирования
        editor.putBoolean(FIRST_LAUNCH_KEY, true);
        editor.apply();
    }

    public void skip(View view) {
        startMainActivity();
    }

    private void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
