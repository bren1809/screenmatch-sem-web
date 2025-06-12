package br.com.alura.screenmatch.service;

import okhttp3.*;

import java.io.IOException;

public class ConsultaDeepL {
    private static final String DEEPL_API_URL = "https://api-free.deepl.com/v2/translate";
    private static final String API_KEY = System.getenv("DEEPL_APIKEY"); // Substitua pela sua chave DeepL

    public static String obterTraducao(String texto) {
        OkHttpClient client = new OkHttpClient();

        // Configura o corpo da requisição (formato x-www-form-urlencoded)
        RequestBody body = new FormBody.Builder()
                .add("auth_key", API_KEY)
                .add("text", texto)
                .add("target_lang", "PT-BR") // PT para português (use "EN" para inglês)
                .build();

        // Cria a requisição POST
        Request request = new Request.Builder()
                .url(DEEPL_API_URL)
                .post(body)
                .build();

        // Executa a requisição e processa a resposta
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Erro na requisição: " + response.code() + " - " + response.body().string());
            }

            String respostaJson = response.body().string();
            // Extrai o texto traduzido do JSON (simplificado - considere usar uma lib como Gson para parsing completo)
            return respostaJson.split("\"text\":\"")[1].split("\"")[0];
        } catch (IOException e) {
            throw new RuntimeException("Erro ao fazer requisição de tradução",e);
        }
    }
}
