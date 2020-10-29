package com.moherdi.fastfood_app.DAOs.interfaces;

import com.moherdi.fastfood_app.entities.Pedido;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IPedidoDAO extends JpaRepository<Pedido, Integer> {

}
