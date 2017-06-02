package br.com.osmael.terremoto;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by root on 01/06/17.
 */
public class ConfiguracaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuracao_activity);
    }

    public static class TerremotoPreferenciaFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.configuracao_main);

            // atualizando o resumo de preferências quando a
            // {@link SettingsActivity} é iniciada.

            // recuperando um objeto {@link Preference} a partir
            // da chave de uma preferência
            Preference minMagnitude = findPreference(getString(R.string.configuracao_min_chave_magnitude));
            bindPreferenceSummaryToValue(minMagnitude);

            Preference ordernarPor = findPreference(getString(R.string.configuracao_order_by_chave));
            bindPreferenceSummaryToValue(ordernarPor);

        }

        /**
         * Atualiza o resumo de preferências exibido
         * após ele ter sido alterado.
         */
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {

            // recuperando novo valor de preferência como uma String
            String stringValue = value.toString();

            // verificando se o preference é uma instância
            // de ListPreference
            if (preference instanceof ListPreference) {

                ListPreference listPreference = (ListPreference) preference;

                int prefIndex = listPreference.findIndexOfValue(stringValue);

                if (prefIndex >= 0) {
                    CharSequence[] labels = listPreference.getEntries();
                    preference.setSummary(labels[prefIndex]);
                }

            } else {

                // atualizando o resumo de preferência com o novo valor
                preference.setSummary(stringValue);

            }

            return true;

        }

        /**
         * Define a instância atual de {@link TerremotoPreferenciaFragment}
         * como um listener em cada preferência.
         *
         * Também ler o valor atual da preferência armazenado
         * no {@link SharedPreferences} no dispositivo, e
         * exibe isso no resumo de preferências.
         *
         */
        private void bindPreferenceSummaryToValue(Preference preference) {

            // definindo instância corrente de {@link TerremotoPreferenciaFragment}
            // como um listener em cada preferência.
            preference.setOnPreferenceChangeListener(this);

            // recuperando valor atual da preferência
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String preferenceString = preferences.getString(preference.getKey(), "");

            // definindo preferência para ser exibida no resumo de preferências.
            onPreferenceChange(preference, preferenceString);
        }

    }

}