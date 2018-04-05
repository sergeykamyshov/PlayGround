package ru.sergeykamyshov.emotiondiary.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.sergeykamyshov.emotiondiary.R;
import ru.sergeykamyshov.emotiondiary.create.CreateEntryActivity;
import ru.sergeykamyshov.emotiondiary.model.Event;

public class ListActivity extends AppCompatActivity {

    /**
     * Параметр определяет будет ли размер элемента списка всегда одинаковой по высоте и ширине
     */
    public static final boolean HAS_FIXED_SIZE = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        RecyclerView recylerView = findViewById(R.id.recyler_view);
        recylerView.setLayoutManager(new LinearLayoutManager(this));
        recylerView.setHasFixedSize(HAS_FIXED_SIZE);
        recylerView.setAdapter(new EventsRecyclerAdapter(this, generateTestEvents()));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), CreateEntryActivity.class);
                startActivity(intent);
            }
        });
    }

    private List<Event> generateTestEvents() {
        List<Event> events = new ArrayList<>();
        String empty = "";
        for (int i = 0; i < 50; i++) {
            events.add(new Event(new Date(), "Situation " + i + ". And long long long long " +
                    "long long long long long long long long long long long ", empty, empty, empty));
        }
        return events;
    }
}
