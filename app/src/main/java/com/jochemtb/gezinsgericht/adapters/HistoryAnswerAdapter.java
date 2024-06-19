// HistoryAnswerAdapter.java
package com.jochemtb.gezinsgericht.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.jochemtb.gezinsgericht.R;
import com.jochemtb.gezinsgericht.domain.GroupedHistoryAnswer;
import com.jochemtb.gezinsgericht.API.HistoryAnswers.HistoryAnswerResponse;

import java.util.List;

public class HistoryAnswerAdapter extends RecyclerView.Adapter<HistoryAnswerAdapter.ViewHolder> {

    private List<GroupedHistoryAnswer> groupedHistoryAnswers;

    public HistoryAnswerAdapter(List<GroupedHistoryAnswer> groupedHistoryAnswers) {
        this.groupedHistoryAnswers = groupedHistoryAnswers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_group_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GroupedHistoryAnswer group = groupedHistoryAnswers.get(position);
        holder.habitatName.setText(group.getHabitatName());

        holder.questionsContainer.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(holder.itemView.getContext());

        for (HistoryAnswerResponse response : group.getHistoryAnswers()) {
            String[] questions = response.getQuestions().split(",");
            String[] answers = response.getAnswers().split(",");

            for (int i = 0; i < questions.length; i++) {
                View questionItemView = inflater.inflate(R.layout.question_item, holder.questionsContainer, false);
                TextView questionTextView = questionItemView.findViewById(R.id.tv_question_answered);
                TextView answerTextView = questionItemView.findViewById(R.id.b_answer_given);

                questionTextView.setText(questions[i].trim());
                answerTextView.setText(answers.length > i ? answers[i].trim() : "");

                holder.questionsContainer.addView(questionItemView);
            }
        }
    }

    @Override
    public int getItemCount() {
        return groupedHistoryAnswers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView habitatName;
        public LinearLayout questionsContainer;

        public ViewHolder(View itemView) {
            super(itemView);
            habitatName = itemView.findViewById(R.id.habitat_name);
            questionsContainer = itemView.findViewById(R.id.questions_container);
        }
    }
}
