package br.com.osmael.terremoto;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import java.util.List;

import br.com.osmael.terremoto.adapters.TerremotoAdapter;
import br.com.osmael.terremoto.entity.Terremoto;
import br.com.osmael.terremoto.utils.BuscaUtil;

public class MainActivity extends AppCompatActivity {

    /** URL  para o conjunto de dados de terremoto do USGS */
    public static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // List criada a partir de lista fornecida pelo método estático
        // BuscaUtil.obterTerremotos()
//        List<Terremoto> terremotos = BuscaUtil.obterTerremotos(USGS_REQUEST_URL);

//        ListView listView = (ListView) findViewById(R.id.list_view);

        // Adaptador responsável por adaptar os dados para a ListView
//        TerremotoAdapter adapter = new TerremotoAdapter( this, terremotos);

//        listView.setAdapter(adapter);

        Log.v("OSMAEL", BuscaUtil.obterTerremotos(USGS_REQUEST_URL).toString());

    }

}