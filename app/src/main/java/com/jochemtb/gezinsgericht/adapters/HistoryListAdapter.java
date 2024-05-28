package com.jochemtb.gezinsgericht.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jochemtb.gezinsgericht.R;

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.HistoryListViewHolder> {

    //TEMP Set onClickListener
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    private OnItemClickListener listener;
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

    //TEMP Set onClickListener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class HistoryListViewHolder extends RecyclerView.ViewHolder {
        public HistoryListViewHolder(@NonNull View itemView) {
            super(itemView);

            //TEMP Set onClickListener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
            Log.i(LOG_TAG, "HistoryListViewHolder");
        }


    }
}


