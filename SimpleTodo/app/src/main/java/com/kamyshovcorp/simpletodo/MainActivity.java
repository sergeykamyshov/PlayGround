package com.kamyshovcorp.simpletodo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private ListView mDrawerList;
    private String[] mDrawerCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, TopFragment.newInstance())
                .commit();

        mDrawerList = (ListView) findViewById(R.id.drawer_list);
        mDrawerCategories = getResources().getStringArray(R.array.drawer_categories);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mDrawerCategories);
        mDrawerList.setAdapter(adapter);
    }
}
