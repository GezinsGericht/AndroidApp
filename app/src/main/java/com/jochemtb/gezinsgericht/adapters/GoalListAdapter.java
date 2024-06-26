package com.jochemtb.gezinsgericht.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.jochemtb.gezinsgericht.API.Goal.GoalResponse;
import com.jochemtb.gezinsgericht.R;
import com.jochemtb.gezinsgericht.domain.Goal;

import java.util.List;

public class GoalListAdapter extends RecyclerView.Adapter<GoalListAdapter.GoalViewHolder> {

    private List<Goal> goalList;

    public GoalListAdapter(List<Goal> goalList) {
        this.goalList = goalList;
    }

    @NonNull
    @Override
    public GoalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recyclerview_goals, parent, false);
        return new GoalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GoalViewHolder holder, int position) {
        Goal goal = goalList.get(position);
        holder.goal.setText(goal.getGoal());

        // Clear any previous views to avoid duplication
        holder.actionsContainer.removeAllViews();

        LayoutInflater inflater = LayoutInflater.from(holder.itemView.getContext());

        for(GoalResponse response: goal.getActions()){
            String[] actions = response.getActions().split(",");

            for(int i = 0; i<actions.length; i++){
                TextView actionTextView = new TextView(holder.itemView.getContext());
                actionTextView.setText(addBulletPoint(actions[i]));
                actionTextView.setTextSize(20);

                // Set font family
                actionTextView.setTypeface(ResourcesCompat.getFont(holder.itemView.getContext(), R.font.hanken_grotesk_bold));

                // Set margins to achieve horizontal bias effect
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                layoutParams.setMargins(120, 0, 0, 0); // Adjust the left margin as needed
                actionTextView.setLayoutParams(layoutParams);

                holder.actionsContainer.addView(actionTextView);
            }
        }
    }

    @Override
    public int getItemCount() {
        return goalList.size();
    }

    private String addBulletPoint(String text) {
        return "\u2022 " + text; // Unicode for bullet point
    }

    public class GoalViewHolder extends RecyclerView.ViewHolder {
        public TextView goal;
        public LinearLayout actionsContainer;

        public GoalViewHolder(View itemView) {
            super(itemView);
            goal = itemView.findViewById(R.id.goal);
            actionsContainer = itemView.findViewById(R.id.actions_container);
        }
    }
}