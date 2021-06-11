package org.renatosantana.rest.controller;

import org.renatosantana.domain.entity.Cliente;
import org.renatosantana.domain.repository.Clientes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class ClientesController {

    private Clientes clientes;

    public ClientesController(Clientes clientes) {
        this.clientes = clientes;
    }

    @GetMapping("/api/clientes/{id}")
    @ResponseBody
    public ResponseEntity getById( @PathVariable Integer id){
        Optional<Cliente> cliente = clientes.findById(id);
        if (cliente.isPresent()){
            return ResponseEntity.ok(cliente.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/api/clientes")
    @ResponseBody
    public ResponseEntity save(@RequestBody Cliente cliente){
        Cliente clienteSalvo = clientes.save(cliente);

        return ResponseEntity.ok(clienteSalvo);
    }

    @DeleteMapping("/api/clientes/{id}")
    @ResponseBody
    public ResponseEntity delete(@PathVariable Integer id){
        Optional<Cliente> cliente = clientes.findById(id);
        if (cliente.isPresent()){
            clientes.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/api/clientes/{id}")
    @ResponseBody
    public ResponseEntity update(@PathVariable Integer id,
                                 @RequestBody Cliente cliente){

        return clientes
                .findById(id)
                .map(clienteExiste ->{
                    cliente.setId(clienteExiste.getId());
                    clientes.save(cliente);
                    return ResponseEntity.noContent().build();

                }).orElseGet( () -> ResponseEntity.notFound().build());
    }

    @GetMapping("/api/clientes")
    @ResponseBody
    ResponseEntity getAll(){
        List<Cliente> todosClientes = clientes.findAll();

        if (todosClientes.size() > 0){
            return ResponseEntity.ok(todosClientes);
        }

        return ResponseEntity.notFound().build();
    }
}
