package org.renatosantana.domain.repository;

import org.renatosantana.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Clientes extends JpaRepository<Cliente, Integer> {

    List<Cliente> findByNomeLike(String nome);
    List<Cliente> findByNomeOrId(String nome, Integer id);
    Cliente findOneByNome(String nome);
    boolean existsByNome(String nome);

    @Query("SELECT c FROM Cliente c WHERE c.nome LIKE :nome")
    List<Cliente> buscarPorNomeHQL(@Param("nome")String nome);

    @Query(value = "SELECT * FROM Cliente c WHERE c.nome LIKE '%:nome%'", nativeQuery = true)
    List<Cliente>buscarPorNomeSQL(@Param("nome")String nome);
}
