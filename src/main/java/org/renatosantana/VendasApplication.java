package org.renatosantana;

import org.renatosantana.domain.entity.Cliente;
import org.renatosantana.domain.repository.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class VendasApplication {

    @Bean
    public CommandLineRunner init(@Autowired Clientes clientes){

        return args -> {
          clientes.salvarCliente(new Cliente("Renato"));
          clientes.salvarCliente(new Cliente("Jessica"));
          clientes.salvarCliente(new Cliente("Cartório Safado"));

            System.out.println(clientes.obterTodos().toString());
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}
