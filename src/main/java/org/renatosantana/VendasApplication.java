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
          System.out.println(":: Salvando Clientes ::");
          clientes.save(new Cliente("Renato"));
          clientes.save(new Cliente("Jessica"));
          clientes.save(new Cliente("Cartório Safado"));

          System.out.println("Existe o cliente buscado?" + clientes.existsByNome("Jessica"));
          System.out.println(clientes.findByNomeOrId("Renato", 3));
          System.out.println(clientes.buscarPorNomeHQL("Jessica"));
          System.out.println(clientes.buscarPorNomeSQL("Renato"));
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}
