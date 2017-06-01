package br.com.osmael.terremoto;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.osmael.terremoto.adapters.TerremotoAdapter;
import br.com.osmael.terremoto.entity.Terremoto;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Terremoto>> {

    /** URL  para o conjunto de dados de terremoto do USGS */
    public static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

    /** Identificador único do Loader */
    private static final int TERREMOTO_LOADER_ID = 1;

    private ListView mListView;
    private TerremotoAdapter mAdapter;
    private TextView mTextViewEmpty;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.list_view);
        mTextViewEmpty = (TextView) findViewById(R.id.empty_text_view);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        mAdapter = new TerremotoAdapter(this, new ArrayList<Terremoto>());

        // se internet está ativa, inicia o Loader, caso
        // contrário, exibe uma mensagem de sem conexão
        // com a internet.
        if (isNetworkActive()) {

            // Obtém uma referência do LoaderManager
            LoaderManager loaderManager = getSupportLoaderManager();

            // Inicializa-se um loader caso nenhum loader tenha
            // sido criado ou reconecta-se a um loader caso já
            // exista uma instância do mesmo.
            loaderManager.initLoader(TERREMOTO_LOADER_ID, null, this);

        } else {
            mTextViewEmpty.setText(R.string.sem_conexao);
            mProgressBar.setVisibility(View.GONE);
        }

    }

    /**
     * Verifica se há um loader criado, caso não
     * exista este método será acionado.
     *
     * Cria um novo loader {@link TerremotoLoader}
     * para uma dada URL.
     */
    @Override
    public Loader<List<Terremoto>> onCreateLoader(int id, Bundle args) {
        return new TerremotoLoader(this, USGS_REQUEST_URL);
    }

    /**
     *
     * Trabalha na thread principal (UI Thread).
     *
     * Responsável por atualizar do ListView com
     * os dados obtidos do servidor do USGS.
     *
     */
    @Override
    public void onLoadFinished(Loader<List<Terremoto>> loader, List<Terremoto> terremotos) {

        // Limpa o adapter de dados prévios de terremoto
        mAdapter.clear();

        // verifica se a lista de terremotos é diferente de null
        // e seu tamanho é maior do que zero.
        if (terremotos != null && terremotos.size() > 0) {

            mAdapter.addAll(terremotos);
            mListView.setAdapter(mAdapter);

            mProgressBar.setVisibility(View.GONE);

            mTextViewEmpty.setText(R.string.sem_dados);
            mListView.setEmptyView(mTextViewEmpty);
        }

    }

    /**
     * Responsável por descobrir quando os dados estão
     * prestes a serem liberados para que seja possível
     * remover a referência a eles.
     */
    @Override
    public void onLoaderReset(Loader<List<Terremoto>> loader) {
        // Quando loader resetar, podemos limpar os dados
        // existentes.
        mAdapter.clear();
    }

    /**
     * Verifica se o serviço de internet está
     * ativa.
     *
     * @return boolean caso esteja habilitada.
     */
    private boolean isNetworkActive() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

}