package br.com.osmael.terremoto.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.osmael.terremoto.R;
import br.com.osmael.terremoto.entity.Terremoto;

/**
 * Created by root on 08/03/17.
 */

public class TerremotoAdapter extends ArrayAdapter<Terremoto> {

    private Context context;

    public TerremotoAdapter(Context context, List<Terremoto> terremotos) {
        super(context, 0, terremotos);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Checa se há um item view de lista existente (chamado convertView) que podemos re-usar,
        // ao contrário, se convertView for null, inflaremos uma novo item layout de lista.
        View rootView = convertView;
        if (rootView == null) {
            rootView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Terremoto terremoto = getItem(position);

        TextView magnitude = (TextView) rootView.findViewById(R.id.magnitude);
        magnitude.setText(String.valueOf(terremoto.getMagnitude()));

        TextView localizacao = (TextView) rootView.findViewById(R.id.localizacao);
        localizacao.setText(terremoto.getLocalizacao());

        TextView tempo = (TextView) rootView.findViewById(R.id.tempo);
        tempo.setText( String.valueOf(terremoto.getTempoEmMilisegundos()));

        return rootView;
    }

}