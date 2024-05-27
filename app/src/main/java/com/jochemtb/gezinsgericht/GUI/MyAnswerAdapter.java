package com.jochemtb.gezinsgericht.GUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jochemtb.gezinsgericht.R;

import java.util.List;

public class MyAnswerAdapter extends RecyclerView.Adapter<MyAnswerAdapter.MyAnswerViewHolder> {

    private final List<String[]> answers;
    private final List<String[]> questions;

    public MyAnswerAdapter(List<String[]> answers, List<String[]> questions) {
        this.answers = answers;
        this.questions = questions;
    }

    @NonNull
    @Override
    public MyAnswerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new MyAnswerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAnswerViewHolder holder, int position) {
        String[] questionArray = questions.get(position);
        String[] answerArray = answers.get(position);
        holder.bind(questionArray, answerArray);
    }

    @Override
    public int getItemCount() {
        return answers.size();
    }

    public static class MyAnswerViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout container;

        public MyAnswerViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
        }

        public void bind(String[] questions, String[] answers) {
            container.removeAllViews();  // Clear previous views

            for (int i = 0; i < questions.length; i++) {
                View view = LayoutInflater.from(container.getContext()).inflate(R.layout.question_item, container, false);
                TextView questionView = view.findViewById(R.id.question_answered);
                TextView answerView = view.findViewById(R.id.answer_given);

                questionView.setText(questions[i]);
                answerView.setText(answers[i]);

                container.addView(view);
            }
        }
    }
}
