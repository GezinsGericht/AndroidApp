package com.jochemtb.gezinsgericht.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jochemtb.gezinsgericht.GUI.ResultsActivity;
import com.jochemtb.gezinsgericht.R;
import com.jochemtb.gezinsgericht.domain.HistoryItem;
import com.jochemtb.gezinsgericht.domain.Session;

import java.nio.channels.SeekableByteChannel;
import java.util.ArrayList;

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.HistoryListViewHolder> {

    private final String LOG_TAG = "HistoryListAdapter";
    private LayoutInflater inflater;
    private ArrayList<HistoryItem> historyList;

    //Adapter Constructor
    public HistoryListAdapter(Context context, ArrayList<HistoryItem> historyList) {
        inflater = LayoutInflater.from(context);
        this.historyList = historyList; //Set Sessions
        Log.i(LOG_TAG, "HistoryListAdapter");
    }

    public void setHistoryList(ArrayList<HistoryItem> historyList) {
        this.historyList = historyList;
    }

    //Maak itemview aan
    @NonNull
    @Override
    public HistoryListAdapter.HistoryListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_recyclerview_history, parent, false);
        Log.i(LOG_TAG, "onCreateViewHolder");
        return new HistoryListViewHolder(itemView);
    }

    //Zet data in itemview
    @Override
    public void onBindViewHolder(@NonNull HistoryListAdapter.HistoryListViewHolder holder, int position) {

        HistoryItem item = historyList.get(position);
        holder.tvHistoryDate.setText(item.getDate());
        holder.tvHistoryProfName.setText(item.getProfessional_Name());

        Log.i(LOG_TAG, "onBindViewHolder");
    }

    //Return aantal sessions
    @Override
    public int getItemCount() {
        if (historyList != null) {
            return historyList.size();
        } else {
            return 0;
        }
    }

    //Koppeld itemview aan recyclerview
    public class HistoryListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvHistoryDate;
        public TextView tvHistoryProfName;

        public HistoryListViewHolder(@NonNull View itemView) {
            super(itemView);

            tvHistoryDate = itemView.findViewById(R.id.TV_history_date);
            tvHistoryProfName = itemView.findViewById(R.id.TV_history_profName);

            itemView.setOnClickListener(this);
            Log.i(LOG_TAG, "HistoryListViewHolder");
        }

        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                HistoryItem clickedSession = historyList.get(position);
                Intent intent = new Intent(v.getContext(), ResultsActivity.class);
                intent.putExtra("session", clickedSession.getSessionId());
                v.getContext().startActivity(intent);
            }
        }
    }
}


