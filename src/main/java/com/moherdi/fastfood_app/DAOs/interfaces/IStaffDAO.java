package com.moherdi.fastfood_app.DAOs.interfaces;

import java.util.List;

import com.moherdi.fastfood_app.entities.Staff;
import com.moherdi.fastfood_app.entities.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;


public interface IStaffDAO extends JpaRepository<Staff, Integer> {

    Staff findByNombres(String nombres);

    Staff findByUser(Usuario id_user);

    List<Staff> findByRol(String rol);
}
