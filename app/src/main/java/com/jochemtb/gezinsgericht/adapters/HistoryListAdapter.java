package com.jochemtb.gezinsgericht.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jochemtb.gezinsgericht.R;

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.HistoryListViewHolder> {

    private final String LOG_TAG = "HistoryListAdapter";
    private LayoutInflater inflater;

    public HistoryListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        Log.i(LOG_TAG, "HistoryListAdapter");
    }

    @NonNull
    @Override
    public HistoryListAdapter.HistoryListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_recyclerview_history, parent, false);
        Log.i(LOG_TAG, "onCreateViewHolder");
        return new HistoryListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryListAdapter.HistoryListViewHolder holder, int position) {


    Log.i(LOG_TAG, "onBindViewHolder");
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class HistoryListViewHolder extends RecyclerView.ViewHolder {
        public HistoryListViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
