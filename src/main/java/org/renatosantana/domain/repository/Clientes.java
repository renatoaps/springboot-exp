package org.renatosantana.domain.repository;

import org.renatosantana.domain.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class Clientes {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static String INSERT = "INSERT INTO cliente (nome) VALUES ?";
    private static String SELECT_ALL = "SELECT * FROM cliente";
    private static String UPDATE = "UPDATE cliente SET NOME = ? WHERE ID = ?";
    private static String DELETE = "DELETE FROM cliente WHERE ID = ?";

    public Cliente salvarCliente(Cliente cliente){
        this.jdbcTemplate.update(INSERT, cliente.getNome());

        return cliente;
    }

    public List<Cliente> obterTodos(){
      return jdbcTemplate.query(SELECT_ALL, obterClienteMapper());
    }

    public List<Cliente> obterPorNome(String nome){
        return jdbcTemplate.query(
                SELECT_ALL.concat(" WHERE nome LIKE ? "),
                new Object[]{"%" + nome + "%"},
                obterClienteMapper());
    }

    private RowMapper<Cliente> obterClienteMapper() {
        return (resultSet, i) -> new Cliente(resultSet.getInt("id"),
                resultSet.getString("nome"));
    }

    public void deletarCliente(Integer id){
         jdbcTemplate.update(DELETE, id);
    }

    public Cliente atualizarCliente(Cliente cliente){
        jdbcTemplate.update(UPDATE, new Object[]{
                cliente.getNome(), cliente.getId()} );

        return cliente;
    }

}
