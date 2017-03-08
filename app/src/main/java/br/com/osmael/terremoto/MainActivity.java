package br.com.osmael.terremoto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.osmael.terremoto.adapters.TerremotoAdapter;
import br.com.osmael.terremoto.entity.Terremoto;
import br.com.osmael.terremoto.utils.BuscaUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // List criada a partir de lista fornecida pelo método estático
        // BuscaUtil.obterTerremotos()
        List<Terremoto> terremotos = BuscaUtil.obterTerremotos();

        ListView listView = (ListView) findViewById(R.id.list_view);

        // Adaptador responsável por adaptar os dados para a ListView
        TerremotoAdapter adapter = new TerremotoAdapter(
                this,
                terremotos);

        listView.setAdapter(adapter);

    }

}