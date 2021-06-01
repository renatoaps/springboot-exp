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
          clientes.save(new Cliente("Renato"));
          clientes.save(new Cliente("Jessica"));
          clientes.save(new Cliente("Cartório Safado"));

          System.out.println(":: Listando Clientes");
          List<Cliente> todosClientes = clientes.findAll();
            todosClientes.forEach(System.out::println);

            todosClientes.forEach(c -> {
            c.setNome(c.getNome() + " atualizado");
            clientes.save(c);
          });

          System.out.println(":: Atualizando Clientes ::");
            todosClientes.forEach(System.out::println);

          System.out.println(":: Buscando Cliente::");
          List<Cliente> busca = clientes.findByNomeLike("Jess");
          busca.forEach(System.out::println);

          System.out.println(":: Deletando Clientes::");
          clientes.findAll().forEach(clientes::delete);

            todosClientes = clientes.findAll();
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
