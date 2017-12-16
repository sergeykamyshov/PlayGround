package ru.kamyshovcorp.weekplanner.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import ru.kamyshovcorp.weekplanner.R;
import ru.kamyshovcorp.weekplanner.activities.CardActivity;
import ru.kamyshovcorp.weekplanner.model.Card;
import ru.kamyshovcorp.weekplanner.model.Task;

import static ru.kamyshovcorp.weekplanner.activities.CardActivity.EXTRA_CARD_ID;

public class WeekRecyclerAdapter extends RecyclerView.Adapter<WeekRecyclerAdapter.ViewHolder> {

    private Context mContext;
    private RealmResults<Card> mCards;

    public WeekRecyclerAdapter(Context context, RealmResults<Card> cards) {
        mContext = context;
        mCards = cards;

        mCards.addChangeListener(new RealmChangeListener<RealmResults<Card>>() {
            @Override
            public void onChange(RealmResults<Card> cards) {
                notifyDataSetChanged();
            }
        });
    }

    public void setCards(RealmResults<Card> cards) {
        mCards = cards;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.week_recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Card card = mCards.get(position);
        String cardTitle = card != null ? card.getTitle() : "";
        if (TextUtils.isEmpty(cardTitle)) {
            holder.mCardTitle.setVisibility(View.GONE);
        } else {
            holder.mCardTitle.setText(cardTitle);
            holder.mCardTitle.setVisibility(View.VISIBLE);
        }

        // Очищаем список задач для карточки перед заполнением
        holder.mRecyclerItemLayout.removeAllViews();
        LinearLayout linearLayout = createTasksLayout(card);
        holder.mRecyclerItemLayout.addView(linearLayout);
    }

    @Override
    public int getItemCount() {
        return mCards.size();
    }

    private LinearLayout createTasksLayout(Card card) {
        LinearLayout linearLayout = new LinearLayout(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int tasksLayoutTopMargin = (int) mContext.getResources().getDimension(R.dimen.tasks_layout_top_margin);
        params.setMargins(0, tasksLayoutTopMargin, 0, 0);
        linearLayout.setLayoutParams(params);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        for (Task task : card.getTasks()) {
            createTaskItemLayout(linearLayout, task);
        }
        return linearLayout;
    }

    private void createTaskItemLayout(LinearLayout linearLayout, Task task) {
        LinearLayout taskLayout = new LinearLayout(mContext);
        LinearLayout.LayoutParams taskParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        taskLayout.setLayoutParams(taskParams);
        taskLayout.setOrientation(LinearLayout.HORIZONTAL);

        CheckBox taskCheckBox = new CheckBox(mContext);
        taskCheckBox.setGravity(Gravity.TOP);
        taskCheckBox.setChecked(task.isDone());
        taskCheckBox.setClickable(false);
        taskCheckBox.setBackgroundResource(android.R.color.transparent);
        taskLayout.addView(taskCheckBox);

        TextView taskDescriptionTextView = new TextView(mContext);
        LinearLayout.LayoutParams taskDescriptionParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        taskDescriptionParams.gravity = Gravity.CENTER;
        taskDescriptionTextView.setLayoutParams(taskDescriptionParams);
        taskDescriptionTextView.setText(task.getTitle());
        taskLayout.addView(taskDescriptionTextView);

        linearLayout.addView(taskLayout);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView;
        public TextView mCardTitle;
        public LinearLayout mRecyclerItemLayout;

        public ViewHolder(View view) {
            super(view);
            mCardView = view.findViewById(R.id.recycler_card_view);
            mCardTitle = view.findViewById(R.id.txt_card_title);
            mRecyclerItemLayout = view.findViewById(R.id.list_tasks);

            mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, CardActivity.class);
                    intent.putExtra(EXTRA_CARD_ID, mCards.get(getAdapterPosition()).getId());
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
