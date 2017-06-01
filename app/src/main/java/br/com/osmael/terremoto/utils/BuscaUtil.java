package br.com.osmael.terremoto.utils;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import br.com.osmael.terremoto.entity.Terremoto;

public class BuscaUtil {

    private static final String LOG_TAG = BuscaUtil.class.getSimpleName();

    /**
     * Criando um construtor private pois o objeto {@link BuscaUtil} nunca
     * deve ser criado. Está classe destina-se apenas a manter métodos e
     * variáveis estáticas que podem ser acessados diretamente a partir do
     * nome da classe BuscaUtil.
     */
    private BuscaUtil() {
    }

    public static List<Terremoto> obterTerremotos(String stringUrl) {

        URL url = criaUrl(stringUrl);

        String respostaJson = null;

        try {
            respostaJson = executaRequisicaoHttp(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Erro ao obter lista de terremotos.", e);
        }

        // Cria uma lista de terremotos a partir do método extraiListaTerremotos()
        List<Terremoto> terremotos = extraiListaTerremotos(respostaJson);

        // Retorna a lista de terremotos
        return terremotos;
    }

    /**
     * Responsável por criar uma URL a partir
     * de uma string representando a URL.
     */
    private static URL criaUrl(String stringUrl) {

        URL url = null;

        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Erro ao criar URL", e);
        }

        return url;
    }

    /**
     * Responsável por executar uma requisição http
     * para os servidores do USGS e retornar um JSON
     * que representa o conteúdo da resposta da
     * requisição.
     */
    private static String executaRequisicaoHttp(URL url) throws IOException {

        String respostaJson = "";

        if (url == null) {
            return respostaJson;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(15000);
            urlConnection.connect();

            // verifica se a resposta da conexão é HTTP_OK (200)
            // atribui o input stream de resposta da conexão para
            // a variável inputStream. Atribui o retorno do método
            // lerDoStream() para a variável respostaJson.
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = urlConnection.getInputStream();
                respostaJson = lerDoStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Resposta de requisição estranha " + urlConnection.getResponseCode());
            }

        } catch (IOException e) {
            Log.e(LOG_TAG, "Erro ao tentar efetuar requisição", e);
        } finally {

            if (urlConnection != null) {
                urlConnection.disconnect();
            }

            if (inputStream != null) {
                inputStream.close();
            }

        }

        return respostaJson;
    }

    /**
     * Responsável por receber um {@link InputStream} e
     * converte-ló para uma String que representa a resposta
     * JSON do servidor.
     */
    private static String lerDoStream(InputStream inputStream) throws IOException {

        StringBuilder builder = new StringBuilder();

        if (inputStream != null) {

            InputStreamReader reader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader buffer = new BufferedReader(reader);
            String line = buffer.readLine();

            while (line != null) {
                builder.append(line);
                line = buffer.readLine();
            }

        }

        return builder.toString();
    }

    /**
     * Responsável por extrai uma resposta JSON para uma lista de objetos
     * {@link Terremoto}
     *
     * @return uma lista de objetos {@link Terremoto} que foi construído
     * a partir de uma análise de uma resposta JSON.
     */
    private static List<Terremoto> extraiListaTerremotos(String respostaJson) {

        // se o parâmetro respostaJson for null ou estiver
        // vazia, será retornado null
        if (TextUtils.isEmpty(respostaJson)) {
            return null;
        }

        List<Terremoto> terremotos = new ArrayList<>();

        try {

            // Responsável por fazer parsing da response geoJSON do USGS
            JSONObject baseJSONObject = new JSONObject(respostaJson);
            JSONArray terremotoArray = baseJSONObject.getJSONArray("features");

            for (int i = 0; i < terremotoArray.length(); i++) {

                JSONObject terremotoAtual = terremotoArray.getJSONObject(i);

                // extrai o objeto JSON associado com a chave "properties"
                JSONObject properties = terremotoAtual.getJSONObject("properties");

                // acessando os valores individuais do objeto JSON "properties"
                double magnitude = properties.getDouble("mag");
                String localizacao = properties.getString("place");
                long tempo = properties.getLong("time");

                Terremoto terremoto = new Terremoto(magnitude, localizacao, tempo);
                terremotos.add(terremoto);
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problema ao fazer parsing para terremoto JSON resultado", e);
        }

        return terremotos;
    }

}