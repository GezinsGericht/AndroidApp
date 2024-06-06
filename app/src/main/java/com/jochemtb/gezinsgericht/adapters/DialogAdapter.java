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
    private Context context;
    private Dialog dialog;
    private ScrollView scrollView;
    private QuizManager quizManager;
    private LinearLayout unansweredQuestionsLayout;

    public DialogAdapter(Context context, Dialog dialog, ScrollView scrollView, LinearLayout unansweredQuestionsLayout) {
        this.context = context;
        this.dialog = dialog;
        this.scrollView = scrollView;
        this.quizManager = QuizManager.getInstance();
        this.unansweredQuestionsLayout = unansweredQuestionsLayout;
    }

    public void showQuestionOverview() {
        GridLayout gridLayout = new GridLayout(context);
        gridLayout.setColumnCount(10);
        gridLayout.setRowCount(10);

        for (int index : quizManager.getQuestionIds()) {
            Button questionButton = new Button(context);
            questionButton.setText(String.format("Vraag %d", index + 1));
            questionButton.setLayoutParams(new GridLayout.LayoutParams(
                    GridLayout.spec(index / 3),
                    GridLayout.spec(index % 3, 1f)));
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
