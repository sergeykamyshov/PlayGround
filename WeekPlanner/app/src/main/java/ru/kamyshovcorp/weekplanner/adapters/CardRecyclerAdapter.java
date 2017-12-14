package ru.kamyshovcorp.weekplanner.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmList;
import ru.kamyshovcorp.weekplanner.R;
import ru.kamyshovcorp.weekplanner.activities.CardActivity;
import ru.kamyshovcorp.weekplanner.activities.TaskActivity;
import ru.kamyshovcorp.weekplanner.model.Task;

import static ru.kamyshovcorp.weekplanner.activities.TaskActivity.EXTRA_CARD_ID;
import static ru.kamyshovcorp.weekplanner.activities.TaskActivity.EXTRA_TASK_ID;

public class CardRecyclerAdapter extends RecyclerView.Adapter<CardRecyclerAdapter.ViewHolder> {

    private Context mContext;
    private RealmList<Task> mTasks;

    public CardRecyclerAdapter(Context context, RealmList<Task> tasks) {
        mContext = context;
        mTasks = tasks;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_recycler_item, parent, false);
        return new CardRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Task task = mTasks.get(position);
//        holder.mIsDone.setChecked(task.isDone());
//        holder.mTaskTitle.setText(task.getTask());
        holder.bind(task);
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox mIsDone;
        TextView mTaskTitle;
        ImageView mMoreImg;

        public ViewHolder(View itemView) {
            super(itemView);

            mIsDone = itemView.findViewById(R.id.cb_is_done);
//            mIsDone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
//                    Realm realm = Realm.getDefaultInstance();
//                    realm.executeTransaction(new Realm.Transaction() {
//                        @Override
//                        public void execute(Realm realm) {
//                            mTasks.get(getAdapterPosition()).setDone(isChecked);
//                        }
//                    });
//                }
//            });

            mTaskTitle = itemView.findViewById(R.id.txt_task_title);
            mMoreImg = itemView.findViewById(R.id.img_more);

//            itemView.setOnClickListener(new OnTaskItemClickListener());
        }

        public void bind(Task task) {
            mIsDone.setChecked(task.isDone());
            mIsDone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                    Realm realm = Realm.getDefaultInstance();
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            mTasks.get(getAdapterPosition()).setDone(isChecked);
                        }
                    });
                }
            });
            mTaskTitle.setOnClickListener(new OnTaskItemClickListener());
            // TODO: impl it here
//            itemView.setOnClickListener();
        }

        /**
         * Реализация обработчика нажатия элемента в списке задач
         */
        class OnTaskItemClickListener implements View.OnClickListener {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, TaskActivity.class);
                intent.putExtra(EXTRA_CARD_ID, "");
                intent.putExtra(EXTRA_TASK_ID, "");
                mContext.startActivity(intent);
//                Toast.makeText(mContext, "Position: " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
