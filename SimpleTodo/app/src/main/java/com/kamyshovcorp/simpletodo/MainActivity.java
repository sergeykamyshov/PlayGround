package com.kamyshovcorp.simpletodo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.kamyshovcorp.simpletodo.fragments.TodayListFragment;
import com.kamyshovcorp.simpletodo.fragments.TopFragment;

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
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.content_frame, TopFragment.newInstance())
                                .addToBackStack(null)
                                .commit();
                        break;
                    case 1:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.content_frame, TodayListFragment.newInstance())
                                .addToBackStack(null)
                                .commit();
                        break;
                    default:
                        break;

                }
            }
        });
    }

    public void showUpButton() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public void hideUpButton() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }
}
