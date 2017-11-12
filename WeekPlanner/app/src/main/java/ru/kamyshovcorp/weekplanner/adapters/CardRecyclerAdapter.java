package ru.kamyshovcorp.weekplanner.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;

import io.realm.Realm;
import io.realm.RealmList;
import ru.kamyshovcorp.weekplanner.R;
import ru.kamyshovcorp.weekplanner.model.Task;

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
        holder.mIsDone.setChecked(task.isDone());
        holder.mTaskDesc.setText(task.getTask());
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox mIsDone;
        EditText mTaskDesc;
        ImageView mDeleteTask;

        public ViewHolder(View itemView) {
            super(itemView);

            mIsDone = itemView.findViewById(R.id.done_task_check_box);
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

            mTaskDesc = itemView.findViewById(R.id.description_task_edit_text);
            // Обновляем данные после изменения описания задачи
            mTaskDesc.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    final String changedText = s.toString();
                    Realm realm = Realm.getDefaultInstance();
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            Task task = mTasks.get(getAdapterPosition());
                            task.setTask(changedText);
                        }
                    });
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
            // Делаем описание задачи доступным для редактирования при касании
            mTaskDesc.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    mTaskDesc.setFocusable(true);
                    mTaskDesc.setFocusableInTouchMode(true);
                    return false;
                }
            });
            mDeleteTask = itemView.findViewById(R.id.img_more);
            // Показываем/скрываем View для удаления задачи в зависимости от фокуса
            mTaskDesc.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        mDeleteTask.setVisibility(View.VISIBLE);
                    } else {
                        mDeleteTask.setVisibility(View.INVISIBLE);
                    }
                }
            });

            // Удаляем задачу
            mDeleteTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Realm realm = Realm.getDefaultInstance();
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            int position = getAdapterPosition();
                            mTasks.remove(position);
                            notifyItemRemoved(position);
                        }
                    });
                }
            });
        }
    }
}
