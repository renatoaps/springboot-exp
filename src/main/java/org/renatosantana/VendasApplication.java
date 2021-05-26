package org.renatosantana;

import org.renatosantana.domain.entity.Cliente;
import org.renatosantana.domain.repository.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@SpringBootApplication
public class VendasApplication {

    @Bean
    public CommandLineRunner init(@Autowired Clientes clientes){

        return args -> {
          System.out.println(":: Salvando Clientes");
          clientes.salvarCliente(new Cliente("Renato"));
          clientes.salvarCliente(new Cliente("Jessica"));
          clientes.salvarCliente(new Cliente("Cartório Safado"));

          System.out.println(":: Listando Clientes");
          List<Cliente> obterTodos = clientes.obterTodos();
          obterTodos.forEach(System.out::println);

          obterTodos.forEach(c -> {
            c.setNome(c.getNome() + " atualizado");
            clientes.atualizarCliente(c);
          });

          System.out.println(":: Atualizando Clientes ::");
          obterTodos.forEach(System.out::println);

          System.out.println(":: Buscando Cliente::");
          List<Cliente> busca = clientes.obterPorNome("Jess");
          busca.forEach(System.out::println);

          System.out.println(":: Cliente Deletado ::");
          clientes.deletarCliente(3);

          System.out.println(":: Limpando tabela um por um, porque sim::");
          obterTodos.forEach(c -> clientes.deletarCliente(c.getId()));

        };
    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}
