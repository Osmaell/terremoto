package br.com.osmael.terremoto;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

import br.com.osmael.terremoto.entity.Terremoto;
import br.com.osmael.terremoto.utils.BuscaUtil;

/**
 * Created by root on 31/05/17.
 */
public class TerremotoLoader extends AsyncTaskLoader<List<Terremoto>>{

    /** String que representa a url do web service com o recurso desejado */
    private String mUrl;

    public TerremotoLoader(Context context, String url) {
        super(context);
        this.mUrl = url;
    }

    /**
     * Retorna uma lista de terremotos que
     * é obtida do método estático obterTerremotos
     * da classe {@link BuscaUtil}.
     *
     * Está é a thread de background.
     */
    @Override
    public List<Terremoto> loadInBackground() {

        if (mUrl == null) {
            return null;
        }

        return BuscaUtil.obterTerremotos(mUrl);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

}