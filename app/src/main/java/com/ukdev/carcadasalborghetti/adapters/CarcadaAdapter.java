package com.ukdev.carcadasalborghetti.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ukdev.carcadasalborghetti.R;
import com.ukdev.carcadasalborghetti.model.Carcada;

/**
 * Carcada adapter
 * Adapts a ListView to hold Carcadas
 * Created by Alan Camargo - May 2016
 */
public class CarcadaAdapter extends RecyclerView.Adapter<CarcadaAdapter.CarcadaHolder> {

    private Context context;
    private Carcada[] data;
    private OnItemClickListener onItemClickListener;

    public CarcadaAdapter(Context context, Carcada[] data, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.data = data;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public CarcadaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        final boolean attachToRoot = false;
        View view = inflater.inflate(R.layout.card_carcada, parent, attachToRoot);
        return new CarcadaHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(CarcadaHolder holder, int position) {
        Carcada carcada = data[position];
        holder.titleTextView.setText(carcada.getTitle());
        holder.lengthTextView.setText(carcada.getLength());
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onItemLongClick(int position);
    }

    /**
     * Holds all fields for the custom ListView item
     */
    static class CarcadaHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {

        TextView titleTextView;
        TextView lengthTextView;
        View.OnClickListener onClickListener;
        View.OnLongClickListener onLongClickListener;
        OnItemClickListener onItemClickListener;

        CarcadaHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            this.onItemClickListener = onItemClickListener;
            onClickListener = this;
            onLongClickListener = this;
            itemView.setOnClickListener(onClickListener);
            itemView.setOnLongClickListener(onLongClickListener);
            titleTextView = itemView.findViewById(R.id.titleRow);
            lengthTextView = itemView.findViewById(R.id.lengthRow);
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(getLayoutPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            onItemClickListener.onItemLongClick(getLayoutPosition());
            return true;
        }

    }

}
