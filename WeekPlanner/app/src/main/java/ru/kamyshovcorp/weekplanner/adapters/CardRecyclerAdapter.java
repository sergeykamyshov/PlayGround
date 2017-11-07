package ru.kamyshovcorp.weekplanner.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.List;

import ru.kamyshovcorp.weekplanner.R;
import ru.kamyshovcorp.weekplanner.model.Task;

public class CardRecyclerAdapter extends RecyclerView.Adapter<CardRecyclerAdapter.ViewHolder> {

    private Context mContext;
    private List<Task> mTasks;

    public CardRecyclerAdapter(Context context, List<Task> tasks) {
        mContext = context;
        mTasks = tasks;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new CardRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Task task = mTasks.get(position);
        holder.mIsDone.setChecked(task.isDone());
        holder.mIsDone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mTasks.get(position).setDone(isChecked);
            }
        });
        holder.mTaskDesc.setText(task.getDescription());
        holder.mTaskDesc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mTasks.get(position).setDescription(s.toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox mIsDone;
        EditText mTaskDesc;

        public ViewHolder(View itemView) {
            super(itemView);
            mIsDone = itemView.findViewById(R.id.done_task_check_box);
            mTaskDesc = itemView.findViewById(R.id.description_task_edit_text);
        }
    }

    public void setTasks(List<Task> tasks) {
        mTasks = tasks;
    }
}
