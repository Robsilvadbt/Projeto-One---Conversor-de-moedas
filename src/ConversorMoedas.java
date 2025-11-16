import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.Scanner;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ConversorMoedas {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String apiKey = "36c73abe954f56f80a6693b3";

        System.out.println("==== CONVERSOR DE MOEDAS ====");

        while (true) {

            System.out.println("\nEscolha uma opção:");
            System.out.println("1 - BRL -> USD");
            System.out.println("2 - USD -> BRL");
            System.out.println("3 - BRL -> EUR");
            System.out.println("4 - EUR -> BRL");
            System.out.println("5 - USD -> EUR");
            System.out.println("6 - EUR -> USD");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");

            int opcao = sc.nextInt();

            if (opcao == 0) {
                System.out.println("Valeu! Até a próxima!");
                break;
            }

            // 🔥 Validação de opção inválida
            if (opcao < 1 || opcao > 6) {
                System.out.println("⚠ Opção inválida! Tente novamente.");
                continue; // volta para o menu direto
            }

            System.out.print("Digite o valor que quer converter: ");
            double valor = sc.nextDouble();

            try {
                double convertido = converter(apiKey, opcao, valor);
                System.out.println("Resultado: " + convertido);
            } catch (Exception e) {
                System.out.println("Deu erro ao tentar converter :(");
            }
        }

        sc.close();
    }

    public static double converter(String apiKey, int opcao, double valor) throws Exception {
        String base = "";
        String destino = "";

        switch (opcao) {
            case 1: base = "BRL"; destino = "USD"; break;
            case 2: base = "USD"; destino = "BRL"; break;
            case 3: base = "BRL"; destino = "EUR"; break;
            case 4: base = "EUR"; destino = "BRL"; break;
            case 5: base = "USD"; destino = "EUR"; break;
            case 6: base = "EUR"; destino = "USD"; break;
        }

        String url = "https://v6.exchangerate-api.com/v6/"
                + apiKey + "/latest/" + base;

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();
        JsonObject rates = jsonObject.getAsJsonObject("conversion_rates");

        double taxa = rates.get(destino).getAsDouble();

        return valor * taxa;
    }
}
