package com.moherdi.fastfood_app.DAOs.interfaces;

import com.moherdi.fastfood_app.entities.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioRepo extends JpaRepository<Usuario, Integer> {

    Usuario findByNombre(String nombre);
}
