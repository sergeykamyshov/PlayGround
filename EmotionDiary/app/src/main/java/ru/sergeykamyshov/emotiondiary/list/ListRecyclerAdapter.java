package ru.sergeykamyshov.emotiondiary.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.sergeykamyshov.emotiondiary.R;
import ru.sergeykamyshov.emotiondiary.database.Entry;

public class ListRecyclerAdapter extends RecyclerView.Adapter<ListRecyclerAdapter.ViewHolder> {

    // Контекст используется для создания разметки
    private Context mContext;
    // Список элементов, который нужно показывать
    private List<Entry> mEntries = new ArrayList<>();

    public ListRecyclerAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Создаем View из разметки для одного элемента RecyclerView
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Заполняем/перезаполняем ViewHolder данными из списка
        Entry entry = mEntries.get(position);
        holder.mSituation.setText(entry.getSituation());
        holder.mThoughts.setText(entry.getThoughts());
    }

    @Override
    public int getItemCount() {
        return mEntries.size();
    }

    public void setEntries(List<Entry> entries) {
        mEntries = entries;
    }

    public Entry getEntryByPosition(int position) {
        return mEntries.get(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mSituation;
        TextView mThoughts;

        public ViewHolder(View itemView) {
            super(itemView);
            mSituation = itemView.findViewById(R.id.situation);
            mThoughts = itemView.findViewById(R.id.thoughts);
        }
    }
}
