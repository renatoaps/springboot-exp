package org.renatosantana;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean(name = "appName")
    String applicationName(){
        return "Sistema de Vendas";
    }
}
