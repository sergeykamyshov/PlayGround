package ru.sergeykamyshov.emotiondiary.list;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.sergeykamyshov.emotiondiary.R;
import ru.sergeykamyshov.emotiondiary.list.EventsRecyclerAdapter;
import ru.sergeykamyshov.emotiondiary.model.Event;

public class ListActivity extends AppCompatActivity {

    public static final boolean HAS_FIXED_SIZE = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recylerView = findViewById(R.id.recyler_view);
        recylerView.setLayoutManager(new LinearLayoutManager(this));
        recylerView.setHasFixedSize(HAS_FIXED_SIZE);
        recylerView.setAdapter(new EventsRecyclerAdapter(this, generateTestEvents()));
    }

    private List<Event> generateTestEvents() {
        List<Event> events = new ArrayList<>();
        String empty = "";
        for (int i = 0; i < 50; i++) {
            events.add(new Event(new Date(), "Situation " + i, empty, empty, empty));
        }
        return events;
    }
}
