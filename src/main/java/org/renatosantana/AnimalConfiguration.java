package org.renatosantana;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnimalConfiguration {

    @Bean(name = "cachorro")
    public Animal cachorro(){
        return () -> System.out.println("BAW WAW BAW");
    }

    @Bean(name = "gato")
    public Animal gato(){
        return () -> System.out.println("MEOW** DEAD");
    }

}
