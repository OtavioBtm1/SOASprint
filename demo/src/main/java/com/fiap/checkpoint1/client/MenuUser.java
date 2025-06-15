package com.fiap.checkpoint1.client;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Scanner;

public class MenuUser {

    private static final String BASE_URL = "http://localhost:8080/usuarios";
    private static final String AUTH_USERNAME = "admin";
    private static final String AUTH_PASSWORD = "1234";

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n--- MENU SISTEMA DE USUÁRIOS ---");
            System.out.println("1. Cadastrar novo usuário");
            System.out.println("2. Fazer login");
            System.out.println("3. Modo administrador");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // limpa buffer

            switch (opcao) {
                case 1 -> {
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Senha: ");
                    String senha = scanner.nextLine();
                    cadastrarUsuario(nome, email, senha);
                }
                case 2 -> {
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Senha: ");
                    String senha = scanner.nextLine();
                    loginUsuario(email, senha);
                }
                case 3 -> menuAdministrador(scanner);
                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);

        scanner.close();
    }

    private static void menuAdministrador(Scanner scanner) throws IOException {
        int opcaoAdmin;

        do {
            System.out.println("\n--- MODO ADMINISTRADOR ---");
            System.out.println("1. Listar todos os usuários");
            System.out.println("2. Buscar usuário por ID");
            System.out.println("3. Deletar usuário");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            opcaoAdmin = scanner.nextInt();
            scanner.nextLine();

            switch (opcaoAdmin) {
                case 1 -> listarTodos();
                case 2 -> {
                    System.out.print("ID do usuário: ");
                    long id = scanner.nextLong();
                    scanner.nextLine();
                    buscarPorId(id);
                }
                case 3 -> {
                    System.out.print("ID para deletar: ");
                    long id = scanner.nextLong();
                    scanner.nextLine();
                    deletarUsuario(id);
                }
                case 0 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcaoAdmin != 0);
    }

    private static void cadastrarUsuario(String nome, String email, String senha) throws IOException {
        String json = String.format("{\"nome\":\"%s\", \"email\":\"%s\", \"senha\":\"%s\"}", nome, email, senha);
        sendRequest("POST", BASE_URL + "/register", json);
    }

    private static void loginUsuario(String email, String senha) throws IOException {
        String json = String.format("{\"email\":\"%s\", \"senha\":\"%s\"}", email, senha);
        sendRequest("POST", BASE_URL + "/login", json);
    }

    private static void listarTodos() throws IOException {
        sendRequest("GET", BASE_URL, null);
    }

    private static void buscarPorId(long id) throws IOException {
        sendRequest("GET", BASE_URL + "/" + id, null);
    }

    private static void deletarUsuario(long id) throws IOException {
        sendRequest("DELETE", BASE_URL + "/" + id, null);
    }

    private static void sendRequest(String method, String urlString, String body) throws IOException {
    URL url = new URL(urlString);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod(method);
    conn.setRequestProperty("Content-Type", "application/json");

     String auth = AUTH_USERNAME + ":" + AUTH_PASSWORD;
    String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
    conn.setRequestProperty("Authorization", "Basic " + encodedAuth);

     if (body != null) {
        conn.setDoOutput(true);
        try (OutputStream os = conn.getOutputStream()) {
            os.write(body.getBytes());
            os.flush();
        }
    }
    // mensagens para quaisquer resposta http
    int status = conn.getResponseCode();
    String message = switch (status) {
        case 200 -> "Sucesso: Requisição realizada com sucesso.";
        case 201 -> "Criado: Recurso criado com sucesso.";
        case 204 -> "Sem conteúdo: Ação concluída sem retorno.";
        case 400 -> "Erro 400: Requisição malformada.";
        case 401 -> "Erro 401: Não autorizado. Verifique login/senha.";
        case 403 -> "Erro 403: Acesso proibido.";
        case 404 -> "Erro 404: Recurso não encontrado.";
        case 409 -> "Erro 409: Conflito. O recurso já existe?";
        case 500 -> "Erro 500: Erro interno do servidor.";
        default ->  "Código HTTP inesperado: " + status;
    };

    System.out.println("Status HTTP: " + status + " - " + message);

    InputStream responseStream = status < HttpURLConnection.HTTP_BAD_REQUEST ?
            conn.getInputStream() : conn.getErrorStream();

    if (responseStream != null) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(responseStream))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                System.out.println(linha);
            }
        }
    } else {
        System.out.println("⚠️ Nenhuma resposta recebida do servidor.");
    }

    conn.disconnect();
}
}