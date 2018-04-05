package ru.sergeykamyshov.emotiondiary.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import ru.sergeykamyshov.emotiondiary.R;
import ru.sergeykamyshov.emotiondiary.create.CreateEntryActivity;
import ru.sergeykamyshov.emotiondiary.database.Entry;
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
        final ListRecyclerAdapter recyclerAdapter = new ListRecyclerAdapter(this, Collections.<Entry>emptyList());
        recylerView.setAdapter(recyclerAdapter);

        ListViewModel viewModel = ViewModelProviders.of(this).get(ListViewModel.class);
        LiveData<List<Entry>> liveData = viewModel.getData();
        liveData.observe(this, new Observer<List<Entry>>() {
            @Override
            public void onChanged(@Nullable List<Entry> entries) {
                recyclerAdapter.setEntries(entries);
            }
        });

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
