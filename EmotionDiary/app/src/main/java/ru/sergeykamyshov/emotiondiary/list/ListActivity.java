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

import java.util.List;

import ru.sergeykamyshov.emotiondiary.R;
import ru.sergeykamyshov.emotiondiary.create.CreateEntryActivity;
import ru.sergeykamyshov.emotiondiary.database.Entry;
import ru.sergeykamyshov.emotiondiary.database.Repository;

import static ru.sergeykamyshov.emotiondiary.create.CreateEntryActivity.EXTRA_ENTRY_ID;

public class ListActivity extends AppCompatActivity {

    /**
     * Параметр определяет будет ли размер элемента списка всегда одинаковой по высоте и ширине
     */
    public static final boolean HAS_FIXED_SIZE = true;

    private ListViewModel mListViewModel;
    private ListRecyclerAdapter mRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        RecyclerView recylerView = findViewById(R.id.recyler_view);
        recylerView.setLayoutManager(new LinearLayoutManager(this));
        recylerView.setHasFixedSize(HAS_FIXED_SIZE);
        mRecyclerAdapter = new ListRecyclerAdapter(this);
        recylerView.setAdapter(mRecyclerAdapter);
        recylerView.addOnItemTouchListener(new RecyclerTouchListener(this, new RecyclerTouchListener.OnClickListener() {
            @Override
            public void onClick(View child, int position) {
                Entry entry = mRecyclerAdapter.getEntryByPosition(position);
                Intent intent = new Intent(getApplicationContext(), CreateEntryActivity.class);
                intent.putExtra(EXTRA_ENTRY_ID, entry.getId());
                startActivity(intent);
            }
        }));

//        clearDatabase();

        mListViewModel = ViewModelProviders.of(this).get(ListViewModel.class);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), CreateEntryActivity.class);
                startActivity(intent);
            }
        });
    }

    private void clearDatabase() {
        Repository.getRepository().clear();
    }

    @Override
    protected void onStart() {
        super.onStart();

        LiveData<List<Entry>> liveData = mListViewModel.getData();
        liveData.observe(this, new Observer<List<Entry>>() {
            @Override
            public void onChanged(@Nullable List<Entry> entries) {
                mRecyclerAdapter.setEntries(entries);
                mRecyclerAdapter.notifyDataSetChanged();
            }
        });
    }
}
