package com.ukdev.carcadasalborghetti.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.ukdev.carcadasalborghetti.R;
import com.ukdev.carcadasalborghetti.model.Carcada;

/**
 * Carcada adapter
 * Adapts a ListView to hold Carcadas
 * Created by Alan Camargo - May 2016
 */
public class CarcadaAdapter extends ArrayAdapter<Carcada> {

    private Context context;
    private int layoutResourceId;
    private Carcada[] data;

    public CarcadaAdapter(Context context, int layoutResourceId,
                          Carcada[] data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CarcadaHolder holder = new CarcadaHolder();
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder.title = (TextView)row.findViewById(R.id.titleRow);
            holder.length = (TextView)row.findViewById(R.id.lengthRow);
            row.setTag(holder);
        }
        else
            holder = (CarcadaHolder)row.getTag();
        Carcada carcada = data[position];
        holder.title.setText(carcada.getTitle());
        holder.length.setText(carcada.getLength());
        return row;
    }

    /**
     * Holds all fields for the custom ListView item
     */
    private static class CarcadaHolder {
        TextView title;
        TextView length;
    }

}
