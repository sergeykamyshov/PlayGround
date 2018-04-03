package ru.sergeykamyshov.emotiondiary;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.sergeykamyshov.emotiondiary.model.Event;

public class EventsRecyclerAdapter extends RecyclerView.Adapter<EventsRecyclerAdapter.ViewHolder> {

    // Контекст используется для создания разметки
    private Context mContext;
    // Список элементов, который нужно показывать
    private List<Event> mEvents;

    public EventsRecyclerAdapter(Context context, List<Event> events) {
        mContext = context;
        mEvents = events;
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
        String situation = mEvents.get(position).getSituation();
        holder.mSituation.setText(situation);
    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mSituation;

        public ViewHolder(View itemView) {
            super(itemView);
            mSituation = itemView.findViewById(R.id.situation);
        }
    }
}
