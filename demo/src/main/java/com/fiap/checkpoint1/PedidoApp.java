package com.fiap.checkpoint1;

import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fiap.checkpoint1.model.Pedido;
import com.fiap.checkpoint1.service.PedidoService;

@SpringBootApplication
public class PedidoApp {
    public static void main(String[] args) {
        SpringApplication.run(PedidoApp.class, args);
    }

    @Bean
    CommandLineRunner run(PedidoService pedidoService) {
        return args -> {
            Scanner scanner = new Scanner(System.in);

            while (true) {
                try {
                    System.out.println("\nMenu:");
                    System.out.println("1 - Criar Pedido");
                    System.out.println("2 - Listar Pedidos");
                    System.out.println("3 - Buscar Pedido por ID");
                    System.out.println("4 - Atualizar Pedido");
                    System.out.println("5 - Deletar Pedido");
                    System.out.println("0 - Sair");
                    System.out.print("Escolha uma opção: ");

                    if (!scanner.hasNextInt()) {
                        System.out.println("Erro: Digite um número válido!");
                        scanner.next(); // Descarta entrada inválida
                        continue;
                    }

                    int opcao = scanner.nextInt();
                    scanner.nextLine(); // Limpar buffer

                    switch (opcao) {
                        case 1:
                            System.out.print("Nome do Cliente: ");
                            String nome = scanner.nextLine();
                            System.out.print("Valor Total: ");
                            if (!scanner.hasNextDouble()) {
                                System.out.println("Erro: Digite um valor numérico válido!");
                                scanner.next(); // Descarta entrada inválida
                                continue;
                            }
                            double valor = scanner.nextDouble();
                            scanner.nextLine(); // Limpar buffer
                            Pedido novoPedido = new Pedido(nome, valor);
                            pedidoService.salvar(novoPedido);
                            System.out.println("✅ Pedido criado com sucesso!");
                            break;

                        case 2:
                            System.out.println("📋 Lista de Pedidos:");
                            pedidoService.listarTodos().forEach(System.out::println);
                            break;

                        case 3:
                            System.out.print("🔍 ID do Pedido: ");
                            if (!scanner.hasNextLong()) {
                                System.out.println("Erro: Digite um ID válido!");
                                scanner.next(); // Descarta entrada inválida
                                continue;
                            }
                            Long idBusca = scanner.nextLong();
                            scanner.nextLine(); // Limpar buffer
                            Optional<Pedido> pedidoEncontrado = pedidoService.buscarPorId(idBusca);
                            pedidoEncontrado.ifPresentOrElse(
                                    System.out::println,
                                    () -> System.out.println("⚠️ Pedido não encontrado!"));
                            break;

                        case 4:
                            System.out.print("✏️ ID do Pedido para atualizar: ");
                            if (!scanner.hasNextLong()) {
                                System.out.println("Erro: Digite um ID válido!");
                                scanner.next();
                                continue;
                            }
                            Long idAtualizar = scanner.nextLong();
                            scanner.nextLine();
                            System.out.print("Novo Nome do Cliente: ");
                            String novoNome = scanner.nextLine();
                            System.out.print("Novo Valor Total: ");
                            if (!scanner.hasNextDouble()) {
                                System.out.println("Erro: Digite um valor numérico válido!");
                                scanner.next();
                                continue;
                            }
                            double novoValor = scanner.nextDouble();
                            scanner.nextLine();
                            Pedido pedidoAtualizado = new Pedido(novoNome, novoValor);
                            pedidoService.atualizar(idAtualizar, pedidoAtualizado);
                            System.out.println("✅ Pedido atualizado com sucesso!");
                            break;

                        case 5:
                            System.out.print("🗑️ ID do Pedido para deletar: ");
                            if (!scanner.hasNextLong()) {
                                System.out.println("Erro: Digite um ID válido!");
                                scanner.next();
                                continue;
                            }
                            Long idDeletar = scanner.nextLong();
                            scanner.nextLine();
                            pedidoService.deletar(idDeletar);
                            System.out.println("✅ Pedido deletado!");
                            break;

                        case 0:
                            System.out.println("👋 Saindo...");
                            scanner.close();
                            return;

                        default:
                            System.out.println("❌ Opção inválida! Tente novamente.");
                            break;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Erro: Entrada inválida. Tente novamente.");
                    scanner.nextLine(); // Descarta a entrada inválida
                }
            }
        };
    }
}