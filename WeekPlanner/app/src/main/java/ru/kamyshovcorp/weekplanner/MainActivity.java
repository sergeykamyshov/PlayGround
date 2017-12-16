package ru.kamyshovcorp.weekplanner;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import java.util.GregorianCalendar;

import io.realm.Realm;
import ru.kamyshovcorp.weekplanner.fragments.ArchiveWeekFragment;
import ru.kamyshovcorp.weekplanner.fragments.CurrentWeekFragment;
import ru.kamyshovcorp.weekplanner.model.Card;
import ru.kamyshovcorp.weekplanner.model.Task;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        mNavigationView = findViewById(R.id.navigation);
        mNavigationView.setNavigationItemSelectedListener(new NavigationItemClickListener());

        mDrawerLayout = findViewById(R.id.drawer);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open_drawer, R.string.close_drawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        fillDataForTest();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_content, CurrentWeekFragment.newInstance())
                .commit();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fillDataForTest() {
        Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();

                // --- Card 1 ---
                Card card1 = new Card();
                card1.setTitle("Card title 10");
                card1.setCreationDate(new GregorianCalendar(2017, 11, 10, 10, 30).getTime());

                Task task1 = new Task();
                task1.setTitle("Task 1");
                task1.setDone(true);
                Task task2 = new Task();
                task2.setTitle("Task 2");
                task2.setDone(true);
                Task task3 = new Task();
                task3.setTitle("Task 3");

                card1.addTask(task1);
                card1.addTask(task2);
                card1.addTask(task3);

                // --- Card 2 ---
                Card card2 = new Card();
                card2.setTitle("Card title 12");
                card2.setCreationDate(new GregorianCalendar(2017, 11, 12, 10, 30).getTime());

                Task task21 = new Task();
                task21.setTitle("Task 1");
                task21.setDone(true);
                Task task22 = new Task();
                task22.setTitle("Task 2");
                Task task23 = new Task();
                task23.setTitle("Task 3");

                card2.addTask(task21);
                card2.addTask(task22);
                card2.addTask(task23);

                // --- Card 3 ---
                Card card3 = new Card();
                card3.setTitle("Card title 16");
                card3.setCreationDate(new GregorianCalendar(2017, 11, 16, 10, 30).getTime());

                Task task31 = new Task();
                task31.setTitle("Task 1");
                task31.setDone(true);
                Task task32 = new Task();
                task32.setTitle("Task 2");
                Task task33 = new Task();
                task33.setTitle("Task 3");

                card3.addTask(task31);
                card3.addTask(task32);
                card3.addTask(task33);

                // Last week
                // --- Card 4 ---
                Card card4 = new Card();
                card4.setTitle("Card title 9");
                card4.setCreationDate(new GregorianCalendar(2017, 11, 9, 10, 30).getTime());

                Task task41 = new Task();
                task41.setTitle("Task 1");
                task41.setDone(true);
                Task task42 = new Task();
                task42.setTitle("Task 2");
                Task task43 = new Task();
                task43.setTitle("Task 3");

                card4.addTask(task41);
                card4.addTask(task42);
                card4.addTask(task43);

                // --- Insert ---
                realm.insertOrUpdate(card1);
                realm.insertOrUpdate(card2);
                realm.insertOrUpdate(card3);
                realm.insertOrUpdate(card4);
            }
        });
    }

    private class NavigationItemClickListener implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_item_current_week:
                    setTitle(getString(R.string.app_name));
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frame_content, CurrentWeekFragment.newInstance())
                            .commit();
                    break;
                case R.id.nav_item_archive:
                    setTitle(getString(R.string.title_archive));
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frame_content, ArchiveWeekFragment.newInstance())
                            .commit();
                    break;
            }
            mDrawerLayout.closeDrawers();
            return true;
        }
    }

}
