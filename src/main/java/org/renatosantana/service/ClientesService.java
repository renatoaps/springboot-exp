package org.renatosantana.service;

import org.renatosantana.model.Cliente;
import org.renatosantana.repository.ClientesRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientesService {

    private ClientesRepository clientesRepository;

    public ClientesService(ClientesRepository clientesRepository) {
        this.clientesRepository = clientesRepository;
    }

    public void salvarCliente(Cliente cliente){
        validarCliente(cliente);
        this.clientesRepository.persistir(cliente);
    }

    public void validarCliente(Cliente cliente){
        //validacao do cliente
    }
}
