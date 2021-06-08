package org.renatosantana.domain.repository;

import org.renatosantana.domain.entity.Cliente;
import org.renatosantana.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Pedidos extends JpaRepository<Pedido, Integer> {

    List<Pedido> findByCliente(Cliente cliente);
}
