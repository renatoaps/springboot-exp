package org.renatosantana.rest.controller;

import org.renatosantana.domain.entity.Cliente;
import org.renatosantana.domain.repository.Clientes;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClientesController {

    private Clientes clientes;

    public ClientesController(Clientes clientes) {
        this.clientes = clientes;
    }

    @GetMapping("{id}")
    public Cliente getById( @PathVariable Integer id){

        return clientes
                .findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Cliente não encontrado"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente save(@RequestBody Cliente cliente){
        Cliente clienteSalvo = clientes.save(cliente);

        return clienteSalvo;
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id){
        clientes.findById(id)
                .map( cliente -> {
                    clientes.delete(cliente);
                    return cliente;
                }).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Cliente não encontrado"));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id,
                                 @RequestBody Cliente cliente){

        clientes.findById(id)
                .map( clienteExistente -> {
                    cliente.setId(clienteExistente.getId());
                    clientes.save(cliente);
                    return cliente;
                }).orElseThrow( ()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Cliente não encontrado"));
    }

    @GetMapping
    List<Cliente> find(Cliente filtro){
        ExampleMatcher matcher = ExampleMatcher
                                    .matching()
                                    .withIgnoreCase()
                                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);

        return clientes.findAll(example);
    }
}
