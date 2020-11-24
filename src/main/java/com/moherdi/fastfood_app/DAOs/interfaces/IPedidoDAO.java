package com.moherdi.fastfood_app.DAOs.interfaces;

import java.util.List;
import java.util.Optional;

import com.moherdi.fastfood_app.entities.Cliente;
import com.moherdi.fastfood_app.entities.Pedido;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IPedidoDAO extends JpaRepository<Pedido, Integer> {

	Optional<Pedido> findByCliente(Optional<Cliente> cliente);

	List<Pedido> findByCliente(Cliente cliente);

}
