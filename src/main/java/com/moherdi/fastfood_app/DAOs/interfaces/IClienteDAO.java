package com.moherdi.fastfood_app.DAOs.interfaces;

import com.moherdi.fastfood_app.entities.Cliente;
import com.moherdi.fastfood_app.entities.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IClienteDAO extends JpaRepository<Cliente, Integer> {

    Cliente findByNombres(String nombres);

    Cliente findByUser(Usuario user);
}
