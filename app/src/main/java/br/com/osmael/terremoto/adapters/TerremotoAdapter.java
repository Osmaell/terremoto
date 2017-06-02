package br.com.osmael.terremoto.adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.com.osmael.terremoto.R;
import br.com.osmael.terremoto.entity.Terremoto;

/**
 * Created by root on 08/03/17.
 */
public class TerremotoAdapter extends ArrayAdapter<Terremoto> {

    public TerremotoAdapter(Context context, List<Terremoto> terremotos) {
        super(context, 0, terremotos);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        // Checa se há um item view de lista existente (chamado convertView) que podemos re-usar,
        // ao contrário, se convertView for null, inflaremos uma novo item layout de lista.
        View rootView = convertView;
        if (rootView == null) {
            rootView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Terremoto terremoto = getItem(position);

        TextView magnitude = (TextView) rootView.findViewById(R.id.magnitude);
        magnitude.setText(formatMagnitude(terremoto.getMagnitude()));

        String localizacaoOriginal = terremoto.getLocalizacao();

        String localDeslocamento;
        String localPrimario;

        // verifica se a localização tem o token "of"
        // caso tenha cria um array com duas posições
        // o primeiro contendo uma String com o conteúdo
        // antes do conjunto de caracteres "of" e outra
        // com o conteúdo depois respectivo conjunto
        if (localizacaoOriginal.contains("of")) {

            String[] locais = localizacaoOriginal.split("of");

            localDeslocamento = locais[0];
            localPrimario = locais[1];

        } else {
            localDeslocamento = "Perto de";
            localPrimario = localizacaoOriginal;
        }

        TextView localizacao = (TextView) rootView.findViewById(R.id.localizacao_primaria);
        localizacao.setText(localPrimario);

        TextView local = (TextView) rootView.findViewById(R.id.local_de_deslocamento);
        local.setText(localDeslocamento);

        TextView tempo = (TextView) rootView.findViewById(R.id.tempo);
        tempo.setText( formatData(terremoto.getTempoEmMilisegundos()));

        // Busca o fundo do TextView, que é um GradientDrawable
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitude.getBackground();

        // Obtendo a cor de fundo apropriada baseada na magnitude do terremoto atual
        int color = obterCorMagnitude(terremoto.getMagnitude());

        // Define a cor de fundo apropriada no círculo de magnitude.
        magnitudeCircle.setColor(color);

        return rootView;
    }

    private static String formatMagnitude(double magnitude) {
        DecimalFormat format = new DecimalFormat("0.0");
        return format.format(magnitude);
    }

    private static String formatData(Long tempoEmMilisegundos) {

        // O construtor da classe Date recebe um valor long
        // que é o tempo em milí-segundos e converte para um
        // objeto do próprio tipo
        Date date = new Date(tempoEmMilisegundos);

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        return format.format(date);
    }

    private static String formatHora(Date data) {
        SimpleDateFormat format = new SimpleDateFormat("h:mm a");
        return format.format(data);
    }

    private int obterCorMagnitude(double magnitude) {

        int magnitudeRecursoCor;

        // responsável por obter um valor inteiro arredondado
        // da magnitude
        int magnitudeFloor = (int) Math.floor(magnitude);

        switch (magnitudeFloor) {

            case 0:
            case 1:
                magnitudeRecursoCor = R.color.magnitude1;
                break;
            case 2:
                magnitudeRecursoCor = R.color.magnitude2;
                break;
            case 3:
                magnitudeRecursoCor = R.color.magnitude3;
                break;
            case 4:
                magnitudeRecursoCor = R.color.magnitude4;
                break;
            case 5:
                magnitudeRecursoCor = R.color.magnitude5;
                break;
            case 6:
                magnitudeRecursoCor = R.color.magnitude6;
                break;
            case 7:
                magnitudeRecursoCor = R.color.magnitude7;
                break;
            case 8:
                magnitudeRecursoCor = R.color.magnitude8;
                break;
            case 9:
                magnitudeRecursoCor = R.color.magnitude9;
                break;
            default:
                magnitudeRecursoCor = R.color.magnitude10plus;
                break;
        }

        // converter o ID de recurso de cor em um valor de cor inteiro,
        // e retornar o resultado como o valor de retorno do método
        return ContextCompat.getColor(getContext(), magnitudeRecursoCor);
    }

}