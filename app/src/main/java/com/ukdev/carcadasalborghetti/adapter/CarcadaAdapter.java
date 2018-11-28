package com.ukdev.carcadasalborghetti.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ukdev.carcadasalborghetti.R;
import com.ukdev.carcadasalborghetti.model.Carcada;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Carcada adapter
 * Adapts a ListView to hold Carcadas
 * Created by Alan Camargo - May 2016
 */
public class CarcadaAdapter extends RecyclerView.Adapter<CarcadaAdapter.CarcadaHolder> {

    private Context context;
    private List<Carcada> data;
    private OnItemClickListener onItemClickListener;

    public CarcadaAdapter(Context context, List<Carcada> data, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.data = data;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public CarcadaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        final boolean attachToRoot = false;
        View view = inflater.inflate(R.layout.card_carcada, parent, attachToRoot);
        return new CarcadaHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CarcadaHolder holder, int position) {
        Carcada carcada = data.get(position);
        String title = String.format(Locale.getDefault(),
                "%1$d. %2$s",
                position + 1,
                carcada.getTitle());
        holder.titleTextView.setText(title);
        holder.lengthTextView.setText(carcada.getLength());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void filter(List<Carcada> carcadas, String query) {
        final String lowerCaseQuery = query.toLowerCase();

        final List<Carcada> filteredList = new ArrayList<>();
        for (Carcada carcada : carcadas) {
            final String text = carcada.getTitle().toLowerCase();
            if (text.contains(lowerCaseQuery))
                filteredList.add(carcada);
        }

        data = filteredList;
        notifyDataSetChanged();
    }

    public List<Carcada> getData() {
        return data;
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

        TextView positionTextView;
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
            positionTextView = itemView.findViewById(R.id.positionRow);
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
