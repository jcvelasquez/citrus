package com.eure.citrus.ui.adapter;

import com.eure.citrus.R;
import com.eure.citrus.listener.OnRecyclerItemClickListener;
import com.eure.citrus.model.db.Task;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.realm.RealmResults;

/**
 * Created by katsuyagoto on 15/06/18.
 */
public class ListsTaskListAdapter extends RecyclerView.Adapter<ListsTaskListAdapter.ViewHolder> {

    private LayoutInflater mLayoutInflater;

    private Resources mResources;

    private RealmResults<Task> mTasks;

    private static boolean mShowGroupName = true;

    private static OnRecyclerItemClickListener mOnRecyclerItemClickListener;

    public ListsTaskListAdapter(Context context, RealmResults<Task> tasks,
            OnRecyclerItemClickListener onRecyclerItemClickListener, boolean showGroupName) {
        super();
        mResources = context.getResources();
        mLayoutInflater = LayoutInflater.from(context);
        mTasks = tasks;
        mOnRecyclerItemClickListener = onRecyclerItemClickListener;
        mShowGroupName = showGroupName;
    }

    public void setData(RealmResults<Task> tasks) {
        this.mTasks = tasks;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.item_lists_task_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Task task = mTasks.get(position);
        holder.taskNameText.setChecked(task.isCompleted());
        holder.taskNameText.setText(task.getName());
        changeTaskNameState(holder.itemView, holder.taskNameText, task.isCompleted(), mResources);
        holder.taskGroupText.setText(task.getGroupName());
    }

    public Task getItem(int position) {
        return mTasks.get(position);
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    public void changeTaskNameState(View view, AppCompatCheckedTextView taskNameText, boolean completed,
            Resources resources) {
        taskNameText.setChecked(completed);
        if (completed) {
            view.setBackgroundColor(resources.getColor(R.color.mt_gray5));
            taskNameText.setTextColor(resources.getColor(R.color.mt_gray6));
        } else {
            view.setBackgroundColor(resources.getColor(android.R.color.white));
            taskNameText.setTextColor(resources.getColor(R.color.mt_black));
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        AppCompatCheckedTextView taskNameText;

        AppCompatTextView taskGroupText;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            taskNameText = (AppCompatCheckedTextView) v.findViewById(R.id.lists_task_name);
            taskGroupText = (AppCompatTextView) v.findViewById(R.id.lists_task_group);
            if (!mShowGroupName) {
                taskGroupText.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View view) {
            int position = this.getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                mOnRecyclerItemClickListener.onClickRecyclerItem(view, position);
            }
        }
    }
}