package com.jochemtb.gezinsgericht.adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.jochemtb.gezinsgericht.R;
import com.jochemtb.gezinsgericht.GUI.QuizActivity;
import com.jochemtb.gezinsgericht.domain.QuizManager;

public class DialogAdapter {
    private Dialog dialog;
    private ScrollView scrollView;
    private QuizManager quizManager;
    private LinearLayout unansweredQuestionsLayout;
    private Context context;

    public DialogAdapter(Context context, Dialog dialog, ScrollView scrollView, LinearLayout unansweredQuestionsLayout) {
        this.context = context;
        this.dialog = dialog;
        this.scrollView = scrollView;
        this.quizManager = QuizManager.getInstance();
        this.unansweredQuestionsLayout = unansweredQuestionsLayout;
    }

    public void showQuestionOverview() {
        // Create a GridLayout with proper context
        GridLayout gridLayout = new GridLayout(context);
        gridLayout.setColumnCount(3);  // Set the number of columns
        int totalQuestions = quizManager.getQuestionIds().size();
        int rowCount = (int) Math.ceil(totalQuestions / 3.0); // Calculate the number of rows
        gridLayout.setRowCount(rowCount);

        for (int index : quizManager.getQuestionIds()) {
            Button questionButton = new Button(context);
            questionButton.setText(String.format("Vraag %d", index + 1));
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(
                    GridLayout.spec(index / 3),
                    GridLayout.spec(index % 3, 1f));
            params.width = 0; // Ensure the button width is 0 so it can stretch evenly in the grid
            questionButton.setLayoutParams(params);
            questionButton.setOnClickListener(v -> {
                ((QuizActivity) context).navigateToQuestion(index);
                unansweredQuestionsLayout.setVisibility(View.GONE);
                dialog.dismiss();
            });
            gridLayout.addView(questionButton);
        }

        // Wrap GridLayout in a LinearLayout before adding to ScrollView
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(gridLayout);

        // Clear existing children before adding new layout
        scrollView.removeAllViews();
        scrollView.addView(linearLayout);

        scrollView.fullScroll(View.FOCUS_DOWN);
        dialog.show();
    }
}
