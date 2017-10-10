package ru.kamyshovcorp.weekplanner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ru.kamyshovcorp.weekplanner.fragments.WeekFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_content, WeekFragment.newInstance())
                .commit();
    }
}
