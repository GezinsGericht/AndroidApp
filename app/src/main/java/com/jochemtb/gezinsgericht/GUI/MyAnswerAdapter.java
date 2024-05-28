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

    // Constructor for the adapter, initializing the lists of answers and questions
    public MyAnswerAdapter(List<String[]> answers, List<String[]> questions) {
        this.answers = answers;
        this.questions = questions;
    }

    @NonNull
    @Override

    public MyAnswerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { // Inflates the view for each item in the RecyclerView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new MyAnswerViewHolder(view); // Returns a new ViewHolder instance
    }

    @Override

    public void onBindViewHolder(@NonNull MyAnswerViewHolder holder, int position) { // Binds data to the ViewHolder for each item
        String[] questionArray = questions.get(position); // Get questions for the current position
        String[] answerArray = answers.get(position); // Get answers for the current position
        holder.bind(questionArray, answerArray); // Bind the questions and answers to the ViewHolder
    }

    @Override

    public int getItemCount() { // Returns the total number of items in the list
        return answers.size();
    }


    public static class MyAnswerViewHolder extends RecyclerView.ViewHolder { // ViewHolder class to represent each item in the RecyclerView
        private final LinearLayout container; // Container to hold question and answer views

        public MyAnswerViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);  // Correctly reference the container LinearLayout
        }


        public void bind(String[] questions, String[] answers) { // Binds questions and answers to the views within the ViewHolder
            container.removeAllViews();  // Clear previous views to avoid duplication


            for (int i = 0; i < questions.length; i++) {  // Loop through each question and answer pair

                View view = LayoutInflater.from(container.getContext()).inflate(R.layout.question_item, container, false); // Inflate the question and answer item layout
                TextView questionView = view.findViewById(R.id.question_answered);
                TextView answerView = view.findViewById(R.id.answer_given);

                questionView.setText(questions[i]);
                answerView.setText(answers[i]);

                container.addView(view); // Add the inflated view to the container
            }
        }

    }
}
