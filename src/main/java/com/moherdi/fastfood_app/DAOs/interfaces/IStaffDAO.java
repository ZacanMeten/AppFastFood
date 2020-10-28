package com.moherdi.fastfood_app.DAOs.interfaces;

import com.moherdi.fastfood_app.entities.Staff;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IStaffDAO extends JpaRepository<Staff, Integer> {

    Staff findByNombres(String nombres);

    Staff findByUser(int id_user);
}
