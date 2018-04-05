package ru.sergeykamyshov.emotiondiary.create;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import ru.sergeykamyshov.emotiondiary.R;

public class CreateEntryActivity extends AppCompatActivity {

    /**
     * Параметр определяет показывать ли кнопку "Назад"
     */
    public static final boolean SHOW_HOME_AS_UP = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_entry);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(SHOW_HOME_AS_UP);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_entry, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            case R.id.menu_action_save:
                // save entry to database
        }
        return super.onOptionsItemSelected(item);
    }
}
