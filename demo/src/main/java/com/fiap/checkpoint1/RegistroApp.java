package com.fiap.checkpoint1;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.Scanner;

public class RegistroApp {

    private static final String BASE_URL = "http://localhost:8080/registros";
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        while (true) {
            System.out.println("\n=== REGISTRO APP ===");
            System.out.println("1 - Criar registro");
            System.out.println("2 - Listar registros");
            System.out.println("3 - Buscar por ID");
            System.out.println("4 - Deletar por ID");
            System.out.println("5 - Buscar por cidade");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            int opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1 -> criarRegistro();
                case 2 -> listarRegistros();
                case 3 -> buscarPorId();
                case 4 -> deletarPorId();
                case 5 -> buscarPorCidade();
                case 0 -> System.exit(0);
                default -> System.out.println("Opção inválida");
            }
        }
    }

    private static void criarRegistro() throws Exception {
        System.out.print("Data (AAAA-MM-DD): ");
        String data = scanner.nextLine();
        System.out.print("Horário (HH:MM): ");
        String horario = scanner.nextLine();
        System.out.print("Cidade: ");
        String cidade = scanner.nextLine();
        System.out.print("Estado: ");
        String estado = scanner.nextLine();

        String json = String.format("""
            {
                "data": "%s",
                "horario": "%s",
                "cidade": "%s",
                "estado": "%s"
            }
            """, data, horario, cidade, estado);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(json))
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Resposta: " + response.body());
    }

    private static void listarRegistros() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL))
            .GET()
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Registros:\n" + response.body());
    }

    private static void buscarPorId() throws Exception {
        System.out.print("ID do registro: ");
        String id = scanner.nextLine();

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/" + id))
            .GET()
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Resposta:\n" + response.body());
    }

    private static void deletarPorId() throws Exception {
        System.out.print("ID do registro a deletar: ");
        String id = scanner.nextLine();

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/" + id))
            .DELETE()
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Status: " + response.statusCode());
    }

    private static void buscarPorCidade() throws Exception {
        System.out.print("Nome da cidade: ");
        String cidade = scanner.nextLine();

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/cidade/" + cidade))
            .GET()
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Registros encontrados:\n" + response.body());
    }
}
