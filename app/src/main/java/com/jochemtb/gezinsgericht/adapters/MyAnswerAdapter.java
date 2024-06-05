package com.jochemtb.gezinsgericht.adapters;

import android.util.Log;
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

    private final String LOG_TAG = "MyAnswerAdapter";
    private final List<String> answers;
    private final List<String> questions;

    // Constructor for the adapter, initializing the lists of answers and questions
    public MyAnswerAdapter(List<String> answers, List<String> questions) {
        this.answers = answers;
        this.questions = questions;
        Log.i(LOG_TAG, "MyAnswerAdapter");
    }

    @NonNull
    @Override
    public MyAnswerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { // Inflates the view for each item in the RecyclerView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_item, parent, false);
        Log.i(LOG_TAG, "onCreateViewHolder");
        return new MyAnswerViewHolder(view); // Returns a new ViewHolder instance
    }

    @Override
    public void onBindViewHolder(@NonNull MyAnswerViewHolder holder, int position) { // Binds data to the ViewHolder for each item
        String question = questions.get(position); // Get questions for the current position
        String answer = answers.get(position); // Get answers for the current position
        holder.tvQuestionAnswered.setText(question); // Set the question text to the TextView
        holder.tvAnswerGiven.setText(answer); // Set the answer text to the TextView
    }

    @Override
    public int getItemCount() { // Returns the total number of items in the list
        return answers.size();
    }

    public static class MyAnswerViewHolder extends RecyclerView.ViewHolder { // ViewHolder class to represent each item in the RecyclerView

        public TextView tvQuestionAnswered;
        public TextView tvAnswerGiven;

        public MyAnswerViewHolder(@NonNull View itemView) {
            super(itemView);

            tvQuestionAnswered = itemView.findViewById(R.id.tv_question_answered);
            tvAnswerGiven = itemView.findViewById(R.id.b_answer_given);
        }


    }
}
