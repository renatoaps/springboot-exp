package org.renatosantana.domain.repository;

import org.renatosantana.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Clientes extends JpaRepository<Cliente, Integer> {

    List<Cliente> findByNomeLike(String nome);
    List<Cliente> findByNomeOrId(String nome, Integer id);
    Cliente findOneByNome(String nome);
    boolean existsByNome(String nome);
}
