package ru.kamyshovcorp.weekplanner.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import ru.kamyshovcorp.weekplanner.R;
import ru.kamyshovcorp.weekplanner.model.Category;
import ru.kamyshovcorp.weekplanner.model.Task;

public class WeekRecyclerAdapter extends RecyclerView.Adapter<WeekRecyclerAdapter.ViewHolder> {

    private Context mContext;
    private List<Category> mDataSet;

    public WeekRecyclerAdapter(Context context, List<Category> dataSet) {
        mContext = context;
        mDataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Category category = mDataSet.get(position);
        holder.mCategoryTitleTextView.setText(category.getTitle());
        LinearLayout linearLayout = createTasksLayout(category);
        holder.mItemLayout.addView(linearLayout);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    @NonNull
    private LinearLayout createTasksLayout(Category category) {
        LinearLayout linearLayout = new LinearLayout(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int tasksLayoutTopMargin = (int) mContext.getResources().getDimension(R.dimen.tasks_layout_top_margin);
        params.setMargins(0, tasksLayoutTopMargin, 0, 0);
        linearLayout.setLayoutParams(params);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        for (Task task : category.getTasks()) {
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
        taskCheckBox.setChecked(task.isDone());
        taskLayout.addView(taskCheckBox);

        TextView taskDescriptionTextView = new TextView(mContext);
        taskDescriptionTextView.setText(task.getDescription());
        taskLayout.addView(taskDescriptionTextView);

        linearLayout.addView(taskLayout);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mCategoryTitleTextView;
        public LinearLayout mItemLayout;

        public ViewHolder(View view) {
            super(view);
            mCategoryTitleTextView = view.findViewById(R.id.category_title);
            mItemLayout = view.findViewById(R.id.item_layout);
        }
    }
}
