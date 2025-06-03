package com.fiap.checkpoint1.client;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ClienteTerminal {

    private static final String BASE_URL = "http://localhost:8080/registros";

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n--- MENU API REGISTRO ---");
            System.out.println("1. Listar todos");
            System.out.println("2. Buscar por ID");
            System.out.println("3. Criar registro");
            System.out.println("4. Atualizar registro");
            System.out.println("5. Deletar registro");
            System.out.println("6. Buscar por cidade");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // limpar buffer

            switch (opcao) {
                case 1 -> listarTodos();
                case 2 -> {
                    System.out.print("ID: ");
                    long id = scanner.nextLong();
                    buscarPorId(id);
                }
                case 3 -> {
                    System.out.print("Data (yyyy-MM-dd): ");
                    String data = scanner.nextLine();
                    System.out.print("Horário (HH:mm): ");
                    String horario = scanner.nextLine();
                    System.out.print("Cidade: ");
                    String cidade = scanner.nextLine();
                    System.out.print("Estado: ");
                    String estado = scanner.nextLine();
                    criarRegistro(data, horario, cidade, estado);
                }
                case 4 -> {
                    System.out.print("ID a atualizar: ");
                    long id = scanner.nextLong();
                    scanner.nextLine();
                    System.out.print("Nova data: ");
                    String data = scanner.nextLine();
                    System.out.print("Novo horário: ");
                    String horario = scanner.nextLine();
                    System.out.print("Nova cidade: ");
                    String cidade = scanner.nextLine();
                    System.out.print("Novo estado: ");
                    String estado = scanner.nextLine();
                    atualizarRegistro(id, data, horario, cidade, estado);
                }
                case 5 -> {
                    System.out.print("ID para deletar: ");
                    long id = scanner.nextLong();
                    deletarRegistro(id);
                }
                case 6 -> {
                    System.out.print("Cidade: ");
                    String cidade = scanner.nextLine();
                    buscarPorCidade(cidade);
                }
                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("Opção inválida!");
            }

        } while (opcao != 0);

        scanner.close();
    }

    private static void listarTodos() throws IOException {
        sendRequest("GET", BASE_URL, null);
    }

    private static void buscarPorId(long id) throws IOException {
        sendRequest("GET", BASE_URL + "/" + id, null);
    }

    private static void buscarPorCidade(String cidade) throws IOException {
        sendRequest("GET", BASE_URL + "/cidade/" + cidade, null);
    }

    private static void criarRegistro(String data, String horario, String cidade, String estado) throws IOException {
        String json = String.format("{\"data\":\"%s\",\"horario\":\"%s\",\"cidade\":\"%s\",\"estado\":\"%s\"}", data, horario, cidade, estado);
        sendRequest("POST", BASE_URL, json);
    }

    private static void atualizarRegistro(long id, String data, String horario, String cidade, String estado) throws IOException {
        String json = String.format("{\"data\":\"%s\",\"horario\":\"%s\",\"cidade\":\"%s\",\"estado\":\"%s\"}", data, horario, cidade, estado);
        sendRequest("PUT", BASE_URL + "/" + id, json);
    }

    private static void deletarRegistro(long id) throws IOException {
        sendRequest("DELETE", BASE_URL + "/" + id, null);
    }

    private static void sendRequest(String method, String urlString, String body) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(method);
        conn.setRequestProperty("Content-Type", "application/json");

        if (body != null) {
            conn.setDoOutput(true);
            try (OutputStream os = conn.getOutputStream()) {
                os.write(body.getBytes());
                os.flush();
            }
        }

        int status = conn.getResponseCode();
        System.out.println("Status HTTP: " + status);

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        status < HttpURLConnection.HTTP_BAD_REQUEST ?
                                conn.getInputStream() : conn.getErrorStream()))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                System.out.println(linha);
            }
        }

        conn.disconnect();
    }
}
