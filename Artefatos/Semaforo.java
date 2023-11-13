import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class Semaforo {

  private static final String API_HOST = "<URL DA API>";
  private static final String API_USER = "<USUARIO DA API>";
  private static final String API_PASSWORD = "<SENHA DA API>";

  private static final String MQTT_HOST = "<HOST MQTT>";
  private static final String MQTT_PORT = 1883;
  private static final String MQTT_USERNAME = "<USUARIO MQTT>";
  private static final String MQTT_PASSWORD = "<SENHA MQTT>";
  private static final String MQTT_TOPIC_ROUTING_KEY = "eventos";

  private final MQTTClient mqttClient;

  public Semaforo() throws IOException {
    mqttClient = new MQTTClient(MQTT_HOST, MQTT_PORT, MQTT_USERNAME, MQTT_PASSWORD);
    mqttClient.connect();
  }

  public void executa() throws IOException {
    // Obtém os comportamentos do semáforo da API
    URL url = new URL(String.format("%s/artefato/%d", API_HOST, 1));
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestProperty("Authorization",
        String.format("Basic %s", Base64.getEncoder().encodeToString((API_USER + ":" + API_PASSWORD).getBytes())));
    connection.setRequestMethod("GET");

    int responseCode = connection.getResponseCode();
    if (responseCode == 200) {
      InputStream inputStream = connection.getInputStream();
      InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

      Gson gson = new Gson();
      List<Comportamento> comportamentos = gson.fromJson(inputStreamReader, new TypeToken<List<Comportamento>>() {
      }.getType());

      // Executa o comportamento do semáforo, alternando as cores dos LEDs vermelho e
      // verde
      for (Comportamento comportamento : comportamentos) {
        executaComportamento(comportamento);
      }
    } else {
      System.err.println("Erro ao obter os comportamentos do semáforo: " + responseCode);
    }
  }

  private void executaComportamento(Comportamento comportamento) throws IOException {
    // Publica um evento no tópico MQTT para informar o estado do semáforo
    String mensagem = String.format("{\"artefato\": \"1\", \"corpo\": {\"estado\": \"%s\"}}",
        comportamento.getEstado());
    mqttClient.publish(MQTT_TOPIC_ROUTING_KEY, mensagem.getBytes());

    // Aguarda o tempo de duração do comportamento
    try {
      Thread.sleep(comportamento.getDuracao());
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void fecha() {
    mqttClient.disconnect();
  }
}

class Comportamento {

  private String estado;
  private int duracao;

  public Comportamento(String estado, int duracao) {
    this.estado = estado;
    this.duracao = duracao;
  }

  public String getEstado() {
        return estado;
    }

public int getDuracao() {
        return duracao
