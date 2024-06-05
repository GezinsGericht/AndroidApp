package com.jochemtb.gezinsgericht.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jochemtb.gezinsgericht.R;
import com.jochemtb.gezinsgericht.domain.Goal;

import java.util.ArrayList;
import java.util.List;

public class GoalListAdapter extends RecyclerView.Adapter<GoalListAdapter.GoalViewHolder> {

    private List<Goal> goalList;

    public GoalListAdapter(List<Goal> goalList) {
        this.goalList = goalList;
    }

    @NonNull
    @Override
    public GoalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_goals, parent, false);
        return new GoalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GoalViewHolder holder, int position) {
        Goal goal = goalList.get(position);
        holder.goal.setText(goal.getGoal());
        ArrayList<String> actions = goal.getActions();
        holder.action1.setText(actions.get(0));
        holder.action2.setText(actions.get(1));
        holder.action3.setText(actions.get(2));
        holder.action4.setText(actions.get(3));
        holder.action5.setText(actions.get(4));
    }

    @Override
    public int getItemCount() {
        return goalList.size();
    }

    public static class GoalViewHolder extends RecyclerView.ViewHolder {
        TextView goal, action1, action2, action3, action4, action5;

        public GoalViewHolder(@NonNull View itemView) {
            super(itemView);
            goal = itemView.findViewById(R.id.goal);
            action1 = itemView.findViewById(R.id.action1);
            action2 = itemView.findViewById(R.id.action2);
            action3 = itemView.findViewById(R.id.action3);
            action4 = itemView.findViewById(R.id.action4);
            action5 = itemView.findViewById(R.id.action5);
        }
    }
}
