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
          clientes.salvar(new Cliente("Renato"));
          clientes.salvar(new Cliente("Jessica"));
          clientes.salvar(new Cliente("Cartório Safado"));

          System.out.println(":: Listando Clientes");
          List<Cliente> todosClientes = clientes.obterTodos();
            todosClientes.forEach(System.out::println);

            todosClientes.forEach(c -> {
            c.setNome(c.getNome() + " atualizado");
            clientes.atualizar(c);
          });

          System.out.println(":: Atualizando Clientes ::");
            todosClientes.forEach(System.out::println);

          System.out.println(":: Buscando Cliente::");
          List<Cliente> busca = clientes.buscarPorNome("Jess");
          busca.forEach(System.out::println);

          System.out.println(":: Deletando Clientes::");
          clientes.obterTodos().forEach(clientes::deletar);

            todosClientes = clientes.obterTodos();
            if(todosClientes.isEmpty()){
                System.out.println("Nenhum cliente encontrado.");
            }else{
                todosClientes.forEach(System.out::println);
            }

        };
    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}
