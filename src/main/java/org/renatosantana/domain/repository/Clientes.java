package org.renatosantana.domain.repository;

import org.renatosantana.domain.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class Clientes {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static String INSERT = "INSERT INTO cliente (nome) VALUES ?";
    private static String SELECT_ALL = "SELECT * FROM cliente";

    public Cliente salvarCliente(Cliente cliente){
        this.jdbcTemplate.update(INSERT, cliente.getNome());

        return cliente;
    }

    public List<Cliente> obterTodos(){
      return jdbcTemplate.query(SELECT_ALL, (resultSet, i) -> new Cliente(resultSet.getInt("id"),
              resultSet.getString("nome")));
    }

}
