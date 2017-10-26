package ru.kamyshovcorp.weekplanner.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import ru.kamyshovcorp.weekplanner.R;
import ru.kamyshovcorp.weekplanner.activities.CardActivity;
import ru.kamyshovcorp.weekplanner.model.Card;
import ru.kamyshovcorp.weekplanner.model.Task;

import static ru.kamyshovcorp.weekplanner.activities.CardActivity.EXTRA_CARD_INDEX;

public class WeekRecyclerAdapter extends RecyclerView.Adapter<WeekRecyclerAdapter.ViewHolder> {

    private Context mContext;
    private List<Card> mDataSet;

    public WeekRecyclerAdapter(Context context, List<Card> dataSet) {
        mContext = context;
        mDataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Card card = mDataSet.get(position);
        holder.mCardTitleTextView.setText(card.getTitle());
        LinearLayout linearLayout = createTasksLayout(card);
        holder.mRecyclerItemLayout.addView(linearLayout);

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CardActivity.class);
                intent.putExtra(EXTRA_CARD_INDEX, position);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    @NonNull
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
        taskLayout.addView(taskCheckBox);

        TextView taskDescriptionTextView = new TextView(mContext);
        LinearLayout.LayoutParams taskDescriptionParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        taskDescriptionParams.gravity = Gravity.CENTER;
        taskDescriptionTextView.setLayoutParams(taskDescriptionParams);
        taskDescriptionTextView.setText(task.getDescription());
        taskLayout.addView(taskDescriptionTextView);

        linearLayout.addView(taskLayout);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView;
        public TextView mCardTitleTextView;
        public LinearLayout mRecyclerItemLayout;

        public ViewHolder(View view) {
            super(view);
            mCardView = view.findViewById(R.id.recycler_card_view);
            mCardTitleTextView = view.findViewById(R.id.card_title);
            mRecyclerItemLayout = view.findViewById(R.id.recycler_item_layout);
        }
    }
}
