package com.jochemtb.gezinsgericht.GUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jochemtb.gezinsgericht.R;

import java.util.List;

public class MyAnswerAdapter extends RecyclerView.Adapter<MyAnswerAdapter.MyAnswerViewHolder> {

    private final List<String[]> answers;

    public MyAnswerAdapter(List<String[]> answers) {
        this.answers = answers;
    }

    @NonNull
    @Override
    public MyAnswerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_item, parent, false);
        return new MyAnswerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAnswerViewHolder holder, int position) {
        String[] answer = answers.get(position);
        holder.questionAnswered.setText(answer[0]);
        holder.answerGiven.setText(answer[1]);
    }

    @Override
    public int getItemCount() {
        return answers.size();
    }

    public static class MyAnswerViewHolder extends RecyclerView.ViewHolder {
        TextView questionAnswered;
        TextView answerGiven;

        public MyAnswerViewHolder(@NonNull View itemView) {
            super(itemView);
            questionAnswered = itemView.findViewById(R.id.question_answered);
            answerGiven = itemView.findViewById(R.id.answer_given);
        }
    }
}
